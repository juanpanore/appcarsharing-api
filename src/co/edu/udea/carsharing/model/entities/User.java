package co.edu.udea.carsharing.model.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class User implements Serializable {

	private static final long serialVersionUID = 8155172105840619244L;

	private static String ID = "_id";
	private static String NAME = "name";
	private static String LAST_NAME = "lastName";
	private static String EMAIL = "email";
	private static String PASSWORD = "password";
	private static String FACEBOOK_ID = "facebookId";
	private static String CARS = "cars";
	private static String BIRTHDATE = "birthDate";

	private String id;
	private String name;
	private String lastName;
	private String email;
	private String password;
	private String facebookId;
	private Date birthDate;
	private List<Car> cars;

	public User() {
		super();
	}

	public User(String name, String lastName, String email) {
		super();
		this.name = name;
		this.lastName = lastName;
		this.email = email;
	}

	public User(String name, String lastName, String email, String password,
			Date birthDate) {
		super();
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.birthDate = birthDate;
	}

	public User(String name, String lastName, String email, String password,
			String facebookId, Date birthDate, List<Car> cars) {
		super();
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.facebookId = facebookId;
		this.birthDate = birthDate;
		this.cars = cars;
	}

	public User(String name, String lastName, String email, String facebookId) {
		super();
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.facebookId = facebookId;
	}

	public static User entityFromDBObject(DBObject dbObject) {
		User user = null;

		if (dbObject != null) {
			user = new User();

			if (dbObject.containsField(ID)) {
				user.setId(dbObject.get(ID).toString().trim());
			}

			if (dbObject.containsField(NAME)) {
				user.setName(((String) dbObject.get(NAME)).trim());
			}

			if (dbObject.containsField(LAST_NAME)) {
				user.setLastName(((String) dbObject.get(LAST_NAME)).trim());
			}

			if (dbObject.containsField(EMAIL)) {
				user.setEmail(((String) dbObject.get(EMAIL)).trim());
			}

			if (dbObject.containsField(PASSWORD)) {
				user.setPassword(((String) dbObject.get(PASSWORD)).trim());
			}

			if (dbObject.containsField(FACEBOOK_ID)) {
				user.setFacebookId(((String) dbObject.get(FACEBOOK_ID)).trim());
			}

			if (dbObject.containsField(BIRTHDATE)) {
				user.setBirthDate((Date) dbObject.get(BIRTHDATE));
			}

			if (dbObject.containsField(CARS)) {
				BasicDBList basicDBbList = (BasicDBList) dbObject.get(CARS);
				user.setCars(new ArrayList<Car>());
				for (Object object : basicDBbList) {
					user.getCars().add(
							Car.entityFromDBObject((BasicDBObject) object));
				}
			}
		}

		return user;
	}

	public BasicDBObject entityToDBObject() {
		BasicDBObject basicDBObject = new BasicDBObject();

		if (this.getId() != null && !("").equals(this.getId().trim())) {
			basicDBObject.put(ID, new ObjectId(this.getId().trim()));
		}

		if (null != this.getBirthDate()) {
			basicDBObject.put(BIRTHDATE, this.getBirthDate());
		}

		if (null != this.getEmail() && !("").equals(this.getEmail().trim())) {
			basicDBObject.put(EMAIL, this.getEmail());
		}

		if (null != this.getFacebookId()
				&& !("").equals(this.getFacebookId().trim())) {
			basicDBObject.put(FACEBOOK_ID, this.getFacebookId());
		}

		if (null != this.getLastName()
				&& !("").equals(this.getLastName().trim())) {
			basicDBObject.put(LAST_NAME, this.getLastName());
		}

		if (null != this.getPassword()
				&& !("").equals(this.getPassword().trim())) {
			basicDBObject.put(PASSWORD, this.getPassword());
		}

		if (null != this.getName() && !("").equals(this.getName().trim())) {
			basicDBObject.put(NAME, this.getName());
		}

		if (null != this.getCars() && !this.getCars().isEmpty()) {
			BasicDBList basicDBList = new BasicDBList();
			for (Car car : this.getCars()) {
				basicDBList.add(car.entityToDBObject());
			}

			basicDBObject.put(CARS, basicDBList);
		}

		return basicDBObject;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public List<Car> getCars() {
		return cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", lastName=" + lastName
				+ ", email=" + email + ", password=" + password
				+ ", facebookId=" + facebookId + ", birthDate=" + birthDate
				+ ", cars=" + cars + "]";
	}
}
