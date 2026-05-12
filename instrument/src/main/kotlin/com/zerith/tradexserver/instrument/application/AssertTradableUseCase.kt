package com.zerith.tradexserver.instrument.application

import com.zerith.tradexserver.instrument.application.command.AssertTradableCommand
import com.zerith.tradexserver.instrument.domain.exception.InstrumentNotFoundException
import com.zerith.tradexserver.instrument.domain.exception.InstrumentNotTradableException
import com.zerith.tradexserver.instrument.domain.model.Instrument
import com.zerith.tradexserver.instrument.domain.model.Symbol
import com.zerith.tradexserver.instrument.domain.port.InstrumentCache
import com.zerith.tradexserver.instrument.domain.repository.InstrumentRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class AssertTradableUseCase(
    private val instrumentRepository: InstrumentRepository,
    private val instrumentCache: InstrumentCache
) {

    @Transactional(readOnly = true)
    fun execute(command: AssertTradableCommand): Instrument {
        val symbol = Symbol.of(command.symbol)

        val instrument = instrumentCache.find(symbol)
            ?: instrumentRepository.findBySymbol(symbol)
            ?: throw InstrumentNotFoundException()

        if (!instrument.isTradable()) {
            throw InstrumentNotTradableException()
        }

        instrumentCache.put(instrument)
        return instrument
    }
}