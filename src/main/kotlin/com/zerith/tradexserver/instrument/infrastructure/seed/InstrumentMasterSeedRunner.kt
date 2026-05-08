package com.zerith.tradexserver.instrument.infrastructure.seed

import com.fasterxml.jackson.databind.ObjectMapper
import com.zerith.tradexserver.instrument.application.LoadInstrumentMasterUseCase
import com.zerith.tradexserver.instrument.application.command.LoadInstrumentMasterCommand
import com.zerith.tradexserver.instrument.domain.repository.InstrumentRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
@ConditionalOnProperty(
    prefix = "tradex.instrument.seed",
    name = ["enabled"],
    havingValue = "true"
)
@EnableConfigurationProperties(InstrumentSeedProperties::class)
class InstrumentMasterSeedRunner(
    private val loadInstrumentMasterUseCase: LoadInstrumentMasterUseCase,
    private val instrumentRepository: InstrumentRepository,
    private val objectMapper: ObjectMapper,
    private val seedProperties: InstrumentSeedProperties
) : ApplicationRunner {

    private val log = LoggerFactory.getLogger(InstrumentMasterSeedRunner::class.java)

    override fun run(args: ApplicationArguments) {
        if (seedProperties.skipIfNotEmpty && instrumentRepository.count() > 0) {
            log.info("instrument seed skipped: table not empty")
            return
        }

        val resource = ClassPathResource(seedProperties.path)
        if (!resource.exists()) {
            log.warn("instrument seed not found path={}", seedProperties.path)
            return
        }

        val payloads = resource.inputStream.use {
            objectMapper.readValue(it, Array<SeedPayload>::class.java).toList()
        }

        val entries = payloads.map {
            LoadInstrumentMasterCommand.Entry.of(
                symbol = it.symbol,
                name = it.name,
                market = it.market,
                sector = it.sector,
                lotSize = it.lotSize,
                listedAt = LocalDate.parse(it.listedAt)
            )
        }

        val count = loadInstrumentMasterUseCase.execute(
            LoadInstrumentMasterCommand.of(entries)
        )

        log.info("instrument master loaded count={}", count)
    }

    data class SeedPayload(
        val symbol: String = "",
        val name: String = "",
        val market: String = "",
        val sector: String? = null,
        val lotSize: Int = 1,
        val listedAt: String = ""
    )
}