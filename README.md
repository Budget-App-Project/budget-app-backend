# Budget App Back End
This project is a portfolio website created to give a little bit of insight into who I am, what technologies I work with, and what projects I have completed. However, the site can be adapted to the needs of anyone as it utilizes a CMS backend that can be created and changed by anyone.

## Table of Contents
- [Tech Stack](#tech-stack)
- [Installation and Setup](#installation-and-setup)
- [Setup](#setup)
  (optional)
- [Deployment](#deployment)
- [Contributors](#contributors)

## Tech Stack
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Azure](https://img.shields.io/badge/azure-%230072C6.svg?style=for-the-badge&logo=microsoftazure&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Apache Tomcat](https://img.shields.io/badge/apache%20tomcat-%23F8DC75.svg?style=for-the-badge&logo=apache-tomcat&logoColor=black)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)

#### Installation and Setup
* Fork and clone github repo onto local machine
* Firstly, ensure you have PostgreSQL installed locally
* Then, install a either a JVM or a JDK and Apache Tomcat
* If just running on a JVM simply cd into the folder src/main/java.../budgetappbackend and run the command:
```bash
java -jar BudgetAppBackendApplication.java
```
* Otherwise, set up the Apache Tomcat server and change the configurations in the build settings from jar to war and compile:
* Then, move the war file over into the webserver and run it.

## End Points
* Get Expenses
  * Given a user session cookie, this endpoint will return a list of Expenses for the current month or selected timeframe for given user associated with that session.
* Post Expense
  * Given a session cookie and all necessary information, this endpoint will create an expense in the database for related to that particular user with all included data.
* Update Expense
  * Given a session cooke and all necessary information, this endpoint will update an expense in the database to the new information.
* Delete Expense
  * Given a session cookie and expenseID, this endpoint will delete the corresponding expense from the database.
* ExportCSV
  * Given a session cookie and all necessary information, this endpoint will create a CSV filestream in sorted in the order selected by the user.
* Signup
  * Given the necessary information, this endpoint create a user in the database.
* Login
  * Given the necessary information, this endpoint attaches a session cookie with the corresponding user to user's browser.

## Contributors
<a href="https://github.com/Bornean-Orangutan/Ratings-and-Reviews-API/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=Bornean-Orangutan/Ratings-and-Reviews-API" />
</a>
