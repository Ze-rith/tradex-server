package com.zerith.tradexserver.registration.infrastructure.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "tradex.registration.clients")
class RegistrationClientProperties(
    val auth: ServiceEndpoint,
    val member: ServiceEndpoint
) {
    class ServiceEndpoint(
        val baseUrl: String
    )
}
