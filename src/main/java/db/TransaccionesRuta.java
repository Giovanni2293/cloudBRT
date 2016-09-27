package db;

import java.util.ArrayList;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import utilidad.FormatearDatos;


/**
 * El fin de esta clase es otorgar transacciones consernientes a una Ruta
 * 
 * @author Carlos Andr�s Pereira Grimaldo
 * @author Jose Giovanni Florez Nocua
 *
 */
public class TransaccionesRuta {

	private static ConectarMongo mongo;
	private static final String nombreColeccion = "Ruta";

	/**
	 * Crea una ruta nueva sin paradas. No permite que se creen rutas repetidas
	 * sin importar que se envien rutas en minuscula. Transforma de minusculas a
	 * mayusculas la entrada.
	 * 
	 * @param nombre
	 */
	public static boolean crearRuta(String nombre) {
		String nombreMayus;
		nombreMayus = nombre.toUpperCase();
		DBObject consulta;
		BasicDBObject data;
		mongo = new ConectarMongo();
		data = new BasicDBObject("Nombre", nombreMayus);
		consulta = mongo.consultarMDB(nombreColeccion, data);
		ArrayList<BasicDBObject> paradas = new ArrayList<>();
		if (consulta == null) {
			data.append(nombreColeccion, paradas);
			mongo.insertarMDB(nombreColeccion, data);
			mongo.cerrarConexion();
			return true;
		} else {
			System.out.println(
					"Error: No se puede crear la ruta " + nombreMayus + ". Esta ya existe " + "en la base de datos");
			
		}
		mongo.cerrarConexion();
		return false;
	}

	public static boolean a�adirXPosicionARuta(String nombreRuta, String clave, int posicion) {
		DBObject ruta;
		DBObject parada;
		clave = clave.toUpperCase();
		nombreRuta = nombreRuta.toUpperCase();
		posicion = posicion - 1;
		ArrayList<DBObject> paradas = new ArrayList<>();
		BasicDBObject nuevaData, dataARemplazar;
		nuevaData = new BasicDBObject("Nombre", nombreRuta);
		dataARemplazar = new BasicDBObject("Nombre", nombreRuta);
		mongo = new ConectarMongo();
		ruta = mongo.consultarMDB(nombreColeccion, dataARemplazar);
		if (ruta != null) {
			paradas = (ArrayList<DBObject>) ruta.get(nombreColeccion);
			if (posicion >= 0 && posicion <= paradas.size()) {
				parada = mongo.consultarMDB("Parada", new BasicDBObject("Clave", clave));
				if (parada != null) {
					paradas.add(posicion, parada);
					nuevaData.append(nombreColeccion, paradas);
					mongo.actualizarMDB(nombreColeccion, nuevaData, dataARemplazar);
					mongo.cerrarConexion();
					return true;
				} else {
					System.out.println("Error: La parada no existe. Debe crear la parada " + clave
							+ " primero antes de a�adirle elementos");
					
				}
			} else {
				System.out.println("Error: Ingrese una posicion entre: 1 y " + paradas.size());
				
			}
		} else {
			System.out.println("Error: La ruta " + nombreRuta + " a la que esta intentando a�adir elementos no existe");
			
		}
		mongo.cerrarConexion();
		return false;
	}

	public static boolean a�adirAlFinalDeRuta(String nombreRuta, String clave) {
		DBObject ruta;
		DBObject parada;
		nombreRuta =nombreRuta.toUpperCase();
		clave = clave.toUpperCase();
		ArrayList<DBObject> paradas = new ArrayList<>();
		BasicDBObject nuevaData, dataARemplazar;
		nuevaData = new BasicDBObject("Nombre", nombreRuta);
		dataARemplazar = new BasicDBObject("Nombre", nombreRuta);
		mongo = new ConectarMongo();
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
						+ " primero antes de a�adirle elementos");
				
				
			}
		} else {
			System.out.println("Error: La ruta no existe. Debe crear la ruta " + nombreRuta
					+ " primero antes de a�adirle elementos");
		}
		mongo.cerrarConexion();
		return false;
		
	}

	
	public static boolean eliminarRuta(String ruta) {
		boolean elimino;
		ruta = ruta.toUpperCase();
		mongo = new ConectarMongo();
		BasicDBObject rutaAEliminar = new BasicDBObject("Nombre", ruta);
		elimino = mongo.eliminarMDB(nombreColeccion, rutaAEliminar);
		

		if (elimino == true) {
			System.out.println("Se ha eliminado la ruta " + ruta);
		} else {
			System.out.println(
					"No se ha podido eliminar la ruta " + ruta + " Por que  no existe en" + "la base de datos ");
		}
		mongo.cerrarConexion();
		return elimino;

	}

	public static boolean removerParadaDeRuta(String nombreRuta, int posicion) {
		DBObject ruta;
		nombreRuta = nombreRuta.toUpperCase();
		posicion = posicion - 1;
		ArrayList<DBObject> paradas = new ArrayList<>();
		BasicDBObject nuevaData, dataARemplazar;
		nuevaData = new BasicDBObject("Nombre", nombreRuta);
		dataARemplazar = new BasicDBObject("Nombre", nombreRuta);
		mongo = new ConectarMongo();
		ruta = mongo.consultarMDB(nombreColeccion, dataARemplazar);
		if (ruta != null) {
			paradas = (ArrayList<DBObject>) ruta.get(nombreColeccion);
			if (posicion >= 0 && posicion <= paradas.size()) {

				paradas.remove(posicion);
				nuevaData.append(nombreColeccion, paradas);
				mongo.actualizarMDB(nombreColeccion, nuevaData, dataARemplazar);
				mongo.cerrarConexion();
				return true;
			} else {
				System.out.println("Error: Ingrese una posicion entre: 1 y " + paradas.size());
				
			}
		} else {
			System.out.println("Error: La parada " + nombreRuta + " a la que esta intentando acceder no existe");
			
		}
		mongo.cerrarConexion();
		return false;
	}
	
	public static boolean reemplazarParadaDeRuta(String nombreRuta, String clave, int posicion){
		boolean estado1,estado2;
		
		estado1 = a�adirXPosicionARuta(nombreRuta, clave, posicion);
		if(estado1 == true){
			System.out.println("se a�adio");
		estado2 = removerParadaDeRuta(nombreRuta, posicion+1);
		if(estado2 == true){
			System.out.println("se removio el siguiente");
		}else{
			estado1 = false;
		}
		}else {
			System.out.println("fallo");
		}
			
		return estado1 ;
	}
}
