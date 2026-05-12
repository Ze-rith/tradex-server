CREATE SCHEMA IF NOT EXISTS instrument;

CREATE TABLE instrument.instruments (
                                        symbol          VARCHAR(6)      NOT NULL,
                                        name            VARCHAR(100)    NOT NULL,
                                        market          VARCHAR(20)     NOT NULL,
                                        sector          VARCHAR(50),
                                        status          VARCHAR(20)     NOT NULL,
                                        lot_size        INTEGER         NOT NULL DEFAULT 1,
                                        listed_at       DATE            NOT NULL,
                                        delisted_at     DATE,
                                        created_at      TIMESTAMPTZ     NOT NULL,
                                        updated_at      TIMESTAMPTZ     NOT NULL,
                                        version         BIGINT          NOT NULL DEFAULT 0,
                                        CONSTRAINT pk_instruments PRIMARY KEY (symbol),
                                        CONSTRAINT ck_instruments_market CHECK (market IN ('KOSPI', 'KOSDAQ', 'KONEX')),
                                        CONSTRAINT ck_instruments_status CHECK (status IN ('NORMAL', 'SUSPENDED', 'MANAGED', 'WARNING', 'DELISTED')),
                                        CONSTRAINT ck_instruments_symbol_format CHECK (symbol ~ '^[0-9]{6}$'),
    CONSTRAINT ck_instruments_lot_size CHECK (lot_size > 0)
);

CREATE INDEX idx_instruments_market_status ON instrument.instruments (market, status);
CREATE INDEX idx_instruments_status_active ON instrument.instruments (status) WHERE status != 'DELISTED';