package co.edu.udea.carsharing.ws.rest.util;

import co.edu.udea.carsharing.model.entities.Brand;
import co.edu.udea.carsharing.model.entities.Car;
import co.edu.udea.carsharing.model.entities.Comment;
import co.edu.udea.carsharing.model.entities.Event;
import co.edu.udea.carsharing.model.entities.Site;
import co.edu.udea.carsharing.model.entities.User;

public class WSUtil {

	public static boolean validateEvent(Event event) {
		if (event == null || event.getAuthor() == null
				|| !validateAuthor(event.getAuthor()) || event.getCar() == null
				|| !validateCar(event.getCar())
				|| event.getCreateDate() == null
				|| event.getEventDate() == null || event.getSource() == null
				|| !validateSite(event.getSource())
				|| event.getTarget() == null
				|| !validateSite(event.getTarget()) || event.getValue() <= 0.0) {

			return false;
		}

		return true;
	}

	public static boolean validateSite(Site site) {
		if (site == null || site.getDescription() == null
				|| site.getDescription().trim().isEmpty()
				|| site.getLatitude() == null
				|| site.getLatitude().trim().isEmpty()
				|| site.getLongitude() == null
				|| site.getLongitude().trim().isEmpty()) {

			return false;
		}

		return true;
	}

	public static boolean validateCar(Car car) {
		if (car == null || car.getBrand() == null
				|| !validateBrand(car.getBrand()) || car.getCapacity() <= 0
				|| car.getCarriagePlate() == null
				|| car.getCarriagePlate().trim().isEmpty()
				|| car.getModel() == null || car.getModel().trim().isEmpty()
				|| car.getColor() == null || car.getColor().trim().isEmpty()) {

			return false;
		}

		return true;
	}

	public static boolean validateAuthor(User author) {
		if (author == null || author.getEmail() == null
				|| author.getEmail().trim().isEmpty()
				|| author.getLastName() == null
				|| author.getLastName().trim().isEmpty()
				|| author.getName() == null
				|| author.getName().trim().isEmpty()) {

			return false;
		}

		return true;
	}

	public static boolean validateUser(User user) {
		if (!validateAuthor(user) || user.getBirthDate() == null
				|| user.getPassword() == null
				|| user.getPassword().trim().isEmpty()) {

			return false;
		}

		return true;
	}

	public static boolean validateBrand(Brand brand) {
		if (brand == null || brand.getBrand() == null
				|| brand.getBrand().trim().isEmpty()) {

			return false;
		}

		return true;
	}

	public static boolean validateComment(Comment comment) {
		if (comment == null || comment.getMessage() == null
				|| comment.getMessage().trim().isEmpty()
				|| !validateAuthor(comment.getAuthor())
				|| comment.getCreateDate() == null) {

			return false;
		}

		return true;
	}
}