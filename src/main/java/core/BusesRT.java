package core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

import db.DBGeneralBRT;

public class BusesRT {

	// private static ArrayList<Bus> buses;
	private LinkedHashMap<String, Bus> busesHashMap;
	private static BusesRT p;
	private final String coleccion = "Bus";

	private BusesRT() {
		busesHashMap = new LinkedHashMap<>();
		// buses = new ArrayList<>();
		inicializarParque();
	}

	/*
	 * private void inicializarParque() { DBGeneralBRT conexion = new
	 * DBGeneralBRT(); DBCollection collection =
	 * conexion.consultarColeccion(coleccion); DBCursor cursor =
	 * collection.find(); buses = new ArrayList<>(); while (cursor.hasNext()) {
	 * BasicDBObject obj = (BasicDBObject) cursor.next(); buses.add(new
	 * Bus((String) obj.get("Placa"))); } }
	 */

	private void inicializarParque() {
		DBGeneralBRT conexion = new DBGeneralBRT();
		DBCollection collection = conexion.consultarColeccion(coleccion);
		DBCursor cursor = collection.find();
		while (cursor.hasNext()) {
			BasicDBObject obj = (BasicDBObject) cursor.next();
			busesHashMap.put((String) obj.get("Placa"), new Bus((String) obj.get("Placa")));
		}
	}

	public static BusesRT getBusesRT() {
		if (p == null) {
			p = new BusesRT();
		}

		return p;

	}

	/*
	 * public ArrayList<Bus> getBuses() { return buses; }
	 */

	public Bus encontrarBus(String placa) {
		return busesHashMap.get(placa);
	}

	/*
	 * public Bus encontrarBus(String placa) { Iterator<Bus> i =
	 * buses.iterator(); Bus temp; while (i.hasNext()) { temp = i.next(); if
	 * (placa.equals(temp.getPlaca())) { return temp; } } return null; }
	 */

	/**
	 * Encuentra un bus en el arreglo y modifica su estado
	 * 
	 * @param placa
	 * @param estado
	 */
	public void modificarEstado(String placa, boolean estado) {
		Bus encontrado = encontrarBus(placa);
		if (encontrado != null) {
			encontrado.setEstado(estado);
		}

	}

	/**
	 * Agrega un nuevo bus al arreglo solo si no existe previamente
	 * 
	 * @param bus
	 */

	public void agregarNuevo(Bus bus) {

		if (encontrarBus(bus.getPlaca()) == null) {
			busesHashMap.put(bus.getPlaca(), bus);
		}

	}

	/*
	 * public void agregarNuevo(Bus bus) {
	 * 
	 * if (encontrarBus(bus.getPlaca()) == null) { buses.add(bus); } }
	 */

	/**
	 * Encuentra un bus y lo elimina del arreglo.
	 * 
	 * @param placa
	 * @return
	 */

	public boolean eliminarBus(String placa) {
		Bus estado = busesHashMap.remove(placa);
		if (estado != null) {
			return true;
		}
		return false;
	}

	/*
	 * public boolean eliminarBus(String placa) { Iterator<Bus> i =
	 * buses.iterator(); Bus temp; while (i.hasNext()) { temp = i.next(); if
	 * (placa.equals(temp.getPlaca())) { i.remove(); return true; } } return
	 * false; }
	 */



}
