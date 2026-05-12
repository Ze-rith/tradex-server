package com.zerith.tradexserver.instrument.domain.model

enum class InstrumentStatus {
    NORMAL,
    SUSPENDED,
    MANAGED,
    WARNING,
    DELISTED;

    fun isTradable(): Boolean {
        return this == NORMAL
    }
}