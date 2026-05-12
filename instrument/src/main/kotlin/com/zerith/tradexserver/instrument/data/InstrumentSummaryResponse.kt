package com.zerith.tradexserver.instrument.data

import com.zerith.tradexserver.instrument.domain.model.Instrument

data class InstrumentSummaryResponse(
    val symbol: String,
    val name: String,
    val market: String,
    val sector: String?,
    val status: String
) {
    companion object {
        fun of(instrument: Instrument): InstrumentSummaryResponse {
            return InstrumentSummaryResponse(
                symbol = instrument.symbol.value,
                name = instrument.name,
                market = instrument.market.name,
                sector = instrument.sector,
                status = instrument.status.name
            )
        }
    }
}