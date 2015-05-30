package co.edu.udea.carsharing.persistence.dao.impl;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import co.edu.udea.carsharing.model.entities.Brand;
import co.edu.udea.carsharing.model.entities.Car;
import co.edu.udea.carsharing.model.entities.Comment;
import co.edu.udea.carsharing.model.entities.Event;
import co.edu.udea.carsharing.model.entities.Site;
import co.edu.udea.carsharing.model.entities.User;
import co.edu.udea.carsharing.persistence.dao.exception.CarSharingDAOException;
import co.edu.udea.carsharing.persistence.exception.CarSharingPersistenceBusinessException;
import co.edu.udea.carsharing.technical.exception.CarSharingTechnicalException;

public class EventDAOImplTest {

	@Test
	public void testFindAll() throws CarSharingDAOException,
			CarSharingTechnicalException {
		List<Event> events = new ArrayList<Event>();

		events = EventDAOImpl.getInstance().findAll();

		assertTrue(events.size() >= 0);
	}

	@Test
	public void testInsert() throws CarSharingDAOException,
			CarSharingPersistenceBusinessException,
			CarSharingTechnicalException {
		Brand brand = new Brand("CHEVROLET");
		List<Car> cars = new ArrayList<Car>();
		Car car = new Car("Blanco", "hil456", brand, "2014", 4);
		cars.add(car);

		User author = new User("Migue Ángel", "Ossa Ruiz",
				"miguelcold8@gmail.com");
		Site source = new Site("latitud1", "longitud1", "U de A");
		Site target = new Site("latitud2", "longitud2", "U de A");

		List<Comment> comments = new ArrayList<Comment>();
		comments.add(new Comment(author, "Comentario 1", new Date()));
		comments.add(new Comment(author, "Comentario 2", new Date()));

		List<User> partners = new ArrayList<User>();
		partners.add(new User("Partner 1", "Partner 1", "partner1@gmail.com"));
		partners.add(new User("Partner 2", "Partner 2", "partner2@gmail.com"));

		Event event = new Event(new Date(), new Date(), author, car, source,
				target, 1000);
		event.setComments(comments);
		event.setPartners(partners);

		event = EventDAOImpl.getInstance().insert(event);

		assertTrue(null != event);
	}

	@Test
	public void testFind() throws CarSharingDAOException,
			CarSharingPersistenceBusinessException,
			CarSharingTechnicalException {
		Event event = new Event();
		String idEventFind = "55575f6aba3ed37bfe2d6431";

		event = EventDAOImpl.getInstance().find(idEventFind);

		assertTrue(event != null && event.getAuthor() != null);
	}

	@Test
	public void testInsertComment() throws CarSharingDAOException,
			CarSharingPersistenceBusinessException,
			CarSharingTechnicalException {
		String id = "55575f6aba3ed37bfe2d6431";

		User author = new User("Nombre Comentario 4", "Apellidos Comentario 4",
				"test4@gmail.com");
		Comment comment = new Comment(author, "Comentario 4", new Date());

		Event event = EventDAOImpl.getInstance().insertComment(comment, id);

		assertTrue(event != null);
	}

	@Test
	public void testJoin() throws CarSharingDAOException,
			CarSharingPersistenceBusinessException,
			CarSharingTechnicalException {
		String idEventJoin = "55575f6aba3ed37bfe2d6431";
		Event event;

		User partner = new User("New Partner", "New Partner",
				"newpartner@gmail.com");

		event = EventDAOImpl.getInstance().join(partner, idEventJoin);

		assertTrue(event != null);
	}
}
