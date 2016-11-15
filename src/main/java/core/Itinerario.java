package core;

public class Itinerario {

	private String id;
	private Conductor conductorDesignado;
	private Recorrido recorridoDesignado;
	private Bus busDesignado;
	
	public Itinerario(String id, Bus bus , Conductor conductor, Recorrido recorrido)
	{
		this.id = id;
		busDesignado = bus;
		recorridoDesignado = recorrido;
	}

	public String getId() {
		return id;
	}
	
	/*public double getAvancePorcentual()
	{
		
	}*/

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
