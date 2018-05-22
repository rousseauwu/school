package log;

import java.util.HashMap;
import java.util.Map;

/**
 * Manage all loggers used in the simulator.</p>
 * 
 * @author zwei@uvic.ca
 */
public class LoggerManager {
	private static LoggerManager instance = new LoggerManager();
	
	public static LoggerManager getInstance() {
		return instance;
	}
	
	private Map<String, ILogger> loggers;
	
	private LoggerManager() {
		loggers = new HashMap<String, ILogger>();
	}
	
	public void addLogger(String logName, ELoggerType type) {
		ILogger logger = type.getNewLogger(logName);
		logger.open();
		loggers.put(logName, logger);
	}
	
	public ILogger getLogger(String logName) {
		return loggers.get(logName);
	}
	
	public void closeLogger(String logName) {
		ILogger logger = loggers.get(logName);
		logger.close();
		loggers.remove(logName);
	}
	
	public void closeAll() {
		for (ILogger logger : loggers.values()) {
			logger.close();
		}
	}
}
