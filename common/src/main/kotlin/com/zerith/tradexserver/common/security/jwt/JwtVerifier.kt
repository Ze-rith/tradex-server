package com.zerith.tradexserver.common.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import java.security.PublicKey

class JwtVerifier(
    private val publicKey: PublicKey,
    private val issuer: String
) {

    fun verifyAccess(rawToken: String): AuthenticatedPrincipal {
        val claims = parse(rawToken)
        val type = claims[CLAIM_TYPE] as? String
        if (type != ACCESS_TYPE) {
            throw JwtInvalidException()
        }
        val subject = claims.subject ?: throw JwtInvalidException()
        val role = claims[CLAIM_ROLE] as? String ?: throw JwtInvalidException()
        return AuthenticatedPrincipal(subject, role)
    }

    private fun parse(rawToken: String): Claims {
        try {
            return Jwts.parser()
                .verifyWith(publicKey)
                .requireIssuer(issuer)
                .build()
                .parseSignedClaims(rawToken)
                .payload
        } catch (e: JwtException) {
            throw JwtInvalidException()
        } catch (e: IllegalArgumentException) {
            throw JwtInvalidException()
        }
    }

    companion object {
        private const val CLAIM_ROLE = "role"
        private const val CLAIM_TYPE = "typ"
        private const val ACCESS_TYPE = "access"
    }
}

class JwtInvalidException : RuntimeException()
