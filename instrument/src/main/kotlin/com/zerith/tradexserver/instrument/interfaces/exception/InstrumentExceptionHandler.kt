package com.zerith.tradexserver.instrument.interfaces.exception

import com.zerith.tradexserver.common.response.BaseResponse
import com.zerith.tradexserver.instrument.domain.exception.InstrumentException
import com.zerith.tradexserver.instrument.domain.exception.InstrumentNotFoundException
import com.zerith.tradexserver.instrument.domain.exception.InstrumentNotTradableException
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class InstrumentExceptionHandler {

    @ExceptionHandler(InstrumentNotFoundException::class)
    fun handleInstrumentNotFound(e: InstrumentNotFoundException): ResponseEntity<BaseResponse<Unit>> {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(BaseResponse.error("INSTRUMENT_NOT_FOUND", "not found"))
    }

    @ExceptionHandler(InstrumentNotTradableException::class)
    fun handleInstrumentNotTradable(e: InstrumentNotTradableException): ResponseEntity<BaseResponse<Unit>> {
        return ResponseEntity
            .status(HttpStatus.UNPROCESSABLE_ENTITY)
            .body(BaseResponse.error("INSTRUMENT_NOT_TRADABLE", "invalid request"))
    }

    @ExceptionHandler(InstrumentException::class)
    fun handleInstrument(e: InstrumentException): ResponseEntity<BaseResponse<Unit>> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(BaseResponse.error("INSTRUMENT_INVALID_REQUEST", "invalid request"))
    }
}
