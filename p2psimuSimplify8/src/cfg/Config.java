package cfg;

import java.util.HashMap;
import java.util.Map;

/**
 * This module contains all the parameters that will be used in the simulation.
 * All these parameters are given default value.</p>
 * 
 * We can also reset the values for some of them through the command line.</p>
 * 
 * @author zwei@uvic.ca
 */
public class Config {
	public static Config config = new Config();
	
	public static Config getInstance() {
		return config;
	}
	
	private Config() {}

	//**********the parameters in the simulation**************
	//the run time of the simulation, in ms
	private long runTime=10000000000000l;
	
	//the name of the logs
	private String statsLogName = "statsLog"; //this log records the statistics 
	
	//************----------------------------****************
	
	/**
	 * Initialize the config using command-line arguments. Allow users to use customized
	 * config values rather than the default values.
	 */
	public void initialize(String[] args) {
		try {
			updateValues(parseArgs(args));
		} catch (Exception e) {
			System.err.println("Error parsing command-line arguments.");
			System.err.println("Using default values.");
		}
	}
	
	//parse the command line parameters and feed to a string array
	private Map<String, String> parseArgs(String[] args) throws Exception {
		Map<String, String> argsMap = new HashMap<String, String>();
		
		String tmpKey = null;
		for (String arg : args) {
			if (tmpKey == null) {
				if (arg.startsWith("-")) tmpKey = arg.substring(1);
				else throw new Exception();
			} else {
				if (arg.startsWith("-")) argsMap.put(tmpKey, "true");
				else argsMap.put(tmpKey, arg);
				tmpKey = null;
			}
		}
		
		return argsMap;
	}	
	
	//update the parameters through command line
	private void updateValues(Map<String, String> argsMap) throws Exception {
		for (String key : argsMap.keySet()) {
			String arg = argsMap.get(key);
			
			if (key.equalsIgnoreCase("rt")) {
		    	runTime = Long.valueOf(arg);
			} else if (key.equalsIgnoreCase("ln")) {
				statsLogName = arg;
			}
		}
	}
	
	//******************getters and setters*******************
	public long getRunTime() {
		return runTime;
	}

	public void setRunTime(long runTime) {
		this.runTime = runTime;
	}

	public String getStatsLogName() {
		return statsLogName;
	}

	public void setStatsLogName(String statsLogName) {
		this.statsLogName = statsLogName;
	}

	//-----------------to string------------------//
	@Override
	public String toString() {
		return "Config [runTime=" + runTime + ", statsLogName=" + statsLogName
				+ "]";
	}
	

	
	
}
