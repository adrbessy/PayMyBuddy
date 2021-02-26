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


POST

http://localhost:9003/userAccount

http://localhost:9003/friend


### Testing
The app has unit tests written.

To run the tests from maven, go to the folder that contains the pom.xml file and execute the below command.

mvn test


### UML class diagram
![Alt text](ressources/class_diagram.png?raw=true "UML class diagram of PayMyBuddy")


### Physical data model
![Alt text](ressources/MPD.png?raw=true "Physical data model of PayMyBuddy")
