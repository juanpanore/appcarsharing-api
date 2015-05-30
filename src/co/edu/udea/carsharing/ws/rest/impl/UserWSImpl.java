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
import co.edu.udea.carsharing.business.impl.UserBusinessImpl;
import co.edu.udea.carsharing.model.entities.Car;
import co.edu.udea.carsharing.model.entities.User;
import co.edu.udea.carsharing.ws.IUserWS;
import co.edu.udea.carsharing.ws.exception.CarSharingWSException;
import co.edu.udea.carsharing.ws.rest.contract.RESTFulWebServicesContract;
import co.edu.udea.carsharing.ws.rest.util.WSUtil;

@Path(value = RESTFulWebServicesContract.UserWebServicesContract.ROOT_PATH)
public class UserWSImpl implements IUserWS {

	@Path(value = "/{"
			+ RESTFulWebServicesContract.UserWebServicesContract.EMAIL_PARAM
			+ "}/{"
			+ RESTFulWebServicesContract.UserWebServicesContract.PASSWORD_PARAM
			+ "}")
	@GET()
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Override()
	public Response findByEmailAndPassword(
			@PathParam(value = RESTFulWebServicesContract.UserWebServicesContract.EMAIL_PARAM) String email,
			@PathParam(value = RESTFulWebServicesContract.UserWebServicesContract.PASSWORD_PARAM) String password)
			throws CarSharingWSException {
		if (email == null || email.trim().isEmpty() || password == null
				|| password.trim().isEmpty()) {

			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		User user = null;
		try {
			user = UserBusinessImpl.getInstance().findByEmailAndPassword(email,
					password);
		} catch (CarSharingBusinessException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.build();
		}

		return (user == null) ? Response.status(Response.Status.NO_CONTENT)
				.build() : Response.ok(user).build();
	}

	@Path(value = RESTFulWebServicesContract.UserWebServicesContract.FIND_USER_BY_EMAIL_PATH
			+ "/{"
			+ RESTFulWebServicesContract.UserWebServicesContract.EMAIL_PARAM
			+ "}")
	@GET()
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Override()
	public Response findByEmail(
			@PathParam(value = RESTFulWebServicesContract.UserWebServicesContract.EMAIL_PARAM) String email)
			throws CarSharingWSException {
		if (email == null || email.trim().isEmpty()) {

			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		User user = null;
		try {
			user = UserBusinessImpl.getInstance().findByEmail(email);
		} catch (CarSharingBusinessException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.build();
		}

		return (user == null) ? Response.status(Response.Status.NO_CONTENT)
				.build() : Response.ok(user).build();
	}

	@POST()
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Override()
	public Response insert(User user) throws CarSharingWSException {
		if (!WSUtil.validateUser(user)) {

			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		User u = null;
		try {
			u = UserBusinessImpl.getInstance().insert(user);
		} catch (CarSharingBusinessException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.build();
		}

		return (u == null) ? Response.status(Response.Status.NO_CONTENT)
				.build() : Response.ok(u).build();
	}

	@Path(value = "/{"
			+ RESTFulWebServicesContract.UserWebServicesContract.EMAIL_PARAM
			+ "}")
	@PUT()
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Override()
	public Response addCar(
			@PathParam(value = RESTFulWebServicesContract.UserWebServicesContract.EMAIL_PARAM) String email,
			Car car) throws CarSharingWSException {
		if (email == null || email.trim().isEmpty() || !WSUtil.validateCar(car)) {

			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		User user = null;
		try {
			user = UserBusinessImpl.getInstance().addCar(email, car);
		} catch (CarSharingBusinessException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.build();
		}

		return (user == null) ? Response.status(Response.Status.NO_CONTENT)
				.build() : Response.ok(user).build();
	}

	@Path(value = RESTFulWebServicesContract.UserWebServicesContract.FIND_CARS_BY_USER_PATH
			+ "/{"
			+ RESTFulWebServicesContract.UserWebServicesContract.EMAIL_PARAM
			+ "}")
	@GET()
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Override()
	public Response getCarsByUser(
			@PathParam(value = RESTFulWebServicesContract.UserWebServicesContract.EMAIL_PARAM) String email)
			throws CarSharingWSException {
		if (email == null || email.trim().isEmpty()) {

			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		List<Car> cars = new ArrayList<Car>();
		try {
			cars = UserBusinessImpl.getInstance().getCarsByUser(email);
		} catch (CarSharingBusinessException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.build();
		}

		GenericEntity<List<Car>> entity = new GenericEntity<List<Car>>(cars) {
		};

		return (cars.isEmpty()) ? Response.status(Response.Status.NO_CONTENT)
				.build() : Response.ok(entity).build();
	}
}