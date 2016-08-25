package clasesDeUtilidad;

import javax.json.Json;
import javax.json.JsonObject;

public class MensajeError {
	
	public static JsonObject servicioCaido()
	{
		JsonObject mensaje = Json.createObjectBuilder().add("Mensaje","El servicio de base de datos no responde").build();
		return mensaje;
	}
	public static JsonObject denegar()
	{
		JsonObject mensaje = Json.createObjectBuilder().add("Mensaje","La placa del bus no esta registrada. Comuniquese con un administrador"
				+ "del sistema para agregarla").build();
		return mensaje;
	}
}
