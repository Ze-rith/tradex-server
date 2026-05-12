package com.zerith.tradexserver.auth.interfaces.exception

import com.zerith.tradexserver.auth.domain.exception.AccountLockedException
import com.zerith.tradexserver.auth.domain.exception.AuthException
import com.zerith.tradexserver.auth.domain.exception.EmailAlreadyExistsException
import com.zerith.tradexserver.auth.domain.exception.InvalidCredentialException
import com.zerith.tradexserver.auth.domain.exception.InvalidEmailException
import com.zerith.tradexserver.auth.domain.exception.TokenInvalidException
import com.zerith.tradexserver.auth.domain.exception.WeakPasswordException
import com.zerith.tradexserver.common.response.BaseResponse
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class AuthExceptionHandler {

    @ExceptionHandler(InvalidCredentialException::class)
    fun handleInvalidCredential(e: InvalidCredentialException): ResponseEntity<BaseResponse<Unit>> {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(BaseResponse.error("AUTH_INVALID_CREDENTIAL", "invalid credential"))
    }

    @ExceptionHandler(AccountLockedException::class)
    fun handleAccountLocked(e: AccountLockedException): ResponseEntity<BaseResponse<Unit>> {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(BaseResponse.error("AUTH_INVALID_CREDENTIAL", "invalid credential"))
    }

    @ExceptionHandler(TokenInvalidException::class)
    fun handleTokenInvalid(e: TokenInvalidException): ResponseEntity<BaseResponse<Unit>> {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(BaseResponse.error("AUTH_INVALID_TOKEN", "invalid token"))
    }

    @ExceptionHandler(InvalidEmailException::class)
    fun handleInvalidEmail(e: InvalidEmailException): ResponseEntity<BaseResponse<Unit>> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(BaseResponse.error("AUTH_INVALID_REQUEST", "invalid request"))
    }

    @ExceptionHandler(WeakPasswordException::class)
    fun handleWeakPassword(e: WeakPasswordException): ResponseEntity<BaseResponse<Unit>> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(BaseResponse.error("AUTH_INVALID_REQUEST", "invalid request"))
    }

    @ExceptionHandler(EmailAlreadyExistsException::class)
    fun handleEmailExists(e: EmailAlreadyExistsException): ResponseEntity<BaseResponse<Unit>> {
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(BaseResponse.error("AUTH_INVALID_REQUEST", "invalid request"))
    }

    @ExceptionHandler(AuthException::class)
    fun handleAuth(e: AuthException): ResponseEntity<BaseResponse<Unit>> {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(BaseResponse.error("AUTH_FAILED", "invalid request"))
    }
}
