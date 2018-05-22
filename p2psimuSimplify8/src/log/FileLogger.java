package log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Write the logs to a file.</p>
 * 
 * @author zwei@uvic.ca
 */
public class FileLogger extends AbstractLogger {
	public static final String LOG_DIR = "log";
	
	public static long timestamp = System.currentTimeMillis();
	
	private String logName;
	
	private File file;
	private FileWriter fw;
	private BufferedWriter bw;
	
	public FileLogger(String logName) {
		super(ELoggerType.FILE);
		this.logName = logName;
	}
	
	@Override
	public void open() {
		String fileName = String.format("%s/%s%d.log", LOG_DIR, logName, timestamp);
		
		try {
			file = new File(fileName);
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
		} catch (IOException ioe) {
			System.err.printf("Failed to open log file : %s\n", logName);
			System.err.println(ioe.getLocalizedMessage());
		}
	}
	
	@Override
	public void write(String s) {
		try {
			bw.write(s);
			bw.write("\n");
			bw.flush();
		} catch (IOException ioe) {
			System.err.printf("Failed to write to log file : %s\n", logName);
			System.err.println(ioe.getLocalizedMessage());
		}
	}
	
	@Override
	public void write(String fmt, Object... objects) {
		String s = String.format(fmt, objects);
		write(s);
	}
	
	@Override
	public void close() {
		try {
			bw.close();
			fw.close();
		} catch (IOException ioe) {
			/* best-effort */
		}
	}
}
