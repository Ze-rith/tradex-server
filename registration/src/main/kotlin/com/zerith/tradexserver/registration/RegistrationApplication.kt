package com.zerith.tradexserver.registration

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication(
    scanBasePackages = [
        "com.zerith.tradexserver.registration",
        "com.zerith.tradexserver.common"
    ]
)
@ConfigurationPropertiesScan(basePackages = ["com.zerith.tradexserver.registration"])
class RegistrationApplication

fun main(args: Array<String>) {
    runApplication<RegistrationApplication>(*args)
}
