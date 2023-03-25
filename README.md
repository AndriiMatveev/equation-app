# equation-app
## Project description
Equation-app is a console application for checking equation syntax and save it to DB.It implements the main functionality such as:
- check equation and answer syntax
- check, if answer is correct for the equation
- save equation and answer to DB
- find all equations from DB with entered answer
------------------
Structure
-----------------
Project was built according to n-tier architecture:
- Services (Application tier) - this layer coordinates work of all application, process commands and performs calculations.
- Repository (Data tier) - here information is stored and retrieved.
------------------
Technologies
-----------------
- JDK 
- Maven 
- Hibernate 
- SpringBoot
- MySQL 8.0.22
- Lombok
- ------------------
Installation
-----------------
- Clone this project from GitHub
```bash
git clone github.com/AndriiMatveev/equation-app
```
- Install [MySQL](https://dev.mysql.com/doc/mysql-installation-excerpt/5.7/en/)
- Create a schema in your MySQL DB.
  You can run the following query:<br>
```
CREATE SCHEMA IF NOT EXISTS `equation-app` DEFAULT CHARACTER SET utf8;
```
- Change URL, username, password and JDBC driver in [db.properties](src/main/resources/application.properties)
