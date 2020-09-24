
call mvnw clean
call mvnw install -Dmaven.test.skip=true


copy application.properties target/application.properties

call docker stop prueba
call docker rm prueba


call docker build -t pruebatecnica .
call docker run -d -p 8080:8080 --name prueba pruebatecnica
