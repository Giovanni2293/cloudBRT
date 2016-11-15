package core;

import java.util.LinkedHashMap;

public class Itinerario {

	private String id;
	private LinkedHashMap<String,String> horaReal;
	private Conductor conductorDesignado;
	private Recorrido recorridoDesignado;
	private Bus busDesignado;
	
	public Itinerario(String id,String placa, String conductor, String recorrido)
	{
		this.id = id;
		horaReal = new LinkedHashMap<>();
		busDesignado = BusesRT.getBusesRT().encontrarBus(placa);
		recorridoDesignado = RecorridosRT.getRecorridosRT().encontrarRecorrido(recorrido);
		conductorDesignado = ConductoresRT.getConductoresRT().encontrarConductor(conductor);
	}

	public String getId() {
		return id;
	}
	
	/*public double getAvancePorcentual()
	{
		
	}*/
	
	public void anotar(Parada p)
	{
		horaReal.put(p.getClave(),Fecha.getFechaClass().getFecha());
	}
	
	public void encontrar()
	{
		Parada p = recorridoDesignado.getRuta().getParadas().get(0);
  		boolean resultado = p.estaDentro(busDesignado.getCoor());
  		if (resultado==true)
  		{
  			anotar(p);
  		}
	}

	public void setId(String id) {
		this.id = id;
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
	
	
}
