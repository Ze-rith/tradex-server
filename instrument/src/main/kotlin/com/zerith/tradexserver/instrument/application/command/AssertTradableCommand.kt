package com.zerith.tradexserver.instrument.application.command

class AssertTradableCommand private constructor(
    val symbol: String
) {
    companion object {
        fun of(symbol: String): AssertTradableCommand {
            return AssertTradableCommand(symbol)
        }
    }
}