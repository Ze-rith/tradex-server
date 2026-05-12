package com.zerith.tradexserver.member

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication(
    scanBasePackages = [
        "com.zerith.tradexserver.member",
        "com.zerith.tradexserver.common"
    ]
)
@ConfigurationPropertiesScan(basePackages = ["com.zerith.tradexserver.member"])
class MemberApplication

fun main(args: Array<String>) {
    runApplication<MemberApplication>(*args)
}
