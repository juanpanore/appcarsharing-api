package co.edu.udea.carsharing.ws.rest.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import co.edu.udea.carsharing.business.exception.CarSharingBusinessException;
import co.edu.udea.carsharing.business.impl.BrandBusinessImpl;
import co.edu.udea.carsharing.model.entities.Brand;
import co.edu.udea.carsharing.technical.exception.CarSharingTechnicalException;
import co.edu.udea.carsharing.ws.IBrandWS;
import co.edu.udea.carsharing.ws.exception.CarSharingWSException;
import co.edu.udea.carsharing.ws.rest.contract.RESTFulWebServicesContract;
import co.edu.udea.carsharing.ws.rest.util.ResponseMessage;
import co.edu.udea.carsharing.ws.rest.util.WSUtil;

@Path(value = RESTFulWebServicesContract.BrandtWebServicesContract.ROOT_PATH)
public class BrandWSImpl implements IBrandWS {

	private static final String REPEATED = "La marca ha insertar se encuentra repetida.";

	@POST()
	@Consumes(value = MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Produces(value = MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Override()
	public Response insert(Brand brand) throws CarSharingWSException {
		if (!WSUtil.validateBrand(brand)) {

			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		Brand b = null;
		try {

			b = BrandBusinessImpl.getInstance().insert(brand);
		} catch (CarSharingBusinessException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.build();
		}

		return (b != null) ? Response.ok(b).build() : Response.ok(
				new ResponseMessage(REPEATED)).build();
	}

	@GET()
	@Produces(value = MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Override()
	public Response findAll() throws CarSharingWSException {
		List<Brand> brands = new ArrayList<Brand>();

		try {
			brands = BrandBusinessImpl.getInstance().findAll();
		} catch (CarSharingBusinessException | CarSharingTechnicalException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.build();
		}

		GenericEntity<List<Brand>> entity = new GenericEntity<List<Brand>>(
				brands) {
		};

		return (brands.isEmpty()) ? Response.status(Response.Status.NO_CONTENT)
				.build() : Response.ok(entity).build();
	}
}