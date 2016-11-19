package core;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

import db.DBGeneralBRT;

/**
 * Clase encargada de administrar todos los itinerarios.
 * 
 * @author 123
 *
 */
public class Despacho {

	private static Despacho d = null;
	private ArrayList<Itinerario> itinerarios;
	private final String coleccion = "Itinerario" ;

	private Despacho() {
		
		inicializarDespacho();
	}

	private void inicializarDespacho() {
		// TODO Auto-generated method stub

		DBGeneralBRT conexion = new DBGeneralBRT();
		DBCollection collection = conexion.consultarColeccion(coleccion );
		DBCursor cursor = collection.find();
		itinerarios = new ArrayList<>();
		while (cursor.hasNext()) {
			
			BasicDBObject obj = (BasicDBObject) cursor.next();
			Itinerario i =  new Itinerario((String) obj.get("Clave"));
			if(i.getTerminado()==false){
			itinerarios.add(i);
			}
		}
	}

	public static Despacho getDespacho() {
		if (d == null) {
			d = new Despacho();
			return d;
		} else {
			return d;
		}
	}

	public void añadirItinerario(String clave) {
		itinerarios.add(new Itinerario(clave));
	}
	
	public ArrayList<Itinerario> getItinerarios()
	{
		return itinerarios;
	}
	
	public Itinerario encontrarItinerario(String id) {
		Iterator<Itinerario> i = itinerarios.iterator();
		Itinerario temp;
		while (i.hasNext()) {
			temp = i.next();
			if (id.equals(temp.getId())) {
				return temp;
			}
		}
		return null;
	}

	/**
	 * Todos los itinerarios que contengan el bus con la placa dada
	 * 
	 * @return
	 */
	public ArrayList<Itinerario> encontarXBus(String Placa) {
		Placa = Placa.toUpperCase();
		Iterator<Itinerario> i = itinerarios.iterator();
		ArrayList<Itinerario> salida = new ArrayList<>();
		Itinerario temp;
		while (i.hasNext()) {
			temp = i.next();
			if (temp.getBusDesignado().getPlaca().equals(Placa)) {
				salida.add(temp);
			}
			return salida;
		}
		return null;
	}

	/**
	 * Busca todos los itinerarios que tengan la misma ruta solicitada
	 * 
	 * @param ruta
	 * @return
	 */
	public ArrayList<Itinerario> encontarXRuta(String ruta) {
		Iterator<Itinerario> i = itinerarios.iterator();
		ArrayList<Itinerario> salida = new ArrayList<>();
		Itinerario temp;
		while (i.hasNext()) {
			temp = i.next();
			if (ruta.equals(temp.getRecorridoDesignado().getRuta().getNombre())) {
				salida.add(temp);
			}
		}
		return salida;
	}

	public ArrayList<Itinerario> encontarXRecorrido(String recorrido) {
		Iterator<Itinerario> i = itinerarios.iterator();
		ArrayList<Itinerario> salida = new ArrayList<>();
		Itinerario temp;
		while (i.hasNext()) {
			temp = i.next();
			if (recorrido.equals(temp.getRecorridoDesignado().getClaveRecorrido())) {
				salida.add(temp);
			}
		}
		return salida;
	}

	public void Refrescar(){
		inicializarDespacho();
	}
	
}
