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

import core.ParqueAutomotor;
import db.DBGeneralBRT;
import db.TBus;
import utilidad.MensajeError;

/**
 * Clase que permitira interactuar con los buses a partir de peticiones GET
 * Permitiendo consultar y eliminar Buses en una base de datos.
 * 
 * @author Jose Giovanni Florez Nocua
 * @author Carlos Andr�s Pereira Grimaldo
 */
@Path("get/buses")
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
		DBGeneralBRT conexion = new DBGeneralBRT();
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
			bso.append("Coordenada",ParqueAutomotor.getParque().encontrarBus(obj.getString("Placa")).getJsonBus().getJsonObject("coordenada").toString());
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
		DBGeneralBRT conexion = new DBGeneralBRT();
		//placaBus = FormatearDatos.ArreglarCharset(placaBus);(correccion de charset)
		System.out.println(placaBus);
		placaBus=placaBus.toUpperCase();
		JsonObject respuesta;
		DBObject json = null;
		BasicDBObject dbo = new BasicDBObject();
		json = conexion.consultarMDB("Bus", new BasicDBObject("Placa",placaBus));
		if (json!=null)
		{
		dbo.append("Placa", json.get("Placa"));
		dbo.append("Capacidad", json.get("Capacidad"));
		dbo.append("TipoBus", json.get("TipoBus"));
		dbo.append("Estado",json.get("Estado"));
		dbo.append("Coordenada",ParqueAutomotor.getParque().encontrarBus(placaBus).getJsonBus().getJsonObject("coordenada").toString());
		JsonReader jsonReader = Json.createReader(new StringReader(dbo.toString()));
		respuesta = jsonReader.readObject();
		}
		else
		{
			respuesta = MensajeError.noEncontroElElemento("bus",placaBus);
		}
		return Response.status(200).entity(respuesta.toString()).build();
	}
	
	/**
	 * Servicio que permite eliminar un bus en especifico utilizando su placa y devuelve
	 * como resultado si fue satisfactoria o no la tarea
	 * @param placaBus
	 * @return {@link Boolean}
	 */
	@Path("/eliminar/{placaBus}")
	@GET
	@Produces("application/json")
	public Response eliminarBuses(@PathParam("placaBus") String placaBus) {
		boolean progreso;
		progreso=TBus.eliminar(placaBus);
		respuesta = Json.createObjectBuilder().add("Encontrado",progreso).build();
		return Response.status(200).entity(respuesta.toString()).build();
	}
	
	/**
	 * Servicio que permite modificar el estado de un bus. True (Bus funcional) False (Bus no funcional) y 
	 * devuelve como resultado si fue satisfactoria o no la tarea.
	 * @param placaBus
	 * @param estado
	 * @return {@link Boolean}
	 */
	@Path("/modificar/{placaBus},{estado}")
	@GET
	@Produces("application/json")
	public Response modificarEstado(@PathParam("placaBus") String placaBus,@PathParam("estado") boolean estado) {
		boolean progreso = TBus.modificarEstado(placaBus, estado);
		respuesta = Json.createObjectBuilder().add("Encontrado",progreso).build();
		return Response.status(200).entity(respuesta.toString()).build();
	}
	
	
}
