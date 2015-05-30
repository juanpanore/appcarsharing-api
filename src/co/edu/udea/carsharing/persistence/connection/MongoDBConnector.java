package co.edu.udea.carsharing.persistence.connection;

import co.edu.udea.carsharing.technical.exception.CarSharingTechnicalException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;

public class MongoDBConnector {

	private static final String SERVER = "mongodb://admin:admin@ds031982.mongolab.com:31982/heroku_app36463312";
	private static final String DATA_BASE_NAME = "heroku_app36463312";

	private static MongoClient mongoClient;
	private static MongoClientURI mongoClientURI;

	private MongoDBConnector() {
		super();
	}

	public static DBCollection connect(String collectionName)
			throws CarSharingTechnicalException {
		try {
			mongoClientURI = new MongoClientURI(SERVER);
			mongoClient = new MongoClient(mongoClientURI);

			@SuppressWarnings("deprecation")
			DB db = mongoClient.getDB(DATA_BASE_NAME);
			DBCollection collection = db.getCollection(collectionName);

			if (collection == null) {
				collection = db.createCollection(collectionName, null);
			}

			return collection;
		} catch (MongoException | NullPointerException e) {
			throw new CarSharingTechnicalException(
					String.format(
							"Clase %s: método %s. Se produjo una excepción al tratar de conectarse"
									+ " a la base de datos o al tratar de obtener la respectiva coleccion.\n%s",
							MongoDBConnector.class.getSimpleName(),
							"connect()", e));
		}
	}
}