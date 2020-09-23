
call mvnw clean install

copy application.properties target/application.properties

call docker stop sesiones-systig
call docker rm sesiones-systig

call docker build -t sesiones .

call docker run -d -p 8080:8080 --name sesiones-systig sesiones
stop