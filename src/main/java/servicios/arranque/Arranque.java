package servicios.arranque;

import core.*;
import db.TColectorBus;
import db.TItinerario;
import db.TRuta;
import utilidad.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import javax.json.*;

@Path("/arranque")
public class Arranque {

	

		
	
	@Path("/inicial")
	@GET
	@Produces("application/json")
	public Response initParqueAutomotor()
	{
		BusesRT.getBusesRT();
		Despacho.getDespacho();
		return Response.status(200).entity(null).build();
	}
	
	
}