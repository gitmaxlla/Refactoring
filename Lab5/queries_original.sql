DROP INDEX IF EXISTS order_date_index;
SELECT *
FROM orders, products, order_items
WHERE orders.order_id = order_items.order_id
AND products.product_id = order_items.product_id
AND orders.order_date > '2023-01-01';

SELECT order_id
FROM order_items
HAVING SUM(quantity) > 10;

SELECT *
FROM users
WHERE user_id IN (SELECT * FROM orders WHERE order_date > '2023-01-01');

SELECT *
FROM users
WHERE registration_date > '2023-01-01';

SELECT *
FROM products
WHERE category = 'Electronics' OR category = 'Books' OR price > 100.00;

SELECT
    username,
    (SELECT COUNT(*) FROM orders WHERE user_id = users.user_id) AS order_count
FROM users;

SELECT *
FROM users, orders
WHERE users.user_id = orders.user_id
AND orders.order_date > '2023-01-01';

SELECT *
FROM products
WHERE name LIKE 'Product 123';

SELECT *
FROM users
WHERE email IS NOT NULL AND registration_date IS NULL;

SELECT *
FROM (
    SELECT *
    FROM orders
    WHERE order_date > '2023-01-01'
) AS subquery;
