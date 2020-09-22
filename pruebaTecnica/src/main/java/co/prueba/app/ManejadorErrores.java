package co.prueba.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.prueba.app.model.dto.ErrorGenerico;

public class ManejadorErrores {
	private static final Logger log = LoggerFactory.getLogger(ErrorGenerico.class);

	public static void logError(ErrorGenerico er) {
		log.error("Status: "+er.getHttpStatus()
				+"\nMessage: "+er.getMessage()
				+"\nCode: "+er.getCode()
				+"\nBackendMessage: "+er.getBackendMessage()
				);
	}

}
