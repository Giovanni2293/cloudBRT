package core;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

import db.DBGeneralBRT;
import utilidad.FormatearDatos;

/**
 * Clase encargada de administrar todos los itinerarios.
 * 
 * @author 123
 *
 */
public class Despacho {

	private static Despacho d = null;
	private ArrayList<Itinerario> itinerarios;
	private final String coleccion = "Itinerario";

	private Despacho() {

		inicializarDespacho();
	}

	private void inicializarDespacho() {
		// TODO Auto-generated method stub

		DBGeneralBRT conexion = new DBGeneralBRT();
		DBCollection collection = conexion.consultarColeccion(coleccion);
		DBCursor cursor = collection.find();
		itinerarios = new ArrayList<>();
		while (cursor.hasNext()) {

			BasicDBObject obj = (BasicDBObject) cursor.next();
			Itinerario i = new Itinerario((String) obj.get("Clave"));
			if (i.getTerminado() == false) {
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

	public void anadirItinerario(String clave) {
		itinerarios.add(new Itinerario(clave));
	}

	public ArrayList<Itinerario> getItinerarios() {
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
		Refrescar();
		Iterator<Itinerario> i = itinerarios.iterator();
		System.out.println("Numero de itinerarios:" + itinerarios.size());
		ArrayList<Itinerario> salida = new ArrayList<>();
		Itinerario temp;
		System.out.println(i.hasNext());
		while (i.hasNext()) {
			temp = i.next();
			if (temp.getBusDesignado().getPlaca().equals(Placa)) {
				salida.add(temp);
			}
		}

		if (salida.size() == 0) {
			return null;
		} else {
			return ordenarItinerarios(salida);
		}
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

	public void Refrescar() {
		inicializarDespacho();
	}

	public ArrayList<Itinerario> ordenarItinerarios(ArrayList<Itinerario> busqueda) {
		int tamano = busqueda.size();
		double[] entrada = new double[tamano];
		LinkedHashMap<Double,Itinerario> diccionario=new LinkedHashMap<>();
		int indice = 0;
		for (Itinerario b : busqueda) {
			
			entrada[indice] = FormatearDatos.removerFormatoDeTiempo(b.getRecorridoDesignado().getHoraPartida());
			diccionario.put(entrada[indice],b);
			indice++;
		}
		FormatearDatos.quickSort(0, entrada.length - 1, entrada);
		busqueda = new ArrayList<>();
		for (int i = 0; i < tamano; i++) {
			
			busqueda.add(diccionario.get(entrada[i]));
		}
		
		return busqueda;
	}

	/**
	 * Metodo de pruebas
	 */
	public void mostrarItinerarios() {
		System.out.println(itinerarios.size());
		for (Itinerario b : itinerarios) {
			System.out.println("Conductor:" + b.getConductorDesignado().getCedula());
			System.out.println("Id:" + b.getId());
			System.out.println("Bus:" + b.getBusDesignado().getPlaca());
			System.out.println("Recorrido" + b.getRecorridoDesignado().getClaveRecorrido());
			System.out.println("HoraSalida:" + b.getHoraSalidaReal());
			System.out.println("CantParadas:" + b.getCantParadas());
			System.out.println("Indice:" + b.getIndex());
			System.out.println("Terminado:" + b.getTerminado());
		}
	}
}
