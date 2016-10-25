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
import db.TParada;
import utilidad.MensajeError;

/**
 * Clase que permitira interactuar con las paradas a partir de peticiones GET
 * Permitiendo consultar y eliminar Paradas en una base de datos.
 * 
 * @author Jose Giovanni Florez Nocua
 * @author Carlos Andrés Pereira Grimaldo
 */
@Path("get/paradas")
public class GetServicioParada {
	private JsonObject respuesta;

	/**
	 * Servicio que permitira obtener todas las Paradas que se encuentren
	 * almacenadas en la base de datos en un arreglo incluido dentro de un
	 * objeto de tipo Json
	 * 
	 * @return Response respuesta del servicio
	 */
	@Path("/consultar")
	@GET
	@Produces("application/json")
	public Response obtenerParadas() {
		DBGeneralBRT conexion = new DBGeneralBRT();
		DBCollection collection = conexion.consultarColeccion("Parada");
		DBCursor cursor = collection.find();
		ArrayList<BasicDBObject> paradas = new ArrayList<>();
		while (cursor.hasNext()) {
			BasicDBObject obj = (BasicDBObject) cursor.next();
			BasicDBObject bso = new BasicDBObject();
			bso.append("Nombre", obj.get("Nombre"));
			bso.append("Coordenada", obj.get("Coordenada"));
			paradas.add(bso);
		}
		BasicDBObject data = new BasicDBObject("Paradas", paradas);
		JsonReader jsonReader = Json.createReader(new StringReader(data.toString()));
		JsonObject json = jsonReader.readObject();
		return Response.status(200).entity(json.toString()).build();
	}

	/**
	 * Servicio que permitira obtener una parada en especifico almacenada en la
	 * base de datos en un objeto de tipo Json
	 * 
	 * @return Response respuesta del servicio
	 */
	@Path("consultar/{claveParada}")
	@GET
	@Produces("application/json")
	public Response obtenerParada(@PathParam("claveParada") String claveParada) {
		DBGeneralBRT conexion = new DBGeneralBRT();
		claveParada = claveParada.toUpperCase() ;
		DBObject json = null;
		BasicDBObject dbo = new BasicDBObject();
		json = conexion.consultarMDB("Parada", new BasicDBObject("Clave", claveParada));
		if (json != null) {
			dbo.append("Clave", claveParada);
			dbo.append("Nombre", json.get("Nombre"));
			dbo.append("Coordenada", json.get("Coordenada"));
			JsonReader jsonReader = Json.createReader(new StringReader(dbo.toString()));
			respuesta = jsonReader.readObject();
		}
		else
		{
			respuesta = MensajeError.noEncontroElElemento("parada",claveParada);
		}
		return Response.status(200).entity(respuesta.toString()).build();
	}
	
	/**
	 * Servicio que permite eliminar una parada mediante su clave. Devuelve si la tarea fue satisfactoria o 
	 * no.
	 * @param clave
	 * @return {@link Boolean}
	 */
	@Path("/eliminar/{clave}")
	@GET
	@Produces("application/json")
	public Response eliminarParada(@PathParam("clave") String clave) {
		boolean progreso;
		progreso=TParada.eliminarParada(clave);
		respuesta = Json.createObjectBuilder().add("Encontrado",progreso).build();
		return Response.status(200).entity(respuesta.toString()).build();
	}
}
