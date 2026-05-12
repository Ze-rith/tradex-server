package com.zerith.tradexserver.registration.infrastructure.client

import com.zerith.tradexserver.registration.domain.exception.InvalidRegistrationRequestException
import com.zerith.tradexserver.registration.domain.exception.PhoneNumberAlreadyExistsException
import com.zerith.tradexserver.registration.domain.exception.UpstreamServiceUnavailableException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientRequestException
import org.springframework.web.reactive.function.client.WebClientResponseException
import java.time.LocalDate
import java.util.UUID

@Component
class MemberServiceClient(
    @Qualifier("memberWebClient")
    private val memberWebClient: WebClient
) {

    private val log = LoggerFactory.getLogger(MemberServiceClient::class.java)

    fun createMember(
        memberId: UUID,
        name: String,
        birthDate: LocalDate,
        phoneNumber: String
    ) {
        try {
            memberWebClient.post()
                .uri("/internal/members")
                .bodyValue(CreateMemberBody(memberId, name, birthDate, phoneNumber))
                .retrieve()
                .toBodilessEntity()
                .block()
        } catch (e: WebClientResponseException) {
            when (e.statusCode) {
                HttpStatus.CONFLICT -> throw PhoneNumberAlreadyExistsException()
                HttpStatus.BAD_REQUEST -> throw InvalidRegistrationRequestException("invalid member data")
                else -> {
                    log.error("member.createMember failed: status={}", e.statusCode, e)
                    throw UpstreamServiceUnavailableException("member")
                }
            }
        } catch (e: WebClientRequestException) {
            log.error("member.createMember network failure", e)
            throw UpstreamServiceUnavailableException("member")
        }
    }

    data class CreateMemberBody(
        val memberId: UUID,
        val name: String,
        val birthDate: LocalDate,
        val phoneNumber: String
    )
}
