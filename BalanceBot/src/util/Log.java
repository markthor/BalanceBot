package util;

import java.util.logging.*;

public class Log {
	private static Logger logger = Logger.getGlobal();
	
	public static void log(String msg) {
		logger.log(Level.INFO, msg);
	}
	
	public static void logDebug(String msg) {
		logger.log(Level.FINE, msg);
	}
	
	public static void setDebugLogging() {
		logger.setLevel(Level.FINE);
	}
	
	public static void setInfoLogging() {
		logger.setLevel(Level.INFO);
	}
}
