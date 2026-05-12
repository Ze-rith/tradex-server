package com.zerith.tradexserver.instrument.infrastructure.persistence

import com.zerith.tradexserver.instrument.domain.model.Instrument
import com.zerith.tradexserver.instrument.domain.model.InstrumentStatus
import com.zerith.tradexserver.instrument.domain.model.Market
import com.zerith.tradexserver.instrument.domain.model.Symbol

object InstrumentMapper {

    fun toDomain(entity: InstrumentJpaEntity): Instrument {
        return Instrument.restore(
            symbol = Symbol.of(entity.symbol),
            name = entity.name,
            market = toDomainMarket(entity.market),
            sector = entity.sector,
            status = toDomainStatus(entity.status),
            lotSize = entity.lotSize,
            listedAt = entity.listedAt,
            delistedAt = entity.delistedAt,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }

    fun toEntity(instrument: Instrument): InstrumentJpaEntity {
        return InstrumentJpaEntity(
            symbol = instrument.symbol.value,
            name = instrument.name,
            market = toJpaMarket(instrument.market),
            sector = instrument.sector,
            status = toJpaStatus(instrument.status),
            lotSize = instrument.lotSize,
            listedAt = instrument.listedAt,
            delistedAt = instrument.delistedAt,
            createdAt = instrument.createdAt,
            updatedAt = instrument.updatedAt
        )
    }

    fun applyTo(
        entity: InstrumentJpaEntity,
        instrument: Instrument
    ) {
        entity.name = instrument.name
        entity.market = toJpaMarket(instrument.market)
        entity.sector = instrument.sector
        entity.status = toJpaStatus(instrument.status)
        entity.lotSize = instrument.lotSize
        entity.listedAt = instrument.listedAt
        entity.delistedAt = instrument.delistedAt
        entity.updatedAt = instrument.updatedAt
    }

    private fun toDomainMarket(market: InstrumentMarketJpa): Market {
        return when (market) {
            InstrumentMarketJpa.KOSPI -> Market.KOSPI
            InstrumentMarketJpa.KOSDAQ -> Market.KOSDAQ
            InstrumentMarketJpa.KONEX -> Market.KONEX
        }
    }

    private fun toJpaMarket(market: Market): InstrumentMarketJpa {
        return when (market) {
            Market.KOSPI -> InstrumentMarketJpa.KOSPI
            Market.KOSDAQ -> InstrumentMarketJpa.KOSDAQ
            Market.KONEX -> InstrumentMarketJpa.KONEX
        }
    }

    private fun toDomainStatus(status: InstrumentStatusJpa): InstrumentStatus {
        return when (status) {
            InstrumentStatusJpa.NORMAL -> InstrumentStatus.NORMAL
            InstrumentStatusJpa.SUSPENDED -> InstrumentStatus.SUSPENDED
            InstrumentStatusJpa.MANAGED -> InstrumentStatus.MANAGED
            InstrumentStatusJpa.WARNING -> InstrumentStatus.WARNING
            InstrumentStatusJpa.DELISTED -> InstrumentStatus.DELISTED
        }
    }

    private fun toJpaStatus(status: InstrumentStatus): InstrumentStatusJpa {
        return when (status) {
            InstrumentStatus.NORMAL -> InstrumentStatusJpa.NORMAL
            InstrumentStatus.SUSPENDED -> InstrumentStatusJpa.SUSPENDED
            InstrumentStatus.MANAGED -> InstrumentStatusJpa.MANAGED
            InstrumentStatus.WARNING -> InstrumentStatusJpa.WARNING
            InstrumentStatus.DELISTED -> InstrumentStatusJpa.DELISTED
        }
    }
}