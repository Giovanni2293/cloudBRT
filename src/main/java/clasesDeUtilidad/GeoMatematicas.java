package clasesDeUtilidad;
import java.lang.Math;

import clasesDelBRT.Coordenadas;


public  class GeoMatematicas {
	
	public static void main(String[] args){
		
		
		System.out.println(GeoMatematicas.DisProy(36, 25,50) );
	}
	public GeoMatematicas(){
		
	}
	/**
	 * Este funcion se encarga de determinar la distancia de la proyección del 
	 * segmento formado por la estación origen y el bus sobre el segmento que 
	 * forma la estación de origen a la estacion destino. 
	 * Estos 3 puntos forman un triangulo por lo cual implementamos el teorema del 
	 * coseno para resolver este problema.
	 * Recibe:
	 * Distancia Entre bus y su destino DBD
	 * Distancia Entre bus y su origen DBO
	 * Distancia entre origen y destino DOD
	 * Entrega:
	 * Distancia entre el origen y la proyección según las unidades 
	 * de los valores de entrada.
	 * @param DBD , DBO ,DOD
	 * @return double
	 */
	public static double DisProy(double DBD, double DBO, double DOD){
		double PB;
		PB = -(DBD*DBD)+ ((DBO*DBO) + (DOD*DOD));
		PB = PB / (2*DOD);
		return PB;
	}
	/**
	 * Recibe coordenadas para retornar la proyeccion usando proyBus
	 * @param EO
	 * @param B
	 * @param ED
	 * @return
	 */
	public static double DisProyCoor(Coordenadas EO, Coordenadas B, Coordenadas ED){
		double DBD,DBO,DOD;
		DBD = calcDistancia(B , ED);
		DBO =calcDistancia(B , EO);
		DOD =calcDistancia(EO , ED);		
		return DisProy(DBD, DBO, DOD);
	}
	
	/**
	 * Calcula la distancia entre dos cordenadas mediante su latitud
	 * y longitud, recibiendo dos coordenadas para el cálculo.
	 * 
	 * @param origen , destino
	 * @return double
	 */
	public static double calcDistancia(Coordenadas origen , Coordenadas destino)
	{
		double distancia;
		double lat1=0,lng1=0;
		double lat2=0,lng2=0;
		
		lat1 = origen.getLatitud();
		lng1 = origen.getLongitud();
		lat2 = destino.getLatitud();
		lng2 = destino.getLongitud();		
		
		double radioTierra = 6371;// en kilómetros
		double dLat,dLng,sindLat,sindLng,va1,va2;
		dLat = Math.toRadians(lat2 - lat1);
		dLng = Math.toRadians(lng2 - lng1);
		sindLat = Math.sin(dLat / 2);
		sindLng = Math.sin(dLng / 2);
		va1 = Math.pow(sindLat, 2)
				+ Math.pow(sindLng, 2) * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
		va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1));
		distancia = radioTierra * va2;
		distancia=distancia*1000;
		return distancia;
		
	}
	
	

}
