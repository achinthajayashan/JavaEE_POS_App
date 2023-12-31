DROP DATABASE IF EXISTS javaee_pos_app;
CREATE DATABASE javaee_pos_app;
USE javaee_pos_app;

CREATE TABLE IF NOT EXISTS customer(
	customer_ID VARCHAR(10),
	customer_name VARCHAR(30),
	address VARCHAR(100),
	contact INTEGER,
	CONSTRAINT PRIMARY KEY(customer_ID)
);

CREATE TABLE IF NOT EXISTS item(
	item_ID VARCHAR(10),
	item_name VARCHAR(30),
	unit_price DOUBLE NOT NULL,
	qty_on_hnd INTEGER,
	CONSTRAINT PRIMARY KEY(item_ID)
);

CREATE TABLE IF NOT EXISTS orders(
	order_ID VARCHAR(10),
	date DATE,
	customer_ID VARCHAR(10),
	CONSTRAINT PRIMARY KEY(order_ID),
	CONSTRAINT FOREIGN KEY(customer_ID) REFERENCES customer(customer_ID)
		ON DELETE CASCADE  ON  UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS order_detail(
	item_ID VARCHAR(10),
	order_ID VARCHAR(10),
	qty INTEGER,
	unitPrice DECIMAL(8,2),
	CONSTRAINT PRIMARY KEY(item_ID,order_ID),
	CONSTRAINT FOREIGN KEY(order_ID) REFERENCES orders(order_ID)
		ON DELETE CASCADE  ON  UPDATE CASCADE,
	CONSTRAINT FOREIGN KEY(item_ID) REFERENCES item(item_ID)
		ON DELETE CASCADE  ON  UPDATE CASCADE
);



