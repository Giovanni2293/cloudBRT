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
import db.TConductor;

@Path("conductor/administracion")
public class PostServicioConductor {
	
	private JsonObject respuesta;
	
	/**
	 * Crea un nuevo conductor mediante un json enviado por POST
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
		
		long cedula, licencia;
		String primerNombre,segundoNombre,
		primerApellido,segundoApellido,tipoSangre;	
				
		boolean  progreso;
		JsonReader jsonReader = Json.createReader(incomingData);
		entrada = jsonReader.readObject();

		cedula = entrada.getInt("Cedula");
		primerNombre = entrada.getString("Primer Nombre");
		segundoNombre = entrada.getString("Segundo Nombre");
		primerApellido = entrada.getString("Primer Apellido");
		segundoApellido = entrada.getString("Segundo Apellido");
		licencia = entrada.getInt("Numero de Licencia");
		tipoSangre = entrada.getString("Grupo Sanguineo");

		progreso = TConductor.crearBus(cedula, primerNombre, segundoNombre,
				primerApellido, segundoApellido, licencia, tipoSangre);
		respuesta = Json.createObjectBuilder().add("Encontrado", progreso).build();
		return Response.status(200).entity(respuesta.toString()).build();

	}

}
