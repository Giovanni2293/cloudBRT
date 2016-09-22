package baseDeDatosMDB;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
/**
 * El fin de esta clase es otorgar transacciones consernientes a un bus
 * 
 * @author Carlos Andrés Pereira Grimaldo
 *
 */
public class TransaccionesBus {

	private static ConectarMongo mongo;
	private static final String nombreColeccion = "Bus";

	public static boolean crearBus(String Placa, int Capacidad, String TipoBus, boolean Estado) {
		Placa = Placa.toUpperCase();
		if (Placa.length() != 6) {
			System.out.println(
					"Error: El nombre de la placa debe ser de 6 Letras o Numeros." + "Dígite una placa valida");
			return false;
		}
		BasicDBObject data;
		DBObject consulta;
		mongo = new ConectarMongo();
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
		DBObject Bus;
		BasicDBObject nuevaData, dataAReemplazar;
		mongo = new ConectarMongo();
		dataAReemplazar = new BasicDBObject("Placa", Placa);
		Bus = mongo.consultarMDB(nombreColeccion, dataAReemplazar);
		if (Bus != null) {
			nuevaData = new BasicDBObject("Placa", Placa).append("Capacidad", (int) Bus.get("Capacidad"))
					.append("TipoBus", (String) Bus.get("TipoBus")).append("Estado", Estado);
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
					"Error: El nombre de la placa debe ser de 6 Letras o Numeros." + "Dígite una placa valida");
			return false;
		}
		boolean elimino;
		mongo = new ConectarMongo();
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
