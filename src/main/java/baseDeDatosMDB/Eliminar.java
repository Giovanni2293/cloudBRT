package baseDeDatosMDB;

import java.util.ArrayList;


import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;


public class Eliminar {
	
	public static void main(String[] args) {

		Eliminar e = new Eliminar();
		e.eliminarParada("Lagos");;
		System.out.println("Eliminar");
	}
	
private ConectarMongo conexion;


	public void eliminarRuta(String ruta) {
		boolean elimino;
		conexion = new ConectarMongo();
		BasicDBObject rutaAEliminar = new BasicDBObject("Nombre",ruta);
		elimino = conexion.eliminarMDB("GeneralBRT","Ruta",rutaAEliminar);
		
		if (elimino == true){
			System.out.println("Se ha eliminado la ruta " + ruta );
		}else{
			System.out.println("No se ha podido eliminar la ruta " + ruta +" Por que  no existe en"
					+ "la base de datos ");
		}
		conexion.cerrarConexion();
		
	}
	public void eliminarParada(String parada) {
		boolean elimino;
		
		conexion = new ConectarMongo();
		BasicDBObject rutaAEliminar = new BasicDBObject("Nombre",parada);
		elimino = conexion.eliminarMDB("GeneralBRT","Parada",rutaAEliminar);
		if (elimino == true){
			System.out.println("Se ha eliminado la parada " + parada );
		}else{
			System.out.println("No se ha podido eliminar la parada " + parada +" Por que  no existe en"
					+ "la base de datos ");
		}
		conexion.cerrarConexion();
		
	}
	
	public void eliminarXPosicionParada(String nombreRuta, int posicion) {
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
									
					paradas.remove(posicion);
					nuevaData.append("Ruta", paradas);
					conexion.actualizarMDB("GeneralBRT", "Ruta", nuevaData, dataARemplazar);
					conexion.cerrarConexion();
				} 
			 else {
				System.out.println("Error: Ingrese una posicion entre: 1 y " + paradas.size());
			}
		} else {
			System.out.println("Error: La para " + nombreRuta + " a la que esta intentando acceder no existe");
		}
	}
	
}
