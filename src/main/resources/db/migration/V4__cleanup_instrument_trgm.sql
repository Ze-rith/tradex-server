DROP INDEX IF EXISTS instrument.idx_instruments_name_trgm;

CREATE INDEX IF NOT EXISTS idx_instruments_name ON instrument.instruments (name);