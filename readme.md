﻿#Anotaciones

[28/07/2016]:[19:14]Hemos optado por cambiar el driver de la base de datos de mongodb de la version 3.2.0
a la 2.14.0
	Razones:
		- Se genera una excepción en la version 3.2.0 llamada MongoSocketOpenException
		cuando se intenta utilizar la conexión con la base de datos estando esta desconectada.
		Esta excepción no la pudimos capturar.
		- La version 2.14 viene de la version 2.11 y de esta se encuentra mayor información
		sobre su implementación que la 3.2.0.

[29/07/2016]:[0:11] Hemos logrado conectar la base de datos con la clase del BRT Bus para consultar los detalles
prescisos del bus.

Definimos que todos los nombres de los nombres clave, variables, campos y parametros tanto de JSON como de JAVA las vamosa 
escribir en minúscula, mientras que en la Base de datos los String Key tendran mayúscula incial.
El bus debe existir para poder modificar sus valores.

Se implementaron los metodos cerrarConexion(), actualizaMDB, insertarMDB y consultarMDB, donde los 3 últimos
son transacciones simuladas con mongo para parecerse a las bases de datos relacionales.

Cuando se hace una consulta utilizando un objeto DBCursor los elementos los almacena desde cero, pero en la posicion
cero esta vacia, es por esto que siempre que se vaya leer una variable se requiere comenzar aplicando el metodo 
Next() al objeto DBCursor y posteriormente el metodo get(String Key), esta esta cadena de metodos retorna
un objeto tipo Object casteable o otro DBCursor, ahora bien una vez utilizado el Next() para leer los 
elementos del mismo documento se debe usar objeto.curr().get(String Key).

Queda pendiente hacer resistente a fallos todas las Clases implementadas y Documentar las clases restantes
y nuevas.

El Bus ahora tiene la capacidad de crear un Objeto Json de si mismo y actualizarlo con el metodo actualizarJsonBus()
en caso de que reciba modificaciones.

UbicacionBus ya no retorna un string con formato Json con las coordenadas promedio, ahora retorna un String
con formato Json con la información del bus obtenida en el post y consultada en la base de datos.

se modifico el formato de envio de ubicacionBus por el siguiente:

{
  	"placa": "ZOE 101",
	 	"coordenada":{
    		"latitud": "7.113633",
		"longitud":"-73.114842"
			}
  	
}

 teniendo en cuenta que la placa debe existiren la base de datos para no lanzar una excepción.

RECORDATORIO: Cuando se haga un CLONE al repocitorio, CAMBIAR el nombre WebServicesTest por rutasBuses


[30/07/2016]:[14:26] Se añade un servicio post de prueba para wilson testear la galileo.

[06/08/2016]:[11:00] El bus ahora solo crea un objeto json si se lo piden y se modifico el metodo consultarMDB 
Para que este devuelva un objeto DBOBject y no un apuntador. Esto con finalidad de facilitar el codigo y evitar
confusiones con el metodo .next() tambien para identificar cuando el objeto que quiere consultar no esta en la 
base de datos. Ademas se creo la clase BusDB que es la clase que se encargara de hacer interactuar el bus con la base de datos.

Clases que se modificaron:

BusDB
ConectarMongo
Bus
UbicacionBus

Tareas pendientes:

Implementar la clase BusDB.
Hacer refactor para trabajar con la libreria (https://json-processing-spec.java.net/nonav/releases/1.0/fcs/javadocs/index.html)

Se creo el paquete baseDeDatosMDB para colocar las clases que intervienen con la base de datos y se movio 
BusDB y ConectarMongo

[06/08/2016]:[18:00]
-Se creo la clase BusDB que es la encargada de obtener los valores del base de datos
del bus.
-Se removio la excepcion de MongoTimeOut de Conectar mongo y se creara el try catch solo cuando se invoque el servicio
que la lanze.
-Se removio el metodo existe() de BusDB y su funcionalidad se delego a valoresBaseDatos().

Clases que se modificaron:

BusDB
ConectarMongo
Bus
UbicacionBus
MensajeError

Tareas Pendientes:
Implementar queries en BusDB
[23/08/2016]:[23:21]
Se implemento la funcionalidad para determinar si un bus se encuentra fuera o dentro de una estacion (area circular)
Para lograrlo se creo una clase de utilidad que contiene el radio de la circunferencia y calcula si el punto (bus) esta fuera o dentro
de este radio.
Se creo un nuevo servicio que hace uso de la funcionalidad descrita anteriormente. El formato es:
{
    "coordenada1" : {"latitud":"7.137157","longitud":"-73.122247"},
    "coordenada2" : {"latitud":"7.136681","longitud":"-73.122551"}
}

y la URI del servicio es: http://localhost:8080/NOMBREDEPROYECTO/apirutas/Proximidad/estaDentro
ej. http://localhost:8080/rutasBuses/apirutas/Proximidad/estaDentro
[24/08/2016]:[15:56]
Se creo una documentacion inicial en excel acerca de todos los servicios implementados hasta el momento. Por el momento
Se llevara en el excel. Mas adelante se creara una documentacion con un framework.

[24/08/2016]:[19:11]
Se implemento un nuevo paquete clientes con una clase llamada cliente que permite crear un cliente que consume
un servicio desde una url. Ademas se modifico el servicio Dbtest para probar el funcionamiento de esta clase.

[24/08/2016]:[19:47]
Se modifico el servicio que utiliza el cliente.

[25/08/2016]:[12:10]Reestructuracion del proyecto para seguir el standard de maven.

[26/08/2016]:[14:20]Se crea el directorio src/test/java para que se puedan hacer pruebas al codigo. Ademas
se actualizo la documentacion con las nuevas url.

[31/08/2016]:[00:02]Se creo la funcion que permite proyectar un punto sobre una recta y una clase para probar cosas de la base de datos.

[31/08/2016]:[17:51]Crear un metodo que permita añadir paradas intermedias sobre rutas previamente creadas.

[01/09/2016]:[11:35]Interacciones con la base de datos

[20/09/2016]:[16:15]Correccion de un bug que impedia accesar a los servicios de forma correcta. Esto se corrigio creando dentro de la carpeta WebContent archivos necesarios para la configuracion de los servicios. 

[20/09/2016]:[19:23]Se crearon metodos para insertar elementos en distintas posiciones o al final. Además se implementarion los metodos eliminarRuta para eliminar una ruta completa y eliminarXPosicionParada que elimina una parada de una ruta atravez de un indice llamado posicion y para concluir se implemento imprimirRutas que imprime
las rutas con informacion detallada, sin embargo aún esta en revisión.
Apartir de este momento se empezo a utilizar una UI llamada "Robomongo" para visualisar la base de datos MongoDB  : https://robomongo.org/

[21/09/2016]:[15:10] Se agrego el metodo eliminarParada.
Se realizo cambios den los metodos eliminarRuta y paradas para que retornen un mensaje que avise si pudo ser eliminado.
Se ha añadido una nueva clase de utilidad llamada FormtearDatos, para tratar las entradas de usuario.