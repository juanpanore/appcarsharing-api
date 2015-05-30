package co.edu.udea.carsharing.ws;

import javax.ws.rs.core.Response;

import co.edu.udea.carsharing.business.exception.CarSharingBusinessException;
import co.edu.udea.carsharing.model.entities.Brand;
import co.edu.udea.carsharing.ws.exception.CarSharingWSException;

public interface IBrandWS {

	public Response insert(Brand brand) throws CarSharingWSException,
			CarSharingBusinessException;

	public Response findAll() throws CarSharingWSException;
}
