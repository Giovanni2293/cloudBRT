package clasesDeUtilidad;

import clasesDelBRT.Coordenadas;
import java.lang.Math;

public class AreaEstacion {

	private double radio;
	private double distancia;
	
	public AreaEstacion(double radio)
	{
		setRadio(radio);
	}
	
	public AreaEstacion()
	{
		radio = 200;
	}
	
	public void setRadio(double radio)
	{
		this.radio = radio;
	}
	
	public double getDistancia()
	{
		return distancia;
	}
	
	public double calcDistancia(Coordenadas vertice,Coordenadas bus)
	{
		double lat1=0,lng1=0;
		double lat2=0,lng2=0;
		
		lat1 = vertice.getLatitud();
		lng1 = vertice.getLongitud();
		lat2 = bus.getLatitud();
		lng2 = bus.getLongitud();		
		
		double radioTierra = 6371;// en kilómetros
		double dLat,dLng,sindLat,sindLng,va1,va2;
		dLat = Math.toRadians(lat2 - lat1);
		dLng = Math.toRadians(lng2 - lng1);
		sindLat = Math.sin(dLat / 2);
		sindLng = Math.sin(dLng / 2);
		va1 = Math.pow(sindLat, 2)
				+ Math.pow(sindLng, 2) * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
		va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1));
		double distancia = radioTierra * va2;
		this.distancia=distancia*1000;
		return this.distancia;
		
	}
	
	public boolean estaDentro(Coordenadas vertice,Coordenadas bus)
	{
		double temp;
		temp = calcDistancia(vertice,bus);
		if (temp>= radio)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
}
