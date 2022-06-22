# Exchange Rates API

Exchange API is a Restful API providing exchange rates and conversions for many currencies.


## 1. Technologies

Exchange Rates API is a Spring Boot project written in Java 11 and it uses following:

- JDK 11
- Spring Framework
- Hibernate
- Maven
- Open API
- Docker
- H2 in memory Database


## 2. Running

The required docker codes to run are listed below;

	 docker build -t exchange-rate . 
     docker run -d -p 8080:8080 -t exchange-rate


## 3. Documentation

API documentation is managed by Spring Doc. To access Swagger UI, open /swagger.html in a web browser after running Exchange API. You can also find Open API specification at /docs as a Json.

## 4. Testing

Exchange API is built with Maven. You can use regular Maven tasks such as clean, compile, test tasks for development and testing.