--liquibase formatted sql
--changeset dabrowskiw:28
CREATE TABLE product_to_category (
    product_id INT NOT NULL,
    category_id INT NOT NULL,
    PRIMARY KEY (product_id, category_id),
    FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES product_category(id) ON DELETE CASCADE
);