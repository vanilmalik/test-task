# test-task

To run this project you need to install git, maven and docker

## 1 Run maven command
* Clone project using git: ```git clone https://github.com/vanilmalik/test-task.git``` 
* Open project
* Open console and run : ```mvn clean install```
## 2 Run Docker
run: ```cd docker```
run: ```docker-build-run.bat``` if you are working on Windows machine
    or  ```docker-build-run.sh``` if you are working on Linux machine
## 3 Stop application
To stop application run: ```docker stop test-task```