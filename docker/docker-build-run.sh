cd "$PWD" || { echo "Cant't read directory"; exit 1; }

cp -r ../target/*.jar .
docker build -t test-task .

rm -f ./*.jar
docker run -d -p 8081:8081 --name test-task test-task