package com.zerith.tradexserver.registration.interfaces.exception

import com.zerith.tradexserver.common.response.BaseResponse
import com.zerith.tradexserver.registration.domain.exception.EmailAlreadyExistsException
import com.zerith.tradexserver.registration.domain.exception.InvalidRegistrationRequestException
import com.zerith.tradexserver.registration.domain.exception.PhoneNumberAlreadyExistsException
import com.zerith.tradexserver.registration.domain.exception.UpstreamServiceUnavailableException
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class RegistrationExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException::class)
    fun handleEmailExists(e: EmailAlreadyExistsException): ResponseEntity<BaseResponse<Unit>> {
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(BaseResponse.error("REGISTRATION_EMAIL_EXISTS", "email already exists"))
    }

    @ExceptionHandler(PhoneNumberAlreadyExistsException::class)
    fun handlePhoneExists(e: PhoneNumberAlreadyExistsException): ResponseEntity<BaseResponse<Unit>> {
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(BaseResponse.error("REGISTRATION_PHONE_EXISTS", "phone number already exists"))
    }

    @ExceptionHandler(InvalidRegistrationRequestException::class)
    fun handleInvalid(e: InvalidRegistrationRequestException): ResponseEntity<BaseResponse<Unit>> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(BaseResponse.error("REGISTRATION_INVALID_REQUEST", "invalid request"))
    }

    @ExceptionHandler(UpstreamServiceUnavailableException::class)
    fun handleUpstream(e: UpstreamServiceUnavailableException): ResponseEntity<BaseResponse<Unit>> {
        return ResponseEntity
            .status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(BaseResponse.error("UPSTREAM_UNAVAILABLE", "upstream service unavailable"))
    }
}
