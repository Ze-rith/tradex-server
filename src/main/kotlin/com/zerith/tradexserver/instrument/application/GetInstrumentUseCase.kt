package com.zerith.tradexserver.instrument.application

import com.zerith.tradexserver.instrument.application.command.GetInstrumentCommand
import com.zerith.tradexserver.instrument.domain.exception.InstrumentNotFoundException
import com.zerith.tradexserver.instrument.domain.model.Instrument
import com.zerith.tradexserver.instrument.domain.model.Symbol
import com.zerith.tradexserver.instrument.domain.port.InstrumentCache
import com.zerith.tradexserver.instrument.domain.repository.InstrumentRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class GetInstrumentUseCase(
    private val instrumentRepository: InstrumentRepository,
    private val instrumentCache: InstrumentCache
) {

    @Transactional(readOnly = true)
    fun execute(command: GetInstrumentCommand): Instrument {
        val symbol = Symbol.of(command.symbol)

        instrumentCache.find(symbol)?.let {
            return it
        }

        val instrument = instrumentRepository.findBySymbol(symbol)
            ?: throw InstrumentNotFoundException()

        instrumentCache.put(instrument)
        return instrument
    }
}