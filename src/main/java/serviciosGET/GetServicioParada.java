package serviciosGET;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Clase que permitira interactuar con las paradas a partir de peticiones GET Permitiendo
 * obtener y eliminar Paradas en una base de datos.
 * @author Jose Giovanni Florez Nocua
 * @author Carlos Andrés Pereira Grimaldo
 */
@Path("/Paradas")
public class GetServicioParada {
	private JsonObject respuesta;
	
	/**
	 * Servicio que permitira obtener todas las Paradas que se encuentren almacenadas en la base de datos
	 * en un arreglo incluido dentro de un objeto de tipo Json
	 * @return Response respuesta del servicio
	 */
	@Path("/Obtener")
	@GET
	@Produces("application/json")
	public Response obtenerParadas() {
		respuesta = Json.createObjectBuilder().add("Paradas", "TODAS LAS PARADAS").build();
		return Response.status(200).entity(respuesta.toString()).build();
	}

	/**
	 * Servicio que permitira obtener una parada en especifico almacenada en la base de datos
	 * en un objeto de tipo Json
	 * @return Response respuesta del servicio
	 */
	@Path("Obtener/{nombreParada}")
	@GET
	@Produces("application/json")
	public Response obtenerParada(@PathParam("nombreParada") String nombreParada) {
		respuesta = Json.createObjectBuilder().add("Parada Parametrizada", nombreParada).build();
		return Response.status(200).entity(respuesta.toString()).build();
	}
}
