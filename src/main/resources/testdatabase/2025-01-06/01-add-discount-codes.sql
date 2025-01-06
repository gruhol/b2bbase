--liquibase formatted sql
--changeset dabrowskiw:22
CREATE TABLE discount_codes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(50) NOT NULL,
    discount_type ENUM('percentage', 'value') NOT NULL,
    discount_amount DECIMAL(10, 2) NOT NULL,
    usage_limit INT DEFAULT 1,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATE NOT NULL
);