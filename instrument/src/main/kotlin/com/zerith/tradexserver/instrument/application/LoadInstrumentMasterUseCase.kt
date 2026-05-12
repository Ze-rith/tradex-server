package com.zerith.tradexserver.instrument.application

import com.zerith.tradexserver.instrument.application.command.LoadInstrumentMasterCommand
import com.zerith.tradexserver.instrument.domain.model.Instrument
import com.zerith.tradexserver.instrument.domain.model.Market
import com.zerith.tradexserver.instrument.domain.model.Symbol
import com.zerith.tradexserver.instrument.domain.port.InstrumentCache
import com.zerith.tradexserver.instrument.domain.repository.InstrumentRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.Clock

@Component
class LoadInstrumentMasterUseCase(
    private val instrumentRepository: InstrumentRepository,
    private val instrumentCache: InstrumentCache,
    private val clock: Clock
) {

    @Transactional
    fun execute(command: LoadInstrumentMasterCommand): Int {
        val now = clock.instant()

        val instruments = command.entries.map {
            val symbol = Symbol.of(it.symbol)
            val existing = instrumentRepository.findBySymbol(symbol)

            if (existing == null) {
                Instrument.register(
                    symbol = symbol,
                    name = it.name,
                    market = Market.valueOf(it.market.uppercase()),
                    sector = it.sector,
                    lotSize = it.lotSize,
                    listedAt = it.listedAt,
                    now = now
                )
            } else {
                existing.updateMaster(
                    name = it.name,
                    market = Market.valueOf(it.market.uppercase()),
                    sector = it.sector,
                    lotSize = it.lotSize,
                    listedAt = it.listedAt,
                    now = now
                )
                existing
            }
        }

        val saved = instrumentRepository.saveAll(instruments)

        saved.forEach {
            instrumentCache.evict(it.symbol)
        }

        return saved.size
    }
}