package servicios.front;

import java.io.InputStream;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import core.Bus;
import core.BusesRT;
import core.ConductoresRT;
import core.Coordenadas;
import core.RecorridosRT;
import db.TBus;
import db.TConductor;
import db.TItinerario;
import db.TParada;
import db.TRecorrido;
import db.TRuta;
import utilidad.Diccionario;
import interfaces.PATCH;

@Path("/admin")
public class Admin {

	private JsonObject respuesta;
	private final String root = "http://localhost:8080/cloudBRT/api/";

	///////
	/////////////////////////////////////// BUSES/////////////////////////////////////////////
	///////

	// GET

	/**
	 * Servicio que permite eliminar un bus en especifico utilizando su placa y
	 * devuelve como resultado si fue satisfactoria o no la tarea
	 * 
	 * @param placaBus
	 * @return {@link Boolean}
	 */
	@Path("/buses/{placaBus}")
	@DELETE
	@Produces("application/json")
	public Response eliminarBuses(@PathParam("placaBus") String placaBus) {
		boolean progreso;
		
		BusesRT.getBusesRT().eliminarBus(placaBus); // Elimina el bus en RT
		progreso = TBus.eliminarBus(placaBus);
		
		
		
		if(progreso == true)
		{
			respuesta = Json.createObjectBuilder().add("Eliminado", progreso).add("RecursoBus",root+"monitoreo/buses").build();
			return Response.status(200).entity(respuesta.toString()).build();
		}
		else
		{
			respuesta = Json.createObjectBuilder().add("Eliminado", progreso).add("RecursoBus",root+"monitoreo/buses").build();
			return Response.status(404).entity(respuesta.toString()).build();
		}
		
	}

	/**
	 * Servicio que permite modificar el estado de un bus. True (Bus funcional)
	 * False (Bus no funcional) y devuelve como resultado si fue satisfactoria o
	 * no la tarea.
	 * 
	 * @param placaBus
	 * @param estado
	 * @return {@link Boolean}
	 */
	@Path("/buses/{placaBus},{estado}")
	@PATCH
	@Produces("application/json")
	public Response modificarEstado(@PathParam("placaBus") String placaBus, @PathParam("estado") boolean estado) {
		BusesRT.getBusesRT().modificarEstado(placaBus, estado); // Modifica el estado del bus en RT
		boolean progreso = TBus.modificarEstado(placaBus, estado);//Modifica en DB
		
		if (progreso == true)
		{
			respuesta = Json.createObjectBuilder().add("Modificado", progreso).add("RecursoBus",root+"monitoreo/buses/"+placaBus).build();
			return Response.status(200).entity(respuesta.toString()).build();
		}
		else
		{
			respuesta = Json.createObjectBuilder().add("Modificado", progreso).add("RecursoBus",root+"monitoreo/buses").build();
			return Response.status(404).entity(respuesta.toString()).build();
		}
	}

	// POST

	/**
	 * Crea un nuevo bus mediante un json enviado por POST
	 * 
	 * @param incomingData
	 * @return {@link Response}
	 */
	@Path("/buses")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response crearBus(InputStream incomingData) {
		JsonObject entrada;
		String placa, tipoBus,operador;
		int capacidad;
		boolean estado, progreso;
		JsonReader jsonReader = Json.createReader(incomingData);
		entrada = jsonReader.readObject();

		placa = entrada.getString("Placa");
		capacidad = entrada.getInt("Capacidad");
		tipoBus = entrada.getString("TipoBus");
		estado = Boolean.parseBoolean(entrada.getString("Estado"));
		operador = entrada.getString("Operador");

		progreso = TBus.crearBus(placa, capacidad, tipoBus, estado,operador); //Modifica en DB
		respuesta = Json.createObjectBuilder().add("Creado", progreso).add("RecursoBus",root+"monitoreo/buses/"+placa).build();
		System.out.println("Placa:" + placa + " Capacidad:" + capacidad + " TipoBus:" + tipoBus + " Estado:" + estado);
		BusesRT.getBusesRT().agregarNuevo(new Bus(placa)); // Agrega un nuevo bus al RT
		return Response.status(201).entity(respuesta.toString()).build();

	}

	/////////
	//////////////////////////////////////////// PARADAS//////////////////////////////////////////
	/////////

	// GET

	/**
	 * Servicio que permite eliminar una parada mediante su clave. Devuelve si
	 * la tarea fue satisfactoria o no.
	 * 
	 * @param clave
	 * @return {@link Boolean}
	 */
	@Path("/paradas/{clave}")
	@DELETE
	@Produces("application/json")
	public Response eliminarParada(@PathParam("clave") String clave) {
		boolean progreso;
		progreso = TParada.eliminarParada(clave);
	
		
		if (progreso == true)
		{
			respuesta = Json.createObjectBuilder().add("Eliminado", progreso).add("RecursoParada",root+"monitoreo/paradas").build();
			return Response.status(200).entity(respuesta.toString()).build();
		}
		else
		{
			respuesta = Json.createObjectBuilder().add("Eliminado", progreso).add("RecursoParada",root+"monitoreo/buses").build();
			return Response.status(404).entity(respuesta.toString()).build();
		}
	}

	// POST

	/**
	 * Crea una nueva parada empleando la informacion obtenida desde un Json
	 * enviado por POST
	 * 
	 * @param incomingData
	 * @return {@link Response}
	 */
	@Path("/paradas")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response crearParada(InputStream incomingData) {
		JsonObject entrada;
		JsonObject coordenada;
		String clave, nombre;
		String slatitud, slongitud;
		Coordenadas c1;
		boolean progreso;

		JsonReader jsonReader = Json.createReader(incomingData);
		entrada = jsonReader.readObject();

		clave = entrada.getString("Clave");
		nombre = entrada.getString("Nombre");
		coordenada = entrada.getJsonObject("Coordenada");
		slatitud = coordenada.getString("Latitud");
		slongitud = coordenada.getString("Longitud");
		c1 = new Coordenadas(Double.parseDouble(slatitud), Double.parseDouble(slongitud));
		progreso = TParada.crearParada(clave, nombre, c1.getLatitud(), c1.getLongitud());
		respuesta = Json.createObjectBuilder().add("Creado", progreso).add("RecursoParada",root+"monitoreo/paradas/"+clave).build();
		System.out.println("Clave:" + clave + " Nombre:" + nombre + " Latitud:" + c1.getLatitud() + " Longitud:"
				+ c1.getLongitud());
		return Response.status(201).entity(respuesta.toString()).build();

	}

	///////
	///////////////////////////////////////// RUTAS////////////////////////////////////////////////
	///////

	// GET

	/**
	 * Elimina todas las paradas de una ruta existente. La ruta tiene que
	 * existir para poder efectuarse la tarea. Finalmente devuelve si fue o no
	 * satisfactoria la tarea
	 * 
	 * @param nombre
	 * @return
	 */
	@Path("/rutas/{nombre}/paradas")
	@DELETE
	@Produces("application/json")
	public Response eliminarParadasDeRuta(@PathParam("nombre") String nombre) {
		boolean progreso;
		progreso = TRuta.eliminarParadas(nombre);
		
		if (progreso==true)
		{
			respuesta = Json.createObjectBuilder().add("ParadasEliminadas", progreso).add("RecursoRuta",root+"monitoreo/rutas/"+nombre).build();
			return Response.status(200).entity(respuesta.toString()).build();
		}
		else
		{
			respuesta = Json.createObjectBuilder().add("ParadasEliminadas", progreso).add("RecursoRuta",root+"monitoreo/rutas").build();
			return Response.status(404).entity(respuesta.toString()).build();
		}
	}

	/**
	 * Remueve una parada de una ruta dando una posicion.
	 * 
	 * @param nombreRuta
	 * @param posicion
	 * @return {@link Response}
	 */
	@Path("/rutas/{nombreRuta}/paradas/{posicion}")
	@DELETE
	@Produces("application/json")
	public Response eliminarParadaEspecificaDeRuta(@PathParam("nombreRuta") String nombreRuta,
			@PathParam("posicion") int posicion) {
		boolean progreso;
		progreso = TRuta.removerParadaDeRuta(nombreRuta, posicion);
		if (progreso==true)
		{
			respuesta = Json.createObjectBuilder().add("ParadaEliminada", progreso).add("RecursoRuta",root+"monitoreo/rutas/"+nombreRuta).build();
			return Response.status(200).entity(respuesta.toString()).build();
		}
		else
		{
			respuesta = Json.createObjectBuilder().add("ParadaEliminada", progreso).add("RecursoRuta",root+"monitoreo/rutas").build();
			return Response.status(404).entity(respuesta.toString()).build();
		}
	}
	
	/**
	 * Agrega una parada al final de una ruta especificada en la url.
	 * 
	 * @param nombreRuta
	 * @param clave
	 * @return {@link Response}
	 */
	@Path("/rutas/{nombreRuta}/paradas/{clave}")
	@PATCH
	@Produces("application/json")
	public Response agregarParadasAlFinal(@PathParam("nombreRuta") String nombreRuta,
			@PathParam("clave") String clave) {
		boolean progreso;
		progreso = TRuta.anadirAFinalDeRuta(nombreRuta, clave);
		
		
		if (progreso==true)
		{
			respuesta = Json.createObjectBuilder().add("Agrego", progreso).add("RecursoParada",root+"monitoreo/paradas/"+clave).add("RecursoRuta",root+"monitoreo/rutas/"+nombreRuta).build();
			return Response.status(200).entity(respuesta.toString()).build();
		}
		else
		{
			respuesta = Json.createObjectBuilder().add("Agrego", progreso).add("RecursoParada",root+"monitoreo/paradas").add("RecursoRuta",root+"monitoreo/rutas").build();
			return Response.status(404).entity(respuesta.toString()).build();
		}
	}


	/**
	 * Elimina una ruta por completo
	 * 
	 * @param nombreRuta
	 * @return {@link Response}
	 */
	@Path("/rutas/{nombreRuta}")
	@DELETE
	@Produces("application/json")
	public Response eliminarRuta(@PathParam("nombreRuta") String nombreRuta) {
		boolean progreso;
		progreso = TRuta.eliminarRuta(nombreRuta);
		
		if(progreso == true)
		{
			respuesta = Json.createObjectBuilder().add("Elimino", progreso).add("RecursoParada",root+"monitoreo/paradas").build();
			return Response.status(200).entity(respuesta.toString()).build();
		}
		else
		{
			respuesta = Json.createObjectBuilder().add("Elimino", progreso).add("RecursoParada",root+"monitoreo/paradas").build();
			return Response.status(404).entity(respuesta.toString()).build();
		}
		
	}


	/**
	 * Crea una nueva ruta sin paradas, con los parametros categoria y
	 * descripcion vacios
	 * 
	 * @param nombreRuta
	 * @return {@link Response}
	 */
	@Path("/rutas")
	@POST
	@Produces("application/json")
	public Response crearRuta(InputStream incomingData) {
		boolean progreso;
		String categoria = "";
		String descripcion = "";
		
		JsonReader jsonReader = Json.createReader(incomingData);
		JsonObject entrada = jsonReader.readObject();
		
		progreso = TRuta.crearRuta(entrada.getString("NombreRuta"), categoria, descripcion);
		
		
			respuesta = Json.createObjectBuilder().add("Creado", progreso).add("RecursoRuta",root+"monitoreo/rutas/"+entrada.getString("NombreRuta")).build();
			return Response.status(201).entity(respuesta.toString()).build();
		
	}

	// POST

	/**
	 * Agrega una parada a una ruta dada su posicion sin reemplazar la que existe. Los datos se obtienen de
	 * un json recibido por POST
	 * 
	 * @param incomingData
	 * @return {@link Response}
	 */
	@Path("/rutas/{NombreRuta}/paradas/{ClaveParada}/posicion/{PosicionParada}/SR")
	@PATCH
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response anadirXPosicionARuta(@PathParam("NombreRuta") String nombreRuta,@PathParam("ClaveParada") String claveParada,@PathParam("PosicionParada") int posicionParada) {
		
		boolean progreso;


		progreso = TRuta.anadirXPosicionARuta(nombreRuta, claveParada, posicionParada);
		System.out.println("Clave:" + claveParada + " Nombre:" + nombreRuta + " Posicion:" + posicionParada);
		
		if (progreso==true)
		{
			respuesta = Json.createObjectBuilder().add("Agrego", progreso).add("RecursoRuta",root+"monitoreo/rutas/"+nombreRuta).add("RecursoParada",root+"monitoreo/paradas/"+claveParada).build();
			return Response.status(200).entity(respuesta.toString()).build();
		}
		else
		{
			respuesta = Json.createObjectBuilder().add("Agrego", progreso).add("RecursoRuta",root+"monitoreo/rutas").add("RecursoParada",root+"monitoreo/paradas").build();
			return Response.status(404).entity(respuesta.toString()).build();
		}

	}

	/**
	 * Agrega una parada remplazando la existente en la posicion especificada
	 * 
	 * @param incomingData
	 * @return {@link Response}
	 */
	@Path("/rutas/{NombreRuta}/paradas/{ClaveParada}/posicion/{PosicionParada}/CR")
	@PATCH
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response remplazarParada(@PathParam("NombreRuta") String nombreRuta,@PathParam("ClaveParada") String claveParada,@PathParam("PosicionParada") int posicionParada) {
	

		boolean progreso;

		progreso = TRuta.reemplazarParadaDeRuta(nombreRuta, claveParada, posicionParada);
		System.out.println("Clave:" + claveParada + " Nombre:" + nombreRuta + " Posicion:" + posicionParada);
		
		if(progreso==true)
		{
			respuesta = Json.createObjectBuilder().add("Agrego", progreso).add("RecursoRuta",root+"monitoreo/rutas/"+nombreRuta).add("RecursoParada",root+"monitoreo/paradas/"+claveParada).build();
			return Response.status(200).entity(respuesta.toString()).build();
		}
		else
		{
			respuesta = Json.createObjectBuilder().add("Agrego", progreso).add("RecursoRuta",root+"monitoreo/rutas").add("RecursoParada",root+"monitoreo/paradas").build();
			return Response.status(404).entity(respuesta.toString()).build();
		}

	}

	/**
	 * modifica las caracteristicas de una ruta
	 * 
	 * @param incomingData
	 * @return {@link Response}
	 */
	@Path("/rutas/datos")
	@PATCH
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response modificarCaracteristica(InputStream incomingData) {

		JsonObject entrada;
		JsonReader jsonReader = Json.createReader(incomingData);
		boolean progreso;
		entrada = jsonReader.readObject();
		progreso = TRuta.modificarDatoRuta(entrada.getString("Ruta"), entrada.getString("Atributo"),
				entrada.getString("Datos"));
		
		
		if (progreso==true)
		{
			respuesta = Json.createObjectBuilder().add("Modifico", progreso).add("RecursoRuta",root+"monitoreo/rutas/"+entrada.getString("Ruta")).build();
			return Response.status(200).entity(respuesta.toString()).build();
		}
		else
		{
			respuesta = Json.createObjectBuilder().add("Modifico", progreso).add("RecursoRuta",root+"monitoreo/rutas").build();
			return Response.status(404).entity(respuesta.toString()).build();
		}
	}

	///////
	///////////////////////////////////////// RECORRIDOS///////////////////////////////////////////
	///////
	//GET
	
		/**
		 * Servicio que permite eliminar un recorrido en especifico utilizando su clave y
		 * devuelve como resultado si fue satisfactoria o no la tarea
		 * 
		 * @param clave
		 * @return {@link Boolean}
		 */
		@Path("/recorridos/{clave}")
		@DELETE
		@Produces("application/json")
		public Response eliminarRecorrido(@PathParam("clave") String clave) {
			boolean progreso;
			
			RecorridosRT.getRecorridosRT().eliminarRecorrido(clave); // Elimina el bus en RT
			progreso = TRecorrido.eliminarRecorrido(clave);
			
			
			if(progreso==true)
			{
				respuesta = Json.createObjectBuilder().add("Eliminado", progreso).add("RecursoRecorrido",root+"monitoreo/recorridos").build();
				return Response.status(200).entity(respuesta.toString()).build();
			}
			else
			{
				respuesta = Json.createObjectBuilder().add("Eliminado", progreso).add("RecursoRecorrido",root+"monitoreo/recorridos").build();
				return Response.status(404).entity(respuesta.toString()).build();
			}
			
		}

		

	// POST

	@Path("/recorridos")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response crearRecorrido(InputStream incomingData) {
		JsonObject entrada;
		String clave, ruta, horaDePartida;
		boolean progreso;
		JsonReader jsonReader = Json.createReader(incomingData);
		entrada = jsonReader.readObject();

		clave = entrada.getString("Clave");
		ruta = entrada.getString("Ruta");
		horaDePartida = entrada.getString("HoraDePartida");
		progreso = TRecorrido.crearRecorridoAutomatico(clave, ruta, horaDePartida);//Modifica en DB
		
		RecorridosRT.getRecorridosRT().agregarRecorrido(clave); // Crea un recorrido en RT
		
		if (progreso==true)
		{
			respuesta = Json.createObjectBuilder().add("Creado", progreso).add("RecursoRecorrido",root+"monitoreo/recorridos/"+clave).build();
			return Response.status(201).entity(respuesta.toString()).build();
		}
		else
		{
			respuesta = Json.createObjectBuilder().add("Creado", progreso).add("RecursoRecorrido",root+"monitoreo/recorridos").build();
			return Response.status(404).entity(respuesta.toString()).build();
		}
		

	}

	/**
	 * Modifica el recorrido indicado en la URI
	 * @param incomingData
	 * @return
	 */
	@Path("/recorridos/{clave}")
	@PATCH
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editarRecorrido(@PathParam("clave") String clave,InputStream incomingData) {
		JsonObject entrada;
		String parada, horaAnterior, horaNueva;
		boolean progreso;
		JsonReader jsonReader = Json.createReader(incomingData);
		entrada = jsonReader.readObject();

		parada = entrada.getString("Parada");
		horaAnterior = entrada.getString("HoraAnterior");
		horaNueva = entrada.getString("HoraNueva");
		RecorridosRT.getRecorridosRT().editarHoraRecorridoRT(clave, parada, horaAnterior, horaNueva); /*Edita la hora de un punto de
		un recorrido en RT*/
		progreso = TRecorrido.editarHoraRecorrido(clave, parada, horaAnterior, horaNueva);//Modifica en DB
		if (progreso==true)
		{
			respuesta = Json.createObjectBuilder().add("Modificado", progreso).add("RecursoRecorrido",root+"monitoreo/recorridos/"+clave).build();
			return Response.status(200).entity(respuesta.toString()).build();
		}
		else
		{
			respuesta = Json.createObjectBuilder().add("Modificado", progreso).add("RecursoRecorrido",root+"monitoreo/recorridos").build();
			return Response.status(404).entity(respuesta.toString()).build();
		}


	}

	///////
	///////////////////////////////////////// CONDUCTORES///////////////////////////////////////////
	///////

	// GET

	@Path("/conductores/{cedula}")
	@DELETE
	@Produces("application/json")
	public Response eliminarConductor(@PathParam("cedula") String cedula) {
		boolean progreso;
		ConductoresRT.getConductoresRT().eliminarConductor(cedula); // Elimina un conductor en RT
		progreso = TConductor.eliminarConductor(cedula);//Modifica en DB
		if (progreso==true)
		{
			respuesta = Json.createObjectBuilder().add("Eliminado", progreso).add("RecursoConductor",root+"monitoreo/conductores").build();
			return Response.status(200).entity(respuesta.toString()).build();
		}
		else
		{
			respuesta = Json.createObjectBuilder().add("Eliminado", progreso).add("RecursoConductor",root+"monitoreo/conductores").build();
			return Response.status(404).entity(respuesta.toString()).build();
		}
		
	}

	// POST

	/**
	 * Crea un nuevo conductor mediante un json enviado por POST
	 * 
	 * @param incomingData
	 * @return {@link Response}
	 */
	@Path("/conductores")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response crearConductor(InputStream incomingData) {
		JsonObject entrada;

		String cedula;
		String licencia;
		String primerNombre, segundoNombre, primerApellido, segundoApellido, tipoSangre;

		boolean progreso;
		JsonReader jsonReader = Json.createReader(incomingData);
		entrada = jsonReader.readObject();

		cedula = entrada.getString("Cedula");
		primerNombre = entrada.getString("Primer Nombre");
		segundoNombre = entrada.getString("Segundo Nombre");
		primerApellido = entrada.getString("Primer Apellido");
		segundoApellido = entrada.getString("Segundo Apellido");
		licencia = entrada.getString("Numero de Licencia");
		tipoSangre = entrada.getString("Grupo Sanguineo");

		progreso = TConductor.crearConductor(cedula, primerNombre, segundoNombre, primerApellido, segundoApellido,
				licencia, tipoSangre); //Modifica en DB
		ConductoresRT.getConductoresRT().agregarConductor(cedula);// Agrega un conductor al RT
		
		if(progreso==true)
		{
			respuesta = Json.createObjectBuilder().add("Creado", progreso).add("RecursoConductor",root+"monitoreo/conductores/"+cedula).build();
			return Response.status(201).entity(respuesta.toString()).build();
		}
		else
		{
			respuesta = Json.createObjectBuilder().add("Creado", progreso).add("RecursoConductor",root+"monitoreo/conductores").build();
			return Response.status(404).entity(respuesta.toString()).build();
		}
		

	}

	/**
	 * Modifica uno de los parametros indicado mediante la url del conductor segun se indique en el json
	 * 
	 * @param incomingData
	 * @return {@link Response}
	 */
	@Path("/conductores/{Dato}")
	@PATCH
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response modificarDato(@PathParam("Dato") String dato,InputStream incomingData) {
		JsonObject entrada;

		String cedula;
		String nuevoValor;

		boolean progreso;
		JsonReader jsonReader = Json.createReader(incomingData);
		entrada = jsonReader.readObject();

		cedula = entrada.getString("Cedula");
		nuevoValor = entrada.getString("NuevoValor");

		String datoMapeado =Diccionario.atributoConduc(dato);
		
		ConductoresRT.getConductoresRT().modificarConductor(cedula,datoMapeado, nuevoValor);//Modifica RT
		
		progreso = TConductor.modificarDatoConductor(cedula, datoMapeado, nuevoValor);//Modifica la DB
		
		if (progreso == true)
		{
			respuesta = Json.createObjectBuilder().add("Modificado", progreso).add("RecursoConductor",root+"monitoreo/conductores/"+cedula).build();
			return Response.status(200).entity(respuesta.toString()).build();
		}
		else
		{
			respuesta = Json.createObjectBuilder().add("Modificado", progreso).add("RecursoConductor",root+"monitoreo/conductores").build();
			return Response.status(404).entity(respuesta.toString()).build();
		}

	}
	

	///////
	/////////////////////////////////////// ITINERARIO /////////////////////////////////////////////
	///////
	
    //GET
	
	/**
	 * Servicio que permite eliminar un itinerario en especifico utilizando su clave y
	 * devuelve como resultado si fue satisfactoria o no la tarea
	 * 
	 * @param clave
	 * @return {@link Boolean}
	 */
	@Path("/itinerarios/{clave}")
	@DELETE
	@Produces("application/json")
	public Response eliminarItinerario(@PathParam("clave") String clave) {
		boolean progreso;
		
		TItinerario.modificarTerminado(clave, true);
		progreso = TItinerario.eliminarItinerario(clave);
		
		if (progreso==true)
		{
			respuesta = Json.createObjectBuilder().add("Eliminado", progreso).add("RecursoItinerarios",root+"monitoreo/itinerarios").build();
			return Response.status(200).entity(respuesta.toString()).build();
		}
		else
		{
			respuesta = Json.createObjectBuilder().add("Eliminado", progreso).add("RecursoItinerarios",root+"monitoreo/itinerarios").build();
			return Response.status(404).entity(respuesta.toString()).build();
		}
	}

	
	// POST

	/**
	 * Crea un nuevo itinerario mediante un json enviado por POST
	 * 
	 * @param incomingData
	 * @return {@link Response}
	 */
	@Path("/itinerarios")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response crearItinerario (InputStream incomingData) {
		JsonObject entrada;
		String clave, conductor, bus, recorrido,horaSalidaEstimada ;
		boolean progreso;
		JsonReader jsonReader = Json.createReader(incomingData);
		entrada = jsonReader.readObject();
		

		clave = entrada.getString("Clave");
		conductor = entrada.getString("Conductor");
		bus = entrada.getString("Placa");
		recorrido = entrada.getString("Recorrido");
		horaSalidaEstimada = entrada.getString("HoraSalidaEstimada");

		progreso = TItinerario.crearItinerario(clave, conductor, bus, recorrido,horaSalidaEstimada); //Modifica en DB
		respuesta = Json.createObjectBuilder().add("Encontrado", progreso).build();
		
		if (progreso==true)
		{
			respuesta = Json.createObjectBuilder().add("Encontrado", progreso).add("RecursoItinerarios",root+"monitoreo/itinerarios/"+clave).build();
			return Response.status(201).entity(respuesta.toString()).build();
			
		}
		else
		{
			respuesta = Json.createObjectBuilder().add("Encontrado", progreso).add("RecursoItinerarios",root+"monitoreo/itinerarios").build();
			return Response.status(404).entity(respuesta.toString()).build();
		}

	}

}
