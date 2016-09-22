package AndresGiovanni;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.util.JSON;

import baseDeDatosMDB.ConectarMongo;
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
		//TransaccionesRuta.reemplazarParadaDeRuta("P2", "Lagos", 4);
		//TransaccionesRuta.añadirAlFinalDeRuta("P2", "Provenza");
		//TransaccionesParada.crearParada("Parque estacion uiS", 7.137213,  -73.122289);
		//TransaccionesParada.eliminarParada("Parque estacion uis");
		
		//FormatearDatos.mayusInicialMulti("lagos");
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
