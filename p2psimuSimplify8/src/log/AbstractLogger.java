package log;

/**
 * Abstract implementation of the ILogger interface.</p>
 * 
 * @author zwei@uvic.ca
 */
public abstract class AbstractLogger implements ILogger {
	protected ELoggerType type;
	
	public AbstractLogger(ELoggerType type) {
		this.type = type;
	}
	
	public ELoggerType getType() {
		return type;
	}
}
