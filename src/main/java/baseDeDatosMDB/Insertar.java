package baseDeDatosMDB;

import java.awt.List;
import java.util.ArrayList;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import clasesDeUtilidad.GeoMatematicas;

public class Insertar {
	private ConectarMongo conexion;
	
	public Insertar(){
		//crearRuta("P2");
		añadirARuta("P2","Provenza");
	}

	public static void main(String[] args) {

		Insertar i = new Insertar();
	}
	
	public String mayusInicial(String original)
	{
		original = original.toLowerCase();
	if (original == null || original.length()==0)
	{
		return original;
	}
	return original.substring(0,1).toUpperCase() + original.substring(1);
	}

	/**
	 * Este metodo permite insertar una parada a la base de datos con su latitud y longitud.
	 * @param nombre
	 * @param latitud
	 * @param longitud
	 */
	public void insertarParada(String nombre,double latitud,double longitud) {
		DBObject prueba;
	    String nombreCap;
	    BasicDBObject data;
	    nombreCap = mayusInicial(nombre);
		conexion = new ConectarMongo();
		data = new BasicDBObject("Nombre", nombreCap);
		prueba = conexion.consultarMDB("GeneralBRT", "Parada", data);
		if (prueba == null) {

			data = new BasicDBObject("Nombre", nombreCap).append("Coordenada",
					new BasicDBObject("Latitud", latitud).append("Longitud", longitud));
			conexion.insertarMDB("GeneralBRT", "Parada", data);
		}else{
			System.out.println("Error: El elemento ya existe en la base de datos");
		}

	}
	
	/**
	 * Crea una ruta nueva sin paradas. No permite que se creen rutas repetidas sin importar que se envien rutas
	 * en minuscula. Transforma de minusculas a mayusculas la entrada.
	 * @param nombre
	 */
	public void crearRuta(String nombre)
	{
		String nombreMayus;
	    nombreMayus=nombre.toUpperCase();
		DBObject consulta;
		BasicDBObject data;
		conexion= new ConectarMongo();
		data= new BasicDBObject("Nombre", nombreMayus);
		consulta = conexion.consultarMDB("GeneralBRT", "Ruta", data);
		ArrayList<BasicDBObject> paradas = new ArrayList<>();
		if (consulta == null)
		{
			data.append("Ruta",paradas);
			conexion.insertarMDB("GeneralBRT", "Ruta", data);
		}
		else
		{
			System.out.println("Error: No se puede crear la ruta " + nombreMayus + ". Esta ya existe "
					+ "en la base de datos" );
		}
		conexion.cerrarConexion();
		
	}
	
	public void añadirARuta(String nombreRuta,String nombreParada)
	{
		DBObject ruta;
		ArrayList<DBObject> paradas = new ArrayList<>();
		BasicDBObject nuevaData,dataARemplazar;
		nuevaData = new BasicDBObject("Nombre",nombreRuta);
		dataARemplazar = new BasicDBObject("Nombre",nombreRuta);
		conexion= new ConectarMongo();
		ruta = conexion.consultarMDB("GeneralBRT","Ruta",dataARemplazar);
	    paradas = (ArrayList<DBObject>) ruta.get("Ruta");
	    paradas.add(conexion.consultarMDB("GeneralBRT","Parada", 
	    		new BasicDBObject("Nombre",nombreParada)));
	    nuevaData.append("Ruta",paradas);
		conexion.actualizarMDB("GeneralBRT", "Ruta",nuevaData,dataARemplazar);
	}
	

}
