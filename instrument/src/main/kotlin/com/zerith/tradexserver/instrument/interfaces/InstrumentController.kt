package com.zerith.tradexserver.instrument.interfaces

import com.zerith.tradexserver.common.response.BaseResponse
import com.zerith.tradexserver.instrument.application.GetInstrumentUseCase
import com.zerith.tradexserver.instrument.application.SearchInstrumentsUseCase
import com.zerith.tradexserver.instrument.application.command.GetInstrumentCommand
import com.zerith.tradexserver.instrument.application.command.SearchInstrumentsCommand
import com.zerith.tradexserver.instrument.data.InstrumentResponse
import com.zerith.tradexserver.instrument.data.InstrumentSummaryResponse
import com.zerith.tradexserver.instrument.data.SearchInstrumentsResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/instruments")
class InstrumentController(
    private val getInstrumentUseCase: GetInstrumentUseCase,
    private val searchInstrumentsUseCase: SearchInstrumentsUseCase
) {

    @GetMapping("/search")
    fun search(
        @RequestParam("q")
        query: String,
        @RequestParam("market", required = false)
        market: String?,
        @RequestParam("size", required = false)
        size: Int?
    ): BaseResponse<SearchInstrumentsResponse> {
        val instruments = searchInstrumentsUseCase.execute(
            SearchInstrumentsCommand.of(
                query = query,
                market = market,
                size = size
            )
        )
        val items = instruments.map {
            InstrumentSummaryResponse.of(it)
        }
        return BaseResponse.ok(
            SearchInstrumentsResponse.of(items)
        )
    }

    @GetMapping("/{symbol}")
    fun get(
        @PathVariable
        symbol: String
    ): BaseResponse<InstrumentResponse> {
        val instrument = getInstrumentUseCase.execute(
            GetInstrumentCommand.of(symbol)
        )
        return BaseResponse.ok(
            InstrumentResponse.of(instrument)
        )
    }
}