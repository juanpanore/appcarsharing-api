package co.edu.udea.carsharing.persistence.dao;

import java.util.List;

import co.edu.udea.carsharing.model.entities.Car;
import co.edu.udea.carsharing.model.entities.User;
import co.edu.udea.carsharing.persistence.dao.exception.CarSharingDAOException;
import co.edu.udea.carsharing.persistence.exception.CarSharingPersistenceBusinessException;

public interface IUserDAO {

	public User findByEmailAndPassword(String email, String password)
			throws CarSharingDAOException,
			CarSharingPersistenceBusinessException;

	public User findByEmail(String email) throws CarSharingDAOException,
			CarSharingPersistenceBusinessException;

	public User insert(User user) throws CarSharingDAOException,
			CarSharingPersistenceBusinessException;

	public User update(User user) throws CarSharingDAOException,
			CarSharingPersistenceBusinessException;

	public List<Car> getCarsByUser(String email) throws CarSharingDAOException;
}