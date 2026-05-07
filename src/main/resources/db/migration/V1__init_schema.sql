create table orders (
    id UUID PRIMARY KEY,
    customer_id varchar(255) NOT NULL,
    amount NUMERIC(19, 2) NOT NULL,
    status VARCHAR(30) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL
);
