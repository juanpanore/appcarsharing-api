package co.edu.udea.carsharing.model.entities;

import java.io.Serializable;
import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class Comment implements Serializable {

	private static final long serialVersionUID = 3823050722730537024L;

	private static String AUTHOR = "author";
	private static String MESSAGE = "message";
	private static String CREATEDATE = "createDate";

	private User author;
	private String message;
	private Date createDate;

	public Comment() {
		super();
	}

	public Comment(User author, String message, Date createDate) {
		super();
		this.author = author;
		this.message = message;
		this.createDate = createDate;
	}

	public static Comment entityFromDBObject(DBObject dbObject) {
		Comment comment = null;

		if (dbObject != null) {
			comment = new Comment();

			if (dbObject.containsField(AUTHOR)) {
				comment.setAuthor(User.entityFromDBObject((DBObject) dbObject
						.get(AUTHOR)));
			}

			if (dbObject.containsField(MESSAGE)) {
				comment.setMessage(((String) dbObject.get(MESSAGE)).trim());
			}

			if (dbObject.containsField(CREATEDATE)) {
				comment.setCreateDate((Date) dbObject.get(CREATEDATE));
			}
		}

		return comment;
	}

	public BasicDBObject entityToDBObject() {
		BasicDBObject basicDBObject = new BasicDBObject();

		if (null != this.getAuthor()) {
			basicDBObject.put(AUTHOR, this.getAuthor().entityToDBObject());
		}

		if (null != this.getMessage() && !("").equals(this.getMessage().trim())) {
			basicDBObject.put(MESSAGE, this.getMessage());
		}

		if (null != this.getCreateDate()) {
			basicDBObject.put(CREATEDATE, this.getCreateDate());
		}

		return basicDBObject;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "Comment [author=" + author + ", message=" + message
				+ ", createDate=" + createDate + "]";
	}
}