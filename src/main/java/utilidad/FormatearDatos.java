package utilidad;

import java.nio.charset.StandardCharsets;
import java.util.StringTokenizer;

/**
 * La intención de esta darle formato a las entradas del usuario para evitar realizar inserciones
 * erroneas al sistema que afecten la integridad de este mismo, ademas quitarle la responsabilidad 
 * al usuario de pensar en cierto detalles a la hora de ingresar datos, a travez de un conjunto de 
 * metodos utiles.
 * 
 * 
 * @author Carlos Andrés Pereira Grimaldo 
 * @author Jose Giovanni Flores Nocua
 *
 */
public class FormatearDatos {

	private static final double factorDeConversionKmhToMs=0.277778;
	/**
	 * Coloca una unica mayuscula inicial el resto de caracteres en minuscula
	 * @param original
	 * @return
	 */
	public static String mayusInicial(String original) {
		original = original.toLowerCase();
		if (original == null || original.length() == 0) {
			return original;
		}
		return original.substring(0, 1).toUpperCase() + original.substring(1);
	}
	/**
	 * Coloca mayuscula inicial a cada elemento espaciado.
	 * @param frase
	 * @return
	 */
	public static String mayusInicialMulti(String frase){
		StringTokenizer st = new StringTokenizer(frase, " ");
		String result ="";
		while (st.hasMoreElements()) {
			result += mayusInicial(st.nextElement().toString())+" ";
		}
		StringBuilder borrar = new StringBuilder(result);
		borrar.deleteCharAt(result.length()-1);
		result = borrar.toString();

		return result;
	}
	
	public static String ArreglarCharset(String corrupto)
	{
		 byte[] bytes = corrupto.getBytes();
		 return new String(bytes, StandardCharsets.ISO_8859_1);
	}
	
	public static double hoursToSeconds(double hour){
		hour = hour * 3600;
		return hour;
	}

	public static double hoursToMinutes(double hour){
		hour = hour * 60;
		return hour;
	}
	
	public static double kmxhTomxs(double velocidad){
		return factorDeConversionKmhToMs*velocidad;
	}
	
	public static String formatoDeTiempo(double tiempo){
		String formato;
		String horasS,minutosS,segundosS;
		int horas = (int) (tiempo / 3600);
		int minutos = (int) ((tiempo % 3600) / 60 ) ;
		int segundos =  (int) (tiempo % 60);
		
		if (horas==24)horas = 0;
		
		if (horas<=9)horasS="0"+horas;
		else horasS=""+horas;
		
		if (minutos<=9)minutosS="0"+minutos;
		else minutosS=""+minutos;
		
		if (segundos<=9)segundosS="0"+segundos;
		else segundosS=""+segundos;
		
		formato = "" + horasS +":" + minutosS + ":" +segundosS;
		
		return formato;
		
	}
	
	public static double removerFormatoDeTiempo(String hora) {
		String horaT, minT, segT;
		int horaI, minI, segI;
		int segundos;
		StringTokenizer st = new StringTokenizer(hora, ":");
		horaT = st.nextToken();
		minT = st.nextToken();
		segT = st.nextToken();
		horaI = (int) Double.parseDouble(horaT);
		minI = (int) Double.parseDouble(minT);
		segI = (int) Double.parseDouble(segT);

		segundos = horaI * 3600 + minI * 60 + segI;
		return segundos;

	}
}
