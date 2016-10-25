/**
 /**
 * Clase encargada de obtener la posicion promedio de un bus
 */

package servicios.post;

import core.*;
import db.TColectorBus;
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

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import javax.json.*;

@Path("/colector")
public class UbicacionBus {

	@Path("/buses")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response recibirBus(InputStream incomingData) {
		String placa;
		JsonObject entrada;
		BasicDBObject salida;
		FileWriter escritor;
		BufferedWriter buffer;
		JsonReader jsonReader = Json.createReader(incomingData);
		entrada = jsonReader.readObject();
		
		placa = entrada.getString("placa");
		placa = placa.toUpperCase();
		
		// Inicia asignacion de coordenada a bus del parque automotor runtime
		asignarCoorABus(entrada);
		// Termina la asignacion de coordenada a bus del parque automotor runtime
		
		salida = new BasicDBObject("tde", entrada.get("tde"))
				.append("tdr", Fecha.getFechaClass().getFecha())
				.append("coordenada", entrada.get("coordenada"));

		
		TColectorBus.regDiarioBuses(salida,placa);
		//
		
		return Response.status(200).entity(salida.toString()).build();

		
	}
	
	private void asignarCoorABus(JsonObject entrada)
	{
		Bus bus;
		bus = ParqueAutomotor.getParque().encontrarBus(entrada.getString("placa"));
		if (bus != null) {
			//El bus existe
			String latS, lngS;
			Double lat, lng;
			JsonObject coordenada = entrada.getJsonObject("coordenada");
			latS = coordenada.getString("latitud");
			lngS = coordenada.getString("longitud");
			lat = Double.parseDouble(latS);
			lng = Double.parseDouble(lngS);
			System.out.println("lat:"+lat+" lng:"+lng);
			bus.setCoor(new Coordenadas(lat, lng));
		}
		ParqueAutomotor.getParque().mostarParque();
	}

}