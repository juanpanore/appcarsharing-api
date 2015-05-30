package co.edu.udea.carsharing.business.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.carsharing.business.IUserBusiness;
import co.edu.udea.carsharing.business.exception.CarSharingBusinessException;
import co.edu.udea.carsharing.model.entities.Car;
import co.edu.udea.carsharing.model.entities.User;
import co.edu.udea.carsharing.persistence.dao.impl.UserDAOImpl;

public class UserBusinessImpl implements IUserBusiness {

	private static IUserBusiness instance;

	private UserBusinessImpl() {
		super();
	}

	public static synchronized IUserBusiness getInstance() {
		if (instance == null) {
			instance = new UserBusinessImpl();
		}

		return instance;
	}

	@Override()
	public User findByEmailAndPassword(String email, String password)
			throws CarSharingBusinessException {
		try {
			if (null == email || email.trim().isEmpty() || null == password
					|| password.trim().isEmpty()) {
				throw new CarSharingBusinessException(
						String.format(
								"Clase %s: método %s. Los parámetros email y password, "
										+ "ambos tipo %s, no pueden ser ni nulos ni vacíos."
										+ "\n%s\n%s",
								UserBusinessImpl.class.getSimpleName(),
								"findByEmailAndPassword()",
								String.class.getSimpleName(), email.toString(),
								password.toString()));
			} else {

				return UserDAOImpl.getInstance().findByEmailAndPassword(email,
						password);
			}
		} catch (Exception e) {
			throw new CarSharingBusinessException(String.format(
					"Clase %s: método %s. Se ha producido un error al tratar de buscar "
							+ "un usuario por email y password.\n%s",
					UserBusinessImpl.class.getSimpleName(),
					"findByEmailAndPassword()", e));
		}
	}

	@Override()
	public User findByEmail(String email) throws CarSharingBusinessException {
		try {
			if (null == email || email.trim().isEmpty()) {
				throw new CarSharingBusinessException(String.format(
						"Clase %s: método %s. El parámetro email de tipo %s no "
								+ "puede ser nulo ni vacío.\n%s",
						UserBusinessImpl.class.getSimpleName(),
						"findByEmail()", String.class.getSimpleName(),
						email.toString()));
			} else {

				return UserDAOImpl.getInstance().findByEmail(email);
			}
		} catch (Exception e) {
			throw new CarSharingBusinessException(String.format(
					"Clase %s: método %s. Se ha producido un error inesperado "
							+ "al tratar de buscar un usuario por email.\n%s",
					UserBusinessImpl.class.getSimpleName(), "findByEmail()", e));
		}
	}

	@Override()
	public User insert(User user) throws CarSharingBusinessException {
		try {
			if (!validateUser(user)) {
				throw new CarSharingBusinessException(String.format(
						"Clase %s: método %s. Alguno de los atributos obligatorios de "
								+ "usert (%s) es nulo o vacío. %s",
						UserBusinessImpl.class.getSimpleName(), "insert()",
						User.class.getSimpleName(), user.toString()));
			} else if (null == this.findByEmail(user.getEmail())) {

				return UserDAOImpl.getInstance().insert(user);
			} else {

				return null;
			}
		} catch (Exception e) {
			throw new CarSharingBusinessException(String.format(
					"Clase %s: método %s. Se ha producido un error inesperado "
							+ "al tratar de insertar un nuevo usuario.\n%s",
					UserBusinessImpl.class.getSimpleName(), "insert()", e));
		}
	}

	@Override()
	public User addCar(String email, Car car)
			throws CarSharingBusinessException {
		try {
			if (null == email || email.trim().isEmpty() || !validateCar(car)) {
				throw new CarSharingBusinessException(
						String.format(
								"Clase %s: método %s. El email (%s) no puede ser "
										+ "nulo ni vacío, o alguno de los atributos "
										+ "obligatorios de car (%s) es vacío  o nulo.\n%s\n%s",
								UserBusinessImpl.class.getSimpleName(),
								"addCar()", String.class.getSimpleName(),
								Car.class.getSimpleName(), email.toString(),
								car.toString()));
			} else {
				User user = this.findByEmail(email);
				if (user != null) {
					if (user.getCars() == null) {
						user.setCars(new ArrayList<Car>());
					}

					car.setCarriagePlate(car.getCarriagePlate().toUpperCase());
					user.getCars().add(car);
					return UserDAOImpl.getInstance().update(user);
				}

				return null;
			}
		} catch (Exception e) {
			throw new CarSharingBusinessException(
					String.format(
							"Clase %s: método %s. Se ha producido un error inesperado "
									+ "al tratar de agregar un carro al listado de un usuario."
									+ "\n%s",
							UserBusinessImpl.class.getSimpleName(), "addCar()",
							e));
		}
	}

	@Override()
	public List<Car> getCarsByUser(String email)
			throws CarSharingBusinessException {
		try {
			if (null == email || email.trim().isEmpty()) {
				throw new CarSharingBusinessException(String.format(
						"Clase %s: método %s. El parámetro email (%s) no puede "
								+ "ser nulo ni vacío.\n%s",
						UserBusinessImpl.class.getSimpleName(),
						"getCarsByUser()", String.class.getSimpleName(),
						email.toString()));
			} else {

				return UserDAOImpl.getInstance().getCarsByUser(email);
			}
		} catch (Exception e) {
			throw new CarSharingBusinessException(
					String.format(
							"Clase %s: método %s. Se ha producido un error inesperado "
									+ "al tratar de obtener el listado de carros de un usuario.\n%s",
							UserBusinessImpl.class.getSimpleName(),
							"getCarsByUser()", e));
		}
	}

	private boolean validateUser(User user) {
		if (null != user && null != user.getBirthDate()
				&& null != user.getEmail() && !user.getEmail().trim().isEmpty()
				&& null != user.getLastName()
				&& !user.getLastName().trim().isEmpty()
				&& null != user.getName() && !user.getName().trim().isEmpty()
				&& null != user.getPassword()
				&& !user.getPassword().trim().isEmpty()) {

			return true;
		}

		return false;
	}

	private boolean validateCar(Car car) {
		if (null != car && null != car.getBrand()
				&& !car.getBrand().getBrand().trim().isEmpty()
				&& !car.getCarriagePlate().trim().isEmpty()
				&& null != car.getColor() && !car.getColor().trim().isEmpty()
				&& null != car.getModel() && !car.getModel().trim().isEmpty()
				&& car.getCapacity() > 0) {

			return true;
		}

		return false;
	}
}
