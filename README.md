# Spring Boot MicroService for grade management

# Table of contents

* [About the project](#question-about-the-project)
* [Getting started](#rocket-getting-started)
    * [Frameworks](#zap-frameworks)
    * [Prerequesites](#exclamation-prerequesites)
    * [Set up the database](#books-setup-the-database)
    * [Installation](#hammer-installation)
        * [Setup with Docker (Recommended)](#whale-setup-with-docker-recommended)
        * [Setup with Gradle](#elephant-setup-with-gradle)
    * [Run](#runner-run)
        * [Run with Docker (Recommended)](#whale-run-with-docker-recommended)
        * [Run with Gradle](#elephant-run-with-gradle)
    * [Run tests](#game_die-run-tests)
* [Tree structure](#deciduous_tree-tree-structure)
* [API](#boom-api)
* [Database](#books-database)
* [Acknowledgments](#pencil-acknowledgments)
* [License](#lock-license)

## About the project

The purpose of this project is to create a grade management for a school, it can be used by teachers to assign grades to student in a particular courses.

It can also be used for the students to check their grades. 

These are the main features of the project.

## Getting started

### Frameworks

The project is built using the **Java Programming Language** under the **Spring Boot** framework.

Spring Boot is a framework used to build stand-alone application and production ready spring applications. It facilitates the way to launch and deploy API REST based application without huge configurations.

For more information about Spring Boot, view the [official documentation][spring-boot].

### Prerequesites

Before launch the application, you have to satisfy the following requirements.

[Git][git] must be installed and configured on your machine in order to clone the repository and pull the new updates.

A [MySql][mysql] server must be installed on your machine so that the application can connect to the database. It is recommended to have a good knowledge of relational databases. Click here to download [MySql community server][mysql-download].

To increase flexibility and portability, we used [Docker (Desktop)][docker] to contain and launch our service on several devices. If you want to deploy your application using containers, you must have Docker installed on your machine. Otherwise, if you don't want to use Docker you can just install a valid Java JDK and launch it with Maven.

### Setup the database

Before to launch the application, you must create your database. For that, use the [MySQL database script][database-script]. If you run this file in MySQL Workbench, it will automatically create the database with all its tables.

When the database is set on your server, you must configure the database informations so that the application can connect to it. The configurations must be put in the folder [ressource][configuration-folder] with the name `application.properties`.

You must specify the following parameters:

* application-port
    * Port used by your application. `8081` by default.
* database-ip
    * IP address or domain name of your database
* database-port
    * `3306` by default but we will use the 3307 port because we will MySQL in a Docker container (we will explain you how to setup it after)
* database-name
* database-username
* database-password
* application-name

This is the example of our `application.properties` :
```properties
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:${MYSQL_PORT:3307}/spring?allowPublicKeyRetrieval=true&useSSL=false
spring.datasource.username= ${MYSQL_USER:root}
spring.datasource.password= ${MYSQL_USER:root}

spring.jpa.properties.hibernate.dialect= org.hibernate.dialect.MySQL5InnoDBDialect
spring.jpa.hibernate.ddl-auto= update
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.show-sql=true
spring.jpa.properties.dialect= true
server.port=8081
```

### Installation

#### Setup with Docker (Recommended)

Install MySQL in a Docker Container

```bash
docker run -p 3307:3306 -d --name mysqldb -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=spring mysql
```

Now you have to click on Maven (on the right on IntelliJ) -> LifeCycle -> Install

Create a network to link your Spring Boot application and the MySQL in the Docker container.

```docker
docker network create spring-net
```

Connect the MySQL in the network

```docker
docker network connect spring-net mysqldb
```

Now you can build your Docker image, the build uses the `Dockerfile` to create the Docker image of the Spring Boot application

```docker
docker build -t app .
```

Now we can run this `app` Docker image

```docker
docker run -p 9090:8081 --name app --net spring-net -e MYSQL_HOST=mysqldb -e MYSQL_USER=root -e MYSQL_PASSWORD=root -e MYSQL_PORT=3306 app
```

Now you can use the Spring Boot application linked to the MySQL, everything is contained in Docker and run on the port 9090

To check all the API's : Visit `http://localhost:9090/swagger-ui/index.html#/`

Don't forget it's a snapshot of your code since you click on `Install` in Maven.
If you change the code, you have to reinstall.

#### Setup with Maven

If you don't want to install the application with Docker, for example if you are a developper to the application, you can use Maven instead.

You can click on `GradeManagementApplication.java` (don't hesitate to check the Tree Structure bellow) and click on run directly the project on the top right. <br/>
Be careful, you also need to pull the MySQL docker image `docker run -p 3307:3306 -d --name mysqldb -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=spring mysql`

### Run tests

The unit test framework used is [JUnit][junit].

To run the tests, we run the project in the `GradeManagementApplicationTests.java`

## Tree Structure

```bash
GradeManagement
    ├───.mvn
    │   └───wrapper
    └───src
        ├───main
        │   ├───java
        │   │   └───GradeManagement
        │   │       └───GradeManagement
        │   │           ├───controller
        │   │           ├───exception
        │   │           ├───model
        │   │           ├───repository
        │   │           └───service
        │   └───resources
        │        └───application.properties
        └───test
            └───java
                └───GradeManagement
                    └───GradeManagement
                        └───controller
```

## API

For our API REST, we generate our documentation schema using OpenAPI. We have done all of our tests using [Swagger UI][swagger-ui].

We integrated the database in our code using [JPA][jpa].

The OpenAPI schema is in [OpenAPI.yml][open-api-schema].

You can reach all the API's with `http://localhost:9090/swagger-ui/index.html#/`

## Database

As explaing above, we use a relational database such as MySQL or MariaDB.

Here is the database relational diagram used in the project.

![Database diagram][database-diagram]

## Code documentation

The code is documented using `Javadoc`. You can view the code documentation by loading [index.html][code-documentation] file.

### Class

**WIP**

## Acknowledgments

* [Spring Boot Reference Documentation][spring-boot-documentation]
* [Spring Framework Documentation][spring-documentation]
* [Docker Documentation][docker-documentation]
* [JUnit User Guide][junit-guide]
* [Postman][postman]
* [Introduction to Spring Data JPA][jpa-tutorial]

## License

Distributed under the AGPL-3.0 License. See [LICENSE.md][license] for more information.

<!-- Internal file links -->
[configuration-folder]: ./ms_studenthelp/src/main/resources/
[open-api-schema]: ./OpenAPI.yml
[database-diagram]: ./ms_studenthelp.png
[license]: ./LICENSE.md
[database-script]: ./ms_studenthelp.sql
[code-documentation]: ./JavaDoc/index.html

<!-- Links -->
[spring-boot]: https://spring.io/projects/spring-boot
[spring-boot-documentation]: https://www.google.com/url?sa=t&rct=j&q=&esrc=s&source=web&cd=&cad=rja&uact=8&ved=2ahUKEwiM1Z3Gg777AhUKiP0HHb88AK8QFnoECBYQAQ&url=https%3A%2F%2Fdocs.spring.io%2Fspring-boot%2Fdocs%2Fcurrent%2Freference%2Fhtmlsingle%2F&usg=AOvVaw1hehprHejWPlOVUg-kvg1V
[git]: https://git-scm.com/
[mysql]: https://www.mysql.com
[mysql-download]: https://dev.mysql.com/downloads/mysql/
[docker]: https://www.docker.com/products/docker-desktop/
[docker-documentation]: https://docs.docker.com/get-started/
[swagger-ui]: https://swagger.io/tools/swagger-ui/
[api-documentation]: https://beta.bachelay.eu/ms-studentHelp
[postman]: https://www.postman.com/
[spring-documentation]: https://docs.spring.io/spring-framework/docs/current/javadoc-api/index.html
[junit]: https://junit.org/junit5/
[junit-guide]: https://junit.org/junit5/docs/current/user-guide/
[jpa]: https://spring.io/guides/gs/accessing-data-jpa/
[jpa-tutorial]: https://www.baeldung.com/the-persistence-layer-with-spring-data-jpa