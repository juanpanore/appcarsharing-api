package co.edu.udea.carsharing.business;

import java.util.List;

import co.edu.udea.carsharing.business.exception.CarSharingBusinessException;
import co.edu.udea.carsharing.model.entities.Brand;
import co.edu.udea.carsharing.technical.exception.CarSharingTechnicalException;

public interface IBrandBusiness {

	public List<Brand> findAll() throws CarSharingBusinessException,
			CarSharingTechnicalException;

	public Brand insert(Brand brand) throws CarSharingBusinessException;
}
