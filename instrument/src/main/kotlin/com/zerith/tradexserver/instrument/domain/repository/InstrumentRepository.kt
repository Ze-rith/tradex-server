package com.zerith.tradexserver.instrument.domain.repository

import com.zerith.tradexserver.instrument.domain.model.Instrument
import com.zerith.tradexserver.instrument.domain.model.Market
import com.zerith.tradexserver.instrument.domain.model.Symbol

interface InstrumentRepository {

    fun save(instrument: Instrument): Instrument

    fun saveAll(instruments: List<Instrument>): List<Instrument>

    fun findBySymbol(symbol: Symbol): Instrument?

    fun existsBySymbol(symbol: Symbol): Boolean

    fun count(): Long

    fun search(
        query: String,
        market: Market?,
        size: Int
    ): List<Instrument>
}