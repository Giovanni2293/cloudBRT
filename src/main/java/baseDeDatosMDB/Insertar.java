package baseDeDatosMDB;

import java.awt.List;
import java.util.ArrayList;
import java.util.Iterator;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import clasesDeUtilidad.GeoMatematicas;

public class Insertar {
	private ConectarMongo conexion;

	public Insertar() {
		//crearRuta("P3");
		// añadirARuta("P2","Payador");
		//añadirXPosicionARuta("P2", "Provenza",6);
	}

	public static void main(String[] args) {

		Insertar i = new Insertar();
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
	public void insertarParada(String nombre, double latitud, double longitud) {
		DBObject prueba;
		String nombreCap;
		BasicDBObject data;
		nombreCap = mayusInicial(nombre);
		conexion = new ConectarMongo();
		data = new BasicDBObject("Nombre", nombreCap);
		prueba = conexion.consultarMDB("GeneralBRT", "Parada", data);
		if (prueba == null) {

			data = new BasicDBObject("Nombre", nombreCap).append("Coordenada",
					new BasicDBObject("Latitud", latitud).append("Longitud", longitud));
			conexion.insertarMDB("GeneralBRT", "Parada", data);
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
		consulta = conexion.consultarMDB("GeneralBRT", "Ruta", data);
		ArrayList<BasicDBObject> paradas = new ArrayList<>();
		if (consulta == null) {
			data.append("Ruta", paradas);
			conexion.insertarMDB("GeneralBRT", "Ruta", data);
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
		ArrayList<DBObject> paradas = new ArrayList<>();
		BasicDBObject nuevaData, dataARemplazar;
		nuevaData = new BasicDBObject("Nombre", nombreRuta);
		dataARemplazar = new BasicDBObject("Nombre", nombreRuta);
		conexion = new ConectarMongo();
		ruta = conexion.consultarMDB("GeneralBRT", "Ruta", dataARemplazar);
		if (ruta != null) {
			paradas = (ArrayList<DBObject>) ruta.get("Ruta");
			paradas.add(conexion.consultarMDB("GeneralBRT", "Parada", new BasicDBObject("Nombre", nombreParada)));
			nuevaData.append("Ruta", paradas);
			conexion.actualizarMDB("GeneralBRT", "Ruta", nuevaData, dataARemplazar);
		} else {
			System.out.println(
					"Error: La ruta no existe. Debe crear la ruta " + nombreRuta + " primero antes de añadirle elementos");
		}
	}

	// public void listarParadasDeRuta(String nombreRuta)

	/**
	 * Consulta una parada existente y añade la nueva parada sin reemplazar, desplazando los
	 * demas elemententos.
	 * Este metodo no añade al final
	 * @param nombreRuta
	 * @param nombreParada
	 * @param posicion
	 */
	public void añadirXPosicionARuta(String nombreRuta, String nombreParada, int posicion) {
		DBObject ruta;
		posicion = posicion - 1;
		ArrayList<DBObject> paradas = new ArrayList<>();
		BasicDBObject nuevaData, dataARemplazar;
		nuevaData = new BasicDBObject("Nombre", nombreRuta);
		dataARemplazar = new BasicDBObject("Nombre", nombreRuta);
		conexion = new ConectarMongo();
		ruta = conexion.consultarMDB("GeneralBRT", "Ruta", dataARemplazar);
		if (ruta != null) {
			paradas = (ArrayList<DBObject>) ruta.get("Ruta");
			if (posicion >= 0 && posicion <= paradas.size()) {
				paradas.add(posicion,
						conexion.consultarMDB("GeneralBRT", "Parada", new BasicDBObject("Nombre", nombreParada)));
				nuevaData.append("Ruta", paradas);
				conexion.actualizarMDB("GeneralBRT", "Ruta", nuevaData, dataARemplazar);
			} else {
				System.out.println("Error: Ingrese una posicion entre: 1 y " + paradas.size());
			}
		}
		else
		{
				System.out.println("Error: La ruta " + nombreRuta + " a la que esta intentando añadir elementos no existe");
		}
	}

}
