package com.zerith.tradexserver.instrument

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication(
    scanBasePackages = [
        "com.zerith.tradexserver.instrument",
        "com.zerith.tradexserver.common"
    ]
)
@ConfigurationPropertiesScan(basePackages = ["com.zerith.tradexserver.instrument"])
class InstrumentApplication

fun main(args: Array<String>) {
    runApplication<InstrumentApplication>(*args)
}
