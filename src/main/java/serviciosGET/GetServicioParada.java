package serviciosGET;

import java.io.StringReader;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.swing.text.html.HTMLDocument.HTMLReader.FormAction;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import baseDeDatosMDB.ConectarMongo;
import clasesDeUtilidad.FormatearDatos;
import clasesDeUtilidad.MensajeError;

/**
 * Clase que permitira interactuar con las paradas a partir de peticiones GET
 * Permitiendo consultar y eliminar Paradas en una base de datos.
 * 
 * @author Jose Giovanni Florez Nocua
 * @author Carlos Andrés Pereira Grimaldo
 */
@Path("/paradas")
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
	@Produces("application/json;charset=UTF-8")
	public Response obtenerParadas() {
		ConectarMongo conexion = new ConectarMongo();
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
	@Path("consultar/{nombreParada}")
	@GET
	@Produces("application/json;charset=UTF-8")
	public Response obtenerParada(@PathParam("nombreParada") String nombreParada) {
		ConectarMongo conexion = new ConectarMongo();
		//nombreParada=FormatearDatos.mayusInicialMulti(nombreParada);
		JsonObject respuesta;
		DBObject json = null;
		BasicDBObject dbo = new BasicDBObject();
		json = conexion.consultarMDB("Parada", new BasicDBObject("Nombre", nombreParada));
		if (json != null) {
			dbo.append("Nombre", json.get("Nombre"));
			dbo.append("Coordenada", json.get("Coordenada"));
			JsonReader jsonReader = Json.createReader(new StringReader(dbo.toString()));
			respuesta = jsonReader.readObject();
		}
		else
		{
			respuesta = MensajeError.noEncontroElElemento("parada",nombreParada);
		}
		return Response.status(200).entity(respuesta.toString()).build();
	}
}
