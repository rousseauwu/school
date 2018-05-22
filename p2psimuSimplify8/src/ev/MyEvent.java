package ev;

import cfg.Config;
import log.LoggerManager;

public class MyEvent extends AbstractEvent{

	public MyEvent() {
		super(EEventType.MY_EV);
	}
	
	@Override
	public void doTask(long timeNow) {
		System.out.println("Event "+ EEventType.MY_EV+" happens at time "+timeNow+"." );
		LoggerManager.getInstance().getLogger(Config.getInstance().getStatsLogName())
		.write("This sentence goes into the log.");
	}
	
}