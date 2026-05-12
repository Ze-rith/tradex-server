package com.zerith.tradexserver.common.security.jwt

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnProperty(prefix = "tradex.auth.jwt", name = ["public-key-pem"])
@EnableConfigurationProperties(JwtVerificationProperties::class)
class JwtVerificationConfig {

    @Bean
    fun jwtVerifier(properties: JwtVerificationProperties): JwtVerifier {
        val publicKey = JwtPublicKeyLoader.load(properties.publicKeyPem)
        return JwtVerifier(publicKey, properties.issuer)
    }
}
