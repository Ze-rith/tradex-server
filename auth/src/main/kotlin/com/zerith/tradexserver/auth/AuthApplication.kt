package com.zerith.tradexserver.auth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication(
    scanBasePackages = [
        "com.zerith.tradexserver.auth",
        "com.zerith.tradexserver.common"
    ]
)
@ConfigurationPropertiesScan(basePackages = ["com.zerith.tradexserver.auth"])
class AuthApplication

fun main(args: Array<String>) {
    runApplication<AuthApplication>(*args)
}
