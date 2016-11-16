package core;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import db.DBGeneralBRT;
import db.TItinerario;
import interfaces.Observer;
import interfaces.Subject;

public class Itinerario implements Subject{

	private String clave;
	private LinkedHashMap<String,String> horarioReal;
	private Conductor conductorDesignado;
	private Recorrido recorridoDesignado;
	private Bus busDesignado;
	private ArrayList<Observer> observers;
	private String horaSalidaReal;
	
	public Itinerario(String clave)
	{
		
		this.clave = clave;
		BasicDBObject itinerarioDB = new BasicDBObject("Clave", clave);
		DBGeneralBRT mongo = new DBGeneralBRT();
		DBObject itineario = mongo.consultarMDB("Itinerario", itinerarioDB);
		horarioReal = (LinkedHashMap<String, String>) itineario.get("HorarioReal");
		horaSalidaReal = (String) itineario.get("HoraSalidaReal");
		busDesignado = BusesRT.getBusesRT().encontrarBus((String) itineario.get("Bus"));
		recorridoDesignado = RecorridosRT.getRecorridosRT().encontrarRecorrido((String) itineario.get("Recorrido"));
		conductorDesignado = ConductoresRT.getConductoresRT().encontrarConductor((String) itineario.get("Conductor"));
		observers = new ArrayList<>();
		mongo.cerrarConexion();
	}

	public String getId() {
		return clave;
	}
	
	/*public double getAvancePorcentual()
	{
		
	}*/
	
	public void actualizarHorarioReal(Parada p)
	{
		BasicDBObject itinerarioDB = new BasicDBObject("Clave", clave);
		DBGeneralBRT mongo = new DBGeneralBRT();
		DBObject itineario = mongo.consultarMDB("Itinerario", itinerarioDB);
		horarioReal = (LinkedHashMap<String, String>) itineario.get("HorarioReal");
		horarioReal.put(p.getClave(),Fecha.getFechaClass().getFecha());
		TItinerario.marcarHora(clave, horarioReal);
		mongo.cerrarConexion();
		
	}
	
	public void encontrar()
	{
		Parada p = recorridoDesignado.getRuta().getParadas().get(0);
  		boolean resultado = p.estaDentro(busDesignado.getCoor());
  		if (resultado==true)
  		{
  			actualizarHorarioReal(p);
  			NotifyObservers();
  		}
	}

	public void setId(String clave) {
		this.clave = clave;
	}

	public Conductor getConductorDesignado() {
		return conductorDesignado;
	}

	public void setConductorDesignado(Conductor conductorDesignado) {
		this.conductorDesignado = conductorDesignado;
	}

	public Recorrido getRecorridoDesignado() {
		return recorridoDesignado;
	}

	public void setRecorridoDesignado(Recorrido recorridoDesignado) {
		this.recorridoDesignado = recorridoDesignado;
	}

	public Bus getBusDesignado() {
		return busDesignado;
	}

	public void setBusDesignado(Bus busDesignado) {
		this.busDesignado = busDesignado;
	}
	
	public void mostrarHoraReal()
	{
	System.out.println(horarioReal.toString());
	}	
	
	

	@Override
	public void AddObserver(Observer e) {
		// TODO Auto-generated method stub
		observers.add(e);
	}

	@Override
	public void RemoveObserver(Observer e) {
		// TODO Auto-generated method stub
		observers.remove(e);
	}

	@Override
	public void NotifyObservers() {
		// TODO Auto-generated method stub
		for (int i=0;i<observers.size();i++)
		{
			observers.get(i).Update();
		}
	}
	
	


	
	
}
