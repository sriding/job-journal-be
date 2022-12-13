# Requirements

**Using this application requires the creation of a .env file in the resources folder found in the main folder.** This .env file must contain the following key value pairs (more information below):

```
AUTH0_DOMAIN=
AUTH0_ISSUER-URI=https://AUTH0_DOMAIN/
AUTH0_AUDIENCE=
MYSQL_URL=jdbc:mysql://HOST:PORT/DATABASE_NAME
MYSQL_USERNAME=
MYSQL_USER_PASSWORD=
FRONT_END_URL=http://localhost:3000
```

This application uses Auth0 for authentication purposes. You will need to create an account on Auth0 and login. Once you have done that, you can head to the Applications section and click on the Default App. The default app will give you the domain value. Replace the AUTH0_DOMAIN in the issuer-uri with this value. After, switch to the APIs section and then head to the APIs section and go to the Auth0 Management API and grab the identifier url and use that for the audience key.

This applicaiton makes use of MySQL database management software. Replace the host, port, and database_name in the the mysql_url with your own personal database information. Pick a user to use and enter their information for the username and user_password.

**This repository is only the back-end of the job journal web application.** It acts as a REST API and receives requests from the front-end. For the front end url, it will either be localhost on some port for development or a custom domain if deployed online.
