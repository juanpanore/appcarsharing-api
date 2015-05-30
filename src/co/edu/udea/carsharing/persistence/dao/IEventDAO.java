package co.edu.udea.carsharing.persistence.dao;

import java.util.List;

import co.edu.udea.carsharing.model.entities.Comment;
import co.edu.udea.carsharing.model.entities.Event;
import co.edu.udea.carsharing.model.entities.User;
import co.edu.udea.carsharing.persistence.dao.exception.CarSharingDAOException;
import co.edu.udea.carsharing.persistence.exception.CarSharingPersistenceBusinessException;

public interface IEventDAO {

	public Event find(String eventId) throws CarSharingDAOException,
			CarSharingPersistenceBusinessException;

	public List<Event> findAll() throws CarSharingDAOException;

	public Event insert(Event event) throws CarSharingDAOException,
			CarSharingPersistenceBusinessException;

	public Event insertComment(Comment newComment, String eventId)
			throws CarSharingDAOException, CarSharingPersistenceBusinessException;

	public Event join(User newPartner, String eventId)
			throws CarSharingDAOException, CarSharingPersistenceBusinessException;

	public Event update(Event event) throws CarSharingDAOException,
			CarSharingPersistenceBusinessException;
}
