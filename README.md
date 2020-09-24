# CarritoDeCompras
Se requiere de usar el metodo POST en http://localhost:8080/login enviando 'user' y 'password' para obtener token
Se requiere enviar a cualquier recursi el Header 'Authorization' con el token
Los usuarios y contraseñas se encuentan en 'pruebaTecnica/src/main/resources/static/usuarios.json'

La prueba se realizaron usando postman y JUnit 5 esas evidencias se encuentan en la carpeta test de ra rama principal
La base de datos Seleccionada es PostgresSQL y sus configuraciones se encuentran dentro del archivo application.properties

Para implementar la solucion en Docker se debe de modificar la direccion de la base de datos en el archivo 'application.properties' parametro 'spring.datasource.url:' y copiarlo a la raiz del proyecto
ademas de ejecutar el Script 'dockerInstal.bat' el cual carga la configuracion de 'Dockerfile' y lanzara los servicios en el puerto 8080

Recordar que se debe de configurar la base de datos para que admita conecciones externas para el contenedor de docker

4)Respuesta Al problema planteado: Se puede mejorar la autenticación JWT mediante el uso de una base de datos o un servicio que sea proveedor de los usuarios registrados y no tenerlos en un archivo de texto como es el caso, en cuanto a la optimizacion se puede pensar en abandonar el modelo monolitico para el backend y implementar colas y/o balanceadores

Usar Java 8
Los logs son parametrizados por el achivo 'resources/log4j2.xml'

Gracias por ver
Att Oscar Andres Hernandez Ariza