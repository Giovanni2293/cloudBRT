package core;

public class Conductor {

	private long cedula;
	private String primerNombre;
	private String segundoNombre;
	private String primerApellido;
	private String segundoApellido;	
	private long licencia;
	private	String tipoSangre;
	
	
	public Conductor(long cedula, String primerNombre, String segundoNombre, String primerApellido,
			String segundoApellido, long licencia, String tipoSangre) {
		this.cedula = cedula;
		this.primerNombre = primerNombre;
		this.segundoNombre = segundoNombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.licencia = licencia;
		this.tipoSangre = tipoSangre;
	}


	public long getLicencia() {
		return licencia;
	}


	public void setLicencia(long licencia) {
		this.licencia = licencia;
	}


	public String getTipoSangre() {
		return tipoSangre;
	}


	public void setTipoSangre(String tipoSangre) {
		this.tipoSangre = tipoSangre;
	}


	public long getCedula() {
		return cedula;
	}


	public String getPrimerNombre() {
		return primerNombre;
	}


	public String getSegundoNombre() {
		return segundoNombre;
	}


	public String getPrimerApellido() {
		return primerApellido;
	}


	public String getSegundoApellido() {
		return segundoApellido;
	}
	
	
	
	
}
