package co.edu.udea.carsharing.ws;

import javax.ws.rs.core.Response;

import co.edu.udea.carsharing.model.entities.Car;
import co.edu.udea.carsharing.model.entities.User;
import co.edu.udea.carsharing.ws.exception.CarSharingWSException;

public interface IUserWS {

	public Response findByEmailAndPassword(String email, String password)
			throws CarSharingWSException;

	public Response findByEmail(String email) throws CarSharingWSException;

	public Response insert(User user) throws CarSharingWSException;

	public Response addCar(String email, Car car) throws CarSharingWSException;

	public Response getCarsByUser(String email) throws CarSharingWSException;
}