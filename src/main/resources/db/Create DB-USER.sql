-- As a ROOT user
-- Create database
create database loyaltyProgram;

-- Use database loyaltyProgram
use loyaltyProgram;

-- Create user DEVBIMBO wiht pwd
CREATE USER 'devbimbo'@'localhost' IDENTIFIED BY '12345';

-- Grant privileges on bd to user
GRANT ALL PRIVILEGES ON loyaltyProgram . * TO 'devbimbo'@'localhost';
FLUSH PRIVILEGES;