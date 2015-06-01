package co.edu.udea.carsharing.ws.rest.util;

import java.io.Serializable;

public class ResponseMessage implements Serializable {

	private static final long serialVersionUID = -8501236506528907274L;

	private String messageResponse;

	public ResponseMessage(String messageResponse) {
		super();
		this.messageResponse = messageResponse;
	}

	public String getMessageResponse() {
		return messageResponse;
	}

	public void setMessageResponse(String messageResponse) {
		this.messageResponse = messageResponse;
	}
}