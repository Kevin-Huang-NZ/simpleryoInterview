CREATE TABLE user (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  email varchar(100) NOT NULL,
  password varchar(100) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY idx_user_0 (email,password)
);

CREATE TABLE goods (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(200) NOT NULL,
  amount int NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE orders (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  buy_date varchar(20) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE order_detail (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  order_id bigint(20) NOT NULL,
  good_id bigint(20) NOT NULL,
  buy_amount int NOT NULL,
  PRIMARY KEY (id)
);