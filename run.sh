#!/usr/bin/env bash
set -euo pipefail

cd "$(dirname "$0")"

if [[ ! -f .env ]]; then
  echo "[run.sh] .env not found"
  exit 1
fi

set -a
# shellcheck disable=SC1091
source .env
set +a

SERVICE="${1:-}"
shift || true

case "$SERVICE" in
  auth|member|registration|instrument)
    exec ./gradlew ":${SERVICE}:bootRun" "$@"
    ;;
  "")
    echo "usage: $0 <auth|member|registration|instrument> [gradle args]"
    exit 1
    ;;
  *)
    echo "[run.sh] unknown service: $SERVICE"
    echo "available: auth, member, registration, instrument"
    exit 1
    ;;
esac
