package com.zerith.tradexserver.instrument.application.command

import java.time.LocalDate

class LoadInstrumentMasterCommand private constructor(
    val entries: List<Entry>
) {

    class Entry private constructor(
        val symbol: String,
        val name: String,
        val market: String,
        val sector: String?,
        val lotSize: Int,
        val listedAt: LocalDate
    ) {
        companion object {
            fun of(
                symbol: String,
                name: String,
                market: String,
                sector: String?,
                lotSize: Int,
                listedAt: LocalDate
            ): Entry {
                return Entry(symbol, name, market, sector, lotSize, listedAt)
            }
        }
    }

    companion object {
        fun of(entries: List<Entry>): LoadInstrumentMasterCommand {
            return LoadInstrumentMasterCommand(entries)
        }
    }
}