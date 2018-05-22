package log;

/**
 * Enumeration for available ILogger implementations.</p>
 * 
 * @author zwei@uvic.ca
 */
public enum ELoggerType {
	FILE {
		@Override
		public ILogger getNewLogger(String logName) {
			return new FileLogger(logName);
		}
	},
	CONSOLE {
		@Override
		public ILogger getNewLogger(String logName) {
			return new ConsoleLogger();
		}
	};
	
	abstract public ILogger getNewLogger(String logName);
}
