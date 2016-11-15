package core;

import java.util.ArrayList;
import java.util.Iterator;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

import db.DBGeneralBRT;

public class BusesRT {

	private static ArrayList<Bus> buses;
	private static BusesRT p;
	private final String coleccion = "Bus";
	
	private BusesRT()
	{
		inicializarParque();
	}
	private void inicializarParque()
	{
		DBGeneralBRT conexion = new DBGeneralBRT();
		DBCollection collection = conexion.consultarColeccion(coleccion);
		DBCursor cursor = collection.find();
		buses = new ArrayList<>();
		while (cursor.hasNext()) {
			BasicDBObject obj = (BasicDBObject) cursor.next();
			buses.add(new Bus((String)obj.get("Placa")));
		}
	}
	
	public static BusesRT getBusesRT()
	{
		if (p==null)
		{
			p=new BusesRT();
		}
		
		return p;

	}
	
	public ArrayList<Bus> getBuses()
	{
		return buses;
	}
	
	public Bus encontrarBus(String placa)
	{
		Iterator<Bus> i = buses.iterator();
		Bus temp;
		while (i.hasNext())
		{
			temp = i.next();
			if (placa.equals(temp.getPlaca()))
			{
				return temp;
			}
		}
		return null;
	}
	
	public void mostrarBuses()
	{
		System.out.println(buses.size());
		for (Bus b : buses)
		{
			System.out.println(b.getJsonBus());
		}
	}
	
	
}
