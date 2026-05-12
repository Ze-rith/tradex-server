package com.zerith.tradexserver.common.security.jwt

import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec
import java.util.Base64

object JwtPublicKeyLoader {

    private const val ALGORITHM = "RSA"
    private const val HEADER = "-----BEGIN PUBLIC KEY-----"
    private const val FOOTER = "-----END PUBLIC KEY-----"

    fun load(pem: String): PublicKey {
        val sanitized = pem
            .replace(HEADER, "")
            .replace(FOOTER, "")
            .replace("\\s".toRegex(), "")
        val der = Base64.getDecoder().decode(sanitized)
        val keySpec = X509EncodedKeySpec(der)
        return KeyFactory.getInstance(ALGORITHM).generatePublic(keySpec)
    }
}
