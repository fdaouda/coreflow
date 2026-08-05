-- Migration V2 : Table pour l'idempotence des événements (Kafka / RabbitMQ)

CREATE TABLE processed_events (
      event_id VARCHAR(255) PRIMARY KEY,
      event_type VARCHAR(100) NOT NULL,
      processed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_processed_events_event_type ON processed_events(event_type);
