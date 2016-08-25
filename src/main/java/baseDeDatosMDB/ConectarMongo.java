package baseDeDatosMDB;

import java.net.UnknownHostException;
import java.util.List;

import javax.swing.text.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class ConectarMongo{
	private MongoClient mongo;
	private DBCollection Colleccion;
	private DB db;

	public ConectarMongo() {

		try {
			mongo = new MongoClient();
			mongo.getAddress();

		} catch (UnknownHostException UKHe) {

			System.out.println("Error: Base de datos desconocida");
		} 

	}

	public DBObject consultarMDB(String DB, String Collection, BasicDBObject clave) {
		String respuesta;
		DBCursor encontrar;
		// Si no existe la base de datos la crea
		db = mongo.getDB(DB);
		// Crea una tabla si no existe y agrega datos
		Colleccion = db.getCollection(Collection);
		encontrar = Colleccion.find(clave);
		if (encontrar.hasNext())
		{
			//devuelve el objeto DBObject si el cursor tiene un elemento siguiente (analogia con null)
			return encontrar.next();
		}
		else
		{
			//no encontro el objeto
			return null;
		}
	}

	public void insertarMDB(String DB, String Collection, BasicDBObject Document) {

		// Si no existe la base de datos la crea
		db = mongo.getDB(DB);
		// Crea una tabla si no existe y agrega datos
		Colleccion = db.getCollection(Collection);
		// Inserta Documento a la base de datos
		Colleccion.insert(Document);

	}

	public void actualizarMDB(String DB, String Collection, BasicDBObject DocToChange, BasicDBObject IdDoc) {

		// Si no existe la base de datos la crea
		db = mongo.getDB(DB);
		// Crea una tabla si no existe y agrega datos
		Colleccion = db.getCollection(Collection);

		// Dato que se desea actualizar
		BasicDBObject ActualizarDato = new BasicDBObject();
		ActualizarDato.append("$set", DocToChange);

		// Documento en el cual se desea actualizar el dato
		BasicDBObject searchById = IdDoc;

		Colleccion.updateMulti(searchById, ActualizarDato);

	}

	/**
	 * Clase que imprime por pantalla todas las bases de datos MongoDB.
	 * 
	 * @param mongo
	 *            conexión a MongoDB
	 */
	private static void printDatabases(MongoClient mongo) {
		List<String> dbs = mongo.getDatabaseNames();
		for (String db : dbs) {
			System.out.println(" - " + db);
		}
	}
	public void cerrarConexion(){
		mongo.close();
	}

}
