/**
 /**
 * Clase encargada de obtener la posicion promedio de un bus
 */

package servicios.post;

import core.*;
import utilidad.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import javax.json.*;

@Path("/colector")
public class UbicacionBus {

	@Path("/buses")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response recibirBus(InputStream incomingData) {
		JsonObject entrada;
		JsonObject salida;
		FileWriter escritor;
		BufferedWriter buffer;

		JsonReader jsonReader = Json.createReader(incomingData);
		entrada = jsonReader.readObject();
		
		String locatefile = "../../historico/";
		String filename = entrada.getString("placa") + "-" + Fecha.getFechaClass().getSoloYMD()+".txt";
		System.out.println(filename);
		String uri = locatefile + filename;
		File folder = new File(locatefile);
		if (!folder.exists()){ 
			folder.mkdirs();
		}
		String v = entrada.toString();
		
		
		salida = Json.createObjectBuilder().add("placa", entrada.get("placa"))
				.add("tde", entrada.get("tde"))
				.add("tdr", Fecha.getFechaClass().getFecha())
				.add("coordenada", entrada.get("coordenada")).build();
		
		try {
			escritor = new FileWriter(uri, true);
			buffer = new BufferedWriter(escritor);
			buffer.write(salida.toString() + "\n");
			buffer.close();

		} catch (IOException e) {
			System.out.println("IO Exception ;v");
		}

		return Response.status(200).entity(salida.toString()).build();

		/*
		 * StringTokenizer tokenizer = new StringTokenizer(v, ",");
		 * 
		 * v = tokenizer.nextToken() + ", \"tdr\":" + "\"" +
		 * Fecha.getFechaClass().getFecha()+ "\",";
		 * while(tokenizer.hasMoreTokens()){ v +="," + tokenizer.nextToken() ; }
		 */
	}

	
}