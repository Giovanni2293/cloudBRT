package db;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import utilidad.FormatearDatos;

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
	public static boolean crearParada(String clave , String nombre, double latitud, double longitud) {
		
		DBObject consulta;		
		BasicDBObject data;
		clave = clave.toUpperCase();
		nombre = FormatearDatos.mayusInicialMulti(nombre);
		mongo = new ConectarMongo();
		data = new BasicDBObject("Clave", clave);
		consulta = mongo.consultarMDB(nombreColeccion, data);
		if (consulta == null) {

			data = new BasicDBObject("Clave", clave).append("Nombre", nombre).append("Coordenada",
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
		parada = parada.toUpperCase();
		mongo = new ConectarMongo();
		BasicDBObject paradaAEliminar = new BasicDBObject("Clave",parada);
		elimino = mongo.eliminarMDB(nombreColeccion,paradaAEliminar);
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
