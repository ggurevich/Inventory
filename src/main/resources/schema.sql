CREATE SCHEMA IF NOT EXISTS penultimo;
USE penultimo;

CREATE TABLE IF NOT EXISTS penultimo.products (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  upc VARCHAR(30) NOT NULL,
  product_name VARCHAR(30) NOT NULL,
  product_manufacturer VARCHAR(30) NOT NULL,
  product_price DECIMAL NOT NULL,
  PRIMARY KEY (id));
  
CREATE TABLE IF NOT EXISTS penultimo.customers (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(30) NOT NULL,
  last_name VARCHAR(30) NOT NULL,
  email VARCHAR(50) NOT NULL,
  phone_number VARCHAR(16) NOT NULL,
  PRIMARY KEY (id));
  
 CREATE TABLE IF NOT EXISTS penultimo.transactions (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  customer_id INT UNSIGNED NOT NULL,
  product_id INT UNSIGNED NOT NULL,
  transaction_date VARCHAR(60) NOT NULL,
  PRIMARY KEY (id));
  