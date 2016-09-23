package serviciosGET;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.bson.BSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import baseDeDatosMDB.ConectarMongo;
import clasesDeUtilidad.MensajeError;

/**
 * Clase que permitira interactuar con las Rutas a partir de peticiones GET
 * Permitiendo consultar, eliminar y crear rutas. Ademas se podran agregar,
 * eliminar y reemplazar paradas en una ruta.
 * 
 * @author Jose Giovanni Florez Nocua
 * @author Carlos Andrés Pereira Grimaldo
 */
@Path("/rutas")
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
	@Produces("application/json;charset=UTF-8")
	public Response obtenerRutas() {
		ConectarMongo conexion = new ConectarMongo();
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
	@Produces("application/json;charset=UTF-8")
	public Response obtenerRuta(@PathParam("nombreRuta") String nombreRuta) {
		ConectarMongo conexion = new ConectarMongo();
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

}
