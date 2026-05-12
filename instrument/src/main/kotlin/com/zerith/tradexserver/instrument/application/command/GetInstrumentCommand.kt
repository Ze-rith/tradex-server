package com.zerith.tradexserver.instrument.application.command

class GetInstrumentCommand private constructor(
    val symbol: String
) {
    companion object {
        fun of(symbol: String): GetInstrumentCommand {
            return GetInstrumentCommand(symbol)
        }
    }
}