package log;

/**
 * Write the logs to the console.</p>
 * 
 * @author zwei@uvic.ca
 */
public class ConsoleLogger extends AbstractLogger {
	
	public ConsoleLogger() {
		super(ELoggerType.CONSOLE);
	}

	@Override
	public void open() {
		/* do nothing */
	}

	@Override
	public void write(String s) {
		System.out.println(s);
	}

	@Override
	public void write(String fmt, Object... objects) {
		System.out.printf(fmt, objects);
		System.out.printf("\n");
	}
	
	@Override
	public void close() {
		/* do nothing */
	}
}
