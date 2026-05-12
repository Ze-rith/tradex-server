package com.zerith.tradexserver.auth.interfaces.internal

import com.zerith.tradexserver.auth.application.RegisterUserUseCase
import com.zerith.tradexserver.auth.application.command.RegisterUserCommand
import com.zerith.tradexserver.auth.domain.repository.UserRepository
import com.zerith.tradexserver.auth.domain.model.UserId
import com.zerith.tradexserver.common.response.BaseResponse
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/internal/users")
class InternalUserController(
    private val registerUserUseCase: RegisterUserUseCase,
    private val userRepository: UserRepository
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun register(
        @Valid
        @RequestBody
        request: InternalRegisterUserRequest
    ): BaseResponse<InternalRegisterUserResponse> {
        val userId = registerUserUseCase.execute(
            RegisterUserCommand.of(request.email, request.password)
        )
        return BaseResponse.ok(
            InternalRegisterUserResponse(userId.asString())
        )
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable userId: UUID) {
        userRepository.deleteById(UserId.from(userId))
    }
}

class InternalRegisterUserRequest(
    @field:NotBlank
    val email: String,
    @field:NotBlank
    val password: String
)

class InternalRegisterUserResponse(
    val userId: String
)
