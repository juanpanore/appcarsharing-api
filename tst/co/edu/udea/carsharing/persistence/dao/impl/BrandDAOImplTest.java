package co.edu.udea.carsharing.persistence.dao.impl;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import co.edu.udea.carsharing.model.entities.Brand;
import co.edu.udea.carsharing.persistence.dao.exception.CarSharingDAOException;
import co.edu.udea.carsharing.persistence.exception.CarSharingPersistenceBusinessException;
import co.edu.udea.carsharing.technical.exception.CarSharingTechnicalException;

public class BrandDAOImplTest {

	@Test
	public void testFindAll() throws CarSharingDAOException,
			CarSharingTechnicalException {
		List<Brand> brands = new ArrayList<Brand>();

		brands = BrandDAOImpl.getInstance().findAll();

		assertTrue(brands.size() >= 0);
	}

	@Test
	public void testInsert() throws CarSharingDAOException,
			CarSharingPersistenceBusinessException,
			CarSharingTechnicalException {
		Brand brand = new Brand("MAZDA");
		brand = BrandDAOImpl.getInstance().insert(brand);

		assertTrue(null != brand);
	}

	@Test
	public void testFind() throws CarSharingDAOException,
			CarSharingPersistenceBusinessException,
			CarSharingTechnicalException {
		String brand = "MAZDA";

		Brand b = BrandDAOImpl.getInstance().find(brand);

		assertTrue(b != null);
	}
}
