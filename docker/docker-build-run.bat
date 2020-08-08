xcopy ..\target\*.jar /y
docker build -t test-task .

erase /F *.jar
docker run -d -p 8081:8081 --name test-task test-task