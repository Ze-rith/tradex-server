package com.zerith.tradexserver.instrument.data

import com.zerith.tradexserver.instrument.domain.model.Instrument
import java.time.LocalDate

data class InstrumentResponse(
    val symbol: String,
    val name: String,
    val market: String,
    val sector: String?,
    val status: String,
    val lotSize: Int,
    val listedAt: LocalDate,
    val delistedAt: LocalDate?
) {
    companion object {
        fun of(instrument: Instrument): InstrumentResponse {
            return InstrumentResponse(
                symbol = instrument.symbol.value,
                name = instrument.name,
                market = instrument.market.name,
                sector = instrument.sector,
                status = instrument.status.name,
                lotSize = instrument.lotSize,
                listedAt = instrument.listedAt,
                delistedAt = instrument.delistedAt
            )
        }
    }
}