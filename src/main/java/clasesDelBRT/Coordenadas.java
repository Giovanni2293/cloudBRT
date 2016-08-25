package clasesDelBRT;

/**
 * Una Clase sencilla que se encarga de manejar coordenadas
 * 
 * @author Carlos Pereira 
 *
 */
public class Coordenadas {

	private double latitud;
	private double longitud;
	
	public Coordenadas()
	{
		
		latitud = 0;
		longitud = 0;
	}

	/**
	 * Retorna la latitud
	 * @return double
	 */
	public double getLatitud() {
		return latitud;
	}

	/**
	 * Modifica la latitud
	 * @param latitud
	 */
	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	/**
	 * Retorna la Longitud
	 * @return double
	 */
	public double getLongitud() {
		return longitud;
	}

	/**
	 * Modifica la Longitud
	 * @param longitud
	 */
	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}
	
	
	
}
