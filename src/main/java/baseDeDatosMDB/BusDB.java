package baseDeDatosMDB;

import com.mongodb.BasicDBObject;

import com.mongodb.DBObject;


public class BusDB {
	
	private BasicDBObject placa;
	private int capacidad;
	private String tipoBus;
	private boolean estado;

	private String nombreColeccion;
	private ConectarMongo mongo;
	private DBObject datos;
	
	public BusDB(String placa)
	{
		
		this.nombreColeccion="Bus";
		this.placa = new BasicDBObject("Placa",placa);
	}

	public boolean valoresBaseDatos()
	{
		mongo = new ConectarMongo();
		datos = mongo.consultarMDB(nombreColeccion,placa);
		if (datos!=null)
		{
			
			capacidad = (int) datos.get("Capacidad");
			tipoBus = (String) datos.get("TipoBus");
			estado = (boolean) datos.get("Estado");
			mongo.cerrarConexion();
			return true; 
		}
		else
		{
			return false;
		}
	}

	

	public int getCapacidad() {
		return capacidad;
	}

	public String getTipoBus() {
		return tipoBus;
	}

	public boolean isEstado() {
		return estado;
	}

}