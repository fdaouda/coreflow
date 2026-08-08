-- Conversion de la colonne VARCHAR vers UUID pour PostgreSQL
ALTER TABLE orders
ALTER COLUMN customer_id TYPE UUID USING customer_id::uuid;
