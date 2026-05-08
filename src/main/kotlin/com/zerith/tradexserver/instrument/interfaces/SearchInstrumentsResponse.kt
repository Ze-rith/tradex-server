package com.zerith.tradexserver.instrument.interfaces

import com.zerith.tradexserver.instrument.data.InstrumentSummaryResponse

data class SearchInstrumentsResponse(
    val items: List<InstrumentSummaryResponse>
) {
    companion object {
        fun of(items: List<InstrumentSummaryResponse>): SearchInstrumentsResponse {
            return SearchInstrumentsResponse(items)
        }
    }
}