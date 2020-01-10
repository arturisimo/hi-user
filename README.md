## Simple JAVA Http Server

This application have 3 different private pages and a login form. In order to access any of these private pages the user need to have a session started through the login form and to have the right role to be able to access the page.

The application will also have a REST API endpoint exposing the User resource. Creating, deleting and modifying users and their permissions will be done through this API.

**Download**

> git clone https://github.com/arturisimo/hi-user.git

**Build**

> cd hi-user/

> mvn clean install resources:resources

**Run**

> cd target/

> java -jar hi-user-0.0.1-jar-with-dependencies.jar

**Web Application**

> [localhost:8000](http://localhost:8000) - user/password: pepe/pepe

**API Users**

> [localhost:8000/users](http://localhost:8000/users) - user/password: admin/admin

**Dependencies**

Jackson API for JSON 
