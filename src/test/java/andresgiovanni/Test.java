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
 * @author Carlos Andrés Pereira Grimaldo
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
		//TransaccionesRuta.removerParadaDeRuta("P2",1);
		/*TransaccionesRuta.crearRuta("T3");
		TransaccionesRuta.añadirAlFinalDeRuta("T3", "ST1");
		TransaccionesRuta.añadirAlFinalDeRuta("T3", "ST2");
		TransaccionesRuta.añadirAlFinalDeRuta("T3", "ST3");
		TransaccionesRuta.añadirAlFinalDeRuta("T3", "ST4");
		TransaccionesRuta.añadirAlFinalDeRuta("T3", "ST5");*/
		TransaccionesRecorrido.crearRecorridoAutomatico("hola","T3", "23:54:59");
		//TransaccionesRuta.eliminarParadas("P2");
		/*TransaccionesParada.crearParada("ST1", "EST. TEMPRANA",7.002608, -73.055068);
		TransaccionesParada.crearParada("ST2", "EST. ESPANOLITA", 7.017099, -73.057545);
		TransaccionesParada.crearParada("ST3", "EST. PALMICHAL", 7.038484, -73.073994);
		TransaccionesParada.crearParada("ST4", "EST. ESTANCIA", 7.137213,  -73.122289);
		TransaccionesParada.crearParada("ST5", "EST. LAGOS", 7.066655, -73.099632);
		TransaccionesParada.crearParada("ST6", "EST. CAÑAVERAL", 7.070660, -73.104938);
		TransaccionesParada.crearParada("ST7", "EST. PROVENZA", 7.090184, -73.108926);
		TransaccionesParada.crearParada("ST8", "EST. LA ROSITA", 7.112485, -73.121840);
		TransaccionesParada.crearParada("ST9", "EST. CHORRERAS", 7.116745, -73.125948);
		TransaccionesParada.crearParada("ST10", "EST. SAN MATEO", 7.118520, -73.126639);
		TransaccionesParada.crearParada("ST11", "EST. QUEBRADASECA", 7.122087, -73.128070);
		TransaccionesParada.crearParada("ST12", "EST. CAMPO ALEGRE", 7.023763, -73.063267);
		TransaccionesParada.crearParada("ST13", "EST. MENZULY", 7.043636, -73.077585);
		TransaccionesParada.crearParada("ST14", "EST. PAYADOR", 7.137213,  -73.122289);
		TransaccionesParada.crearParada("ST15", "EST. ISLA", 7.107813, -73.116056);
		TransaccionesParada.crearParada("ST16", "EST. MOLINOS", 7.075204, -73.108315);
		TransaccionesParada.crearParada("ST17", "EST. HORMIGUEROS", 7.078735, -73.108101);
		TransaccionesParada.crearParada("ST18", "EST. PAYADOR", 7.084418, -73.107869);*/
		//TransaccionesParada.crearParada("ST18", "EST. PAYADOR", 7.095377, -73.110602);
		//TransaccionesParada.eliminarParada("ST3");
		//Test t = new Test();
		//t.imprimirRutas();
	    // System.out.println(FormatearDatos.ArreglarCharset("Ã±"));
		//System.out.println(FormatearDatos.mayusInicialMulti("Parque Estacion Uis"));
		//FormatearDatos.mayusInicialMulti("lagos");
		//TransaccionesRuta.crearRuta("T3");
		//TransaccionesRuta.removerParadaDeRuta("P2", 	4	);
		//TransaccionesRecorrido.crearRecorrido("hola2", "","");
		//System.out.println(FormatearDatos.formatoDeTiempo(3821));		
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
