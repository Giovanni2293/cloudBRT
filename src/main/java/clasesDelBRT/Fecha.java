/**
 * Esta clase se encarga de construir una fecha y hora de acuerdo al formato indicado año/mes/dia hora/minuto/segundo
 * y devuelve la misma como un String
 */

package clasesDelBRT;

import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Fecha {

	private String fecha;//cadena que contendra la fecha 
	
	/**
	 *Constructor sin parametros donde se define el formato y se construye la fecha 
	 */
	public Fecha()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	    Date date = new Date();
		fecha=dateFormat.format(date);
	}
	
	/**
	 * Devuelve la fecha
	 * @return fecha
	 */
	public String getFecha()
	{
		return fecha;
	}
}