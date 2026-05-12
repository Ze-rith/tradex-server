package com.zerith.tradexserver.common.security.jwt

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.nio.file.Files
import java.nio.file.Paths

@Configuration
@ConditionalOnProperty(prefix = "tradex.auth.jwt", name = ["public-key-path"])
@EnableConfigurationProperties(JwtVerificationProperties::class)
class JwtVerificationConfig {

    @Bean
    fun jwtVerifier(properties: JwtVerificationProperties): JwtVerifier {
        val pem = Files.readString(Paths.get(properties.publicKeyPath))
        val publicKey = JwtPublicKeyLoader.load(pem)
        return JwtVerifier(publicKey, properties.issuer)
    }
}
