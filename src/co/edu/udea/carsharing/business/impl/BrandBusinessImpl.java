package co.edu.udea.carsharing.business.impl;

import java.util.List;

import co.edu.udea.carsharing.business.IBrandBusiness;
import co.edu.udea.carsharing.business.exception.CarSharingBusinessException;
import co.edu.udea.carsharing.model.entities.Brand;
import co.edu.udea.carsharing.persistence.dao.impl.BrandDAOImpl;
import co.edu.udea.carsharing.technical.exception.CarSharingTechnicalException;

public class BrandBusinessImpl implements IBrandBusiness {

	private static IBrandBusiness instance;

	private BrandBusinessImpl() {
		super();
	}

	public static synchronized IBrandBusiness getInstance() {
		if (instance == null) {
			instance = new BrandBusinessImpl();
		}

		return instance;
	}

	@Override()
	public List<Brand> findAll() throws CarSharingBusinessException {
		try {
			return BrandDAOImpl.getInstance().findAll();
		} catch (Exception e) {
			throw new CarSharingBusinessException(
					String.format(
							"Clase: %s, método %s. Se ha producido un error inesperado "
									+ "al tratar de obtener todas las marcas disponibles.\n%s",
							BrandBusinessImpl.class.getSimpleName(),
							"findAll()", e));
		}
	}

	@Override()
	public Brand insert(Brand brand) throws CarSharingBusinessException {
		try {
			if (null == brand || null == brand.getBrand()
					|| brand.getBrand().trim().isEmpty()) {
				throw new CarSharingBusinessException(
						String.format(
								"Clase: %s, método %s. El parámetro brand (%s) no "
										+ "puede ser nulo, o su atributo brand (%s) no puede "
										+ "ser ni nulo ni vacío.\n%s",
								BrandBusinessImpl.class.getSimpleName(),
								"insert()", Brand.class.getSimpleName(),
								String.class.getSimpleName(), brand.toString()));
			} else if (null != this.find(brand.getBrand().toUpperCase())) {

				return null;
			} else {

				return BrandDAOImpl.getInstance().insert(brand);
			}
		} catch (Exception e) {
			throw new CarSharingBusinessException(String.format(
					"Clase: %s, método %s. Se ha producido un error inesperado "
							+ "al tratar de insertar una marca.\n%s",
					BrandBusinessImpl.class.getSimpleName(), "insert()", e));
		}
	}

	@Override()
	public Brand find(String brand) throws CarSharingBusinessException,
			CarSharingTechnicalException {
		Brand b = null;
		try {
			if (null == brand || brand.trim().isEmpty()) {
				throw new CarSharingBusinessException(String.format(
						"Clase: %s, método %s. El parámetro brand (%s) no "
								+ "puede ser nulo ni vacío.",
						BrandBusinessImpl.class.getSimpleName(), "find()",
						String.class.getSimpleName()));
			} else {
				b = BrandDAOImpl.getInstance().find(brand);
			}
		} catch (Exception e) {
			throw new CarSharingBusinessException(String.format(
					"Clase: %s, método %s. Se ha producido un error inesperado "
							+ "al tratar de buscar una marca.\n%s",
					BrandBusinessImpl.class.getSimpleName(), "find()", e));
		}

		return b;
	}
}