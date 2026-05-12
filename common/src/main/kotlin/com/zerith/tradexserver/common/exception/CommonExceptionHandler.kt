package com.zerith.tradexserver.common.exception

import com.zerith.tradexserver.common.response.BaseResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class CommonExceptionHandler {

    private val log = LoggerFactory.getLogger(CommonExceptionHandler::class.java)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(e: MethodArgumentNotValidException): ResponseEntity<BaseResponse<Unit>> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(BaseResponse.error("VALIDATION_FAILED", "invalid request"))
    }

    @ExceptionHandler(Exception::class)
    fun handleUnexpected(e: Exception): ResponseEntity<BaseResponse<Unit>> {
        log.error("unexpected error", e)
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(BaseResponse.error("INTERNAL_ERROR", "internal error"))
    }
}
