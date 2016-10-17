package core;

import java.util.ArrayList;
import java.util.Iterator;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

import db.ConectarMongo;

public class ParqueAutomotor {

	private static ArrayList<Bus> buses;
	private static ParqueAutomotor p;
	
	private ParqueAutomotor()
	{
		inicializarParque();
	}
	private void inicializarParque()
	{
		ConectarMongo conexion = new ConectarMongo();
		DBCollection collection = conexion.consultarColeccion("Bus");
		DBCursor cursor = collection.find();
		buses = new ArrayList<>();
		while (cursor.hasNext()) {
			BasicDBObject obj = (BasicDBObject) cursor.next();
			buses.add(new Bus((String)obj.get("Placa")));
		}
	}
	
	public static ParqueAutomotor getParque()
	{
		if (p==null)
		{
			p=new ParqueAutomotor();
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
	
	public void mostarParque()
	{
		System.out.println(buses.size());
		for (Bus b : buses)
		{
			System.out.println(b.getJsonBus());
		}
	}
	
	
}
