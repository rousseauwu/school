package log;

/**
 * Interface for logger implementations used in the simulator.</p>
 * 
 * @author zwei@uvic.ca
 */
public interface ILogger {
	
	/**
	 * Open the logger.
	 */
	public void open();
	
	/**
	 * Write to the logger.
	 * @param s the log entry to write
	 */
	public void write(String s);
	
	/**
	 * Write to the logger using a formatter.
	 * @param fmt formatter for the log entry
	 * @param objects used with the formatter
	 */
	public void write(String fmt, Object... objects);
	
	/**
	 * Close the logger.
	 */
	public void close();
}
