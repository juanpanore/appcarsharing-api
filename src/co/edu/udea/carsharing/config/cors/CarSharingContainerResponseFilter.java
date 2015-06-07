package co.edu.udea.carsharing.config.cors;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

public class CarSharingContainerResponseFilter implements
		ContainerResponseFilter {

	public CarSharingContainerResponseFilter() {
		super();
	}

	@Override()
	public ContainerResponse filter(ContainerRequest requestContext,
			ContainerResponse responseContext) {
		ResponseBuilder responseBuilder = Response.fromResponse(responseContext
				.getResponse());

		// responseBuilder.header("Access-Control-Allow-Credentials", "false");
		responseBuilder.header("Access-Control-Allow-Origin", "*");
		responseBuilder.header("Access-Control-Allow-Methods",
				"GET, POST, OPTIONS, PUT");
		responseBuilder.header("Access-Control-Allow-Headers",
				"origin, content-type, accept, authorization");

		String requestHeader = requestContext
				.getHeaderValue("Access-Control-Request-Headers");

		if ((null != requestHeader) && !(requestHeader.equals(""))) {
			responseBuilder.header("Access-Control-Allow-Headers",
					requestHeader);
		}
		responseContext.setResponse(responseBuilder.build());

		return (responseContext);
	}
}