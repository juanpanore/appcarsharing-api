package co.edu.udea.carsharing.ws.rest.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import co.edu.udea.carsharing.business.exception.CarSharingBusinessException;
import co.edu.udea.carsharing.business.impl.EventBusinessImpl;
import co.edu.udea.carsharing.model.entities.Comment;
import co.edu.udea.carsharing.model.entities.Event;
import co.edu.udea.carsharing.model.entities.User;
import co.edu.udea.carsharing.ws.IEventWS;
import co.edu.udea.carsharing.ws.exception.CarSharingWSException;
import co.edu.udea.carsharing.ws.rest.contract.RESTFulWebServicesContract;
import co.edu.udea.carsharing.ws.rest.util.ResponseMessage;
import co.edu.udea.carsharing.ws.rest.util.WSUtil;

@Path(value = RESTFulWebServicesContract.EventWebServicesContract.ROOT_PATH)
public class EventWSImpl implements IEventWS {

	private static final String FULL = "El cupo m�ximo ya se encuentra ocupado.";
	private static final String JOINT = "El usuario que se intenta unir al viaje es el creador del mismo.";

	@Path(value = "/{"
			+ RESTFulWebServicesContract.EventWebServicesContract.EVENT_ID_PARAM
			+ "}")
	@GET()
	@Produces(value = MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Override()
	public Response find(
			@PathParam(value = RESTFulWebServicesContract.EventWebServicesContract.EVENT_ID_PARAM) String eventId)
			throws CarSharingWSException {
		if (eventId == null || eventId.isEmpty()) {

			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		Event event = null;
		try {

			event = EventBusinessImpl.getInstance().find(eventId);
		} catch (CarSharingBusinessException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.build();
		}

		return (event != null) ? Response.ok(event).build() : Response.status(
				Response.Status.NO_CONTENT).build();
	}

	@GET()
	@Path(value = RESTFulWebServicesContract.EventWebServicesContract.FIND_ALL_PATH)
	@Produces(value = MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Override()
	public Response findAll() throws CarSharingWSException {
		List<Event> eventList = new ArrayList<Event>();

		try {
			eventList = EventBusinessImpl.getInstance().findAll();
		} catch (CarSharingBusinessException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.build();
		}

		GenericEntity<List<Event>> entity = new GenericEntity<List<Event>>(
				eventList) {
		};

		return (eventList.isEmpty()) ? Response.status(
				Response.Status.NO_CONTENT).build() : Response.ok(entity)
				.build();
	}

	@POST()
	@Consumes(value = MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Produces(value = MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Override()
	public Response insert(Event event) throws CarSharingWSException {
		if (event == null || !WSUtil.validateEvent(event)) {

			return (Response.status(Response.Status.BAD_REQUEST).build());

		} else {
			Event returnedEvent = null;
			try {

				returnedEvent = EventBusinessImpl.getInstance().insert(event);
			} catch (CarSharingBusinessException e) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
						.build();
			}

			return (returnedEvent != null) ? Response.ok(returnedEvent).build()
					: Response.status(Response.Status.INTERNAL_SERVER_ERROR)
							.build();
		}
	}

	@Path(value = RESTFulWebServicesContract.EventWebServicesContract.INSERT_COMMENT
			+ "/{"
			+ RESTFulWebServicesContract.EventWebServicesContract.EVENT_ID_PARAM
			+ "}")
	@PUT()
	@Consumes(value = MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Produces(value = MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Override()
	public Response insertComment(
			Comment newComment,
			@PathParam(value = RESTFulWebServicesContract.EventWebServicesContract.EVENT_ID_PARAM) String eventId)
			throws CarSharingWSException {
		Event returnedEvent;
		if (!WSUtil.validateComment(newComment) || eventId == null
				|| eventId.isEmpty()) {

			return Response.status(Response.Status.BAD_REQUEST).build();
		} else {
			try {
				returnedEvent = EventBusinessImpl.getInstance().find(eventId);
				if (returnedEvent != null) {

					returnedEvent = EventBusinessImpl.getInstance()
							.insertComment(newComment, returnedEvent);
				} else {

					return Response.status(Response.Status.BAD_REQUEST).build();
				}

			} catch (CarSharingBusinessException e) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
						.build();
			}

			return (returnedEvent != null) ? Response.ok(returnedEvent).build()
					: Response.status(Response.Status.INTERNAL_SERVER_ERROR)
							.build();
		}
	}

	@Path(value = RESTFulWebServicesContract.EventWebServicesContract.JOIN_PARTNER
			+ "/{"
			+ RESTFulWebServicesContract.EventWebServicesContract.EVENT_ID_PARAM
			+ "}")
	@PUT()
	@Produces(value = MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(value = MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Override()
	public Response join(
			User newPartner,
			@PathParam(value = RESTFulWebServicesContract.EventWebServicesContract.EVENT_ID_PARAM) String eventId) {
		Event returnedEvent;
		if (!WSUtil.validateAuthor(newPartner) || eventId == null
				|| eventId.isEmpty()) {

			return (Response.status(Response.Status.BAD_REQUEST).build());
		} else {
			try {
				returnedEvent = EventBusinessImpl.getInstance().find(eventId);
				if (returnedEvent != null && validateJoin(returnedEvent)) {

					if (returnedEvent.getAuthor().getEmail() != null
							&& !returnedEvent.getAuthor().getEmail()
									.equals(newPartner.getEmail())) {

						returnedEvent = EventBusinessImpl.getInstance().join(
								newPartner, returnedEvent);
					} else {

						ResponseMessage responseMessage = new ResponseMessage(
								JOINT);

						return Response.ok(responseMessage).build();
					}
				} else {
					if (returnedEvent.getPartners() != null
							&& returnedEvent.getPartners().size() == returnedEvent
									.getAmountPeople()
							&& returnedEvent.getCar().getCapacity() == returnedEvent
									.getAmountPeople()) {
						ResponseMessage responseMessage = new ResponseMessage(
								FULL);

						return Response.ok(responseMessage).build();
					}

					return Response.status(
							Response.Status.INTERNAL_SERVER_ERROR).build();
				}

			} catch (CarSharingBusinessException e) {
				return (Response.status(Response.Status.CONFLICT).build());
			}

			return (returnedEvent != null) ? Response.ok(returnedEvent).build()
					: Response.status(Response.Status.NO_CONTENT).build();
		}
	}

	private boolean validateJoin(Event event) {
		if (event != null) {
			int amount = 0;
			if (event.getPartners() != null) {

				amount = event.getPartners().size();
			}

			int amountPeople = event.getAmountPeople();

			if (amount == amountPeople) {
				int capacity = event.getCar().getCapacity();
				if (capacity > amountPeople) {

					return true;
				}
			}
		}

		return false;
	}
}
