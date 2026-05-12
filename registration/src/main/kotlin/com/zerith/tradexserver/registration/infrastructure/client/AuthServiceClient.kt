package com.zerith.tradexserver.registration.infrastructure.client

import com.zerith.tradexserver.common.response.BaseResponse
import com.zerith.tradexserver.registration.domain.exception.EmailAlreadyExistsException
import com.zerith.tradexserver.registration.domain.exception.InvalidRegistrationRequestException
import com.zerith.tradexserver.registration.domain.exception.UpstreamServiceUnavailableException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientRequestException
import org.springframework.web.reactive.function.client.WebClientResponseException
import java.util.UUID

@Component
class AuthServiceClient(
    @Qualifier("authWebClient")
    private val authWebClient: WebClient
) {

    private val log = LoggerFactory.getLogger(AuthServiceClient::class.java)

    fun registerUser(email: String, password: String): UUID {
        try {
            val body = authWebClient.post()
                .uri("/internal/users")
                .bodyValue(RegisterUserBody(email, password))
                .retrieve()
                .bodyToMono(REGISTER_RESPONSE_TYPE)
                .block()
                ?: throw UpstreamServiceUnavailableException("auth")

            val data = body.data ?: throw UpstreamServiceUnavailableException("auth")
            return UUID.fromString(data.userId)
        } catch (e: WebClientResponseException) {
            when (e.statusCode) {
                HttpStatus.CONFLICT -> throw EmailAlreadyExistsException()
                HttpStatus.BAD_REQUEST -> throw InvalidRegistrationRequestException("invalid email or password")
                else -> {
                    log.error("auth.registerUser failed: status={}", e.statusCode, e)
                    throw UpstreamServiceUnavailableException("auth")
                }
            }
        } catch (e: WebClientRequestException) {
            log.error("auth.registerUser network failure", e)
            throw UpstreamServiceUnavailableException("auth")
        }
    }

    fun deleteUser(userId: UUID) {
        try {
            authWebClient.delete()
                .uri("/internal/users/{userId}", userId)
                .retrieve()
                .toBodilessEntity()
                .block()
        } catch (e: Exception) {
            log.error("auth.deleteUser compensation failed for userId={}", userId, e)
        }
    }

    data class RegisterUserBody(
        val email: String,
        val password: String
    )

    data class RegisterUserResponseData(
        val userId: String
    )

    companion object {
        private val REGISTER_RESPONSE_TYPE =
            object : ParameterizedTypeReference<BaseResponse<RegisterUserResponseData>>() {}
    }
}
