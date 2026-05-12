plugins {
    id("org.springframework.boot")
}

dependencies {
    implementation(project(":common"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = true
    mainClass.set("com.zerith.tradexserver.registration.RegistrationApplicationKt")
    archiveBaseName.set("tradex-registration")
}

tasks.named<Jar>("jar") {
    enabled = false
}
