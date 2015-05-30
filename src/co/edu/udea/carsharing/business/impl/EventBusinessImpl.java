package co.edu.udea.carsharing.business.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.carsharing.business.IEventBusiness;
import co.edu.udea.carsharing.business.exception.CarSharingBusinessException;
import co.edu.udea.carsharing.model.entities.Comment;
import co.edu.udea.carsharing.model.entities.Event;
import co.edu.udea.carsharing.model.entities.User;
import co.edu.udea.carsharing.persistence.dao.impl.EventDAOImpl;

public class EventBusinessImpl implements IEventBusiness {

	private static IEventBusiness instance;

	private EventBusinessImpl() {
		super();
	}

	public static synchronized IEventBusiness getInstance() {
		if (instance == null) {
			instance = new EventBusinessImpl();
		}
		return instance;
	}

	@Override()
	public Event find(String eventId) throws CarSharingBusinessException {
		try {
			if (null == eventId || eventId.trim().isEmpty()) {
				throw new CarSharingBusinessException(String.format(
						"Clase %s: método %s. El parámetro eventId,"
								+ " no puede ser ni nulo ni vacío.",
						EventBusinessImpl.class.getSimpleName(), "find()",
						String.class.getSimpleName()));
			} else {

				return EventDAOImpl.getInstance().find(eventId);
			}
		} catch (Exception e) {
			throw new CarSharingBusinessException(String.format(
					"Clase %s: método %s. Se ha producido un error al tratar de buscar "
							+ "un evento por el id.\n%s",
					EventBusinessImpl.class.getSimpleName(), "find()", e));
		}
	}

	@Override()
	public List<Event> findAll() throws CarSharingBusinessException {
		try {
			return EventDAOImpl.getInstance().findAll();
		} catch (Exception e) {
			throw new CarSharingBusinessException(String.format(
					"Clase %s: método %s. Se ha producido un error al tratar de buscar "
							+ "una lista de eventos.\n%s",
					EventBusinessImpl.class.getSimpleName(), "findAll()", e));
		}
	}

	@Override()
	public Event insert(Event event) throws CarSharingBusinessException {
		try {
			if (null == event) {
				throw new CarSharingBusinessException(String.format(
						"Clase %s: método %s. El parámetro event,"
								+ " no puede ser nulo.",
						EventBusinessImpl.class.getSimpleName(), "insert()",
						String.class.getSimpleName()));
			} else {
				if (event.getId() == null || event.getId().trim().isEmpty()) {
					event.setAmountPeople(0);
					return EventDAOImpl.getInstance().insert(event);
				}
				return event;
			}
		} catch (Exception e) {
			throw new CarSharingBusinessException(String.format(
					"Clase %s: método %s. Se ha producido un error al tratar de insertar "
							+ "un evento.\n%s",
					EventBusinessImpl.class.getSimpleName(), "insert()", e));
		}
	}

	@Override()
	public Event insertComment(Comment newComment, Event event)
			throws CarSharingBusinessException {
		try {
			if (null == newComment || event == null || event.getId() == null
					|| event.getId().trim().isEmpty()) {
				throw new CarSharingBusinessException(String.format(
						"Clase %s: método %s. El parámetro newComment y el eventId,"
								+ " no pueden ser nulos ni vacíos.",
						EventBusinessImpl.class.getSimpleName(),
						"insertComment()", String.class.getSimpleName()));
			} else {
				if (event.getComments() == null) {
					event.setComments(new ArrayList<Comment>());
				}
				event.getComments().add(newComment);
				event = this.update(event);

				return event;
			}
		} catch (Exception e) {
			throw new CarSharingBusinessException(String.format(
					"Clase %s: método %s. Se ha producido un error al tratar de insertar "
							+ "un comentario en el evento.\n%s",
					EventBusinessImpl.class.getSimpleName(), "insertComment()",
					e));
		}
	}

	@Override()
	public Event join(User newPartner, Event event)
			throws CarSharingBusinessException {
		try {
			if (null == newPartner || event == null || event.getId() == null
					|| event.getId().trim().isEmpty()) {
				throw new CarSharingBusinessException(String.format(
						"Clase %s: método %s. El parámetro newPartner y el eventId,"
								+ " no pueden ser nulos ni vacíos.",
						EventBusinessImpl.class.getSimpleName(),
						"insertComment()", String.class.getSimpleName()));
			} else {
				if (event.getCar().getCapacity() > event.getAmountPeople()) {
					// TODO Falta verificar que la persona que se vaya a unir no
					// se haya unido previamente
					if (event.getPartners() == null) {

						event.setPartners(new ArrayList<User>());
					}
					event.getPartners().add(newPartner);
					event.setAmountPeople(event.getAmountPeople() + 1);
					event = this.update(event);
				} else {

					return null;
				}

				return event;
			}
		} catch (Exception e) {
			throw new CarSharingBusinessException(String.format(
					"Clase %s: método %s. Se ha producido un error al tratar de insertar "
							+ "un compañero en el evento.\n%s",
					EventBusinessImpl.class.getSimpleName(), "insertPartner()",
					e));
		}
	}

	@Override()
	public Event update(Event event) throws CarSharingBusinessException {
		try {
			if (null == event) {
				throw new CarSharingBusinessException(String.format(
						"Clase %s: método %s. El parámetro event,"
								+ " no puede ser nulo.",
						EventBusinessImpl.class.getSimpleName(), "update()",
						String.class.getSimpleName()));
			} else {
				return EventDAOImpl.getInstance().update(event);
			}
		} catch (Exception e) {
			throw new CarSharingBusinessException(String.format(
					"Clase %s: método %s. Se ha producido un error al tratar de insertar "
							+ "un evento.\n%s",
					EventBusinessImpl.class.getSimpleName(), "insert()", e));
		}
	}

}