package baseDeDatosMDB;

import java.util.ArrayList;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;



public class Insertar {
	private ConectarMongo conexion;

	public Insertar() {
		// crearRuta("P10");
		// añadirAlFinalDeRuta("P2", "Payador");
		//añadirXPosicionARuta("P2", "Proveza", 3);
		//crearParada("Lagos", 7.066805, -73.099890);
	}

	public static void main(String[] args) {

		Insertar i = new Insertar();
		//TransaccionesBus.modificarEstado("XB536a", true);
		//Bus.crearBus("XB536a", 50, "Padron", false);
		//Bus.eliminar(Placa)
		//modificarEstado("XB536a", false);
		//TransaccionesRuta.reemplazarParadaDeRuta("P2", "Lagos", 4);
		//TransaccionesRuta.añadirAlFinalDeRuta("P2", "Provenza");
	}

	public String mayusInicial(String original) {
		original = original.toLowerCase();
		if (original == null || original.length() == 0) {
			return original;
		}
		return original.substring(0, 1).toUpperCase() + original.substring(1);
	}

	/**
	 * Este metodo permite insertar una parada a la base de datos con su latitud
	 * y longitud.
	 * 
	 * @param nombre
	 * @param latitud
	 * @param longitud
	 */
	public void crearParada(String nombre, double latitud, double longitud) {
		String nombreCap;
		DBObject consulta;		
		BasicDBObject data;
		nombreCap = mayusInicial(nombre);
		conexion = new ConectarMongo();
		data = new BasicDBObject("Nombre", nombreCap);
		consulta = conexion.consultarMDB("Parada", data);
		if (consulta == null) {

			data = new BasicDBObject("Nombre", nombreCap).append("Coordenada",
					new BasicDBObject("Latitud", latitud).append("Longitud", longitud));
			conexion.insertarMDB("Parada", data);
			conexion.cerrarConexion();
		} else {
			System.out.println("Error: El elemento ya existe en la base de datos");
		}

	}

	/**
	 * Crea una ruta nueva sin paradas. No permite que se creen rutas repetidas
	 * sin importar que se envien rutas en minuscula. Transforma de minusculas a
	 * mayusculas la entrada.
	 * 
	 * @param nombre
	 */
	public void crearRuta(String nombre) {
		String nombreMayus;
		nombreMayus = nombre.toUpperCase();
		DBObject consulta;
		BasicDBObject data;
		conexion = new ConectarMongo();
		data = new BasicDBObject("Nombre", nombreMayus);
		consulta = conexion.consultarMDB("Ruta", data);
		ArrayList<BasicDBObject> paradas = new ArrayList<>();
		if (consulta == null) {
			data.append("Ruta", paradas);
			conexion.insertarMDB("Ruta", data);
			conexion.cerrarConexion();
		} else {
			System.out.println(
					"Error: No se puede crear la ruta " + nombreMayus + ". Esta ya existe " + "en la base de datos");
		}
		conexion.cerrarConexion();

	}

	/**
	 * Añade la parada al final
	 * 
	 * @param nombreRuta
	 * @param nombreParada
	 */
	public void añadirAlFinalDeRuta(String nombreRuta, String nombreParada) {
		DBObject ruta;
		DBObject parada;
		ArrayList<DBObject> paradas = new ArrayList<>();
		BasicDBObject nuevaData, dataARemplazar;
		nuevaData = new BasicDBObject("Nombre", nombreRuta);
		dataARemplazar = new BasicDBObject("Nombre", nombreRuta);
		conexion = new ConectarMongo();
		ruta = conexion.consultarMDB("Ruta", dataARemplazar);
		if (ruta != null) {
			paradas = (ArrayList<DBObject>) ruta.get("Ruta");
			parada = conexion.consultarMDB("Parada", new BasicDBObject("Nombre", nombreParada));
			if (parada != null) {
				paradas.add(parada);
				nuevaData.append("Ruta", paradas);
				conexion.actualizarMDB("Ruta", nuevaData, dataARemplazar);
				conexion.cerrarConexion();
			} else {
				System.out.println("Error: La parada no existe. Debe crear la parada " + nombreParada
						+ " primero antes de añadirle elementos");
			}
		} else {
			System.out.println("Error: La ruta no existe. Debe crear la ruta " + nombreRuta
					+ " primero antes de añadirle elementos");
		}
	}

	

	/**
	 * Consulta una parada existente y añade la nueva parada sin reemplazar,
	 * desplazando los demas elemententos. Este metodo no añade al final
	 * 
	 * @param nombreRuta
	 * @param nombreParada
	 * @param posicion
	 */
	public void añadirXPosicionARuta(String nombreRuta, String nombreParada, int posicion) {
		DBObject ruta;
		DBObject parada;
		posicion = posicion - 1;
		ArrayList<DBObject> paradas = new ArrayList<>();
		BasicDBObject nuevaData, dataARemplazar;
		nuevaData = new BasicDBObject("Nombre", nombreRuta);
		dataARemplazar = new BasicDBObject("Nombre", nombreRuta);
		conexion = new ConectarMongo();
		ruta = conexion.consultarMDB("Ruta", dataARemplazar);
		if (ruta != null) {
			paradas = (ArrayList<DBObject>) ruta.get("Ruta");
			if (posicion >= 0 && posicion <= paradas.size()) {
				parada = conexion.consultarMDB("Parada", new BasicDBObject("Nombre", nombreParada));
				if (parada != null) {
					paradas.add(posicion, parada);
					nuevaData.append("Ruta", paradas);
					conexion.actualizarMDB("Ruta", nuevaData, dataARemplazar);
					conexion.cerrarConexion();
				} else {
					System.out.println("Error: La parada no existe. Debe crear la parada " + nombreParada
							+ " primero antes de añadirle elementos");
				}
			} else {
				System.out.println("Error: Ingrese una posicion entre: 1 y " + paradas.size());
			}
		} else {
			System.out.println("Error: La ruta " + nombreRuta + " a la que esta intentando añadir elementos no existe");
		}
	}
	 public String imprimirRutas(){
		 conexion = new ConectarMongo();
		 DBCollection collection = conexion.consultarColeccion("Ruta");
		 DBCursor cursor = collection.find();
		 JSON json = new JSON();
		 String rutas = json.serialize(cursor);
		 System.out.println(rutas);
		 conexion.cerrarConexion();
		 return rutas;
		 
	 }

}
