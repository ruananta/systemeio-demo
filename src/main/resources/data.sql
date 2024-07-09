
-- Вставка данных в таблицу product
INSERT INTO product (description, name, price) VALUES ('Описание Iphone', 'Iphone', 100.00);
INSERT INTO product (description, name, price) VALUES ('Описание наушников', 'Наушники', 20.00);
INSERT INTO product (description, name, price) VALUES ('Описание чехла', 'Чехол', 10.00);

-- Вставка данных в таблицу coupon
INSERT INTO coupon (code, discount_type, discount) VALUES ('P6', 0, 6.00);
INSERT INTO coupon (code, discount_type, discount) VALUES ('P10', 0, 10.00);
INSERT INTO coupon (code, discount_type, discount) VALUES ('P100', 0, 100.00);
INSERT INTO coupon (code, discount_type, discount) VALUES ('F10', 1, 10.00);
