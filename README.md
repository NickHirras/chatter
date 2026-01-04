# Chatter

A simple Java web application using Spring Boot, SQLite, and JSP.

## Prerequisites

- Java 25 (Project is configured for Java 21 target compatibility)
- Maven 3.9+

## Building the Project

To build the project, run:

```bash
mvn clean package
```

This will create an executable JAR file in the `target` directory.

## Running the Application

You can run the application using the Spring Boot Maven plugin:

```bash
mvn spring-boot:run
```

Or by running the built JAR file directly:

```bash
java -jar target/chatter-0.0.1-SNAPSHOT.jar
```

The application will start on port 8080.
Access the home page at: [http://localhost:8080](http://localhost:8080)

## Project Structure

- `src/main/java`: Java source code
- `src/main/resources`: Configuration files
- `src/main/webapp`: JSP templates
