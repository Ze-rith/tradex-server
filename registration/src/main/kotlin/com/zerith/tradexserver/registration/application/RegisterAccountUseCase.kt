package com.zerith.tradexserver.registration.application

import com.zerith.tradexserver.registration.application.command.RegisterAccountCommand
import com.zerith.tradexserver.registration.infrastructure.client.AuthServiceClient
import com.zerith.tradexserver.registration.infrastructure.client.MemberServiceClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class RegisterAccountUseCase(
    private val authServiceClient: AuthServiceClient,
    private val memberServiceClient: MemberServiceClient
) {

    private val log = LoggerFactory.getLogger(RegisterAccountUseCase::class.java)

    fun execute(command: RegisterAccountCommand): UUID {
        val userId = authServiceClient.registerUser(command.email, command.rawPassword)

        try {
            memberServiceClient.createMember(
                memberId = userId,
                name = command.name,
                birthDate = command.birthDate,
                phoneNumber = command.phoneNumber
            )
        } catch (e: Exception) {
            log.warn("member creation failed; compensating by deleting userId={}", userId, e)
            authServiceClient.deleteUser(userId)
            throw e
        }

        return userId
    }
}
