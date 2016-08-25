package clasesDeUtilidad;

import java.util.ArrayList;

//Import de paquetes de la aplicación
import clasesDelBRT.*;


import org.json.JSONObject;

/**
 * Esta clase se encarga de convertir las lecturas de Json en objetos de Java.
 * 
 * @author Carlos Pereira
 *
 */
public class Extractor {

	private JSONObject jsonToObject;
	private String[] nombres;
	private ArrayList<Coordenadas> salida;

	public Extractor() {
	}

	/**
	 * Este método se encarga de extraer unicamente coordenadas en Json que han
	 * sido enviadas con un formato especifico en el que varia el subíndice de
	 * la coordenada. ejemplo de formato : coordenada1 , coordenada2 , ... ,
	 * coordenadan
	 * 
	 * 
	 * @param stringToObject
	 */
	public ArrayList<Coordenadas> extraerCoordenadas(String stringToObject) {
		salida = new ArrayList<>();

		JSONObject coordenada;

		jsonToObject = new JSONObject(stringToObject);
		nombres = JSONObject.getNames(jsonToObject);
		int size = nombres.length;
		int temp = 1;
		while (temp <= size) {

			coordenada = jsonToObject.getJSONObject("coordenada" + temp);
			Coordenadas C1 = new Coordenadas();
			C1.setLatitud(coordenada.getDouble("latitud"));
			C1.setLongitud(coordenada.getDouble("longitud"));

			salida.add(C1);
			temp++;

		}

		return salida;

	}

	public Bus extractBus(String stringToObject) {

		JSONObject coordenada;
		jsonToObject = new JSONObject(stringToObject);
		nombres = JSONObject.getNames(jsonToObject);
		String placa = jsonToObject.getString("placa");
		coordenada = jsonToObject.getJSONObject("coordenada");
		Coordenadas C1 = new Coordenadas();
		C1.setLatitud(coordenada.getDouble("latitud"));
		C1.setLongitud(coordenada.getDouble("longitud"));
		Bus extracBus = new Bus(placa, C1);
		return extracBus;

	}

}
