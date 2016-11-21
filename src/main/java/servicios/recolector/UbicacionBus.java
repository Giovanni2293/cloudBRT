
package servicios.recolector;

import core.*;
import db.TColectorBus;
import utilidad.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

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

	private Despacho despacho;
	private String placa;

	@Path("/buses")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response recibirBus(InputStream incomingData) {
		String tde;
		Coordenadas coor;
		JsonObject entrada;
		BasicDBObject salida;
		BufferedWriter buffer;
		JsonReader jsonReader = Json.createReader(incomingData);
		entrada = jsonReader.readObject();

		placa = entrada.getString("Placa");
		placa = placa.toUpperCase();
		tde = entrada.getString("Tde");
		JsonObject coordenada = entrada.getJsonObject("Coordenada");
		coor = new Coordenadas(Double.parseDouble(coordenada.getString("Latitud")),
				Double.parseDouble(coordenada.getString("Longitud")));

		// Inicia asignacion de coordenada a bus del parque automotor runtime
		asignarCoorABus(entrada, coor);
		// Termina la asignacion de coordenada a bus del parque automotor
		// runtime

		salida = new BasicDBObject("Tde", tde).append("Tdr", Fecha.getFechaClass().getFecha()).append("Coordenada",
				new BasicDBObject("Latitud", coor.getLatitud()).append("Longitiud", coor.getLongitud()));

		TColectorBus.regDiarioBuses(salida, placa);
		//
		horaReal();
		return Response.status(200).entity(entrada.toString()).build();

	}

	private void asignarCoorABus(JsonObject entrada, Coordenadas coor) {
		Bus bus;
		bus = BusesRT.getBusesRT().encontrarBus(entrada.getString("Placa"));
		if (bus != null) {
			// El bus existe

			bus.setCoor(coor);
		}
		// BusesRT.getBusesRT().mostrarBuses();
	}

	private void horaReal() {
		despacho = Despacho.getDespacho();
		
		
			if (despacho.encontarXBus(placa)!=null) {
				Itinerario i = despacho.encontarXBus(placa).get(0);
			for (int x = 0; x < BusesRT.getBusesRT().getBuses().size(); x++) {
				i.AddObserver(BusesRT.getBusesRT().getBuses().get(x));
			}
			i.encontrar();
		} else {
			System.out.println("No hay itinerarios cargados");
		}
	}
}