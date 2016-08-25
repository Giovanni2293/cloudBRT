package serviciosGET;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import baseDeDatosMDB.ConectarMongo;
import clientes.HTTPClient;

@Path("/Mongo")
public class Dbtest {

	ConectarMongo d ;
	@Path("/TestMG")
	@GET
	@Produces("application/json")
	public void probarMongo(){
		HTTPClient h = new HTTPClient();
		h.traer();	
	}
}
