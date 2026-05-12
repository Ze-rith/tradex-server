package com.zerith.tradexserver.common.security.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter(
    private val jwtVerifier: JwtVerifier
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = extractBearer(request)

        if (token != null) {
            try {
                val principal = jwtVerifier.verifyAccess(token)
                SecurityContextHolder.getContext().authentication = toAuthentication(principal)
            } catch (e: JwtInvalidException) {
                SecurityContextHolder.clearContext()
                response.status = HttpServletResponse.SC_UNAUTHORIZED
                return
            }
        }

        filterChain.doFilter(request, response)
    }

    private fun extractBearer(request: HttpServletRequest): String? {
        val header = request.getHeader(HttpHeaders.AUTHORIZATION) ?: return null
        if (!header.startsWith(BEARER_PREFIX)) {
            return null
        }
        return header.substring(BEARER_PREFIX.length).trim().ifBlank {
            null
        }
    }

    private fun toAuthentication(principal: AuthenticatedPrincipal): UsernamePasswordAuthenticationToken {
        val authorities = listOf(SimpleGrantedAuthority("ROLE_${principal.role}"))
        return UsernamePasswordAuthenticationToken(principal.userId, null, authorities)
    }

    companion object {
        private const val BEARER_PREFIX = "Bearer "
    }
}
