package co.edu.udea.carsharing.persistence.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import co.edu.udea.carsharing.model.entities.Car;
import co.edu.udea.carsharing.model.entities.User;
import co.edu.udea.carsharing.persistence.connection.MongoDBConnector;
import co.edu.udea.carsharing.persistence.dao.IUserDAO;
import co.edu.udea.carsharing.persistence.dao.exception.CarSharingDAOException;
import co.edu.udea.carsharing.persistence.exception.CarSharingPersistenceBusinessException;
import co.edu.udea.carsharing.technical.exception.CarSharingTechnicalException;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.mongodb.WriteResult;

public class UserDAOImpl implements IUserDAO {

	private static final String ID = "_id";
	private static final String EMAIL = "email";
	private static final String CARS = "cars";
	private static final String PASSWORD = "password";
	private static final String USERS_COLLECTION_NAME = "Users";

	private static IUserDAO instance;
	private DBCollection collection;

	private UserDAOImpl() throws CarSharingTechnicalException {
		this.collection = MongoDBConnector.connect(USERS_COLLECTION_NAME);
	}

	public static synchronized IUserDAO getInstance()
			throws CarSharingTechnicalException {
		if (null == instance) {
			instance = new UserDAOImpl();
		}

		return instance;
	}

	@Override()
	public User findByEmailAndPassword(String email, String password)
			throws CarSharingDAOException,
			CarSharingPersistenceBusinessException {
		try {
			if (null == email || ("").equals(email.trim()) || null == password
					|| ("").equals(password.trim())) {
				throw new CarSharingPersistenceBusinessException(String.format(
						"Clase %s: método %s. Los parámetros email o password (ambos de tipo %s) "
								+ "no pueden ser ni nulos ni vacíos.",
						UserDAOImpl.class.getSimpleName(), "findByEmail",
						String.class.getSimpleName()));
			} else {
				BasicDBObject projection = new BasicDBObject(PASSWORD, 0);
				BasicDBObject query = new BasicDBObject();
				query.put(EMAIL, email);
				query.put(PASSWORD, password);
				DBObject dbObject = this.collection.findOne(query, projection);

				return (dbObject == null) ? null : User
						.entityFromDBObject(dbObject);
			}
		} catch (Exception e) {
			throw new CarSharingDAOException(
					String.format(
							"Clase %s: método %s. Se presentó un error inesperado.\n%s",
							UserDAOImpl.class.getSimpleName(),
							"findByEmailAndPassword", e));
		}
	}

	@Override()
	public User insert(User user) throws CarSharingDAOException,
			CarSharingPersistenceBusinessException {
		try {
			if (user != null) {
				BasicDBObject basicDBObject = user.entityToDBObject();
				WriteResult wr = this.collection.insert(basicDBObject);

				BasicDBObject projection = new BasicDBObject(PASSWORD, 0);
				ObjectId id = (ObjectId) basicDBObject.get(ID);
				DBObject dbObject = collection.findOne(id, projection);

				return (dbObject != null && wr.getN() == 0) ? User
						.entityFromDBObject(dbObject) : null;
			} else {
				throw new CarSharingPersistenceBusinessException(
						String.format(
								"Clase %s: método %s. El objeto user de tipo %s no puede ser nulo.",
								UserDAOImpl.class.getSimpleName(), "insert()",
								User.class.getSimpleName()));
			}
		} catch (Exception e) {
			throw new CarSharingDAOException(
					String.format(
							"Clase %s: método %s. Se presentó un error inesperdado.\n%s",
							UserDAOImpl.class.getSimpleName(), "insert()", e));
		}
	}

	@Override()
	public User findByEmail(String email) throws CarSharingDAOException,
			CarSharingPersistenceBusinessException {
		try {
			if (null == email || ("").equals(email.trim())) {
				throw new CarSharingPersistenceBusinessException(String.format(
						"Clase %s: método %s. El parámetro email de tipo %s "
								+ "no puede ser ni nulo ni vacío.",
						UserDAOImpl.class.getSimpleName(), "findByEmail",
						String.class.getSimpleName()));
			} else {
				BasicDBObject projection = new BasicDBObject(PASSWORD, 0);
				BasicDBObject query = new BasicDBObject();
				query.put(EMAIL, email);
				DBObject dbObject = this.collection.findOne(query, projection);

				return (dbObject == null) ? null : User
						.entityFromDBObject(dbObject);
			}
		} catch (Exception e) {
			throw new CarSharingDAOException(
					String.format(
							"Clase %s: método %s. Se presentó un error inesperado.\n%s",
							UserDAOImpl.class.getSimpleName(), "findByEmail", e));
		}
	}

	@Override()
	public User update(User user) throws CarSharingDAOException,
			CarSharingPersistenceBusinessException {
		try {
			if (user == null) {
				throw new CarSharingPersistenceBusinessException(
						String.format(
								"Clase: %s, método %s. El parámetro user, de tipo %s, no puede ser nulo.",
								UserDAOImpl.class.getSimpleName(), "update()",
								User.class.getSimpleName()));
			} else {
				BasicDBObject searchingBasicDBObject = new BasicDBObject(ID,
						new ObjectId(user.getId()));
				BasicDBObject updatingBasicDBObject = new BasicDBObject("$set",
						user.entityToDBObject());

				WriteResult wr = this.collection.update(searchingBasicDBObject,
						updatingBasicDBObject, false, true);

				return (wr.getN() != 0) ? user : null;
			}
		} catch (MongoException | NullPointerException e) {
			throw new CarSharingDAOException(String.format(
					"Clase %s: método %s. Se presentó un error inesperado "
							+ "al tratar de actualizar un usuario.\n%s",
					UserDAOImpl.class.getSimpleName(), "update()", e));
		}
	}

	@Override()
	public List<Car> getCarsByUser(String email) throws CarSharingDAOException {
		try {
			if (null == email || email.trim().isEmpty()) {
				throw new CarSharingPersistenceBusinessException(
						String.format(
								"Clase %s: método %s. El parámetro email de tipo %s no puede ser ni nulo ni vacío.",
								UserDAOImpl.class.getSimpleName(),
								"getCarsByUser", String.class.getSimpleName()));
			} else {
				List<Car> cars = new ArrayList<Car>();
				DBObject projection = new BasicDBObject(CARS, 1);
				projection.put(ID, 0);
				DBObject query = new BasicDBObject(EMAIL, email);

				DBCursor dbCursor = this.collection.find(query, projection);
				DBObject dbObject = dbCursor.one();

				if (dbObject != null && dbObject.containsField(CARS)) {

					BasicDBList basicDBList = (BasicDBList) dbObject.get(CARS);
					for (Object o : basicDBList) {
						cars.add(Car.entityFromDBObject((DBObject) o));
					}
				}

				return cars;
			}
		} catch (Exception e) {
			throw new CarSharingDAOException(String.format(
					"Clase %s: método %s. Se generó un error al tratar de obtener "
							+ "el listado de carros de un usuario.\n%s",
					UserDAOImpl.class.getSimpleName(), "getCarsByUser", e));
		}
	}
}
