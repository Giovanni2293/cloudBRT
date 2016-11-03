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
import utilidad.MensajeError;

@Path("recorridos")
public class GetServicioRecorrido {

	private JsonObject respuesta;
	
	@Path("monitoreo/consultar")
	@GET
	@Produces("application/json")
	public Response obtenerRecorridos() {
		DBGeneralBRT conexion = new DBGeneralBRT();
		DBCollection collection = conexion.consultarColeccion("Recorrido");
		DBCursor cursor = collection.find();
		ArrayList<BasicDBObject> recorridos = new ArrayList<>();
		while (cursor.hasNext()) {
			BasicDBObject obj = (BasicDBObject) cursor.next();
			BasicDBObject bso = new BasicDBObject();
			bso.append("Clave", obj.get("Clave"));
			bso.append("Ruta", obj.get("Ruta"));
			bso.append("HoraPartida", obj.get("HoraPartida"));
			bso.append("Horario", obj.get("Horario"));
			recorridos.add(bso);
		}
		BasicDBObject data = new BasicDBObject("Recorridos", recorridos);
		JsonReader jsonReader = Json.createReader(new StringReader(data.toString()));
		JsonObject json = jsonReader.readObject();
		return Response.status(200).entity(json.toString()).build();
	}
	
	@Path("monitoreo/consultar/{claveRecorrido}")
	@GET
	@Produces("application/json")
	public Response obtenerParada(@PathParam("claveRecorrido") String claveRecorrido) {
		claveRecorrido = claveRecorrido.toUpperCase();
		DBGeneralBRT conexion = new DBGeneralBRT();
		DBObject json = null;
		BasicDBObject dbo = new BasicDBObject();
		json = conexion.consultarMDB("Recorrido", new BasicDBObject("Clave", claveRecorrido));
		if (json != null) {
			dbo.append("Clave", claveRecorrido);
			dbo.append("Ruta", json.get("Ruta"));
			dbo.append("HoraPartida", json.get("HoraPartida"));
			dbo.append("Horario", json.get("Horario"));
			JsonReader jsonReader = Json.createReader(new StringReader(dbo.toString()));
			respuesta = jsonReader.readObject();
		}
		else
		{
			respuesta = MensajeError.noEncontroElElemento("Recorrido",claveRecorrido);
		}
		return Response.status(200).entity(respuesta.toString()).build();
	}
}
