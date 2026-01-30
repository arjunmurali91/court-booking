#!/bin/bash

# ===== DATABASE CONFIG =====
DB_NAME="booking"
DB_USER="postgres"
DB_PASSWORD="UpdatePasswordHere"
DB_HOST="localhost"
DB_PORT=5432

PSQL="/opt/homebrew/opt/libpq/bin/psql"

# ===== SQL: EXPIRE HELD BOOKINGS =====
EXPIRE_HOLDS_SQL="
UPDATE booking
SET status = 'EXPIRED'
WHERE status = 'HELD'
AND holdexpiresat <= NOW();
"

# ===== SQL: MARK NO-SHOW BOOKINGS =====
NO_SHOW_SQL="
UPDATE booking
SET status = 'NO_SHOW'
WHERE status = 'CONFIRMED'
AND starttime + INTERVAL '15 minutes' <= NOW();
"

# ===== EXECUTE =====
PGPASSWORD="$DB_PASSWORD" $PSQL \
  -h "$DB_HOST" \
  -p $DB_PORT \
  -U "$DB_USER" \
  -d "$DB_NAME" \
  -v ON_ERROR_STOP=1 \
  -c "$EXPIRE_HOLDS_SQL" \
  -c "$NO_SHOW_SQL"
