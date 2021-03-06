## Implement a Web Application using the Java language.


**Functional Requirements**

The necessary roles to access each page are the next ones:

* Page 1: In order to be able to access this page the logged user needs to have the role PAGE_1
* Page 2: In order to be able to access this page the logged user needs to have the role PAGE_2
* Page 3: In order to be able to access this page the logged user needs to have the role PAGE_3

There's also an ADMIN role that means the user can modify and create other users through the REST API. The other users can only read through it

Each page will simply show the name of the page the user is accessing and the text “Hello {USER_NAME}”.

All pages will also have a link/button to close the user session.

In the case of accessing any of these private pages without a logged session the application will redirect the user to the login form and once a success user login is performed the application will redirect the user to the page it was trying to reach before being redirected to the login form.

In the case of accessing any of these private pages with a logged session but without the necessary role to access the page the application will not allow the user to see the page returning an appropriate status code indicating that access was denied.

The user model will have a `username` field, a `roles` field and a `password` field. The password field is write only and will not be exposed on read operations

There will be a minimum of 3 users, each of them with a different role. There might be users with several roles.

The user session will expire in 5 minutes from the last user action.

The REST API will use the same credentials used in the login form, but no session is needed. The authentication will be done using HTTP basic authentication

All unauthorized, forbidden or resource unknown responses must honor the correct status code [httpstatuses.com](https://httpstatus.es)


**Technical requirements**

You need to use the com.sun.net.httpserver.HttpServer class to create a server. The use of an application server or servlet container is not allowed.

You cannot use any framework.

It is not necessary to have the users and roles in a database, it will be enough having them in memory, a text file or similar (Although the use of an embedded database is ok)

The execution of the application will be through a runnable jar that will allow us to start it through command line (“java -jar test-web-application.jar”) without having to use an application server or container

The inclusion of unit test covering a good percentage of code will be a plus

The use of design patterns like MVC will be a plus. Code maintainability is very important for us

Using content negotiation when delivering the API resources will be highly appreciated.

Using reactive programming when/if it fits will be appreciated

The application should have a dependency and build management system (gradle, mvn) to allow straightforward compilation and

Delivery will be done uploading the code in a public git repository system like github or bitbucket.

The git repository needs to have a clear README file explaining how to build and run the application and its tests along with any other characteristic worth to mention.
