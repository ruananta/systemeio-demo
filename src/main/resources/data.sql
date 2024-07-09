-- Вставка данных в таблицу product, если она пустая
INSERT INTO product (description, name, price)
SELECT 'Описание Iphone', 'Iphone', 100.00
WHERE NOT EXISTS (SELECT 1 FROM product)
UNION ALL
SELECT 'Описание наушников', 'Наушники', 20.00
WHERE NOT EXISTS (SELECT 1 FROM product)
UNION ALL
SELECT 'Описание чехла', 'Чехол', 10.00
WHERE NOT EXISTS (SELECT 1 FROM product);

-- Вставка данных в таблицу coupon, если она пустая
INSERT INTO coupon (code, discount_type, discount)
SELECT 'P6', 0, 6.00
WHERE NOT EXISTS (SELECT 1 FROM coupon)
UNION ALL
SELECT 'P10', 0, 10.00
WHERE NOT EXISTS (SELECT 1 FROM coupon)
UNION ALL
SELECT 'P100', 0, 100.00
WHERE NOT EXISTS (SELECT 1 FROM coupon)
UNION ALL
SELECT 'F10', 1, 10.00
WHERE NOT EXISTS (SELECT 1 FROM coupon);
