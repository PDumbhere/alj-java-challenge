### How to use this spring-boot project

- Install packages with `mvn package`
- Run `mvn spring-boot:run` for starting the application (or use your IDE)

Application (with the embedded H2 database) is ready to be used ! You can access the url below for testing it :

- Swagger UI : http://localhost:8080/swagger-ui/index.html
- H2 UI : http://localhost:8080/h2-console

> Don't forget to set the `JDBC URL` value as `jdbc:h2:mem:testdb` for H2 UI.

### Changes Made By me

- I have added the security to the end points by using JWT Token authorisation
- This is role based authentication. So the user having "Admin" role can be able to perform all operations. While the user having "User" role will be only able to retrive employee information.
- Admin user will be created at the time of application execution.
- In order to use the APIs first the user should be registered. User Registration can be executed by Admin only.
- The registered user need to login using login endpoint and retrive JWT token. The JWT token can be used as a authentication token while using the APIs.
- Also I have implemented Exception handling.

- Admin Credentials are as follows:
    - Username - "admin"
    - Password - "admin"

### More Changes I might have made

- More documentation and comments
- More validation to the API requests.
- Added test cases for the purpose of unit testing.

### Instructions

- download the zip file of this project
- create a repository in your own github named 'java-challenge'
- clone your repository in a folder on your machine
- extract the zip file in this folder
- commit and push

- Enhance the code in any ways you can see, you are free! Some possibilities:
  - Add tests
  - Change syntax
  - Protect controller end points
  - Add caching logic for database calls
  - Improve doc and comments
  - Fix any bug you might find
- Edit readme.md and add any comments. It can be about what you did, what you would have done if you had more time, etc.
- Send us the link of your repository.

#### Restrictions
- use java 8


#### What we will look for
- Readability of your code
- Documentation
- Comments in your code 
- Appropriate usage of spring boot
- Appropriate usage of packages
- Is the application running as expected
- No performance issues

#### Your experience in Java

Please let us know more about your Java experience in a few sentences. For example:

- I have 2 years experience in Java and have worked spring boot and have experience creating Spring MVC applications.
# alj-java-challenge
