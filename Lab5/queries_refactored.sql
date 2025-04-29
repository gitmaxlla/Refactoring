DROP INDEX IF EXISTS order_date_index;
CREATE INDEX IF NOT EXISTS order_date_index
ON orders (order_date);

set enable_seqscan=false;
SELECT *
FROM orders
JOIN products
ON orders.order_id = products.product_id
JOIN order_items
ON orders.order_id = order_items.order_id
WHERE orders.order_date > '2023-01-01';

SELECT order_id
FROM order_items
WHERE quantity > 10;

set enable_seqscan=false;
SELECT *
FROM users
WHERE user_id IN
	(SELECT user_id
	 FROM orders
	 WHERE order_date > '2023-01-01');

SELECT *
FROM users
WHERE registration_date > '2023-01-01';

SELECT *
FROM products
WHERE category = 'Electronics' OR category = 'Books' OR price > 100.00;

SELECT
    username,
	COUNT(*) AS order_count
FROM users
JOIN orders
ON users.user_id = orders.user_id
GROUP BY users.user_id;

set enable_seqscan=false;
SELECT *
FROM users
JOIN orders
ON users.user_id = orders.user_id
WHERE orders.order_date > '2023-01-01';

SELECT product_id, price, category
FROM products
WHERE name = 'Product 123';

SELECT user_id, username, email
FROM users
WHERE email IS NOT NULL AND registration_date IS NULL;

set enable_seqscan=false;
SELECT *
FROM orders
WHERE order_date > '2023-01-01';