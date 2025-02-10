# User Assignment API

## Overview

This project is a Spring Boot application that provides a RESTful API for loading and searching user data. The user data is loaded from an external API (`https://dummyjson.com/users`) into an in-memory H2 database, and the API offers functionality to search users by first name, last name, SSN, and retrieve them by ID or email.

### Technologies Used:
- **Spring Boot**: For building the RESTful API.
- **H2 Database**: In-memory database to store user data.
- **Hibernate Search (Lucene)**: For implementing full-text search capabilities on user data.
- **Swagger/OpenAPI**: For API documentation.
- **JUnit**: For unit testing.

## Features

1. **Load User Data**: Loads user data from the external API into the in-memory H2 database.
2. **Search Users**: Allows free-text search on users by first name, last name, and SSN.
3. **Get User by ID**: Retrieve a user by their unique ID.
4. **Get User by Email**: Retrieve a user by their email.

## Setup and Running the Application

1. **Clone the Repository
2. **Build and Run the Application
3. **The application will be available at http://localhost:9090