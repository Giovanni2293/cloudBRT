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
 * Clase que permitira interactuar con las Rutas a partir de peticiones GET Permitiendo
 * consultar, eliminar y crear rutas. Ademas se podran agregar, eliminar y reemplazar paradas en una ruta.
 * @author Jose Giovanni Florez Nocua
 * @author Carlos Andrés Pereira Grimaldo
 */
@Path("/rutas")
public class GetServicioRuta {
	private JsonObject respuesta;

	/**
	 * Servicio que permitira obtener todas las rutas que se encuentren almacenadas en la base de datos
	 * en un arreglo incluido dentro de un objeto de tipo Json
	 * @return Response respuesta del servicio
	 */
	@Path("/consultar")
	@GET
	@Produces("application/json")
	public Response obtenerRutas() {
		 ConectarMongo conexion = new ConectarMongo();
		 DBCollection collection = conexion.consultarColeccion("Ruta");
		 DBCursor cursor = collection.find();
		 ArrayList<BasicDBObject> rutas = new ArrayList<>();
		 ArrayList<BasicDBObject> ArrayParadas = null;
		 while (cursor.hasNext()) {
			 ArrayList<BasicDBObject> paradasSinId = new ArrayList<>();
       	 BasicDBObject obj = (BasicDBObject) cursor.next();
       	 BasicDBObject bso = new BasicDBObject();
       	 bso.append("Nombre",obj.get("Nombre"));
       	 ArrayParadas = (ArrayList<BasicDBObject>) obj.get("Ruta");
       	 for (BasicDBObject temp : ArrayParadas)
       	 {
       		BasicDBObject parada = new BasicDBObject();
       		parada.append("Nombre",temp.get("Nombre"));
       		parada.append("Coordenada",temp.get("Coordenada"));
       		paradasSinId.add(parada);
       	 }
       	 bso.append("Ruta",paradasSinId);
       	 
       	 rutas.add(bso);
		}
		 BasicDBObject data = new BasicDBObject("Rutas",rutas);
		 JsonReader jsonReader = Json.createReader(new StringReader(data.toString()));
		 JsonObject json = jsonReader.readObject();
		return Response.status(200).entity(json.toString()).build();
	}

	/**
	 * Servicio que permitira obtener una ruta en especifico almacenada en la base de datos
	 * en un objeto Json
	 * @return Response respuesta del servicio
	 */
	@Path("consultar/{nombreRuta}")
	@GET
	@Produces("application/json")
	public Response obtenerRuta(@PathParam("nombreRuta") String nombreRuta) {
		respuesta = Json.createObjectBuilder().add("Ruta Parametrizada", nombreRuta).build();
		return Response.status(200).entity(respuesta.toString()).build();
	}

}
