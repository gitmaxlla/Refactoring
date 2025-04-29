CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE,
    registration_date DATE
);

CREATE TABLE products (
    product_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2),
    category VARCHAR(50)
);

CREATE TABLE orders (
    order_id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(user_id),
    order_date DATE,
    status VARCHAR(20)
);

CREATE TABLE order_items (
    order_item_id SERIAL PRIMARY KEY,
    order_id INT REFERENCES orders(order_id),
    product_id INT REFERENCES products(product_id),
    quantity INT
);


INSERT INTO users (username, email, registration_date)
SELECT 
    'user_' || i,
    'user_' || i || '@example.com',
    NOW() - INTERVAL '1 year' * RANDOM()
FROM generate_series(1, 1000) AS i;

INSERT INTO products (name, price, category)
SELECT 
    'Product ' || i,
    (RANDOM() * 1000)::DECIMAL(10,2),
    CASE WHEN i%3 = 0 THEN 'Electronics' 
         WHEN i%3 = 1 THEN 'Books' 
         ELSE 'Clothing' END
FROM generate_series(1, 500) AS i;

INSERT INTO orders (user_id, order_date, status)
SELECT 
    (RANDOM() * 999 + 1)::INT,
    NOW() - INTERVAL '30 days' * RANDOM(),
    CASE WHEN RANDOM() > 0.1 THEN 'Completed' ELSE 'Pending' END
FROM generate_series(1, 5000);

INSERT INTO order_items (order_id, product_id, quantity)
SELECT 
    (RANDOM() * 4999 + 1)::INT,
    (RANDOM() * 499 + 1)::INT,
    (RANDOM() * 5 + 1)::INT
FROM generate_series(1, 20000);
