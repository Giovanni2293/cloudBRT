package baseDeDatosMDB;

import com.mongodb.BasicDBObject;


public class Eliminar {
	
	public static void main(String[] args) {

		Eliminar e = new Eliminar();
		e.eliminarRuta("P2");
		System.out.println("Eliminar");
	}
	
private ConectarMongo conexion;


	public void eliminarRuta(String ruta) {
		conexion = new ConectarMongo();
		BasicDBObject rutaAEliminar = new BasicDBObject("Nombre",ruta);
		conexion.eliminarMDB("GeneralBRT","Ruta",rutaAEliminar);
		
	}
	
	public void eliminarParadaDeRuta(){
		
	}
	
}
