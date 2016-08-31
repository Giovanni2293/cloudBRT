package clasesDeUtilidad;
import java.lang.Math;


public class GeoMatematicas {
	
	public static void main(String[] args){
		GeoMatematicas g = new GeoMatematicas();
		
		System.out.println(g.proyBus(36, 25,50) );
	}
	public GeoMatematicas(){
		
	}
	/**
	 * Este funcion se encarga de determinar la proyección del segmento formado
	 * por la estación origen y el bus sobre el segmento que forma la estación
	 * de origen a la estacion destino. 
	 * Estos 3 puntos forman un triangulo por lo cual implementamos el teorema del 
	 * coseno para resolver este problema.
	 * Distancia Entre bus y su destino DBD
	 * Distancia Entre bus y su origen DBO
	 * Distancia entre origen y destino DOD
	 * @return ángulo en radianes
	 */
	public  double proyBus(double DBD, double DBO, double DOD){
		double PB;
		PB = -(DBD*DBD)+ ((DBO*DBO) + (DOD*DOD));
		PB = PB / (2*DOD);
		return PB;
	}
	

}
