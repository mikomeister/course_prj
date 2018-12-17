
DROP TABLE orders_services;
DROP TABLE services;
DROP TABLE orders;
DROP TABLE categories;
DROP TABLE statuses;
DROP TABLE users;
DROP TABLE roles;
DROP TABLE user_statuses;
CREATE TABLE roles(


	id INTEGER NOT NULL PRIMARY KEY,

	
	name VARCHAR(10) NOT NULL UNIQUE
);

INSERT INTO roles VALUES(0, 'admin');
INSERT INTO roles VALUES(1, 'user');

CREATE TABLE users(


	id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
	

	login VARCHAR(10) NOT NULL UNIQUE,
	

	password VARCHAR(255) NOT NULL,
	first_name VARCHAR(20) NOT NULL,
	last_name VARCHAR(20) NOT NULL,
	cash INTEGER NOT NULL DEFAULT 0,
	user_status INTEGER NOT NULL DEFAULT 0 REFERENCES user_statuses(user_status),


	role_id INTEGER NOT NULL REFERENCES roles(id) 


		ON DELETE CASCADE 


		ON UPDATE RESTRICT
);


INSERT INTO users VALUES(DEFAULT, 'admin', '21232f297a57a5a743894a0e4a801fc3', 'Ivan', 'Ivanov', DEFAULT,DEFAULT,0);

INSERT INTO users VALUES(DEFAULT, 'user', 'ee11cbb19052e40b07aac0ca060c23ee', 'Oleksandr', 'Petrov', DEFAULT,DEFAULT,1);

CREATE TABLE categories(
	id INTEGER NOT NULL PRIMARY KEY,
	name VARCHAR(15) NOT NULL UNIQUE
);

INSERT INTO categories VALUES(1, 'Internet');
INSERT INTO categories VALUES(2, 'TV');
INSERT INTO categories VALUES(3, 'InternetAndTV');

CREATE TABLE services(
	service_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(50) NOT NULL,
	price INTEGER NOT NULL,
	category_id INTEGER NOT NULL REFERENCES categories(id) 
);


INSERT INTO services VALUES(DEFAULT, 'Unlim50', 80, 1);
INSERT INTO services VALUES(DEFAULT, 'Unlim100', 110, 1);
INSERT INTO services VALUES(DEFAULT, 'Extra', 160, 1); 

INSERT INTO services VALUES(DEFAULT, 'Digital', 130, 2);
INSERT INTO services VALUES(DEFAULT, 'Interactive', 90, 2); 
     
INSERT INTO services VALUES(DEFAULT, 'HD100', 100, 3);
INSERT INTO services VALUES(DEFAULT, 'ExtraHD', 260, 3); 

CREATE TABLE statuses(
	id INTEGER NOT NULL PRIMARY KEY,
	status VARCHAR(10) NOT NULL UNIQUE
);


INSERT INTO statuses VALUES(0, 'unpaid');
INSERT INTO statuses VALUES(1, 'paid');
INSERT INTO statuses VALUES(2, 'closed');

CREATE TABLE orders(
	id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
	user_id INTEGER NOT NULL REFERENCES users(id)
);

INSERT INTO orders VALUES(DEFAULT, 2);

INSERT INTO orders VALUES(DEFAULT, 2);

INSERT INTO orders VALUES(DEFAULT, 1);

CREATE TABLE orders_services(
	order_id INTEGER NOT NULL REFERENCES orders(id),
	service_id INTEGER NOT NULL REFERENCES services(service_id),
	status_id INTEGER NOT NULL DEFAULT 0 REFERENCES statuses(id) 
);

INSERT INTO orders_services VALUES(1, 7, 0);

INSERT INTO orders_services VALUES(2, 2, 1);
INSERT INTO orders_services VALUES(3, 7, DEFAULT);

CREATE TABLE user_statuses(
	id INTEGER NOT NULL PRIMARY KEY,
	user_status VARCHAR(10) NOT NULL UNIQUE
);
INSERT INTO user_statuses VALUES(0, 'unlocked');
INSERT INTO user_statuses VALUES(1, 'locked');


SELECT * FROM orders_services;
SELECT * FROM services;
SELECT * FROM orders;
SELECT * FROM categories;
SELECT * FROM statuses;
SELECT * FROM users;
SELECT * FROM roles;
SELECT * FROM user_statuses;