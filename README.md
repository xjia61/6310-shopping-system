##  Grocery Express Web Application


### Overview
The Grocery Express Web Application satisfies the client's requirements. In addition, four significant architectual improvements were made:
  1) Performance: Utilized in memory database for better performance. The application is integrated with Redis cache, and minimizing the amount of network calls by using cache rather than sending request to the database
  2) Configurability: Built a front-end UI with thymeleaf. The web UI will make our application more user friendly. We decide to use Configurability to replace our proposal of Robustness because we find that a better UI will be more helpful for us to test the project and easier for other users to use it in reality.
  3) Authentication/Authorization: See Steps 6.
  4) Security: See Steps 6.

## Requirements

* MySQL Database
* Java 11+
* JAVA IDE: e.g., IntelliJ IDEA
* Maven 3.0+
* Redis 6.2+

## Maven Spring Dependencies
* Spring Web: to build restful web app.
* Spring Data JPA: persist data in SQL store.
* MySQL Driver
* Spring Security: authentication, authorization, encryption
* Spring Data Redis: in memory database for better performance
* Thymeleaf: html template engines.

## Steps:

1) Open the project in IDE:
2) Under src/main/resources, change the application properties:
   1) Followings are the project default settings: 
    ```md
    spring.datasource.url=jdbc:mysql://localhost:3306/6310Team045?useSSL=false
    spring.datasource.username=root
    spring.datasource.password=password
    ```
    __Change the default database username (`root`), password (`password`), and mysql server port (`3306`) to match 
your own mysql server!__
3) Create a database for the application:
    1) Open a terminal (command prompt in Microsoft Windows) and open a MySQL client as a user who can create new users. 
    2) For example, on a Linux system, use the following command (replace root with your username):``$ mysql -u root -p``
    3) To create a new database, run the following commands on mysql CLI: 
   ```
   mysql> Create database 6310Team045;
   mysql> Use 6310Team045;
    ```
   4) You can use the mysql GUI tool such as MySQL Workbench, just create a database `6310Team045` which the 
   spring web application can run on.
4) Start Redis server
   1) If Redis is not installed: Download from [Redis website](https://redis.io/), or type `brew install redis` on command if using Homebrew 
   for Mac and Linux.
   2) Type `redis-server` or `brew services start redis` on command line to start the Redis server. 
   The application uses the default Redis port of 6379.
   3) Use `redis-cli monitor` in a terminal window to log Redis requests.
   4) You can confirm the Redis cache is being utilized on repeated requests when there is no 'SET' invocation 
   following the initial 'GET' request.
5) Run application:
   1) Run the spring application in IDE. Another option is run the jar file, 
   using the following command `mvn package` to first create the jar file, 
   then run the jar file by `java -jar target/Team045-0.0.1-SNAPSHOT.jar`. 
   2) [Homepage](`http://localhost:8080`) at `http://localhost:8080`
   3) From homepage, you can login into the system as `USER` or `ADMIN`. Details see Steps 6 below.
   4) __Note: Only admin user has the authority to create customer user account in current version due to the 
   restriction on setting user `credit`.__
6) User authentication and authorization and password encryption:
   1) By default, there are two types of users: ``ADMIN`` and ``USER``. The authenticating users information are 
   stored in `auth_user` table.
      1) Customer users (`USER`) are allowed to `display my orders`, `start new order`, `request item`, `purchase order`, 
      and `cancel order`.
      2) In addition to all `USER`'s rights/privileges, admin users (`ADMIN`) are allowed to `make store`, `make pilot`, `make drone`, `stock item`, 
       `display stores`, `display customers`, `display pilots`, `display items of a store`, `display drones of a store`, `display orders of a store`.
   3) The authenticating user password is encrypted by `BCryptPasswordEncoder`. Details see [BCryptPasswordEncoder](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/crypto/bcrypt/BCryptPasswordEncoder.html) documentation.
   4) Once the application has started, you need to create a root ``ADMIN`` user (the username and password are both `admin`):
      ```sql
      INSERT INTO auth_user (
      username,
      password,
      role
      )
      VALUES
      ("admin", "$2a$12$Qpk7f3lC63mOmqejFmPVZukoBVqEQp0CSSHunIYhPdtdLGVxhxwdO","ADMIN");
      ```
7) When finished:
   1) Close the spring web server
   2) Shut down redis server: `ctrl + c`
   3) Shut down mysql server if it is no longer be needed.
