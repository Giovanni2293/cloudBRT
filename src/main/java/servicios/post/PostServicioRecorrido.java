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


import db.TransaccionesRecorrido;

@Path("post/recorrido")
public class PostServicioRecorrido {

	private JsonObject respuesta;
	
	@Path("/crear")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response crearRecorrido(InputStream incomingData) {
		JsonObject entrada;
		String clave,ruta,horaDePartida; 
		boolean progreso;
		JsonReader jsonReader = Json.createReader(incomingData);
		entrada = jsonReader.readObject();
		
	    clave = entrada.getString("Clave");
	    ruta = entrada.getString("Ruta");
	    horaDePartida = entrada.getString("HoraDePartida");
	    progreso=TransaccionesRecorrido.crearRecorridoAutomatico(clave, ruta, horaDePartida);
	    respuesta = Json.createObjectBuilder().add("Encontrado",progreso).build();
		return Response.status(200).entity(respuesta.toString()).build();
		
	}
	
	@Path("/editar")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editarRecorrido(InputStream incomingData) {
		JsonObject entrada;
		String clave,parada,horaAnterior,horaNueva; 
		boolean progreso;
		JsonReader jsonReader = Json.createReader(incomingData);
		entrada = jsonReader.readObject();
		
	    clave = entrada.getString("Clave");
	    parada = entrada.getString("Parada");
	    horaAnterior = entrada.getString("HoraAnterior");
	    horaNueva = entrada.getString("HoraNueva");
	    progreso=TransaccionesRecorrido.editarHoraRecorrido(clave, parada, horaAnterior, horaNueva);
	    respuesta = Json.createObjectBuilder().add("Encontrado",progreso).build();
		return Response.status(200).entity(respuesta.toString()).build();
		
	}
}
