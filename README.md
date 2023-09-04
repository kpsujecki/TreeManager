[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

Minimal [Spring Boot](http://projects.spring.io/spring-boot/) sample app.

## Requirements

For building and running the application you need:

- [JDK 19](https://www.oracle.com/java/technologies/javase/jdk19-archive-downloads.html)
- [Maven 4](https://maven.apache.org)
- [HSQLDB](https://hsqldb.org/)
- [Spring Boot 3](http://projects.spring.io/spring-boot/)
## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `org.sujecki.TreeManagerApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

## Prepare application.properties file

In order for the application to work properly, the application.properties file must be completed. This is where we complete the database information.
```shell
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.hibernate.dialect=org.hibernate.dialect.HSQLDialect
spring.jpa.database= HSQL

spring.datasource.url=jdbc:hsqldb:mem:testdb
spring.datasource.username=sa
spring.datasource.password=
spring.datasource.driverClassName=org.hsqldb.jdbcDriver
```
## Database

This application uses HSQLDB database in 'in-memory' mode by default.
There is a schema.sql file in the project, which is what creates the database schema, and then through properties ```spring.jpa.hibernate.ddl-auto=validate``` is further validated.
Also included is the basic data from the data.sql file

## Endpoints

All endpoints can be displayed using Swagger, which is attached to the project. Just follow the link below. It depends on the port on which the application is running
http://localhost:8080/swagger-ui/index.html

At this address, you can see a page that displays JSON with the entire tree hierarchy from the root:

http://localhost:8080/viewTree

## TODO
* Improve frontend app 
* Add move/copy node/leaf in any place in tree
* Possibility to create multiple trees
* 
## Copyright

Released under the Apache License 2.0. See the [LICENSE](https://github.com/codecentric/springboot-sample-app/blob/master/LICENSE) file.
