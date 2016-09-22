﻿#Anotaciones

####[28/07/2016]:[19:14]

Hemos optado por cambiar el driver de la base de datos de mongodb de la versión 3.2.0 a la 2.14.0 razones:

	- Se genera una excepción en la versión 3.2.0 llamada MongoSocketOpenException cuando se intenta utilizar la conexión con la base de datos estando esta desconectada,Esta excepción no la pudimos capturar.
	- La versión 2.14 viene de la versión 2.11 y de esta se encuentra mayor información sobre su implementación que la 3.2.0.

####[29/07/2016]:[0:11]

* Hemos logrado conectar la base de datos con la clase del BRT Bus para consultar los detalles precisos del bus.

* Definimos que todos los nombres de los nombres clave, variables, campos y parámetros tanto de JSON como de JAVA las vamos a escribir en minúscula, mientras que en la Base de datos los String Key tendrán mayúscula inicial.

* El bus debe existir para poder modificar sus valores.

* Se implementaron los métodos cerrarConexion(), actualizaMDB, insertarMDB y consultarMDB, donde los 3 últimos son transacciones simuladas con mongo para parecerse a las bases de datos relacionales.

* Cuando se hace una consulta utilizando un objeto DBCursor, los elementos lo almacena desde cero, pero en la posición cero esta vacía, es por esto que siempre que se vaya leer una variable se requiere comenzar aplicando el método Next() al objeto DBCursor y posteriormente el método get(String Key); esta esta cadena de métodos retorna un objeto tipo Object casteable u otro DBCursor, ahora bien una vez utilizado el Next() para leer los elementos del mismo documento se debe usar objeto.curr().get(String Key).

* Queda pendiente hacer resistente a fallos todas las Clases implementadas y documentar las clases restantes y nuevas.

* El Bus ahora tiene la capacidad de crear un Objeto Json de sí mismo y actualizarlo con el método actualizarJsonBus(), en caso de que reciba modificaciones.

* UbicacionBus ya no retorna un String con formato Json con las coordenadas promedio, ahora retorna un String con formato Json con la información del bus obtenida en el post y consultada en la base de datos.

* Se modificó el formato de envió de ubicacionBus por el siguiente:
```javascript
{
  	"placa": "ZOE 101",
	 	"coordenada":{
    		"latitud": "7.113633",
		"longitud":"-73.114842"
			}
  	
}
```
Teniendo en cuenta que la placa debe existir en la base de datos para no lanzar una excepción.

##RECORDATORIO: 
- Cuando se haga un CLONE al repositorio, CAMBIAR el nombre WebServicesTest por rutasBuses


[30/07/2016]:[14:26] Se añade un servicio post de prueba para Wilson testear la galileo.

[06/08/2016]:[11:00] El bus ahora solo crea un objeto json si se lo piden y se modificó el método consultarMDB para que este devuelva un objeto DBOBject y no un apuntador. Esto con finalidad de facilitar el código y evitar confusiones con el método .next() también para identificar cuando el objeto que quiere consultar no está en la base de datos. Además se creó la clase BusDB que es la clase que se encargara de hacer interactuar el bus con la base de datos.

Clases que se modificaron:

BusDB
ConectarMongo
Bus
UbicacionBus

Tareas pendientes:

Implementar la clase BusDB.
Hacer refactor para trabajar con la librería (https://json-processing-spec.java.net/nonav/releases/1.0/fcs/javadocs/index.html)

Se creó el paquete baseDeDatosMDB para colocar las clases que intervienen con la base de datos y se movió 
BusDB y ConectarMongo

[06/08/2016]:[18:00]
-Se creó la clase BusDB que es la encargada de obtener los valores de la base de datos del bus.
-Se removió la excepción de MongoTimeOut de Conectar mongo y se creara el try catch solo cuando se invoque el servicio que la lance.
-Se removió el método existe() de BusDB y su funcionalidad se delegó a valoresBaseDatos().

Clases que se modificaron:

BusDB
ConectarMongo
Bus
UbicacionBus
MensajeError

Tareas Pendientes:
Implementar querys en BusDB
[23/08/2016]:[23:21]
Se implementó la funcionalidad para determinar si un bus se encuentra fuera o dentro de una estación (área circular)
Para lograrlo se creó una clase de utilidad que contiene el radio de la circunferencia y calcula si el punto (bus) esta fuera o dentro de este radio.
Se creó un nuevo servicio que hace uso de la funcionalidad descrita anteriormente. El formato es:
```[JSON]
{
    "coordenada1" : {"latitud":"7.137157","longitud":"-73.122247"},
    "coordenada2" : {"latitud":"7.136681","longitud":"-73.122551"}
}
```
Y la URI del servicio es: http://localhost:8080/NOMBREDEPROYECTO/apirutas/Proximidad/estaDentro
Ej. http://localhost:8080/rutasBuses/apirutas/Proximidad/estaDentro
[24/08/2016]:[15:56]
Se creó una documentación inicial en Excel acerca de todos los servicios implementados hasta el momento. Por el momento
Se llevara en el Excel. Más adelante se creara una documentación con un framework.

[24/08/2016]:[19:11]
Se implementó un nuevo paquete clientes con una clase llamada cliente que permite crear un cliente que consume un servicio desde una url. Además se modificó el servicio Dbtest para probar el funcionamiento de esta clase.

[24/08/2016]:[19:47]
Se modificó el servicio que utiliza el cliente.

[25/08/2016]:[12:10]Reestructuración del proyecto para seguir el estándar de maven.

[26/08/2016]:[14:20]Se crea el directorio src/test/java para que se puedan hacer pruebas al código. Además se actualizo la documentación con las nuevas url.

[31/08/2016]:[00:02]Se creó la función que permite proyectar un punto sobre una recta y una clase para probar cosas de la base de datos.

[31/08/2016]:[17:51]Crear un método que permita añadir paradas intermedias sobre rutas previamente creadas.

[01/09/2016]:[11:35]Interacciones con la base de datos

[20/09/2016]:[16:15]Corrección de un bug que impedía el acceso a los servicios de forma correcta. Esto se corrigió creando dentro de la carpeta WebContent archivos necesarios para la configuración de los servicios. 

[20/09/2016]:[19:23]Se crearon métodos para insertar elementos en distintas posiciones o al final. Además se implementaron los métodos eliminarRuta para eliminar una ruta completa y eliminarXPosicionParada que elimina una parada de una ruta por medio de un índice llamado posición y para concluir se implementó imprimirRutas que imprime las rutas con información detallada, sin embargo aún está en revisión.
A partir de este momento se empezó a utilizar una UI llamada "Robomongo" para visualizar la base de datos MongoDB  : https://robomongo.org/

[21/09/2016]:[15:10] Se agregó el método eliminarParada.
Se realizó cambios den los métodos eliminarRuta y paradas para que retornen un mensaje que avise si pudo ser eliminado.
Se ha añadido una nueva clase de utilidad llamada FormtearDatos, para tratar las entradas de usuario.

[21/09/2016]:[19:33] Se corrigió la documentación ahora la placa no lleva espacios.
En bus DB se corrigió el casteo doble de double a int, ahora solo se hace el casteo a int, esto gracias a que logramos insertar enteros en la base de datos.
Se hizo refactor a ConectarMongo para que las transacciones básicas no soliciten la base de datos, el nombre de la base de datos GeneralBRT está definido en conectar mongo como final.
Se creó Transacciones Bus y TransaccionesRutas son clases con métodos estáticos que se encargan de manejar las operaciones validas que se le pueden aplicar a un bus y una ruta en una base de datos.
En las clasesDelBRT se agregó la clase paradas.
En baseDeDatosMDB se agregaron ParadaDB y RutaDB para ser utilizadas de la misma manera que BusDB.

[21/09/2016]:[20:44]
Se eliminaron las clases Eliminar e Insertar.
Se implementó la clase TransaccionParada.
Se creó otro método a FormatearDatos.
Se creó una clase main para probar cambios en las clases y nuevas clases antes de ponerlas a interactuar con otras.




