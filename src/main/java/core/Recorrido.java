package core;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.mongodb.BasicDBObject;

import db.DBGeneralBRT;
import db.TRecorrido;

public class Recorrido {

	private String clave;
	private Ruta ruta;
	private String horaPartida;
	private LinkedHashMap<String,String> horario;
	
	public Recorrido(String clave, String horaPartida) {
		String colleccionRuta="Ruta";
		ruta = new Ruta(clave);
		this.horaPartida = horaPartida;
		BasicDBObject rutaDB = new BasicDBObject("Nombre", clave);
		DBGeneralBRT mongo = new DBGeneralBRT();
		horario=TRecorrido.construirHorario(mongo.consultarMDB(colleccionRuta, rutaDB), horaPartida);
		mongo.cerrarConexion();
	}

	public String getHoraPartida() {
		return horaPartida;
	}
	
	/**
	 * Metodo encargado de obtener una parada directamente desde la ruta con todos sus atributos.
	 * @param i
	 * @return
	 */
	public Parada getObjetoPadadaPorIndice(int i)
	{
		return ruta.getParadas().get(i);
	}
	
	//Metodo de pruebas para comprobar valores
	public void mostarHorario()
	{
		Object[] keys=horario.keySet().toArray();
	     for (int i=0;i<keys.length;i++)System.out.println(getClaveParadaPorIndice(i)+" "+getHoraPorIndice(i));
	}
	
	//Inicia la pareja de metodos encargada de obtener valores del hashmap de horario
	public String getClaveParadaPorIndice(int i) {
		return horario.get(horario.keySet().toArray()[i]);
	}
	
	public String getHoraPorIndice(int i){
		Object[] keys=horario.keySet().toArray();
		return (String)keys[i];
	}
	//Terimina el la obtencion del horario

	public void setHoraPartida(String horaPartida) {
		this.horaPartida = horaPartida;
	}

	public String getClave() {
		return clave;
	}

	public Ruta getRuta() {
		return ruta;
	}
	
	

}
