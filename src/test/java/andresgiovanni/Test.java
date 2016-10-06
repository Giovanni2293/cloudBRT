package andresgiovanni;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.util.JSON;

import db.ConectarMongo;
import db.TransaccionesParada;
import db.TransaccionesRecorrido;
import db.TransaccionesRuta;
import utilidad.FormatearDatos;
/**
 * Esta es una clase que utilizaremos para probar el codigo antes de utilizarlo con
 * otras clases.
 * 
 * Mensaje para Angel: Angel por favor no use esta clase cuando descargue esta version.
 * @author Carlos Andr�s Pereira Grimaldo
 * @author Jose Giovanni Flores Nocua
 *
 */
public class Test {
	private ConectarMongo conexion;
	public static void main(String[] args) {
		
		//Insertar i = new Insertar();
		//TransaccionesBus.modificarEstado("XB536a", true);
		//Bus.crearBus("XB536a", 50, "Padron", false);
		//Bus.eliminar(Placa)
		//modificarEstado("XB536a", false);
		//TransaccionesRuta.reemplazarParadaDeRuta("P2", "ST1", 4);
		//TransaccionesRuta.a�adirAlFinalDeRuta("P2", "ST5");
		//TransaccionesParada.crearParada("ST3", "ca�averal", 7.137213,  -73.122289);
		//TransaccionesParada.eliminarParada("ST3");
		//Test t = new Test();
		//t.imprimirRutas();
	    // System.out.println(FormatearDatos.ArreglarCharset("ñ"));
		//System.out.println(FormatearDatos.mayusInicialMulti("Parque Estacion Uis"));
		//FormatearDatos.mayusInicialMulti("lagos");
		//TransaccionesRuta.crearRuta("T3");
		//TransaccionesRuta.removerParadaDeRuta("P2", 	4	);
		//TransaccionesRecorrido.crearRecorrido("hola", "P2");
		//System.out.println(FormatearDatos.formatoDeMinutos());		
	}
	
	
	 public String imprimirRutas(){
		 conexion = new ConectarMongo();
		 DBCollection collection = conexion.consultarColeccion("Ruta");
		 DBCursor cursor = collection.find();
		 ArrayList<BasicDBObject> rutas = new ArrayList<>();
		 ArrayList<BasicDBObject> ArrayParadas = null;
		 while (cursor.hasNext()) {
			 ArrayList<BasicDBObject> paradasSinId = new ArrayList<>();
        	 BasicDBObject obj = (BasicDBObject) cursor.next();
        	 BasicDBObject bso = new BasicDBObject();
        	 bso.append("Nombre",obj.get("Nombre"));
        	 ArrayParadas = (ArrayList<BasicDBObject>) obj.get("Ruta");
        	 for (BasicDBObject temp : ArrayParadas)
        	 {
        		BasicDBObject parada = new BasicDBObject();
        		parada.append("Nombre",temp.get("Nombre"));
        		parada.append("Coordenada",temp.get("Coordenada"));
        		paradasSinId.add(parada);
        	 }
        	 bso.append("Ruta",paradasSinId);
        	 
        	 rutas.add(bso);
		}
		 BasicDBObject data = new BasicDBObject("Rutas",rutas);
		 JsonReader jsonReader = Json.createReader(new StringReader(data.toString()));
		 JsonObject json = jsonReader.readObject();
		 System.out.println(json.toString());
		 
        
		 return null;
		 
	 }
	 
	 
}
