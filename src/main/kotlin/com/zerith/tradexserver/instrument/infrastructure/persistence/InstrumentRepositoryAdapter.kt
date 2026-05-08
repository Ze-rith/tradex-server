package com.zerith.tradexserver.instrument.infrastructure.persistence

import com.zerith.tradexserver.instrument.domain.model.Instrument
import com.zerith.tradexserver.instrument.domain.model.Market
import com.zerith.tradexserver.instrument.domain.model.Symbol
import com.zerith.tradexserver.instrument.domain.repository.InstrumentRepository
import org.springframework.stereotype.Component

@Component
class InstrumentRepositoryAdapter(
    private val instrumentJpaRepository: InstrumentJpaRepository
) : InstrumentRepository {

    override fun save(instrument: Instrument): Instrument {
        val existing = instrumentJpaRepository.findById(instrument.symbol.value).orElse(null)
        val saved = if (existing == null) {
            instrumentJpaRepository.save(InstrumentMapper.toEntity(instrument))
        } else {
            InstrumentMapper.applyTo(existing, instrument)
            instrumentJpaRepository.save(existing)
        }
        return InstrumentMapper.toDomain(saved)
    }

    override fun saveAll(instruments: List<Instrument>): List<Instrument> {
        return instruments.map {
            save(it)
        }
    }

    override fun findBySymbol(symbol: Symbol): Instrument? {
        val entity = instrumentJpaRepository.findById(symbol.value).orElse(null) ?: return null
        return InstrumentMapper.toDomain(entity)
    }

    override fun existsBySymbol(symbol: Symbol): Boolean {
        return instrumentJpaRepository.existsById(symbol.value)
    }

    override fun count(): Long {
        return instrumentJpaRepository.count()
    }

    override fun search(
        query: String,
        market: Market?,
        size: Int
    ): List<Instrument> {
        val trimmed = query.trim()
        val escaped = escapeLike(trimmed)

        val symbolPrefix = if (trimmed.matches(SYMBOL_PREFIX_PATTERN)) {
            "$escaped%"
        } else {
            NO_MATCH
        }
        val nameLike = "%$escaped%"
        val nameStartsWith = "$escaped%"
        val marketName = market?.name

        return instrumentJpaRepository.search(
            symbolPrefix = symbolPrefix,
            nameLike = nameLike,
            nameStartsWith = nameStartsWith,
            market = marketName,
            size = size
        ).map {
            InstrumentMapper.toDomain(it)
        }
    }

    private fun escapeLike(input: String): String {
        return input
            .replace("\\", "\\\\")
            .replace("%", "\\%")
            .replace("_", "\\_")
    }

    companion object {
        private val SYMBOL_PREFIX_PATTERN = Regex("^[0-9]{1,6}$")
        private const val NO_MATCH = "__NO_MATCH__%"
    }
}