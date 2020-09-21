package co.prueba.app.model;

public class CompletadoGenerico {

	private String httpStatus;
	private String message;

	public CompletadoGenerico(String httpStatus, String message) {
		super();
		this.httpStatus = httpStatus;
		this.message = message;
	}

	public String getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(String httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
