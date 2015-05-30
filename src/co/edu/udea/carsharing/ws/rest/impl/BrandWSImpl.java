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
import co.edu.udea.carsharing.ws.rest.util.WSUtil;

@Path(value = RESTFulWebServicesContract.BrandtWebServicesContract.ROOT_PATH)
public class BrandWSImpl implements IBrandWS {

	@POST()
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Override()
	public Response insert(Brand brand) throws CarSharingWSException {
		if (!WSUtil.validateBrand(brand)) {

			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		Brand b;
		try {

			b = BrandBusinessImpl.getInstance().insert(brand);
		} catch (CarSharingBusinessException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.build();
		}

		return (b != null) ? Response.ok(b).build() : Response.status(
				Response.Status.NO_CONTENT).build();
	}

	@GET()
	@Produces(value = { MediaType.APPLICATION_JSON })
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