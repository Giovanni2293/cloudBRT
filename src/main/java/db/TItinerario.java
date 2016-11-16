package db;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import core.Coordenadas;
import utilidad.FormatearDatos;
import utilidad.GeoMatematicas;

public class TItinerario {

	private static DBGeneralBRT mongo;
	private static final String nombreColeccion = "Itinerario";
	private static LinkedHashMap<String, String> horarioReal;

	public static boolean crearItinerario(String clave, String conductor, String bus, String recorrido) {

		DBObject consultaItinerario;
		BasicDBObject data;
		clave = clave.toUpperCase();
		bus = bus.toUpperCase();
		recorrido = recorrido.toUpperCase();
		horarioReal = new LinkedHashMap<>();
		mongo = new DBGeneralBRT();
		data = new BasicDBObject("Clave", clave);
		consultaItinerario = mongo.consultarMDB(nombreColeccion, data);
		if (consultaItinerario == null) {
						
					data.append("Conductor", conductor);
					data.append("Bus", bus );
					data.append("Recorrido",recorrido);
					data.append("HoraSalidaReal", "");
					data.append("HorarioReal", horarioReal);
					mongo.insertarMDB(nombreColeccion, data);
				
		} else {
			System.out.println("Error: El elemento ya existe en la base de datos");
		}
		mongo.cerrarConexion();
		return false;
	}

	public static boolean iniciarItinerario(String clave, String horaSalidaReal) {
		
		DBObject consultaItinerario;
		BasicDBObject data,nuevaData,dataAReemplazar;
		clave = clave.toUpperCase();
		mongo = new DBGeneralBRT();
		data = new BasicDBObject("Clave", clave);
		dataAReemplazar = new BasicDBObject("Clave", clave);
		consultaItinerario = mongo.consultarMDB(nombreColeccion, data);
		
		if(consultaItinerario != null){
			nuevaData = new BasicDBObject("HoraSalidaReal", horaSalidaReal);
			mongo.actualizarMDB(nombreColeccion, nuevaData, dataAReemplazar);
			mongo.cerrarConexion();			
		}else{
			System.out.println("Error: No se encontro el itinerario: " + clave + "  en la base de datos");
		}
		mongo.cerrarConexion();
		return false;
	}
	
public static boolean marcarHora(String clave, LinkedHashMap<String, String> horarioReal) {
		
		DBObject consultaItinerario;
		BasicDBObject data,nuevaData,dataAReemplazar;
		clave = clave.toUpperCase();
		mongo = new DBGeneralBRT();
		data = new BasicDBObject("Clave", clave);
		dataAReemplazar = new BasicDBObject("Clave", clave);
		consultaItinerario = mongo.consultarMDB(nombreColeccion, data);
		
		if(consultaItinerario != null){
			nuevaData = new BasicDBObject("HorarioReal", horarioReal);
			mongo.actualizarMDB(nombreColeccion, nuevaData, dataAReemplazar);
			mongo.cerrarConexion();			
		}else{
			System.out.println("Error: No se encontro el itinerario: " + clave + "  en la base de datos");
		}
		mongo.cerrarConexion();
		return false;
	}
	

}
