package co.edu.udea.carsharing.model.entities;

import java.io.Serializable;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class Site implements Serializable {

	private static final long serialVersionUID = 1L;

	private static String LATITUDE = "latitude";
	private static String LONGITUDE = "longitude";
	private static String DESCRIPTION = "description";

	private String latitude;
	private String longitude;
	private String description;

	public Site() {
		super();
	}

	public Site(String latitude, String longitude, String description) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.description = description;
	}

	public static Site entityFromDBObject(DBObject dbObject) {
		Site site = null;

		if (dbObject != null) {
			site = new Site();

			if (dbObject.containsField(LATITUDE)) {
				site.setLatitude(((String) dbObject.get(LATITUDE)).trim());
			}

			if (dbObject.containsField(LONGITUDE)) {
				site.setLongitude(((String) dbObject.get(LONGITUDE)).trim());
			}

			if (dbObject.containsField(DESCRIPTION)) {
				site.setDescription(((String) dbObject.get(DESCRIPTION)).trim());
			}
		}

		return site;
	}

	public BasicDBObject entityToDBObject() {
		BasicDBObject basicDBObject = new BasicDBObject();

		if (null != this.getLatitude()
				&& !("").equals(this.getLatitude().trim())) {
			basicDBObject.put(LATITUDE, this.getLatitude().trim());
		}

		if (null != this.getLongitude()
				&& !("").equals(this.getLongitude().trim())) {
			basicDBObject.put(LONGITUDE, this.getLongitude().trim());
		}

		if (null != this.getDescription()
				&& !("").equals(this.getDescription().trim())) {
			basicDBObject.put(DESCRIPTION, this.getDescription());
		}

		return basicDBObject;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
