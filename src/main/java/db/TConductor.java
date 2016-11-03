package db;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class TConductor {
	

	
	private static DBPersonal mongo;
	private static final String nombreColeccion = "Conductores";
	
	public static boolean crearBus( long cedula, String primerNombre, String segundoNombre, 
			String primerApellido, String segundoApellido , long licencia, String tipoSangre) {
		cedula = cedula;
		BasicDBObject data;
		DBObject consulta;
		mongo = new DBPersonal();
		data = new BasicDBObject("Cedula", cedula);
		consulta = mongo.consultarMDB(nombreColeccion, data);
		if (consulta == null) {

			data.append("Primer Nombre", primerNombre).append("Segundo Nombre", segundoNombre)
					.append("Primer Apellido", primerApellido).append("Segundo Apellido", segundoApellido )
					.append("Numero de Licencia", licencia).append("Grupo Sanguineo", tipoSangre);
			mongo.insertarMDB(nombreColeccion, data);
			mongo.cerrarConexion();
			return true;
		} else {
			System.out.println("Error: El conductor con cedula: " + cedula + " ya existe en la base de datos");

		}
		mongo.cerrarConexion();
		return false;
	}
}
