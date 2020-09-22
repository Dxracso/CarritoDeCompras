package co.prueba.app.model.dto;

public class ErrorGenerico {
	
	private String httpStatus;
	private String message;
	private String code;
	private String backendMessage;
	
	
	public ErrorGenerico(String httpStatus, String message, String code, String backendMessage) {
		super();
		this.httpStatus = httpStatus;
		this.message = message;
		this.code = code;
		this.backendMessage = backendMessage;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getBackendMessage() {
		return backendMessage;
	}
	public void setBackendMessage(String backendMessage) {
		this.backendMessage = backendMessage;
	}

	
}
