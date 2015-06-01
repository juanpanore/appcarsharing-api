package co.edu.udea.carsharing.persistence.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import co.edu.udea.carsharing.model.entities.Comment;
import co.edu.udea.carsharing.model.entities.Event;
import co.edu.udea.carsharing.model.entities.User;
import co.edu.udea.carsharing.model.entities.enums.StateEnum;
import co.edu.udea.carsharing.persistence.connection.MongoDBConnector;
import co.edu.udea.carsharing.persistence.dao.IEventDAO;
import co.edu.udea.carsharing.persistence.dao.exception.CarSharingDAOException;
import co.edu.udea.carsharing.persistence.exception.CarSharingPersistenceBusinessException;
import co.edu.udea.carsharing.technical.exception.CarSharingTechnicalException;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

public class EventDAOImpl implements IEventDAO {

	private static IEventDAO instance;

	private final DBCollection collection;

	private static final String EVENTS_COLLECTION_NAME = "Events";
	private static final String ID = "_id";
	private static final String STATE = "state";
	private static String CREATEDATE = "createDate";
	private static String EVENTDATE = "eventDate";
	private static String AUTHOR = "author";
	private static String CAR = "car";
	private static String COMMENTS = "comments";
	private static String SOURCE = "source";
	private static String TARGET = "target";
	private static String VALUE = "value";
	private static String AMOUNTPEOPLE = "amountPeople";
	public static final Map<String, String> INEQUALITIES = new HashMap<String, String>();

	static {
		INEQUALITIES.put("<", "$lt");
		INEQUALITIES.put("<=", "$lte");
		INEQUALITIES.put(">", "$gt");
		INEQUALITIES.put(">=", "$gte");
		INEQUALITIES.put("!=", "$ne");
	}

	private EventDAOImpl() throws CarSharingTechnicalException {
		this.collection = MongoDBConnector.connect(EVENTS_COLLECTION_NAME);
	}

	public static synchronized IEventDAO getInstance()
			throws CarSharingTechnicalException {
		if (instance == null) {
			instance = new EventDAOImpl();
		}

		return instance;
	}

	@Override()
	public Event find(String eventId) throws CarSharingDAOException,
			CarSharingPersistenceBusinessException {
		try {
			if (eventId != null && !("").equals(eventId.trim())) {
				DBObject dbo = new BasicDBObject(ID, new ObjectId(eventId));
				DBObject dbObject = this.collection.findOne(dbo);

				return Event.entityFromDBObject(dbObject);
			} else {
				throw new CarSharingPersistenceBusinessException(
						String.format(
								"Clase %s: método %s. "
										+ "El parámetro eventId (%s) no puede ser ni nulo ni vacío.",
								EventDAOImpl.class.getSimpleName(), "find()",
								String.class.getSimpleName()));
			}
		} catch (Exception e) {
			throw new CarSharingDAOException(String.format(
					"Clase: %s: método %s. Se ha presentado un error inesperado al tratar "
							+ "de encontrar un evento por id.\n%s",
					EventDAOImpl.class.getSimpleName(), "find()", e));
		}
	}

	@Override()
	public List<Event> findAll() throws CarSharingDAOException {
		try {
			List<Event> eventList = new ArrayList<Event>();

			BasicDBObject query = new BasicDBObject(STATE,
					StateEnum.ACTIVE.getDescription());
			DBCursor dbCursor = this.collection
					.find(query, new BasicDBObject());

			for (DBObject dbo : dbCursor) {
				eventList.add(Event.entityFromDBObject(dbo));
			}

			return eventList;
		} catch (Exception e) {
			throw new CarSharingDAOException(
					String.format(
							"Clase %s: método %s. Se ha producido un error inesperado al "
									+ "tratar de obtener todos los eventos activos.\n%s",
							EventDAOImpl.class.getSimpleName(), "findAll()", e));
		}
	}

	@Override()
	public Event insert(Event event) throws CarSharingDAOException,
			CarSharingPersistenceBusinessException {
		try {
			if (event != null) {
				event.setState(StateEnum.ACTIVE.getDescription());
				BasicDBObject basicDBObject = event.entityToDBObject();
				WriteResult wr = this.collection.insert(basicDBObject);

				ObjectId id = (ObjectId) basicDBObject.get(ID);
				DBObject dbObject = collection.findOne(id);

				return (null != dbObject && wr.getN() == 0) ? Event
						.entityFromDBObject(dbObject) : null;
			} else {
				throw new CarSharingPersistenceBusinessException(
						String.format(
								"Clase %s: método %s. El parámetro event (%s) no puede ser nulo.",
								EventDAOImpl.class.getSimpleName(), "insert()",
								Event.class.getSimpleName()));
			}
		} catch (Exception e) {
			throw new CarSharingDAOException(String.format(
					"Clase %s: método %s. Se ha presentado un error inesperado mientras "
							+ "se trataba de insertar un evento.\n%s.",
					EventDAOImpl.class.getSimpleName(), "insert()", e));
		}
	}

	@Override()
	public Event insertComment(Comment newComment, String eventId)
			throws CarSharingDAOException,
			CarSharingPersistenceBusinessException {
		try {
			if (eventId != null && !("").equals(eventId.trim())
					&& newComment != null) {
				Event event = this.find(eventId);
				if (event != null
						&& StateEnum.ACTIVE.getDescription().equals(
								event.getState().trim())) {
					event.getComments().add(newComment);
					event = this.update(event);
				}

				return event;
			} else {
				throw new CarSharingPersistenceBusinessException(
						String.format(
								"Clase %s: método %s. El parámetro eventId (%s) no puede ser nulo "
										+ "ni vacío y el parámetro newComment (%s) no puede ser nulo.",
								EventDAOImpl.class.getSimpleName(),
								"insertComment()",
								String.class.getSimpleName(),
								Comment.class.getSimpleName()));
			}
		} catch (Exception e) {
			throw new CarSharingDAOException(
					String.format(
							"Clase %s: método %s. Se ha presentado un error inesperado "
									+ "mmientras se trataba de agregar un comentario en un "
									+ "evento previamente creado.\n%s",
							EventDAOImpl.class.getSimpleName(),
							"insertComment()", e));
		}
	}

	@Override()
	public Event join(User newPartner, String eventId)
			throws CarSharingDAOException,
			CarSharingPersistenceBusinessException {
		try {
			if (eventId != null && !("").equals(eventId.trim())
					&& newPartner != null) {
				Event event = this.find(eventId);
				if (event != null
						&& StateEnum.ACTIVE.getDescription().equals(
								event.getState().trim())) {
					event.getPartners().add(newPartner);
					event = this.update(event);
				}

				return event;
			} else {
				throw new CarSharingPersistenceBusinessException(
						String.format(
								"Clase %s: método %s. El parámetro eventId (%s) no puede ser nulo "
										+ "ni vacío y el parámetro newPartner (%s) no puede ser nulo.",
								EventDAOImpl.class.getSimpleName(), "join()",
								String.class.getSimpleName(),
								User.class.getSimpleName()));
			}
		} catch (Exception e) {
			throw new CarSharingDAOException(
					String.format(
							"Clase %s: método %s. Se ha presentado un error inesperado "
									+ "mmientras se trataba de agregar un nuevo usuario a un "
									+ "evento previamente creado.\n%s",
							EventDAOImpl.class.getSimpleName(), "join()", e));
		}
	}

	@Override()
	public Event update(Event event) throws CarSharingDAOException,
			CarSharingPersistenceBusinessException {
		try {
			if (event != null) {
				if (StateEnum.ACTIVE.getDescription().equals(
						event.getState().trim())) {
					BasicDBObject searchingBasicDBObject = new BasicDBObject(
							ID, new ObjectId(event.getId()));
					BasicDBObject updatingBasicDBObject = new BasicDBObject(
							"$set", event.entityToDBObject());

					WriteResult wr = this.collection.update(
							searchingBasicDBObject, updatingBasicDBObject,
							false, true);

					return (wr.getN() != 0) ? event : null;
				}

				return null;
			} else {
				throw new CarSharingPersistenceBusinessException(String.format(
						"Clase %s: método %s. El parámetro event (%s) no puede ser nulo y "
								+ "debe estar en estado Activo.",
						EventDAOImpl.class.getSimpleName(), "update()",
						Event.class.getSimpleName()));
			}
		} catch (Exception e) {
			throw new CarSharingDAOException(String.format(
					"Clase %s: método %s. Se ha producido un error inesperado mientras "
							+ "se trataba de actualizar un evento activo.\n%s",
					EventDAOImpl.class.getSimpleName(), "update()", e));
		}
	}

	@Override()
	public Event findEventByPartner(String eventId, String partnerEmail)
			throws CarSharingDAOException,
			CarSharingPersistenceBusinessException {
		try {
			if (null == eventId || eventId.trim().isEmpty()
					|| null == partnerEmail || partnerEmail.trim().isEmpty()) {
				throw new CarSharingDAOException(
						String.format(
								"Clase %s: método %s. El parámetro eventId (%s) "
										+ "no puede ser nulo ni vacío y el parámetro "
										+ "partnerEmail (%s) no puede ser nulo ni vacío.",
								EventDAOImpl.class.getSimpleName(),
								"findEventByPartner()",
								String.class.getSimpleName(),
								String.class.getSimpleName()));
			} else {
				BasicDBObject query = new BasicDBObject();
				query.put(ID, new ObjectId(eventId));
				
				BasicDBObject projection = new BasicDBObject();
				projection.put(EVENTDATE, 0);
				projection.put(AMOUNTPEOPLE, 0);
				projection.put(AUTHOR, 0);
				projection.put(CAR, 0);
				projection.put(COMMENTS, 0);
				projection.put(SOURCE, 0);
				projection.put(TARGET, 0);
				projection.put(VALUE, 0);
				projection.put(CREATEDATE, 0);
				
				DBObject dbObject = this.collection.findOne(query, projection);
				if (dbObject == null) {

					return null;
				}

				Event ev = Event.entityFromDBObject(dbObject);
				List<User> partners = ev.getPartners();
				if (partners == null || partners.isEmpty()) {

					return null;
				}

				for (User u : partners) {
					String email = u.getEmail().trim();
					if (partnerEmail.trim().equals(email)) {

						return ev;
					}
				}

				return null;
			}
		} catch (Exception e) {
			throw new CarSharingDAOException(String.format(
					"Clase %s: método %s. Se ha producido un error inesperado mientras "
							+ "se trataba de actualizar un evento activo.\n%s",
					EventDAOImpl.class.getSimpleName(), "findEventByPartner()",
					e));
		}
	}
}