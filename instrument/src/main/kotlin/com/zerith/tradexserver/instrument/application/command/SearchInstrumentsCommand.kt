package com.zerith.tradexserver.instrument.application.command

class SearchInstrumentsCommand private constructor(
    val query: String,
    val market: String?,
    val size: Int?
) {
    companion object {
        fun of(
            query: String,
            market: String?,
            size: Int?
        ): SearchInstrumentsCommand {
            return SearchInstrumentsCommand(query, market, size)
        }
    }
}