#!/usr/bin/env bash
set -euo pipefail

cd "$(dirname "$0")/.."

if [[ ! -f .env ]]; then
  echo "[reset-db.sh] .env not found"
  exit 1
fi

set -a
# shellcheck disable=SC1091
source .env
set +a

DB_NAME="$(echo "$TRADEX_DB_URL" | sed -E 's|.*/||')"

echo "[reset-db.sh] dropping schemas auth/member/instrument in DB=$DB_NAME"

PGPASSWORD="$TRADEX_DB_PASSWORD" psql \
  -h "$(echo "$TRADEX_DB_URL" | sed -E 's|jdbc:postgresql://([^:/]+).*|\1|')" \
  -p "$(echo "$TRADEX_DB_URL" | sed -E 's|.*:([0-9]+)/.*|\1|')" \
  -U "$TRADEX_DB_USERNAME" \
  -d "$DB_NAME" \
  -v ON_ERROR_STOP=1 \
  <<SQL
DROP SCHEMA IF EXISTS auth CASCADE;
DROP SCHEMA IF EXISTS member CASCADE;
DROP SCHEMA IF EXISTS instrument CASCADE;
SQL

echo "[reset-db.sh] done"
