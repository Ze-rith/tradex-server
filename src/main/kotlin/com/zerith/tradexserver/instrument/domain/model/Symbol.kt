package com.zerith.tradexserver.instrument.domain.model

import com.zerith.tradexserver.instrument.domain.exception.InvalidSymbolException

class Symbol private constructor(
    val value: String
) {
    companion object {
        private val PATTERN = Regex("^[0-9]{6}$")

        fun of(raw: String): Symbol {
            val normalized = raw.trim()
            if (!PATTERN.matches(normalized)) {
                throw InvalidSymbolException()
            }
            return Symbol(normalized)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Symbol) return false
        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return value
    }
}