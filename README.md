<img src="https://img.shields.io/badge/java-%23ED8B00.svg?&style=for-the-badge&logo=java&logoColor=white"/> * * *  <img src="https://img.shields.io/badge/spring%20-%236DB33F.svg?&style=for-the-badge&logo=spring&logoColor=white"/> * * *  ![Thymeleaf](https://img.shields.io/badge/Thymeleaf-%23005C0F.svg?style=for-the-badge&logo=Thymeleaf&logoColor=white) * * *  ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)

[![Maintenance](https://img.shields.io/badge/Maintained%3F-yes-green.svg)](https://GitHub.com/Naereen/StrapDown.js/graphs/commit-activity)

# PayMyBuddy api
An application for making money transactions between friends.
This app uses Java to run and stores the data in Postgresql DB.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. 

### Prerequisites

What things you need to install the software and how to install them

- Java 1.8
- Maven 4.0.0

### Installing

A step by step series of examples that tell you how to get a development env running:

1.Install Java:

https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html

2.Install Maven:

https://maven.apache.org/install.html

### Running App

You can run the application in two different ways:

1/ import the code into an IDE of your choice and run the PayMyBuddyApplication.java to launch the application.

2/ Or import the code, unzip it, open the console, go to the folder that contains the pom.xml file, then execute the below command to launch the application.

mvn spring-boot:run 

### API calls (URI, parameters)
GET

http://localhost:9003/friends

http://localhost:9003/userAccounts

http://localhost:9003/transactions

http://localhost:9003/bankAccounts

http://localhost:9003/myFriends?emailAddress=adrien@mail.fr

http://localhost:9003/myTransactions?emailAddress=isabelle@mail.fr

http://localhost:9003/myBankAccounts?emailAddress=adrien@mail.fr

http://localhost:9003/myUserAccount?emailAddress=anne@mail.fr


POST

http://localhost:9003/userAccount

http://localhost:9003/friend

http://localhost:9003/friendTransaction

http://localhost:9003/bankAccount

http://localhost:9003/moneyDeposit

http://localhost:9003/transactionToBankAccount


PUT

http://localhost:9003/userAccount/adrien@mail.fr


DELETE

http://localhost:9003/myFriend?emailAddress=adrien@mail.fr&emailAddressToDelete=helene@mail.fr

http://localhost:9003/myBankAccount?emailAddress=isabelle@mail.fr&iban=FR46541656184548646


### Testing
The app has unit tests written.

To run the tests from maven, go to the folder that contains the pom.xml file and execute the below command.

mvn test


### UML class diagram
![Alt text](ressources/class_diagram.png?raw=true "UML class diagram of PayMyBuddy")


### Physical data model
![Alt text](ressources/MPD.png?raw=true "Physical data model of PayMyBuddy")
