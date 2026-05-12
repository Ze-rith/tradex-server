package com.zerith.tradexserver.instrument.infrastructure.seed

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "tradex.instrument.seed")
class InstrumentSeedProperties(
    val enabled: Boolean = false,
    val path: String = "instruments/instruments-mock.json",
    val skipIfNotEmpty: Boolean = true
)