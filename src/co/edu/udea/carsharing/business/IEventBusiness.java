package co.edu.udea.carsharing.business;

import java.util.List;
import co.edu.udea.carsharing.business.exception.CarSharingBusinessException;
import co.edu.udea.carsharing.model.entities.Comment;
import co.edu.udea.carsharing.model.entities.Event;
import co.edu.udea.carsharing.model.entities.User;

public interface IEventBusiness {

	public Event find(String eventId)throws CarSharingBusinessException;

	public List<Event> findAll()throws CarSharingBusinessException;

	public Event insert(Event event)throws CarSharingBusinessException;

	public Event insertComment(Comment newComment, Event event)throws CarSharingBusinessException;

	public Event join(User newPartner, Event event)throws CarSharingBusinessException;

	public Event update(Event event) throws CarSharingBusinessException;
}
