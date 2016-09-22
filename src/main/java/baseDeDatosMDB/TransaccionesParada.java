package baseDeDatosMDB;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import clasesDeUtilidad.FormatearDatos;

/**
 * El fin de esta clase es otorgar transacciones consernientes a una Parada
 * 
 * @author Carlos Andrés Pereira Grimaldo
 * @author Jose Giovanni Florez Nocua
 *
 */
public class TransaccionesParada {

	private static ConectarMongo mongo;
	private static final String nombreColeccion = "Parada";
	
	/**
	 * Este metodo permite insertar una parada a la base de datos con su latitud
	 * y longitud.
	 * 
	 * @param nombre
	 * @param latitud
	 * @param longitud
	 */
	public static boolean crearParada(String nombre, double latitud, double longitud) {
		String nombreCap;
		DBObject consulta;		
		BasicDBObject data;
		nombreCap = FormatearDatos.mayusInicialMulti(nombre);
		mongo = new ConectarMongo();
		data = new BasicDBObject("Nombre", nombreCap);
		consulta = mongo.consultarMDB(nombreColeccion, data);
		if (consulta == null) {

			data = new BasicDBObject("Nombre", nombreCap).append("Coordenada",
					new BasicDBObject("Latitud", latitud).append("Longitud", longitud));
			mongo.insertarMDB(nombreColeccion, data);
			mongo.cerrarConexion();
			return true;
		} else {
			System.out.println("Error: El elemento ya existe en la base de datos");
		}
		return false;
	}
	public static boolean eliminarParada(String parada) {
		boolean elimino;
		parada = FormatearDatos.mayusInicialMulti(parada);
		mongo = new ConectarMongo();
		BasicDBObject rutaAEliminar = new BasicDBObject("Nombre",parada);
		elimino = mongo.eliminarMDB(nombreColeccion,rutaAEliminar);
		if (elimino == true){
			System.out.println("Se ha eliminado la parada " + parada );
		}else{
			System.out.println("No se ha podido eliminar la parada " + parada +" Por que  no existe en"
					+ "la base de datos ");
		}
		mongo.cerrarConexion();
		return elimino;
		
	}

	
}
