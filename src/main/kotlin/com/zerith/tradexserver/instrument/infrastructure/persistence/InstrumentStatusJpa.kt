package com.zerith.tradexserver.instrument.infrastructure.persistence

enum class InstrumentStatusJpa {
    NORMAL,
    SUSPENDED,
    MANAGED,
    WARNING,
    DELISTED
}