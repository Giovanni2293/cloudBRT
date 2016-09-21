package clasesDeUtilidad;


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

	
	public static String mayusInicial(String original) {
		original = original.toLowerCase();
		if (original == null || original.length() == 0) {
			return original;
		}
		return original.substring(0, 1).toUpperCase() + original.substring(1);
	}
	
}
