package clasesDelBRT;

public class Recorrido {

	public Coordenadas coor;
	public String id;
	public Tiempo hEnvio;
	
	public Recorrido(String id, Coordenadas coor , Tiempo hEnvio){
		
		this.id = id;
		this.coor = coor;
		this.hEnvio = hEnvio;
		
		
		
	}

	public Coordenadas getCoor() {
		return coor;
	}

	public String getId() {
		return id;
	}

	public Tiempo gethEnvio() {
		return hEnvio;
	}
	
}
