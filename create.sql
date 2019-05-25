--MAIN TABLES
--Yelp User Table
CREATE TABLE YELP_USER (
    yelping_since VARCHAR(10),
    funny_votes NUMBER,
    useful_votes NUMBER,
    cool_votes NUMBER,
    review_count NUMBER,
    user_name VARCHAR(255),
    user_id VARCHAR(30) PRIMARY KEY,
    fans NUMBER,
    average_stars FLOAT,
    user_type VARCHAR(10),
    ctype_plain NUMBER,
    ctype_cool NUMBER,    
    ctype_more NUMBER,
    ctype_note NUMBER,    
    ctype_funny NUMBER,
    ctype_writer NUMBER,    
    ctype_hot NUMBER,
    ctype_profile NUMBER,  
    ctype_photo NUMBER,
    ctype_cute NUMBER,
    ctype_list NUMBER
);

--USER'S FRIENDS TABLE
CREATE TABLE USER_FRIENDS(
    user_id VARCHAR(30),
    friend_id VARCHAR(30),
    PRIMARY KEY (user_id,friend_id),
    FOREIGN KEY (user_id) REFERENCES YELP_USER (user_id) ON DELETE CASCADE
);

--ELITE USERS TABLE
CREATE TABLE ELITE_USER(
    user_id VARCHAR(30),
    elite NUMBER,
    Primary KEY (user_id,elite),
    FOREIGN KEY (user_id) REFERENCES yelp_user (user_id) ON DELETE CASCADE
);

--BUSINESS TABLE
CREATE TABLE BUSINESS(
    business_id VARCHAR(30) PRIMARY KEY ,
    full_address VARCHAR(255),
    open_status NUMBER(1),
    city VARCHAR(255),
    review_count NUMBER,
    business_name VARCHAR(255),
    neighborhoods VARCHAR(255),
    longitude FLOAT,
    state VARCHAR(255),
    stars FLOAT,
    latitude FLOAT,
    business_type VARCHAR(20)
);

--BUSINESS_HOURS TABLE
CREATE TABLE BUSINESS_HOURS
(
    business_day VARCHAR(20),
    close_hour FLOAT,
    open_hour FLOAT,
    business_id VARCHAR(30),
    PRIMARY KEY (business_id, business_day),
    FOREIGN KEY (business_id) REFERENCES BUSINESS (business_id) ON DELETE CASCADE
);

--NEIGHBORHOOD TABLE
CREATE TABLE NEIGHBORHOOD
(
	landmark VARCHAR(100),
	business_id VARCHAR(30),
	PRIMARY KEY (business_id, landmark),
    FOREIGN KEY (business_id) REFERENCES BUSINESS (business_id) ON DELETE CASCADE
);

--BUSINESS_ATTRIBUTE TABLE
CREATE TABLE BUSINESS_ATTRIBUTE
(
    attribute_name VARCHAR(255),
    business_id VARCHAR(30),
    PRIMARY KEY (business_id, attribute_name),
    FOREIGN KEY (business_id) REFERENCES BUSINESS (business_id) ON DELETE CASCADE
);


--MAIN_CATEGORIE TABLE
CREATE TABLE MAIN_CATEGORY
(
    main_category_name VARCHAR(255),
    business_id VARCHAR(30),
    PRIMARY KEY (business_id, main_category_name),
    FOREIGN KEY (business_id) REFERENCES BUSINESS (business_id) ON DELETE CASCADE
);

--SUB_CATEGORY TABLE
CREATE TABLE SUB_CATEGORY
(
    sub_category_name VARCHAR(255),
    business_id VARCHAR(30),
    PRIMARY KEY (business_id, sub_category_name),
    FOREIGN KEY (business_id) REFERENCES BUSINESS (business_id) ON DELETE CASCADE
);
--DONE WITH BUSINESS RELATED TABLE

--REVIEW TABLE
CREATE TABLE REVIEWS
(
    funny_vote NUMBER,
    useful_vote NUMBER,
    cool_vote NUMBER,
    user_id VARCHAR(30),
    review_id VARCHAR(30) PRIMARY KEY,
    stars NUMBER,
    review_date DATE, 
    review_message CLOB,
    review_type VARCHAR(20),
    business_id VARCHAR(30),
    FOREIGN KEY (business_id) REFERENCES BUSINESS (business_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES YELP_USER (user_id) ON DELETE CASCADE,
    CHECK (stars BETWEEN 1 AND 5)    
);






--CHECKIN TABLE
CREATE TABLE CHECKIN
(
    checkin_day VARCHAR(30),
    checkin_start NUMBER,
    checkin_end NUMBER,
    checkin_type VARCHAR(15),
    business_id VARCHAR(30) NOT NULL,
    PRIMARY KEY (business_id, checkin_day, checkin_start, checkin_end),
    FOREIGN KEY (business_id) REFERENCES BUSINESS (business_id) ON DELETE CASCADE
   -- CHECK (checkin_start BETWEEN 0 AND 23),
   -- CHECK (checkin_end BETWEEN 0 AND 23)
);







