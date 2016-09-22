#Anotaciones

####[28/07/2016]:[19:14]

Hemos optado por cambiar el driver de la base de datos de mongodb de la versión 3.2.0 a la 2.14.0 razones:

	Se genera una excepción en la versión 3.2.0 llamada MongoSocketOpenException cuando se intenta utilizar la conexión con la base de datos estando esta desconectada, Esta excepción no la pudimos capturar.
	
	La versión 2.14 viene de la versión 2.11 y de esta se encuentra mayor información sobre su implementación que la 3.2.0.

####[29/07/2016]:[0:11]

* Hemos logrado conectar la base de datos con la clase del BRT Bus para consultar los detalles precisos del bus.

* Definimos que todos los nombres de los nombres clave, variables, campos y parámetros tanto de JSON como de JAVA las vamos a escribir en minúscula, mientras que en la Base de datos los String Key tendrán mayúscula inicial.

* El bus debe existir para poder modificar sus valores.

* Se implementaron los métodos cerrarConexion(), actualizaMDB, insertarMDB y consultarMDB, donde los 3 últimos son transacciones simuladas con mongo para parecerse a las bases de datos relacionales.

* Cuando se hace una consulta utilizando un objeto DBCursor, los elementos lo almacena desde cero, pero en la posición cero esta vacía, es por esto que siempre que se vaya leer una variable se requiere comenzar aplicando el método next() al objeto DBCursor y posteriormente el método get(String Key); esta esta cadena de métodos retorna un objeto tipo Object casteable u otro DBCursor, ahora bien una vez utilizado el next() para leer los elementos del mismo documento se debe usar objeto.curr().get(String Key).

* Queda pendiente hacer resistente a fallos todas las Clases implementadas y documentar las clases restantes y nuevas.

* El Bus ahora tiene la capacidad de crear un Objeto Json de sí mismo y actualizarlo con el método actualizarJsonBus(), en caso de que reciba modificaciones.

* UbicacionBus ya no retorna un String con formato Json con las coordenadas promedio, ahora retorna un String con formato Json con la información del bus obtenida en el post y consultada en la base de datos.

* Se modificó el formato de envió de ubicacionBus por el siguiente:
```json
{
  	"placa": "ZOE 101",
	 	"coordenada":{
    		"latitud": "7.113633",
		"longitud":"-73.114842"
		}
}
```
Teniendo en cuenta que la placa debe existir en la base de datos para no lanzar una excepción.

####RECORDATORIO: 

	Cuando se haga un CLONE al repositorio, CAMBIAR el nombre WebServicesTest por rutasBuses


####[30/07/2016]:[14:26]

* Se añade un servicio post de prueba para Wilson testear la galileo.

####[06/08/2016]:[11:00]

* El bus ahora solo crea un objeto json si se lo piden y se modificó el método consultarMDB para que este devuelva un objeto DBOBject y no un apuntador. Esto con finalidad de facilitar el código y evitar confusiones con el método .next(), también para identificar cuando el objeto que quiere consultar no está en la base de datos.

* Se creó la clase BusDB que es la clase que se encargara de hacer interactuar el bus con la base de datos.

* Se creó el paquete baseDeDatosMDB para colocar las clases que intervienen con la base de datos y se movió 
BusDB y ConectarMongo

Clases que se modificaron:

	BusDB
	ConectarMongo
	Bus
	UbicacionBus

####Tareas pendientes:

	Implementar la clase BusDB.
	
	Hacer refactor para trabajar la libreria que sera puesta debajo.
	↓↓↓↓
	
[JSR 353 (JSON Processing) API 1.0 API](https://json-processing-spec.java.net/nonav/releases/1.0/fcs/javadocs/index.html "libreria")



####[06/08/2016]:[18:00]

* Se creó la clase BusDB que es la encargada de obtener los valores de la base de datos del bus.

* Se removió la excepción de MongoTimeOut de Conectar mongo y se creara el try catch solo cuando se invoque el servicio que la lance.

* Se removió el método existe() de BusDB y su funcionalidad se delegó a valoresBaseDatos().

Clases que se modificaron:

	BusDB
	ConectarMongo
	Bus
	UbicacionBus
	MensajeError

####Tareas Pendientes:

	Implementar querys en BusDB

####[23/08/2016]:[23:21]

* Se implementó la funcionalidad para determinar si un bus se encuentra dentro o fuera de una estación (área circular), Para lograrlo se creó una clase de utilidad que contiene el radio de la circunferencia y calcula si el punto (bus) esta dentro o fuera de este radio.
* Se creó un nuevo servicio que hace uso de la funcionalidad descrita anteriormente. El formato es:

```json
{
    "coordenada1" : {"latitud":"7.137157","longitud":"-73.122247"},
    "coordenada2" : {"latitud":"7.136681","longitud":"-73.122551"}
}
```
Y la URI del servicio es: http://localhost:8080/NOMBREDEPROYECTO/CARPETADELWEBXML/Proximidad/estaDentro
Ej. http://localhost:8080/rutasBuses/apirutas/Proximidad/estaDentro

Se crearon las siguientes Clases:

	AreaEstacion (Clase de Utilidad)
	Proximidad (Servicio)

####[24/08/2016]:[15:56]

* Se creó una documentación inicial en Excel acerca de todos los servicios implementados hasta el momento. Por el momento
Se llevara en el Excel. Más adelante se creara una documentación con un framework.

####[24/08/2016]:[19:11]

* Se implementó un nuevo paquete clientes con una clase llamada cliente que permite crear un cliente que consume un servicio desde una url. Además se modificó el servicio Dbtest para probar el funcionamiento de esta clase.

Clases que se modificaron:
	
	Dbtest (Servicio)

Clases que se crearon:

	HTTPClient ( Junto con su paquete Clientes)

####[24/08/2016]:[19:47]

* Se modificó el servicio que utiliza el cliente.

####[25/08/2016]:[12:10]

* Reestructuración del proyecto para seguir el estándar de maven.

####[26/08/2016]:[14:20]

* Se crea el directorio src/test/java para que se puedan hacer pruebas al código. Además se actualizo la documentación con las nuevas url.

####AVISO

	Angel, En este directorio es donde usted puede crear las clases para hacer pruebas.

#### [31/08/2016]:[00:02]

* Se creó la función que permite proyectar un punto sobre una recta y una clase para probar cosas de la base de datos.

Clases que se crearon:

	Geomatematicas (Clase de Utilidad)
	Insertar (BaseDeDatosMongoDB)

####[31/08/2016]:[17:51]

####Tareas Pendientes:

	Crear un método que permita añadir paradas intermedias sobre rutas previamente creadas.

####[01/09/2016]:[11:35]

* Interacciones con la base de datos dentro de la clase Insertar.

####[20/09/2016]:[16:15]

* Corrección de un bug que impedía el acceso a los servicios de forma correcta. Esto se corrigió creando dentro de la carpeta WebContent archivos necesarios para la configuración de los servicios, gracias a una consulta de angel y la ayuda del profesor Gabriel.

####[20/09/2016]:[19:23]

* Se crearon métodos para insertar elementos en distintas posiciones o al final.

* Se implementaron los métodos eliminarRuta para eliminar una ruta completa y eliminarXPosicionParada, esta ultima elimina una parada de una ruta por medio de un índice llamado posición.

* Se implementó imprimirRutas que imprime las rutas con información detallada, sin embargo aún está en revisión.

* A partir de este momento se empezó a utilizar una UI llamada [Robomongo](https://robomongo.org/ "Link de la pagina oficial de Robomongo") para visualizar la base de datos MongoDB.

Clases que se crearon:

	Eliminar

Clases que se modificaron:

	Insertar
	
####Tareas Pendientes:

	Implementar un metodo para listar los contenidos de la base de datos.	

####[21/09/2016]:[15:10] 

* Se agregó el método eliminarParada.

* Se realizó cambios den los métodos eliminarRuta y paradas para que retornen un mensaje que avise si pudo ser eliminado.

* Se ha añadido una nueva clase de utilidad llamada FormtearDatos, para tratar las entradas de usuario.

Clases que se Crearon:

	FormatearDatos (Clase de Utilidad)

Clases que se modificaron
	
	Eliminar


####[21/09/2016]:[19:33]

* Se corrigió la documentación ahora la placa no lleva espacios.

* En BusDB se corrigió el casteo doble de double a int, ahora solo se hace el casteo a int, esto gracias a que logramos insertar enteros en la base de datos.

* Se hizo refactor a ConectarMongo para que las transacciones básicas no soliciten la base de datos, el nombre de la base de datos GeneralBRT está definido en conectar mongo como final.

* Se creó TransaccionesBus y TransaccionesRutas son clases con métodos estáticos que se encargan de manejar las operaciones validas que se le pueden aplicar a un bus y una ruta en una base de datos.

* En las clasesDelBRT se agregó la clase paradas.

* En baseDeDatosMDB se agregaron ParadaDB y RutaDB para ser utilizadas de la misma manera que BusDB.

Clases que se crearon:

	TransaccionesBus (Clase de Base de Datos)
	TransaccionesRutas  (Clase de Base de Datos)
	ParadaDB  (Clase de Base de Datos)
	RutaDB  (Clase de Base de Datos)
	Paradas (Clase del BRT)
	
Clases que se modificaron:
	
	Eliminar
	BusDB
	ConectarMongo
	

####[21/09/2016]:[20:44]

* Se eliminaron las clases Eliminar e Insertar, debido a que sus metodos fueron llevados a las clases de transacciones.

* Se creo la clase TransaccionesParada.

* Se creó otro método a FormatearDatos, el cual hace que una oración tenga mayuscala inicial por cada palabra.

* Se creó una clase main para probar cambios en las clases y nuevas clases antes de ponerlas a interactuar con otras.

Clases que se crearon:

	TransaccionesParada	
	ClasesDePruebaCloud

Clases que se Modificaron:

	FormatearDatos

Clases que se eliminaron:
	
	Insertar
	Eliminar


####Tareas Pendientes

	Crear servicios que retorne una Ruta, una Parada y Un Bus con toda su información estatica.
	Crear servicios que retorne todas las Rutas, Paradas y Buses con la informacion estatica de cada uno de ellos.
	Crear servicios para que usen los metodos que creamos para interactuar con la base de datos.


####[21/09/2016]:[23:19]
	
* Se crearon dos nuevos directorios que contendran las clases de prueba de Angel, Andres y Giovanni.

* Se modifico el nombre de la clase de pruebas de ClasesDePruebaCloud a Test (Sujeto a futuros cambios).

* Se movio la clase Test a src/test/java/AndresGiovanni.

* se movio la clase ClasesDePruebaAca a src/test/java/Angel.

####[22/09/2016]:[03:53]

* Se crearon las clases GetServicioBus, GetServicioRuta, GetServicioParada y se implementaron los cascarones de los servicios de obtencion con y sin parametros encargados de recibir informacion desde la base de datos.

####Tareas Pendientes

	Implementar los cascarones de los servicios para la obtencion de informacion tanto para bus, parada y ruta.
	Crear servicios para que usen los metodos que creamos para interactuar con la base de datos.
	Documentar los nuevos servicios que se implementen.


