package db;

import java.util.ArrayList;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import core.Fecha;

public class TColectorBus {

	private static DBColector mongo;
	private static String fecha;
	private static final String nombreColeccion = "HistoBuses";

	public static boolean regDiarioBuses(DBObject data , String placa ) {
		mongo = new DBColector();
		BasicDBObject nuevaData,dataARemplazar;
		fecha = Fecha.getFechaClass().getSoloYMD();
		DBObject consulta;
		dataARemplazar = new BasicDBObject("Bus", placa);
		nuevaData = new BasicDBObject("Bus", placa);
		ArrayList<DBObject> fechas = new ArrayList<>();	
		ArrayList<DBObject> registros = new ArrayList<>();
		consulta = mongo.consultarMDB(nombreColeccion, dataARemplazar);
		if(consulta != null ){
			fechas =  (ArrayList<DBObject>) consulta.get(fecha);
			
			if(fechas == null){
			
				nuevaData = (BasicDBObject) consulta;
				registros.add(data);
				nuevaData.append(fecha, registros);
				mongo.actualizarMDB(nombreColeccion, nuevaData, dataARemplazar);
				
				
			}else{
				registros = (ArrayList<DBObject>) consulta.get(fecha);					
				registros.add(data);
				nuevaData = (BasicDBObject) consulta;
				nuevaData.replace(fecha, registros);
				mongo.actualizarMDB(nombreColeccion, nuevaData, dataARemplazar);
			}
			
			
			
		}else{
			//No encontro el historico
			System.out.println("Error El historico para " + dataARemplazar + "no ha sido encontrado");
		}
		
		return false;
		
	}

	public static boolean crearHistoBus(BasicDBObject placa){
		DBObject consulta;
		String Placa = placa.getString("Placa");
		BasicDBObject data; 
		fecha = Fecha.getFechaClass().getSoloYMD();
		mongo = new DBColector();
		data = new BasicDBObject("Bus", Placa );
		consulta = mongo.consultarMDB(nombreColeccion, data);
		ArrayList<BasicDBObject> registros = new ArrayList<>();
		ArrayList<ArrayList<BasicDBObject>> Fechas = new ArrayList<>() ;
		Fechas.add(registros);
		
		if (consulta == null) {
			data.append(fecha, Fechas);
			mongo.insertarMDB(nombreColeccion, data);
			mongo.cerrarConexion();
			return true;
		} else {
	
			
		}
		mongo.cerrarConexion();
		
		return true;
	}

}
