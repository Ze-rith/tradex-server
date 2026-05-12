package com.zerith.tradexserver.instrument.infrastructure.config

import com.zerith.tradexserver.common.security.jwt.JwtAuthenticationFilter
import com.zerith.tradexserver.common.security.jwt.JwtVerifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class InstrumentSecurityConfig {

    @Bean
    fun jwtAuthenticationFilter(jwtVerifier: JwtVerifier): JwtAuthenticationFilter {
        return JwtAuthenticationFilter(jwtVerifier)
    }

    @Bean
    fun instrumentSecurityFilterChain(
        http: HttpSecurity,
        jwtAuthenticationFilter: JwtAuthenticationFilter
    ): SecurityFilterChain {
        http
            .csrf {
                it.disable()
            }
            .cors {
            }
            .formLogin {
                it.disable()
            }
            .httpBasic {
                it.disable()
            }
            .logout {
                it.disable()
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authorizeHttpRequests {
                it.requestMatchers("/actuator/**").permitAll()
                it.anyRequest().authenticated()
            }
            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter::class.java
            )
            .exceptionHandling {
                it.authenticationEntryPoint { _, response, _ ->
                    response.status = 401
                }
                it.accessDeniedHandler { _, response, _ ->
                    response.status = 403
                }
            }

        return http.build()
    }
}
