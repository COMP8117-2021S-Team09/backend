# backend

Live API Docs https://tiffin-umbrella.herokuapp.com

# Dependencies
All the dependencies can be found in the pom.xml which are:
1. spring-boot-starter-data-mongodb (Database)
2. spring-boot-starter-web (Server)
3. spring-boot-starter-mail (Emails)
4. springdoc-openapi-ui (Documentation)
5. spring-boot-starter-test (Testing)
6. lombok (Boilerplate code)

# Basic Flow
http request -> controller -> service -> repository -> return to parent callers -> http response

# Quick start
## Start from scratch and run the APIs on your local system
1. Take a pull from the main branch by cloning the repo in your local system
2. Make sure you've got mvn installed with version 3.5+ with `mvn -version`
3. Make a JAR by `mvn clean install` command to be used for deploying or testing
4. Use `-DskipTests` to skip the tests while running any mvn command to reduce package time

## running the ready-to-go JAR (or use the JAR you created above)
1. Download the jar from the releases https://github.com/COMP8117-2021S-Team09/backend/releases
2. Make sure you have java installed with version 8 (preferable) - check version `java -version`
3. Run the `java -jar first_release_1-0.0.1-SNAPSHOT.jar` command where you have kept the JAR (assuming you are in home location and have not changed the JAR name)
4. Open the link http://localhost:8080/ and the API docs should open up where you can try out the APIs
5. If there is already some process running on port 8080, you can switch it by command `java -jar first_release_1-0.0.1-SNAPSHOT.jar --server.port=8091` to start the same JAR on 8091 instead of default 8080 port
