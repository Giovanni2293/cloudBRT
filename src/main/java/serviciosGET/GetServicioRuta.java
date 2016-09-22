package serviciosGET;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Clase que permitira interactuar con las Rutas a partir de peticiones GET Permitiendo
 * obtener, eliminar y crear rutas. Ademas se podran agregar, eliminar y reemplazar paradas en una ruta.
 * @author Jose Giovanni Florez Nocua
 * @author Carlos Andrés Pereira Grimaldo
 */
@Path("/Rutas")
public class GetServicioRuta {
	private JsonObject respuesta;

	/**
	 * Servicio que permitira obtener todas las rutas que se encuentren almacenadas en la base de datos
	 * en un arreglo incluido dentro de un objeto de tipo Json
	 * @return Response respuesta del servicio
	 */
	@Path("/Obtener")
	@GET
	@Produces("application/json")
	public Response obtenerRutas() {
		respuesta = Json.createObjectBuilder().add("Rutas", "TODAS LAS RUTAS").build();
		return Response.status(200).entity(respuesta.toString()).build();
	}

	/**
	 * Servicio que permitira obtener una ruta en especifico almacenada en la base de datos
	 * en un objeto Json
	 * @return Response respuesta del servicio
	 */
	@Path("Obtener/{nombreRuta}")
	@GET
	@Produces("application/json")
	public Response obtenerRuta(@PathParam("nombreRuta") String nombreRuta) {
		respuesta = Json.createObjectBuilder().add("Ruta Parametrizada", nombreRuta).build();
		return Response.status(200).entity(respuesta.toString()).build();
	}

}
