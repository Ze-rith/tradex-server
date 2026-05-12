package com.zerith.tradexserver.registration.domain.exception

sealed class RegistrationException(message: String) : RuntimeException(message)

class EmailAlreadyExistsException : RegistrationException("email already exists")

class PhoneNumberAlreadyExistsException : RegistrationException("phone number already exists")

class InvalidRegistrationRequestException(message: String) : RegistrationException(message)

class UpstreamServiceUnavailableException(service: String) :
    RegistrationException("upstream service unavailable: $service")
