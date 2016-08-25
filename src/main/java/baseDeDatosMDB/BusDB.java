package baseDeDatosMDB;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.mongodb.MongoTimeoutException;

public class BusDB {
	
	private BasicDBObject placa;
	private int capacidad;
	private String tipoBus;
	private boolean estado;
	private String nombreBaseDatos;
	private String nombreColeccion;
	private ConectarMongo mongo;
	private DBObject datos;
	
	public BusDB(String placa)
	{
		this.nombreBaseDatos="GeneralBRT";
		this.nombreColeccion="Bus";
		mongo = new ConectarMongo();
		this.placa = new BasicDBObject("Placa",placa);
	}

	public Boolean valoresBaseDatos()
	{
		datos = mongo.consultarMDB(nombreBaseDatos,nombreColeccion,placa);
		if (datos!=null)
		{
			double castInt =(double) datos.get("Capacidad");
			capacidad = (int) castInt;
			tipoBus = (String) datos.get("TipoBus");
			estado = (boolean) datos.get("Estado");
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