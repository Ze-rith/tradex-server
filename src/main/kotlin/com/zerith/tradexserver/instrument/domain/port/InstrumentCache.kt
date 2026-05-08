package com.zerith.tradexserver.instrument.domain.port

import com.zerith.tradexserver.instrument.domain.model.Instrument
import com.zerith.tradexserver.instrument.domain.model.Symbol

interface InstrumentCache {

    fun find(symbol: Symbol): Instrument?

    fun put(instrument: Instrument)

    fun evict(symbol: Symbol)
}