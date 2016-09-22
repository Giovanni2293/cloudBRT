package serviciosGET;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Clase que permitira interactuar con los buses a partir de peticiones GET Permitiendo
 * obtener y eliminar Buses en una base de datos.
 * @author Jose Giovanni Florez Nocua
 * @author Carlos Andrés Pereira Grimaldo
 */
@Path("/Buses")
public class GetServicioBus {
	private JsonObject respuesta;
	
	/**
	 * Servicio que permitira obtener todas los buses que se encuentren almacenados en la base de datos
	 * en un arreglo incluido dentro de un objeto de tipo Json
	 * @return Response respuesta del servicio
	 */
	@Path("/Obtener")
	@GET
	@Produces("application/json")
	public Response obtenerBuses() {
		respuesta = Json.createObjectBuilder().add("Flota Buses", "TODOS LOS BUSES").build();
		return Response.status(200).entity(respuesta.toString()).build();
	}

	/**
	 * Servicio que permitira obtener un bus en especifico almacenado en la base de datos
	 * en un objeto de tipo Json
	 * @return Response respuesta del servicio
	 */
	@Path("Obtener/{placaBus}")
	@GET
	@Produces("application/json")
	public Response obtenerBus(@PathParam("placaBus") String placaBus) {
		respuesta = Json.createObjectBuilder().add("Bus Parametrizada", placaBus).build();
		return Response.status(200).entity(respuesta.toString()).build();
	}
}
