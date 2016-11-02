package servicios.post;

import java.io.InputStream;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import db.TBus;

/**
 * 
 * @author Jose Giovanni Florez Nocua
 * @author Carlos Andres Pereira
 */
@Path("buses/administracion")
public class PostServicioBus {
	private JsonObject respuesta;

	/**
	 * Crea un nuevo bus mediante un json enviado por POST
	 * 
	 * @param incomingData
	 * @return {@link Response}
	 */
	@Path("/crear")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response crearBus(InputStream incomingData) {
		JsonObject entrada;
		String placa, tipoBus;
		int capacidad;
		boolean estado, progreso;
		JsonReader jsonReader = Json.createReader(incomingData);
		entrada = jsonReader.readObject();

		placa = entrada.getString("Placa");
		capacidad = entrada.getInt("Capacidad");
		tipoBus = entrada.getString("TipoBus");
		estado = Boolean.parseBoolean(entrada.getString("Estado"));

		progreso = TBus.crearBus(placa, capacidad, tipoBus, estado);
		respuesta = Json.createObjectBuilder().add("Encontrado", progreso).build();
		System.out.println("Placa:" + placa + " Capacidad:" + capacidad + " TipoBus:" + tipoBus + " Estado:" + estado);
		return Response.status(200).entity(respuesta.toString()).build();

	}
}
