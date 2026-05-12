package com.zerith.tradexserver.instrument.infrastructure.cache

import com.fasterxml.jackson.databind.ObjectMapper
import com.zerith.tradexserver.instrument.domain.model.Instrument
import com.zerith.tradexserver.instrument.domain.model.InstrumentStatus
import com.zerith.tradexserver.instrument.domain.model.Market
import com.zerith.tradexserver.instrument.domain.model.Symbol
import com.zerith.tradexserver.instrument.domain.port.InstrumentCache
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.Instant
import java.time.LocalDate

@Component
class InstrumentRedisCache(
    private val redisTemplate: StringRedisTemplate,
    private val objectMapper: ObjectMapper
) : InstrumentCache {

    override fun find(symbol: Symbol): Instrument? {
        val raw = runCatching {
            redisTemplate.opsForValue().get(cacheKey(symbol))
        }.getOrNull() ?: return null

        return runCatching {
            val payload = objectMapper.readValue(raw, CachePayload::class.java)
            payload.toDomain()
        }.getOrNull()
    }

    override fun put(instrument: Instrument) {
        runCatching {
            val payload = CachePayload.from(instrument)
            val json = objectMapper.writeValueAsString(payload)
            redisTemplate.opsForValue().set(cacheKey(instrument.symbol), json, TTL)
        }
    }

    override fun evict(symbol: Symbol) {
        runCatching {
            redisTemplate.delete(cacheKey(symbol))
        }
    }

    private fun cacheKey(symbol: Symbol): String {
        return "$KEY_PREFIX${symbol.value}"
    }

    data class CachePayload(
        val symbol: String,
        val name: String,
        val market: String,
        val sector: String?,
        val status: String,
        val lotSize: Int,
        val listedAt: String,
        val delistedAt: String?,
        val createdAt: String,
        val updatedAt: String
    ) {
        fun toDomain(): Instrument {
            return Instrument.restore(
                symbol = Symbol.of(symbol),
                name = name,
                market = Market.valueOf(market),
                sector = sector,
                status = InstrumentStatus.valueOf(status),
                lotSize = lotSize,
                listedAt = LocalDate.parse(listedAt),
                delistedAt = delistedAt?.let {
                    LocalDate.parse(it)
                },
                createdAt = Instant.parse(createdAt),
                updatedAt = Instant.parse(updatedAt)
            )
        }

        companion object {
            fun from(instrument: Instrument): CachePayload {
                return CachePayload(
                    symbol = instrument.symbol.value,
                    name = instrument.name,
                    market = instrument.market.name,
                    sector = instrument.sector,
                    status = instrument.status.name,
                    lotSize = instrument.lotSize,
                    listedAt = instrument.listedAt.toString(),
                    delistedAt = instrument.delistedAt?.toString(),
                    createdAt = instrument.createdAt.toString(),
                    updatedAt = instrument.updatedAt.toString()
                )
            }
        }
    }

    companion object {
        private const val KEY_PREFIX = "instrument:"
        private val TTL: Duration = Duration.ofHours(1)
    }
}