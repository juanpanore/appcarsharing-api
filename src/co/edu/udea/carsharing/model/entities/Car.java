package co.edu.udea.carsharing.model.entities;

import java.io.Serializable;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class Car implements Serializable {

	private static final long serialVersionUID = 599539687173957722L;

	private static String COLOR = "color";
	private static String CARRIAGE_PLATE = "carriagePlate";
	private static String BRAND = "brand";
	private static String MODEL = "model";
	private static String CAPACITY = "capacity";

	private String color;
	private String carriagePlate;
	private Brand brand;
	private String model;
	private int capacity;

	public Car() {
		super();
	}

	public Car(String carriagePlate, Brand brand, String model, int capacity) {
		super();
		this.carriagePlate = carriagePlate;
		this.brand = brand;
		this.model = model;
		this.capacity = capacity;
	}

	public Car(String color, String carriagePlate, Brand brand, String model,
			int capacity) {
		super();
		this.color = color;
		this.carriagePlate = carriagePlate;
		this.brand = brand;
		this.model = model;
		this.capacity = capacity;
	}

	public static Car entityFromDBObject(DBObject dbObject) {
		Car car = null;

		if (dbObject != null) {
			car = new Car();

			if (dbObject.containsField(COLOR)) {
				car.setColor(((String) dbObject.get(COLOR)).trim());
			}

			if (dbObject.containsField(CARRIAGE_PLATE)) {
				car.setCarriagePlate(((String) dbObject.get(CARRIAGE_PLATE))
						.trim());
			}

			if (dbObject.containsField(BRAND)) {
				car.setBrand(Brand.entityFromDBObject((DBObject) dbObject
						.get(BRAND)));
			}

			if (dbObject.containsField(MODEL)) {
				car.setModel(((String) dbObject.get(MODEL)).trim());
			}

			if (dbObject.containsField(CAPACITY)) {
				car.setCapacity(Integer.parseInt(dbObject.get(CAPACITY)
						.toString().trim()));
			}

		}

		return car;
	}

	public BasicDBObject entityToDBObject() {
		BasicDBObject basicDBObject = new BasicDBObject();

		if (null != this.getColor() && !("").equals(this.getColor().trim())) {
			basicDBObject.put(COLOR, this.getColor().trim());
		}

		if (null != this.getCarriagePlate()
				&& !("").equals(this.getCarriagePlate().trim())) {
			basicDBObject.put(CARRIAGE_PLATE, this.getCarriagePlate().trim());
		}

		if (null != this.getBrand()) {
			basicDBObject.put(BRAND, this.getBrand().entityToDBObject());
		}

		if (null != this.getModel() && !("").equals(this.getModel().trim())) {
			basicDBObject.put(MODEL, this.getModel().trim());
		}

		basicDBObject.put(CAPACITY, this.getCapacity());

		return basicDBObject;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getCarriagePlate() {
		return carriagePlate;
	}

	public void setCarriagePlate(String carriagePlate) {
		this.carriagePlate = carriagePlate;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	@Override
	public String toString() {
		return "Car [color=" + color + ", carriagePlate=" + carriagePlate
				+ ", brand=" + brand + ", model=" + model + ", capacity="
				+ capacity + "]";
	}
}
