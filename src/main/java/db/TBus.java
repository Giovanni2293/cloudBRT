package db;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
/**
 * El fin de esta clase es otorgar transacciones consernientes a un bus
 * 
 * @author Carlos Andr�s Pereira Grimaldo
 *
 */
public class TBus {

	private static DBGeneralBRT mongo;
	private static final String nombreColeccion = "Bus";

	public static boolean crearBus(String Placa, int Capacidad, String TipoBus, boolean Estado) {
		Placa = Placa.toUpperCase();
		if (Placa.length() != 6) {
			System.out.println(
					"Error: El nombre de la placa debe ser de 6 Letras o Numeros." + "D�gite una placa valida");
			return false;
		}
		BasicDBObject data;
		DBObject consulta;
		mongo = new DBGeneralBRT();
		data = new BasicDBObject("Placa", Placa);
		consulta = mongo.consultarMDB(nombreColeccion, data);
		if (consulta == null) {

			data = new BasicDBObject("Placa", Placa).append("Capacidad", Capacidad).append("TipoBus", TipoBus)
					.append("Estado", Estado);
			mongo.insertarMDB(nombreColeccion, data);
			mongo.cerrarConexion();
			return true;
		} else {
			System.out.println("Error: El Bus con placa: " + Placa + " ya existe en la base de datos");

		}
		mongo.cerrarConexion();
		return false;
	}

	public static boolean modificarEstado(String Placa, Boolean Estado) {
		Placa = Placa.toUpperCase();
		DBObject bus;
		BasicDBObject nuevaData, dataAReemplazar;
		mongo = new DBGeneralBRT();
		dataAReemplazar = new BasicDBObject("Placa", Placa);
		bus = mongo.consultarMDB(nombreColeccion, dataAReemplazar);
		if (bus != null) {
			nuevaData = new BasicDBObject("Placa", Placa).append("Capacidad", (int) bus.get("Capacidad"))
					.append("TipoBus", (String) bus.get("TipoBus")).append("Estado", Estado);
			mongo.actualizarMDB(nombreColeccion, nuevaData, dataAReemplazar);
			mongo.cerrarConexion();
			return true;
		} else {
			System.out.println("Error: No se encontro el bus con placa: " + Placa + "  en la base de datos");

		}
		mongo.cerrarConexion();
		return false;

	}

	public static boolean eliminar(String Placa) {
		Placa = Placa.toUpperCase();
		if (Placa.length() != 6) {
			System.out.println(
					"Error: El nombre de la placa debe ser de 6 Letras o Numeros." + "D�gite una placa valida");
			return false;
		}
		boolean elimino;
		mongo = new DBGeneralBRT();
		BasicDBObject placaAEliminar = new BasicDBObject("Placa", Placa);
		elimino = mongo.eliminarMDB(nombreColeccion, placaAEliminar);
		mongo.cerrarConexion();
		if (elimino == true) {
			System.out.println("Se ha eliminado el bus con placa " + Placa);
					
			return true;
		} else {
			System.out.println("Error: No se ha podido eliminar el bus con placa " + Placa + " Por que  no existe en"
					+ "la base de datos ");
			return false;
		}
		

	}

}
