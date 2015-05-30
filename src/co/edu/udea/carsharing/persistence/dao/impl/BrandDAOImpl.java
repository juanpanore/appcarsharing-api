package co.edu.udea.carsharing.persistence.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import co.edu.udea.carsharing.model.entities.Brand;
import co.edu.udea.carsharing.persistence.connection.MongoDBConnector;
import co.edu.udea.carsharing.persistence.dao.IBrandDAO;
import co.edu.udea.carsharing.persistence.dao.exception.CarSharingDAOException;
import co.edu.udea.carsharing.persistence.exception.CarSharingPersistenceBusinessException;
import co.edu.udea.carsharing.technical.exception.CarSharingTechnicalException;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

public class BrandDAOImpl implements IBrandDAO {

	private static final String BRANDS_COLLECTION_NAME = "Brands";
	private static final String ID = "_id";

	private static IBrandDAO instance;
	private DBCollection collection;

	private BrandDAOImpl() throws CarSharingTechnicalException {
		this.collection = MongoDBConnector.connect(BRANDS_COLLECTION_NAME);
	}

	public static synchronized IBrandDAO getInstance()
			throws CarSharingTechnicalException {
		if (null == instance) {
			instance = new BrandDAOImpl();
		}

		return instance;
	}

	@Override()
	public List<Brand> findAll() throws CarSharingDAOException {
		try {
			List<Brand> brands = new ArrayList<Brand>();

			DBCursor dbCursor = this.collection.find();
			for (DBObject dbObject : dbCursor) {
				brands.add(Brand.entityFromDBObject(dbObject));
			}

			return brands;
		} catch (Exception e) {
			throw new CarSharingDAOException(
					String.format(
							"Clase %s: m�todo: %s. Error mientras se obten�an todos objetos %s.\n%s",
							BrandDAOImpl.class.getSimpleName(), "findAll()",
							Brand.class.getSimpleName(), e));
		}
	}

	@Override()
	public Brand insert(Brand brand) throws CarSharingDAOException,
			CarSharingPersistenceBusinessException {
		try {
			if (brand != null) {
				BasicDBObject dbo = brand.entityToDBObject();
				WriteResult wr = this.collection.insert(dbo);

				ObjectId id = (ObjectId) dbo.get(ID);
				DBObject dbObject = collection.findOne(id);

				return (null == dbObject && wr.getN() == 0) ? null : Brand
						.entityFromDBObject(dbObject);
			} else {
				throw new CarSharingPersistenceBusinessException(
						String.format(
								"Clase %s: m�todo: %s. El par�metro brand de tipo %s no puede ser nulo.",
								BrandDAOImpl.class.getSimpleName(), "insert()",
								Brand.class.getSimpleName()));
			}
		} catch (Exception e) {
			throw new CarSharingDAOException(
					String.format(
							"Clase %s: Se present� un error inesperado mientras se ejecutaba el m�todo %s.\n%s",
							BrandDAOImpl.class.getSimpleName(), "insert()", e));
		}

	}

	@Override()
	public Brand find(String brand) throws CarSharingDAOException,
			CarSharingPersistenceBusinessException {
		try {
			if (null == brand || brand.trim().isEmpty()) {
				throw new CarSharingPersistenceBusinessException(String.format(
						"Clase %s: método %s. El parámetro brand de "
								+ "tipo %s no puede ser nulo ni vacío.",
						BrandDAOImpl.class.getSimpleName(), "find()",
						String.class.getSimpleName()));
			} else {
				Brand b = new Brand(brand);
				DBObject dbo = this.collection.findOne(b.entityToDBObject());

				return (null != dbo) ? Brand.entityFromDBObject(dbo) : null;
			}
		} catch (Exception e) {
			throw new CarSharingDAOException(
					String.format(
							"Clase %s: método %s. Ha ocurrido un error inesperado.\n%s.",
							BrandDAOImpl.class.getSimpleName(), "find()", e));
		}
	}
}