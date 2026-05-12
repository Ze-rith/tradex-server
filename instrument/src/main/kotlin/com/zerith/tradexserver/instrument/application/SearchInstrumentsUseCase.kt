package com.zerith.tradexserver.instrument.application

import com.zerith.tradexserver.instrument.application.command.SearchInstrumentsCommand
import com.zerith.tradexserver.instrument.domain.exception.InvalidSearchQueryException
import com.zerith.tradexserver.instrument.domain.model.Instrument
import com.zerith.tradexserver.instrument.domain.model.Market
import com.zerith.tradexserver.instrument.domain.repository.InstrumentRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class SearchInstrumentsUseCase(
    private val instrumentRepository: InstrumentRepository
) {

    @Transactional(readOnly = true)
    fun execute(command: SearchInstrumentsCommand): List<Instrument> {
        val query = command.query.trim()
        if (query.length !in MIN_QUERY_LENGTH..MAX_QUERY_LENGTH) {
            throw InvalidSearchQueryException()
        }

        val market = command.market?.let {
            runCatching {
                Market.valueOf(it.uppercase())
            }.getOrElse {
                throw InvalidSearchQueryException()
            }
        }

        val size = command.size?.coerceIn(MIN_SIZE, MAX_SIZE) ?: DEFAULT_SIZE

        return instrumentRepository.search(
            query = query,
            market = market,
            size = size
        )
    }

    companion object {
        private const val MIN_QUERY_LENGTH = 1
        private const val MAX_QUERY_LENGTH = 20
        private const val MIN_SIZE = 1
        private const val MAX_SIZE = 50
        private const val DEFAULT_SIZE = 10
    }
}