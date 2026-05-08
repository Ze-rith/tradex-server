package com.zerith.tradexserver.instrument.domain.exception

sealed class InstrumentException(
    message: String
) : RuntimeException(message)