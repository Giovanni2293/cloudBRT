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

import db.TRuta;

@Path("post/rutas")
public class PostServicioRuta {
	private JsonObject respuesta;
	
	/**
	 * Agrega una parada a una ruta dada su posicion. Los datos se obtienen de un json recibido por POST
	 * @param incomingData
	 * @return {@link Response}
	 */
	@Path("/agregar/paradas")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response a�adirXPosicionARuta(InputStream incomingData) {
		JsonObject entrada;
		String nombreRuta, clave;
		int posicion;

		boolean progreso;

		JsonReader jsonReader = Json.createReader(incomingData);
		entrada = jsonReader.readObject();

		nombreRuta = entrada.getString("NombreRuta");
		clave = entrada.getString("ClaveParada");
		posicion = Integer.parseInt(entrada.getString("PosicionParada"));

		progreso = TRuta.a�adirXPosicionARuta(nombreRuta, clave, posicion);
		respuesta = Json.createObjectBuilder().add("Encontrado", progreso).build();
		System.out.println("Clave:" + clave + " Nombre:" + nombreRuta + " Posicion:" + posicion);
		return Response.status(200).entity(respuesta.toString()).build();

	}

	/**
	 * Agrega una parada remplazando la existente en la posicion especificada
	 * @param incomingData
	 * @return {@link Response}
	 */
	@Path("/reemplazar/paradas")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response remplazarParada(InputStream incomingData) {
		JsonObject entrada;
		String nombreRuta, clave;
		int posicion;

		boolean progreso;

		JsonReader jsonReader = Json.createReader(incomingData);
		entrada = jsonReader.readObject();

		nombreRuta = entrada.getString("NombreRuta");
		clave = entrada.getString("ClaveParada");
		posicion = Integer.parseInt(entrada.getString("PosicionParada"));

		progreso = TRuta.reemplazarParadaDeRuta(nombreRuta, clave, posicion);
		respuesta = Json.createObjectBuilder().add("Encontrado", progreso).build();
		System.out.println("Clave:" + clave + " Nombre:" + nombreRuta + " Posicion:" + posicion);
		return Response.status(200).entity(respuesta.toString()).build();

	}
	/**
	 * modifica la categoria de una ruta
	 * @param incomingData
	 * @return {@link Response}
	 */
	@Path("/modificar/categoria")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response modificarCategoria(InputStream incomingData) {
		
		JsonObject entrada;
		JsonReader jsonReader = Json.createReader(incomingData);
		boolean progreso;
		entrada = jsonReader.readObject();
		progreso = TRuta.modificarCategoria(entrada.getString("Ruta"), entrada.getString("Categoria"));
		respuesta = Json.createObjectBuilder().add("Encontrado", progreso).build();
		return Response.status(200).entity(respuesta.toString()).build();

	}
	/**
	 * modifica la descripcion de una ruta
	 * @param incomingData
	 * @return {@link Response}
	 */
	@Path("/modificar/descripcion")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response modificarDescripcion(InputStream incomingData) {
		
		JsonObject entrada;
		JsonReader jsonReader = Json.createReader(incomingData);
		boolean progreso;
		entrada = jsonReader.readObject();
		progreso = TRuta.modificarDescripcion(entrada.getString("Ruta"), entrada.getString("Descripcion"));
		respuesta = Json.createObjectBuilder().add("Encontrado", progreso).build();
		return Response.status(200).entity(respuesta.toString()).build();

	}
}
