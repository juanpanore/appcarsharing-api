package co.edu.udea.carsharing.model.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class Event implements Serializable {

	private static final long serialVersionUID = -6975227503499946780L;

	private static final String ID = "_id";
	private static String CREATEDATE = "createDate";
	private static String EVENTDATE = "eventDate";
	private static String AUTHOR = "author";
	private static String CAR = "car";
	private static String COMMENTS = "comments";
	private static String SOURCE = "source";
	private static String TARGET = "target";
	private static String PARTNERS = "partners";
	private static String VALUE = "value";
	private static String STATE = "state";
	private static String AMOUNTPEOPLE = "amountPeople";

	private String id;
	private Date createDate;
	private Date eventDate;
	private User author;
	private Car car;
	private List<Comment> comments;
	private Site source;
	private Site target;
	private List<User> partners;
	private double value;
	private String state;
	private int amountPeople;

	public Event() {
		super();
	}

	public Event(Date createDate, Date eventDate, User author, Car car,
			Site source, Site target, double value) {
		this.createDate = createDate;
		this.eventDate = eventDate;
		this.author = author;
		this.car = car;
		this.source = source;
		this.target = target;
		this.value = value;
		this.amountPeople = 0;
	}

	public static Event entityFromDBObject(DBObject dbObject) {
		Event event = null;
		if (dbObject != null) {
			event = new Event();

			if (dbObject.containsField(ID)) {
				event.setId(dbObject.get(ID).toString().trim());
			}

			if (dbObject.containsField(CREATEDATE)) {
				event.setCreateDate((Date) dbObject.get(CREATEDATE));
			}

			if (dbObject.containsField(EVENTDATE)) {
				event.setEventDate((Date) dbObject.get(EVENTDATE));
			}

			if (dbObject.containsField(AUTHOR)) {
				event.setAuthor(User.entityFromDBObject((DBObject) dbObject
						.get(AUTHOR)));
			}

			if (dbObject.containsField(CAR)) {
				event.setCar(Car.entityFromDBObject((DBObject) dbObject
						.get(CAR)));
			}

			if (dbObject.containsField(COMMENTS)) {
				BasicDBList basicDBbList = (BasicDBList) dbObject.get(COMMENTS);
				event.setComments(new ArrayList<Comment>());
				for (Object object : basicDBbList) {
					event.getComments().add(
							Comment.entityFromDBObject((BasicDBObject) object));
				}
			}

			if (dbObject.containsField(SOURCE)) {
				event.setSource(Site.entityFromDBObject((DBObject) dbObject
						.get(SOURCE)));
			}

			if (dbObject.containsField(TARGET)) {
				event.setTarget(Site.entityFromDBObject((DBObject) dbObject
						.get(TARGET)));
			}

			if (dbObject.containsField(PARTNERS)) {
				BasicDBList basicDBbList = (BasicDBList) dbObject.get(PARTNERS);
				event.setPartners(new ArrayList<User>());
				for (Object object : basicDBbList) {
					event.getPartners().add(
							User.entityFromDBObject((BasicDBObject) object));
				}
			}

			if (dbObject.containsField(VALUE)) {
				event.setValue(Double.parseDouble(dbObject.get(VALUE)
						.toString().trim()));
			}

			if (dbObject.containsField(STATE)) {
				event.setState(((String) dbObject.get(STATE)).trim());
			}

			if (dbObject.containsField(AMOUNTPEOPLE)) {
				event.setAmountPeople(Integer.parseInt(dbObject
						.get(AMOUNTPEOPLE).toString().trim()));
			}

		}

		return event;
	}

	public BasicDBObject entityToDBObject() {
		BasicDBObject basicDBObject = new BasicDBObject();

		if (null != this.getId() && !("").equals(this.getId().trim())) {
			basicDBObject.put(ID, new ObjectId(this.getId().trim()));
		}

		if (null != this.getCreateDate()) {
			basicDBObject.put(CREATEDATE, this.getCreateDate());
		}

		if (null != this.getEventDate()) {
			basicDBObject.put(EVENTDATE, this.getEventDate());
		}

		if (null != this.getAuthor()) {
			basicDBObject.put(AUTHOR, this.getAuthor().entityToDBObject());
		}

		if (null != this.getCar()) {
			basicDBObject.put(CAR, this.getCar().entityToDBObject());
		}

		if (null != this.getComments() && !this.getComments().isEmpty()) {
			BasicDBList basicDBList = new BasicDBList();

			for (Comment comment : this.getComments()) {
				basicDBList.add(comment.entityToDBObject());
			}

			basicDBObject.put(COMMENTS, basicDBList);
		}

		if (null != this.getSource()) {
			basicDBObject.put(SOURCE, this.getSource().entityToDBObject());
		}

		if (null != this.getTarget()) {
			basicDBObject.put(TARGET, this.getTarget().entityToDBObject());
		}

		if (null != this.getPartners() && !this.getPartners().isEmpty()) {
			BasicDBList basicDBList = new BasicDBList();

			for (User user : this.getPartners()) {
				basicDBList.add(user.entityToDBObject());
			}

			basicDBObject.put(PARTNERS, basicDBList);
		}

		if (0 <= this.getValue()) {
			basicDBObject.put(VALUE, this.getValue());
		}

		if (null != this.getState()) {
			basicDBObject.put(STATE, this.getState().trim());
		}

		if (0 <= this.getAmountPeople()) {
			basicDBObject.put(AMOUNTPEOPLE, this.getAmountPeople());
		}

		return basicDBObject;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Site getSource() {
		return source;
	}

	public void setSource(Site source) {
		this.source = source;
	}

	public Site getTarget() {
		return target;
	}

	public void setTarget(Site target) {
		this.target = target;
	}

	public List<User> getPartners() {
		return partners;
	}

	public void setPartners(List<User> partners) {
		this.partners = partners;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getAmountPeople() {
		return amountPeople;
	}

	public void setAmountPeople(int amountPeople) {
		this.amountPeople = amountPeople;
	}

	@Override
	public String toString() {
		return "Event [id=" + id + ", createDate=" + createDate
				+ ", eventDate=" + eventDate + ", author=" + author + ", car="
				+ car + ", comments=" + comments + ", source=" + source
				+ ", target=" + target + ", partners=" + partners + ", value="
				+ value + ", state=" + state + ", amountPeople=" + amountPeople
				+ "]";
	}
}
