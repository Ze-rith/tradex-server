package com.zerith.tradexserver.common.security.jwt

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "tradex.auth.jwt")
class JwtVerificationProperties(
    val issuer: String,
    val publicKeyPath: String
)
