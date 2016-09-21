package baseDeDatosMDB;

import java.util.ArrayList;


import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;


public class Eliminar {
	
	public static void main(String[] args) {

		Eliminar e = new Eliminar();
		e.eliminarXPosicionParada("P2", 1);
		System.out.println("Eliminar");
	}
	
private ConectarMongo conexion;


	public void eliminarRuta(String ruta) {
		conexion = new ConectarMongo();
		BasicDBObject rutaAEliminar = new BasicDBObject("Nombre",ruta);
		conexion.eliminarMDB("GeneralBRT","Ruta",rutaAEliminar);
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
