package servicios.get;

import java.io.StringReader;
import java.util.ArrayList;


import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;


import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import db.DBGeneralBRT;
import db.TRuta;
import utilidad.MensajeError;

/**
 * Clase que permitira interactuar con las Rutas a partir de peticiones GET
 * Permitiendo consultar, eliminar y crear rutas. Ademas se podran agregar,
 * eliminar y reemplazar paradas en una ruta.
 * 
 * @author Jose Giovanni Florez Nocua
 * @author Carlos Andrés Pereira Grimaldo
 */
@Path("get/rutas")
public class GetServicioRuta {
	private JsonObject respuesta;

	/**
	 * Servicio que permitira obtener todas las rutas que se encuentren
	 * almacenadas en la base de datos en un arreglo incluido dentro de un
	 * objeto de tipo Json
	 * 
	 * @return Response respuesta del servicio
	 */
	@Path("/consultar")
	@GET
	@Produces("application/json")
	public Response obtenerRutas() {
		DBGeneralBRT conexion = new DBGeneralBRT();
		DBCollection collection = conexion.consultarColeccion("Ruta");
		DBCursor cursor = collection.find();
		ArrayList<BasicDBObject> rutas = new ArrayList<>();
		ArrayList<BasicDBObject> arrayParadas = null;
		while (cursor.hasNext()) {
			ArrayList<BasicDBObject> paradasSinId = new ArrayList<>();
			BasicDBObject obj = (BasicDBObject) cursor.next();
			BasicDBObject bso = new BasicDBObject();
			bso.append("Nombre", obj.get("Nombre"));
			arrayParadas = (ArrayList<BasicDBObject>) obj.get("Ruta");
			for (BasicDBObject temp : arrayParadas) {
				BasicDBObject parada = new BasicDBObject();
				parada.append("Nombre", temp.get("Nombre"));
				parada.append("Coordenada", temp.get("Coordenada"));
				paradasSinId.add(parada);
			}
			bso.append("Ruta", paradasSinId);

			rutas.add(bso);
		}
		BasicDBObject data = new BasicDBObject("Rutas", rutas);
		JsonReader jsonReader = Json.createReader(new StringReader(data.toString()));
		JsonObject json = jsonReader.readObject();
		return Response.status(200).entity(json.toString()).build();
	}

	/**
	 * Servicio que permitira obtener una ruta en especifico almacenada en la
	 * base de datos en un objeto Json
	 * 
	 * @return Response respuesta del servicio
	 */
	@Path("consultar/{nombreRuta}")
	@GET
	@Produces("application/json")
	public Response obtenerRuta(@PathParam("nombreRuta") String nombreRuta) {
		DBGeneralBRT conexion = new DBGeneralBRT();
		JsonObject respuesta;
		DBObject json = null;
		json = conexion.consultarMDB("Ruta", new BasicDBObject("Nombre", nombreRuta));
		if (json != null) {
			ArrayList<BasicDBObject> arrayParadas = null;
			BasicDBObject bso = new BasicDBObject();
			bso.append("Nombre", json.get("Nombre"));
			arrayParadas = (ArrayList<BasicDBObject>) json.get("Ruta");
			ArrayList<BasicDBObject> nuevoArrayParadas = new ArrayList<>();
			for (BasicDBObject temp : arrayParadas) {
				BasicDBObject parada = new BasicDBObject();
				parada.append("Nombre", temp.get("Nombre"));
				parada.append("Coordenada", temp.get("Coordenada"));
				nuevoArrayParadas.add(parada);
			}
			bso.append("Ruta", nuevoArrayParadas);

			JsonReader jsonReader = Json.createReader(new StringReader(bso.toString()));
			respuesta = jsonReader.readObject();
		}
		else
		{
			respuesta = MensajeError.noEncontroElElemento("ruta", nombreRuta);
		}
		return Response.status(200).entity(respuesta.toString()).build();
	}
	
	/**
	 * Elimina todas las paradas de una ruta existente. La ruta tiene que existir
	 * para poder efectuarse la tarea. Finalmente devuelve si fue o no satisfactoria la tarea
	 * @param nombre
	 * @return
	 */
	@Path("/remover/paradas/{nombre}")
	@GET
	@Produces("application/json")
	public Response eliminarParadasDeRuta(@PathParam("nombre") String nombre) {
		boolean progreso;
		progreso=TRuta.eliminarParadas(nombre);
		respuesta = Json.createObjectBuilder().add("Encontrado",progreso).build();
		return Response.status(200).entity(respuesta.toString()).build();
	}
	
	/**
	 * Remueve una parada de una ruta dando una posicion.
	 * @param nombreRuta
	 * @param posicion
	 * @return {@link Response}
	 */
	@Path("/remover/paradas/{nombreRuta},{posicion}")
	@GET
	@Produces("application/json")
	public Response eliminarParadaEspecificaDeRuta(@PathParam("nombreRuta") String nombreRuta,@PathParam("posicion") int posicion) {
		boolean progreso;
		progreso=TRuta.removerParadaDeRuta(nombreRuta, posicion);
		respuesta = Json.createObjectBuilder().add("Encontrado",progreso).build();
		return Response.status(200).entity(respuesta.toString()).build();
	}
	
	/**
	 * Elimina una ruta por completo
	 * @param nombreRuta
	 * @return {@link Response}
	 */
	@Path("/eliminar/{nombreRuta}")
	@GET
	@Produces("application/json")
	public Response eliminarRuta(@PathParam("nombreRuta") String nombreRuta) {
		boolean progreso;
		progreso=TRuta.eliminarRuta(nombreRuta);
		respuesta = Json.createObjectBuilder().add("Encontrado",progreso).build();
		return Response.status(200).entity(respuesta.toString()).build();
	}
	
	/**
	 * Agrega una parada al final de una ruta especificada en la url.
	 * @param nombreRuta
	 * @param clave
	 * @return {@link Response}
	 */
	@Path("/agregar/paradas/{nombreRuta},{clave}")
	@GET
	@Produces("application/json")
	public Response agregarParadasAlFinal(@PathParam("nombreRuta") String nombreRuta,@PathParam("clave") String clave) {
		boolean progreso;
		progreso=TRuta.añadirAlFinalDeRuta(nombreRuta, clave);
		respuesta = Json.createObjectBuilder().add("Encontrado",progreso).build();
		return Response.status(200).entity(respuesta.toString()).build();
	}
	
	/**
	 * Crea una nueva ruta sin paradas
	 * @param nombreRuta
	 * @return {@link Response}
	 */
	@Path("/crear/{nombreRuta}")
	@GET
	@Produces("application/json")
	public Response crearRuta(@PathParam("nombreRuta") String nombreRuta) {
		boolean progreso;
		progreso=TRuta.crearRuta(nombreRuta);
		respuesta = Json.createObjectBuilder().add("Encontrado",progreso).build();
		return Response.status(200).entity(respuesta.toString()).build();
	}
	
	
	

	
	
	
	

}
