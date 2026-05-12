package com.zerith.tradexserver.member.interfaces.exception

import com.zerith.tradexserver.common.response.BaseResponse
import com.zerith.tradexserver.member.domain.exception.MemberException
import com.zerith.tradexserver.member.domain.exception.PhoneNumberAlreadyExistsException
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class MemberExceptionHandler {

    @ExceptionHandler(PhoneNumberAlreadyExistsException::class)
    fun handlePhoneExists(e: PhoneNumberAlreadyExistsException): ResponseEntity<BaseResponse<Unit>> {
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(BaseResponse.error("INVALID_REQUEST", "invalid request"))
    }

    @ExceptionHandler(MemberException::class)
    fun handleMember(e: MemberException): ResponseEntity<BaseResponse<Unit>> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(BaseResponse.error("INVALID_REQUEST", "invalid request"))
    }
}
