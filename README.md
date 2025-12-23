IRCTC Train Booking System (Java)

This project is a console-based train booking system developed using Core Java and Gradle, inspired by the IRCTC platform.
It focuses on implementing fundamental backend concepts such as user authentication, booking management, and persistent data storage using JSON files.

The project is designed as a foundation and will be enhanced later by migrating it to Spring Boot with REST APIs and database support.

----- Project Features -----

User registration and login

Secure password hashing and validation

Train search functionality

Seat booking system

Ticket cancellation

Persistent storage using JSON files

Layered architecture with entities, services, and utilities

----- Technology Stack -----

Java (JDK 21)

Gradle

Jackson (for JSON serialization and deserialization)

BCrypt (for password hashing)

IntelliJ IDEA

----- Project Structure -----
IRCTC/
│── app/
│   ├── src/main/java/org/example/ticket/booking/
│   │   ├── App.java
│   │   ├── entities/
│   │   ├── Service/
│   │   └── util/
│   └── src/main/resources/LocalDb/
│       ├── users.json
│       └── trains.json
│── .gitignore
│── build.gradle
│── settings.gradle

----- How to Run the Project -----

Clone the repository:

git clone https://github.com/Vaibhavitumsare/IRCTC-Train-Booking-System.git


Open the project in IntelliJ IDEA.

Configure JDK 21 in the project settings.

Run the App.java file.

----- Key Learnings from This Project -----

Core Java object-oriented design

File-based data persistence using JSON

Resource loading using ClassLoader

Password security using BCrypt

Exception handling and debugging

Gradle build configuration

Git and GitHub workflow including .gitignore and merge conflict resolution

----- Future Enhancements -----

Migration to Spring Boot

RESTful API development

Database integration (MySQL or MongoDB)

Authentication using JWT

Improved exception handling and logging

Modular and scalable architecture

----- Author -----

Vaibhavi Tumsare
Java Backend Developer (Learning Spring Boot)
GitHub: https://github.com/Vaibhavitumsare
