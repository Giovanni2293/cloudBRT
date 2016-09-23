package serviciosGET;

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

import baseDeDatosMDB.ConectarMongo;

/**
 * Clase que permitira interactuar con los buses a partir de peticiones GET
 * Permitiendo consultar y eliminar Buses en una base de datos.
 * 
 * @author Jose Giovanni Florez Nocua
 * @author Carlos Andr�s Pereira Grimaldo
 */
@Path("/buses")
public class GetServicioBus {
	private JsonObject respuesta;

	/**
	 * Servicio que permitira obtener todas los buses que se encuentren
	 * almacenados en la base de datos en un arreglo incluido dentro de un
	 * objeto de tipo Json
	 * 
	 * @return Response respuesta del servicio
	 */
	@Path("/consultar")
	@GET
	@Produces("application/json")
	public Response obtenerBuses() {
		ConectarMongo conexion = new ConectarMongo();
		DBCollection collection = conexion.consultarColeccion("Bus");
		DBCursor cursor = collection.find();
		ArrayList<BasicDBObject> buses = new ArrayList<>();
		while (cursor.hasNext()) {
			BasicDBObject obj = (BasicDBObject) cursor.next();
			BasicDBObject bso = new BasicDBObject();
			bso.append("Placa", obj.get("Placa"));
			bso.append("Capacidad", obj.get("Capacidad"));
			bso.append("TipoBus", obj.get("TipoBus"));
			bso.append("Estado", obj.get("Estado"));
			buses.add(bso);
		}
		BasicDBObject data = new BasicDBObject("Buses", buses);
		JsonReader jsonReader = Json.createReader(new StringReader(data.toString()));
		JsonObject json = jsonReader.readObject();
		return Response.status(200).entity(json.toString()).build();

	}

	/**
	 * Servicio que permitira obtener un bus en especifico almacenado en la base
	 * de datos en un objeto de tipo Json
	 * 
	 * @return Response respuesta del servicio
	 */
	@Path("consultar/{placaBus}")
	@GET
	@Produces("application/json")
	public Response obtenerBus(@PathParam("placaBus") String placaBus) {
		respuesta = Json.createObjectBuilder().add("Bus Parametrizada", placaBus).build();
		return Response.status(200).entity(respuesta.toString()).build();
	}
}
