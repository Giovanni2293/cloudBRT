package db;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.swing.text.html.HTMLDocument.HTMLReader.FormAction;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import core.Coordenadas;
import utilidad.FormatearDatos;
import utilidad.GeoMatematicas;

public class TransaccionesRecorrido {

	private static ConectarMongo mongo;
	private static final String nombreColeccion = "Recorrido";
	private static final String colleccionRuta = "Ruta";
	private static double velMed = 35; //KM/h
	
public static boolean crearRecorrido(String clave , String ruta) {
		
		DBObject consulta, consultarRuta;		
		BasicDBObject data , rutaDB, anterior, actual;
		int cantParadas;
		clave = clave.toUpperCase();
		ruta = ruta.toUpperCase();
		mongo = new ConectarMongo();
		data = new BasicDBObject("Clave", clave);
		rutaDB =  new BasicDBObject("Nombre", ruta);
		LinkedHashMap<String, String> recorrido = new LinkedHashMap<>();
		consultarRuta = mongo.consultarMDB(colleccionRuta, rutaDB);
		ArrayList<BasicDBObject> paradas = new ArrayList<>();
		paradas = (ArrayList<BasicDBObject>) consultarRuta.get("Ruta");
		cantParadas = paradas.size();
		anterior = paradas.get(0);
		recorrido.put(anterior.getString("Clave"), "0");
		Coordenadas coorAnt,coorAct;
		double tiempo = 0;
		coorAnt = null;
		coorAct = null;
		for(int i = 1; i<cantParadas ; i++){
			
			BasicDBObject temp;
			actual = paradas.get(i);
			temp = (BasicDBObject)anterior.get("Coordenada");
			coorAnt = new Coordenadas((double) temp.getDouble("Latitud"), (double) temp.getDouble("Longitud"));
			temp = (BasicDBObject)actual.get("Coordenada");
			coorAct =new Coordenadas((double) temp.getDouble("Latitud"), (double) temp.getDouble("Longitud"));
			tiempo = GeoMatematicas.hallarTiempo(GeoMatematicas.calcDistancia(coorAnt, coorAct), velMed);
			tiempo = FormatearDatos.hoursToMinutes(tiempo);
			recorrido.put(actual.getString("Clave"),""+tiempo);
			anterior = paradas.get(i);
		}
		
	
		System.out.println(recorrido);
		return true;
		
		
		
		
		
		
		
		
		
		
		
		/*
		
		
		consulta = mongo.consultarMDB(nombreColeccion, data);		
		if (consulta == null) {

			data.append(nombreColeccion, recorrido );
			mongo.insertarMDB(nombreColeccion, data);
			mongo.cerrarConexion();
			return true;
		} else {
			System.out.println("Error: El elemento ya existe en la base de datos");
		}
		return false;*/
	}
	
}
