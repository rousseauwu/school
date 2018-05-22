package mainfunc;

import log.ELoggerType;
import log.LoggerManager;
import cfg.Config;

/**
 * This is the main of the simulation.</p>
 * 
 * @author zwei@uvic.ca
 */
public class Main {
	//version string
	public static final String VERSION = "1.4";

	/**
	 * The main method.
	 * 
	 * @param args valid args are: -ns [numSeed], -nl [numLeecher], -np [natPercent], -fs [fileSize],
	 * -bs [blockSize], -sb [seedBW], -pub [pubUpBW], -pdb [pubDownBW], -nub [natUpBW], -ndb [natDownBW],
	 * -ibs [isBiasedSeed], -ibo [isBiasedOU], -bop [biasedOUProb] 
	 */
	public static void main(String[] args) {
		//initialize the configuration
		Config config = Config.getInstance();
		config.initialize(args);
		System.out.println(config);
		
		//initialize the loggers
		LoggerManager logMgr = LoggerManager.getInstance();
		logMgr.addLogger(config.getStatsLogName(), ELoggerType.FILE);
		
		//here we go...
		System.out.println("Simulation in progress...");
		Controller controller = new Controller();
		controller.run();
		
		//done!
		System.out.println("Simulation done!");
		
		//close all logs
		logMgr.closeAll();
	}
}
