package db;

import java.util.ArrayList;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import core.Fecha;

public class TColectorBus {

	private static DBColector mongo;
	private static String fecha;
	private static final String nombreColeccion = "HistoBuses";

	public static boolean regDiarioBuses(DBObject data , String placa ) {
		mongo = new DBColector();
		BasicDBObject nuevaData;
		//fecha = Fecha.getFechaClass().getSoloYMD();
		fecha = "fecha1";
		DBObject consulta;
		nuevaData = new BasicDBObject("placa", placa);
		ArrayList<DBObject> fechas = new ArrayList<>();	
		ArrayList<DBObject> registros = new ArrayList<>();
		
		/*
		BasicDBObject nuevaData, dataARemplazar;
		nuevaData = new BasicDBObject("Nombre", nombreRuta);
		dataARemplazar = new BasicDBObject("Nombre", nombreRuta);
		mongo = new DBGeneralBRT();
		ruta = mongo.consultarMDB(nombreColeccion, dataARemplazar);
		if (ruta != null) {
			paradas = (ArrayList<DBObject>) ruta.get(nombreColeccion);
			parada = mongo.consultarMDB("Parada", new BasicDBObject("Clave", clave));
			if (parada != null) {
				paradas.add(parada);
				nuevaData.append(nombreColeccion, paradas);
				mongo.actualizarMDB(nombreColeccion, nuevaData, dataARemplazar);
				mongo.cerrarConexion();
				return true;
			} else {
				System.out.println("Error: La parada no existe. Debe crear la parada " + clave
						+ " primero antes de añadirle elementos");
				
				
			}
		} else {
			System.out.println("Error: La ruta no existe. Debe crear la ruta " + nombreRuta
					+ " primero antes de añadirle elementos");
		}
		mongo.cerrarConexion();*/
		return false;
		
	}

	public static boolean crearHistoBus(BasicDBObject placa){
		return true;
	}

}
