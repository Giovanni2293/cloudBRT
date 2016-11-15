package core;

import java.util.ArrayList;
import java.util.Iterator;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

import db.DBPersonal;

public class ConductoresRT {

	private static ArrayList<Conductor> conductores;
	private static ConductoresRT c;
	private final String coleccion = "Conductores";
	
	private ConductoresRT()
	{
		inicializarConductores();
	}
	private void inicializarConductores()
	{
		DBPersonal conexion = new DBPersonal();
		DBCollection collection = conexion.consultarColeccion(coleccion);
		DBCursor cursor = collection.find();
		conductores = new ArrayList<>();
		while (cursor.hasNext()) {
			BasicDBObject obj = (BasicDBObject) cursor.next();
			conductores.add(new Conductor(obj.getString("Cedula")));
		}
	}
	
	public static ConductoresRT getConductoresRT()
	{
		if (c==null)
		{
			c = new ConductoresRT();
		}
		
		return c;

	}
	
	public ArrayList<Conductor> getConductores()
	{
		return conductores;
	}
	
	public Conductor encontrarConductor(String cedula)
	{
		Iterator<Conductor> i = conductores.iterator();
		Conductor temp;
		while (i.hasNext())
		{
			temp = i.next();
			if (cedula.equals(temp.getCedula()))
			{
				return temp;
			}
		}
		return null;
	}
	
	public void mostarConductores()
	{
		System.out.println(conductores.size());
		for (Conductor b : conductores)
		{
			System.out.println(b.getPrimerNombre());
		}
	}
	
	
}
