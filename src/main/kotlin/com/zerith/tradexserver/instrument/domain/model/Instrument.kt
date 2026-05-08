package com.zerith.tradexserver.instrument.domain.model

import java.time.Instant
import java.time.LocalDate

class Instrument private constructor(
    val symbol: Symbol,
    name: String,
    market: Market,
    sector: String?,
    status: InstrumentStatus,
    lotSize: Int,
    listedAt: LocalDate,
    delistedAt: LocalDate?,
    val createdAt: Instant,
    updatedAt: Instant
) {
    var name: String = name
        private set

    var market: Market = market
        private set

    var sector: String? = sector
        private set

    var status: InstrumentStatus = status
        private set

    var lotSize: Int = lotSize
        private set

    var listedAt: LocalDate = listedAt
        private set

    var delistedAt: LocalDate? = delistedAt
        private set

    var updatedAt: Instant = updatedAt
        private set

    companion object {
        fun register(
            symbol: Symbol,
            name: String,
            market: Market,
            sector: String?,
            lotSize: Int,
            listedAt: LocalDate,
            now: Instant
        ): Instrument {
            return Instrument(
                symbol = symbol,
                name = name,
                market = market,
                sector = sector,
                status = InstrumentStatus.NORMAL,
                lotSize = lotSize,
                listedAt = listedAt,
                delistedAt = null,
                createdAt = now,
                updatedAt = now
            )
        }

        fun restore(
            symbol: Symbol,
            name: String,
            market: Market,
            sector: String?,
            status: InstrumentStatus,
            lotSize: Int,
            listedAt: LocalDate,
            delistedAt: LocalDate?,
            createdAt: Instant,
            updatedAt: Instant
        ): Instrument {
            return Instrument(
                symbol = symbol,
                name = name,
                market = market,
                sector = sector,
                status = status,
                lotSize = lotSize,
                listedAt = listedAt,
                delistedAt = delistedAt,
                createdAt = createdAt,
                updatedAt = updatedAt
            )
        }
    }

    fun isTradable(): Boolean {
        return status.isTradable()
    }

    fun updateMaster(
        name: String,
        market: Market,
        sector: String?,
        lotSize: Int,
        listedAt: LocalDate,
        now: Instant
    ) {
        this.name = name
        this.market = market
        this.sector = sector
        this.lotSize = lotSize
        this.listedAt = listedAt
        this.updatedAt = now
    }
}