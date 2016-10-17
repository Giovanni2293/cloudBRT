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

import core.Coordenadas;
import db.TransaccionesParada;

@Path("post/paradas")
public class PostServicioParada {
	private JsonObject respuesta;

	/**
	 * Crea una nueva parada empleando la informacion obtenida desde un Json enviado por POST
	 * @param incomingData
	 * @return {@link Response}
	 */
	@Path("/crear")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response crearParada(InputStream incomingData) {
		JsonObject entrada;
		JsonObject coordenada;
		String clave, nombre;
		String slatitud, slongitud;
		Coordenadas c1;
		boolean progreso;

		JsonReader jsonReader = Json.createReader(incomingData);
		entrada = jsonReader.readObject();

		clave = entrada.getString("Clave");
		nombre = entrada.getString("Nombre");
		coordenada = entrada.getJsonObject("Coordenada");
		slatitud = coordenada.getString("Latitud");
		slongitud = coordenada.getString("Longitud");
		c1 = new Coordenadas(Double.parseDouble(slatitud), Double.parseDouble(slongitud));
		progreso = TransaccionesParada.crearParada(clave, nombre, c1.getLatitud(), c1.getLongitud());
		respuesta = Json.createObjectBuilder().add("Encontrado", progreso).build();
		System.out.println("Clave:" + clave + " Nombre:" + nombre + " Latitud:" + c1.getLatitud() + " Longitud:"
				+ c1.getLongitud());
		return Response.status(200).entity(respuesta.toString()).build();

	}
}
