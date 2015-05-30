package co.edu.udea.carsharing.business;

import java.util.List;

import co.edu.udea.carsharing.business.exception.CarSharingBusinessException;
import co.edu.udea.carsharing.model.entities.Car;
import co.edu.udea.carsharing.model.entities.User;

public interface IUserBusiness {
	public User findByEmailAndPassword(String email, String password)
			throws CarSharingBusinessException;

	public User findByEmail(String email) throws CarSharingBusinessException;

	public User insert(User user) throws CarSharingBusinessException;

	public User addCar(String email, Car car)
			throws CarSharingBusinessException;

	public List<Car> getCarsByUser(String email)
			throws CarSharingBusinessException;
}