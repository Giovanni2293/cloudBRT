package core;

import javax.json.Json;
import javax.json.JsonObject;

import com.mongodb.BasicDBObject;

import db.BusDB;
import utilidad.MensajeError;

public class Bus {
	// Datos del bus
	private String placa;
	private int capacidad;
	private String tipoBus;
	private boolean estado;
	private Coordenadas coor;
	private BusDB busDB;
	//Objetos de json
	private BasicDBObject JsonDatos;

	public Bus(String placa , Coordenadas coor){
		
		this.placa = placa;
		this.coor = coor;
		//valores default en caso de pedir el JSON sin la existencia del objeto en la BD
		this.tipoBus="";
		this.capacidad=0;
		this.estado=false;
		busDB = new BusDB(placa);
		actualizarBusDesdeBD();
	}
	
	public Bus(String placa)
	{
		//Constructor de buses runtime.
		coor = new Coordenadas(0,0);
		this.placa = placa;
		this.tipoBus="";
		this.capacidad=0;
		this.estado=false;
		busDB = new BusDB(placa);
		actualizarBusDesdeBD();
	}
	
	public void actualizarBusDesdeBD()
	{
		if (busDB.valoresBaseDatos())
		{
			this.tipoBus=busDB.getTipoBus();
			this.capacidad=busDB.getCapacidad();
			this.estado=busDB.isEstado();
		}
	}
	
	public void actualizarJSON()
	{
		/*JsonDatos = Json.createObjectBuilder().add("placa", this.placa)
				.add("coordenada" , 
						Json.createObjectBuilder()
						.add("latitud", coor.getLatitud())
						.add("longitud", coor.getLongitud()))
				.add("capacidad", capacidad)
				.add("tipoBus", tipoBus)
				.add("estado", estado).build();*/
		
		JsonDatos = new BasicDBObject("placa",this.placa);
		BasicDBObject coorBD = new BasicDBObject();
		coorBD.append("latitud",coor.getLatitud());
		coorBD.append("longitud",coor.getLongitud());
		JsonDatos.append("coordenada",coorBD);
		JsonDatos.append("capacidad",capacidad);
		JsonDatos.append("tipoBus",tipoBus);
		JsonDatos.append("estado",estado);
		
	}
	
	/**
	 * Metodo que crea y devuelve el JSON del objeto Bus
	 * @return JsonObject representacion JSON del bus.
	 */
	public BasicDBObject getJsonBus(){
		if (busDB.valoresBaseDatos())
		{
			actualizarJSON();
		}
		else
		{
			JsonDatos = MensajeError.denegar();
		}
		return JsonDatos;
	}
	
	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getPlaca() {
		return placa;
	}

	public Coordenadas getCoor() {
		return coor;
	}
	
	public void setCoor(Coordenadas coor)
	{
		this.coor=coor;
	}

	
	
	

}
