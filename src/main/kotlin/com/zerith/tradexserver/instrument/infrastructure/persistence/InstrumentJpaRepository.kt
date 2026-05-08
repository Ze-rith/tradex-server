package com.zerith.tradexserver.instrument.infrastructure.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface InstrumentJpaRepository : JpaRepository<InstrumentJpaEntity, String> {

    @Query(
        value = """
            SELECT * FROM instrument.instruments
            WHERE (:market IS NULL OR market = :market)
              AND status <> 'DELISTED'
              AND (
                symbol LIKE :symbolPrefix
                OR name ILIKE :nameLike
              )
            ORDER BY
              CASE WHEN symbol LIKE :symbolPrefix THEN 0 ELSE 1 END,
              CASE WHEN name ILIKE :nameStartsWith THEN 0 ELSE 1 END,
              symbol ASC
            LIMIT :size
        """,
        nativeQuery = true
    )
    fun search(
        @Param("symbolPrefix") symbolPrefix: String,
        @Param("nameLike") nameLike: String,
        @Param("nameStartsWith") nameStartsWith: String,
        @Param("market") market: String?,
        @Param("size") size: Int
    ): List<InstrumentJpaEntity>
}