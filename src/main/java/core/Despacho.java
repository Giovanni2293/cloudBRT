package core;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Clase encargada de administrar todos los itinerarios.
 * @author 123
 *
 */
public class Despacho {
	
	private static Despacho d=null;
	private ArrayList<Itinerario> itinerarios;
	
	private Despacho()
	{}
	
	public static Despacho getDespacho()
	{
		if (d==null)
		{
			 d=new Despacho();
			return d;
		}
		else
		{
			return d;
		}
	}
	
	
	public void añadirItinerario(String id,String placa,String nombreRuta,String conductor,String claveRecorrido)
	{
		//itinerarios.add(new Itinerario(id, placa, nombreRuta, conductor, claveRecorrido));
	}
	
	/**
	 * Todos los itinerarios que contengan el bus con la placa dada
	 * @return
	 */
	public ArrayList<Itinerario> encontarXPlaca()
	{
		return null;
	}
	
	/**
	 * Busca todos los itinerarios que tengan la misma ruta solicitada
	 * @param ruta
	 * @return
	 */
	public ArrayList<Itinerario> encontarXRuta(String ruta)
	{
		Iterator<Itinerario> i = itinerarios.iterator();
		ArrayList<Itinerario> salida = new ArrayList<>();
		Itinerario temp;
		while (i.hasNext())
		{
			temp = i.next();
			if (ruta.equals(temp.getRecorridoDesignado().getRuta().getNombre()))
			{
				salida.add(temp);
			}
		}
		return salida;
	}
	
	public ArrayList<Itinerario> encontarXRecorrido(String recorrido)
	{
		Iterator<Itinerario> i = itinerarios.iterator();
		ArrayList<Itinerario> salida = new ArrayList<>();
		Itinerario temp;
		while (i.hasNext())
		{
			temp = i.next();
			if (recorrido.equals(temp.getRecorridoDesignado().getClaveRecorrido()))
			{
				salida.add(temp);
			}
		}
		return salida;
	}
	
	public Itinerario encontrarItinerario(String id)
	{
		Iterator<Itinerario> i = itinerarios.iterator();
		Itinerario temp;
		while (i.hasNext())
		{
			temp = i.next();
			if (id.equals(temp.getId()))
			{
				return temp;
			}
		}
		return null;
	}
	
	

}
