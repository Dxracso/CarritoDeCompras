# CarritoDeCompras
Se requiere de usar el metodo POST en http://localhost:8080/login enviando 'user' y 'password' para obtener token
Se requiere enviar a cualquier recursi el Header 'Authorization' con el token
Los usuarios y contraseñas se encuentan en 'pruebaTecnica/src/main/resources/static/usuarios.json'

La prueba se realizaron usando postman y adjunt esas evidencias, por cuestión de tiempo no pude realizarlas con jUnit
La base de datos Seleccionada es PostgresSQL y sus configuraciones se encuentran dentro del archivo application.properties


4)Respuesta Al problema planteado: Se puede mejorar la autenticación JWT mediante el uso de una base de datos o un servicio que sea proveedor de los usuarios registrados y no tenerlos en un archivo de texto como es el caso, en cuanto a la optimizacion se puede pensar en abandonar el modelo monolitico para el backend y implementar colas y/o balanceadores

Se impementara la solucion con dockerHub usando 'docker pull dxracso/carritocompras' 

Gracias por ver
