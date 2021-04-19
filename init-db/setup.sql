-- create the databases
CREATE DATABASE IF NOT EXISTS laboratory;

-- create the users for each database
--CREATE USER 'projectoneuser'@'%' IDENTIFIED BY 'somepassword';
GRANT CREATE, ALTER, INDEX, LOCK TABLES, REFERENCES, UPDATE, DELETE, DROP, SELECT, INSERT ON `laboratory`.* TO 'laboratoryDbUser'@'%';

FLUSH PRIVILEGES;