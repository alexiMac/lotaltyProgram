-- Use database
use loyaltyProgram;

-- Delete table IF EXISTS
DROP TABLE IF EXISTS tbl_redeem;
DROP TABLE IF EXISTS tbl_purchases;
DROP TABLE IF EXISTS tbl_rewards;
DROP TABLE IF EXISTS tbl_points;
DROP TABLE IF EXISTS tbl_users;

-- User table
CREATE TABLE IF NOT EXISTS tbl_users (
	pk_id_user 			INT AUTO_INCREMENT PRIMARY KEY,
    v_first_name  		VARCHAR(255) NOT NULL,
    v_last_name  		VARCHAR(255) NOT NULL,
    v_email				VARCHAR(255) NOT NULL UNIQUE,
	v_password  		VARCHAR(255) NOT NULL,
    v_role				VARCHAR(255) NOT NULL CHECK (v_role in ('USER','ADMIN')),
    b_approved  		TINYINT DEFAULT 0, -- ADMIN, USER
    d_created_date		DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Points table (Points available per User)
CREATE TABLE IF NOT EXISTS tbl_points (
	pk_id_points			INT AUTO_INCREMENT PRIMARY KEY,
	fk_id_user				INT NOT NULL UNIQUE,
    i_available_points		INT NOT NULL DEFAULT 0,
    FOREIGN KEY(fk_id_user) REFERENCES tbl_users(pk_id_user)
);

-- Rewards table (Rewards available to redeem with points)
CREATE TABLE IF NOT EXISTS tbl_rewards (
	pk_id_reward			INT AUTO_INCREMENT PRIMARY KEY,
    v_reward_name			VARCHAR(255) NOT NULL,
    i_reward_points			INT NOT NULL,
    i_rewards_available		INT NOT NULL
);

-- Purchase table (Purchase per User)
CREATE TABLE IF NOT EXISTS tbl_purchases (
	pk_id_purchase			INT AUTO_INCREMENT PRIMARY KEY,
    fk_id_user				INT NOT NULL,
    de_purchase_total		DECIMAL(15,2) NOT NULL,
    i_generated_points		INT NOT NULL,-- AS (FLOOR(de_purchase_total / 10)), -- Must be send in JAVA 10% of purchase
    d_purchase_date			DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(fk_id_user) REFERENCES tbl_users(pk_id_user)
);

-- Redeem table (Redeem per User) 
CREATE TABLE IF NOT EXISTS tbl_redeem (
	pk_id_redeem				INT AUTO_INCREMENT PRIMARY KEY,
    fk_id_user					INT NOT NULL,
    fk_id_reward				INT NOT NULL,
    v_reward_name				VARCHAR(255),
    i_redeem_points				INT NOT NULL,
    i_quantity_reward           INT NOT NULL,
    i_total_redeem_points		INT NOT NULL,
    d_redeem_date				DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(fk_id_user) 	REFERENCES tbl_users(pk_id_user),
    FOREIGN KEY(fk_id_reward) 	REFERENCES tbl_rewards(pk_id_reward)
);