package baseDeDatosMDB;

import java.awt.List;
import java.util.ArrayList;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import clasesDeUtilidad.GeoMatematicas;

public class Insertar {
	private ConectarMongo d;
	private BasicDBObject data;
	
	public Insertar(){
		
	}

	public static void main(String[] args) {

		Insertar i = new Insertar();
	}

	public void insertarParada() {
		String nombre = "Lagos";
		double latitud = 7.066661;
		double longitud = -73.099633;
		DBObject prueba;
		d = new ConectarMongo();
		data = new BasicDBObject("Nombre", nombre);
		prueba = d.consultarMDB("GeneralBRT", "Parada", data);
		if (prueba == null) {

			data = new BasicDBObject("Nombre", nombre).append("Coordenada",
					new BasicDBObject("Latitud", latitud).append("Longitud", longitud));
			d.insertarMDB("GeneralBRT", "Parada", data);
		}else{
			System.out.println("Error: El elemento ya existe en la base de datos");
		}

	}
	public void insertarRuta() {
		String nombre = "P1";
		String parada = "";
		
		DBObject prueba;
		DBObject prueba2;
		d = new ConectarMongo();
		data = new BasicDBObject("Nombre", nombre);
		
		prueba = d.consultarMDB("GeneralBRT", "Ruta", data);
		if (prueba == null) {
			prueba2 = d.consultarMDB("GeneralBRT", "Parada", new BasicDBObject("Nombre", parada));
			ArrayList<BasicDBObject> paradas = new ArrayList<>();
			paradas.add((BasicDBObject) prueba2);
			//data = new BasicDBObject("Nombre", ).append();
					
			d.insertarMDB("GeneralBRT", "Ruta", data);
		}else{
			System.out.println("Error: El elemento ya existe en la base de datos");
		}

	}


}
