package co.edu.udea.carsharing.ws;

import javax.ws.rs.core.Response;

import co.edu.udea.carsharing.model.entities.Comment;
import co.edu.udea.carsharing.model.entities.Event;
import co.edu.udea.carsharing.model.entities.User;
import co.edu.udea.carsharing.ws.exception.CarSharingWSException;

public interface IEventWS {

	public Response find(String eventId) throws CarSharingWSException;

	public Response findAll() throws CarSharingWSException;

	public Response  insert(Event event) throws CarSharingWSException;

	public Response insertComment(Comment newComment, String eventId)
			throws CarSharingWSException;

	public Response join(User newPartner, String eventId)
			throws CarSharingWSException;
}