package com.zerith.tradexserver.instrument.infrastructure.persistence

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.Version
import java.time.Instant
import java.time.LocalDate

@Entity
@Table(
    name = "instruments",
    schema = "instrument"
)
class InstrumentJpaEntity(

    @Id
    @Column(name = "symbol", nullable = false, updatable = false, length = 6)
    val symbol: String,

    @Column(name = "name", nullable = false, length = 100)
    var name: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "market", nullable = false, length = 20)
    var market: InstrumentMarketJpa,

    @Column(name = "sector", length = 50)
    var sector: String?,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    var status: InstrumentStatusJpa,

    @Column(name = "lot_size", nullable = false)
    var lotSize: Int,

    @Column(name = "listed_at", nullable = false)
    var listedAt: LocalDate,

    @Column(name = "delisted_at")
    var delistedAt: LocalDate?,

    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Instant,

    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant,

    @Version
    @Column(name = "version", nullable = false)
    var version: Long = 0
)