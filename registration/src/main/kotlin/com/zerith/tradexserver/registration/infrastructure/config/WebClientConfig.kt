package com.zerith.tradexserver.registration.infrastructure.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig {

    @Bean(name = ["authWebClient"])
    fun authWebClient(properties: RegistrationClientProperties): WebClient {
        return WebClient.builder()
            .baseUrl(properties.auth.baseUrl)
            .build()
    }

    @Bean(name = ["memberWebClient"])
    fun memberWebClient(properties: RegistrationClientProperties): WebClient {
        return WebClient.builder()
            .baseUrl(properties.member.baseUrl)
            .build()
    }
}
