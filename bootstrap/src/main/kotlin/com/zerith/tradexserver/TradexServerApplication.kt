package com.zerith.tradexserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType

@SpringBootApplication
@ComponentScan(
    excludeFilters = [
        ComponentScan.Filter(
            type = FilterType.REGEX,
            pattern = [
                "com\\.zerith\\.tradexserver\\.(auth|common|instrument|member|registration)\\..*Application"
            ]
        )
    ]
)
class TradexServerApplication

fun main(args: Array<String>) {
    runApplication<TradexServerApplication>(*args)
}
