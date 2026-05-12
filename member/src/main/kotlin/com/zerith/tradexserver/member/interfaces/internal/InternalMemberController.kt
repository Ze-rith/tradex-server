package com.zerith.tradexserver.member.interfaces.internal

import com.zerith.tradexserver.common.response.BaseResponse
import com.zerith.tradexserver.member.application.CreateMemberUseCase
import com.zerith.tradexserver.member.application.command.CreateMemberCommand
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.util.UUID

@RestController
@RequestMapping("/internal/members")
class InternalMemberController(
    private val createMemberUseCase: CreateMemberUseCase
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @Valid
        @RequestBody
        request: InternalCreateMemberRequest
    ): BaseResponse<InternalCreateMemberResponse> {
        val memberId = createMemberUseCase.execute(
            CreateMemberCommand.of(
                memberId = request.memberId,
                name = request.name,
                birthDate = request.birthDate,
                phoneNumber = request.phoneNumber
            )
        )
        return BaseResponse.ok(
            InternalCreateMemberResponse(memberId.value.toString())
        )
    }
}

class InternalCreateMemberRequest(
    @field:NotNull
    val memberId: UUID,
    @field:NotBlank
    val name: String,
    @field:NotNull
    val birthDate: LocalDate,
    @field:NotBlank
    val phoneNumber: String
)

class InternalCreateMemberResponse(
    val memberId: String
)
