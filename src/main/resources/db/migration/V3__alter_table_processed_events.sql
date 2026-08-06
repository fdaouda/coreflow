-- Conversion de la colonne VARCHAR vers UUID pour PostgreSQL
ALTER TABLE processed_events
ALTER COLUMN event_id TYPE UUID USING event_id::uuid;
