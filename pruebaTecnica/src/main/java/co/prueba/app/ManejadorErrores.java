package co.prueba.app;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.prueba.app.model.dto.ErrorGenerico;

public class ManejadorErrores {

	private static final Logger logger = LoggerFactory.getLogger(ErrorGenerico.class);

	public static void logError(ErrorGenerico er) {
		logger.error("Status: " + er.getHttpStatus() + "\nMessage: " + er.getMessage() + "\nCode: " + er.getCode()
				+ "\nBackendMessage: " + er.getBackendMessage());
	}

	public static void logError(ErrorGenerico er, Class classe) {

		Logger loggerGenerico = LoggerFactory.getLogger(classe);
		loggerGenerico.error("-Status: " + er.getHttpStatus() + "-Message: " + er.getMessage() + "-Code: "
				+ er.getCode() + "-BackendMessage: " + er.getBackendMessage());
	}

	public static void logWarning(String msg, Class classe) {
		Logger loggerGenerico = LoggerFactory.getLogger(classe);
		loggerGenerico.warn("-Mensage: " + msg + " -TimeStamp: " + new Date().toString());
	}

	public static void logDebug(String msg, Class classe) {
		Logger loggerGenerico = LoggerFactory.getLogger(classe);
		loggerGenerico.debug("-Mensage: " + msg + " -TimeStamp: " + new Date().toString());
	}

	public static void logInfo(String msg, Class classe) {
		Logger loggerGenerico = LoggerFactory.getLogger(classe);
		loggerGenerico.info("-Mensage: " + msg + " -TimeStamp: " + new Date().toString());

	}

}
