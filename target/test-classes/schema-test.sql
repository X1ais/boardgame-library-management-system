DROP TABLE IF EXISTS bibliographic_record_publisher;
DROP TABLE IF EXISTS bibliographic_record_designer;
DROP TABLE IF EXISTS bibliographic_record_artist;
DROP TABLE IF EXISTS bibliographic_record_category;
DROP TABLE IF EXISTS publisher;
DROP TABLE IF EXISTS designer;
DROP TABLE IF EXISTS artist;
DROP TABLE IF EXISTS loan;
DROP TABLE IF EXISTS review;
DROP TABLE IF EXISTS item_record;
DROP TABLE IF EXISTS address;
DROP TABLE IF EXISTS borrower;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS bibliographic_record;

CREATE TABLE  bibliographic_record (
	bib_id INT NOT NULL AUTO_INCREMENT,
	bib_name VARCHAR(127) NOT NULL,
	min_num_of_players INT,
	max_num_of_players INT,
	min_age INT,
	playtime INT,
	complexity DECIMAL,
	edition VARCHAR(63),
	PRIMARY KEY (bib_id)
);

CREATE TABLE category (
	category_id INT NOT NULL AUTO_INCREMENT,
	category_name VARCHAR(255),
	PRIMARY KEY (category_id)
);

CREATE TABLE borrower (
	borrower_id INT NOT NULL AUTO_INCREMENT,
	first_name VARCHAR(127),
	last_name VARCHAR(127),
	phone VARCHAR(64),
	item_limit INT,
	PRIMARY KEY (borrower_id)
);

CREATE TABLE address (
	address_id INT NOT NULL AUTO_INCREMENT,
	borrower_id INT NOT NULL,
	street_address VARCHAR(255),
	street_address2 VARCHAR(255),
	city VARCHAR(127),
	state VARCHAR(63),
	zip INT,
	PRIMARY KEY (address_id),
	FOREIGN KEY (borrower_id) REFERENCES borrower (borrower_id) ON DELETE CASCADE
);

CREATE TABLE item_record (
	item_id INT NOT NULL AUTO_INCREMENT,
	bib_id INT NOT NULL,
	item_name VARCHAR(127) NOT NULL,
	location VARCHAR(255),
	available BOOLEAN,
	checkouts INT,
	checkout_period INT,
	PRIMARY KEY (item_id),
	FOREIGN KEY (bib_id) REFERENCES bibliographic_record (bib_id) ON DELETE CASCADE
);

CREATE TABLE review (
	review_id INT NOT NULL AUTO_INCREMENT,
	bib_id INT NOT NULL,
	borrower_id INT NOT NULL,
	rating INT,
	review_body VARCHAR(1000),
	PRIMARY KEY (review_id),
	FOREIGN KEY (bib_id) REFERENCES bibliographic_record (bib_id) ON DELETE CASCADE,
	FOREIGN KEY (borrower_id) REFERENCES borrower (borrower_id)
);

CREATE TABLE loan (
	loan_id INT NOT NULL AUTO_INCREMENT,
	item_id INT NOT NULL,
	borrower_id INT NOT NULL,
	checkout_date DATE NOT NULL DEFAULT (CURRENT_DATE),
	return_date DATE,
	PRIMARY KEY (loan_id),
	FOREIGN KEY (item_id) REFERENCES item_record (item_id) ON DELETE CASCADE,
	FOREIGN KEY (borrower_id) REFERENCES borrower (borrower_id) ON DELETE CASCADE
);

CREATE TABLE artist (
	artist_id INT NOT NULL AUTO_INCREMENT,
	first_name VARCHAR(127),
	last_name VARCHAR(127),
	PRIMARY KEY (artist_id)
);

CREATE TABLE designer (
	designer_id INT NOT NULL AUTO_INCREMENT,
	first_name VARCHAR(127),
	last_name VARCHAR(127),
	PRIMARY KEY (designer_id)
);

CREATE TABLE publisher (
	publisher_id INT NOT NULL AUTO_INCREMENT,
	publisher_name VARCHAR(127),
	PRIMARY KEY (publisher_id)
);

CREATE TABLE bibliographic_record_category (
	bib_id INT NOT NULL,
	category_id INT NOT NULL,
	FOREIGN KEY (bib_id) REFERENCES bibliographic_record (bib_id) ON DELETE CASCADE,
	FOREIGN KEY (category_id) REFERENCES category (category_id) ON DELETE CASCADE
);

CREATE TABLE bibliographic_record_artist (
	bib_id INT NOT NULL,
	artist_id INT NOT NULL,
	FOREIGN KEY (bib_id) REFERENCES bibliographic_record (bib_id) ON DELETE CASCADE,
	FOREIGN KEY (artist_id) REFERENCES artist (artist_id) ON DELETE CASCADE
);

CREATE TABLE bibliographic_record_designer (
	bib_id INT NOT NULL,
	designer_id INT NOT NULL,
	FOREIGN KEY (bib_id) REFERENCES bibliographic_record (bib_id) ON DELETE CASCADE,
	FOREIGN KEY (designer_id) REFERENCES designer (designer_id) ON DELETE CASCADE
);

CREATE TABLE bibliographic_record_publisher (
	bib_id INT NOT NULL,
	publisher_id INT NOT NULL,
	FOREIGN KEY (bib_id) REFERENCES bibliographic_record (bib_id) ON DELETE CASCADE,
	FOREIGN KEY (publisher_id) REFERENCES publisher (publisher_id) ON DELETE CASCADE
);