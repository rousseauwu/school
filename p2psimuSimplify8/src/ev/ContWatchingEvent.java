package ev;

import cfg.Config;
import log.LoggerManager;
import mainfunc.Node;

public class ContWatchingEvent extends AbstractEvent{

	Node node;
	
	
	public ContWatchingEvent(Node node) {
		super(EEventType.MY_EV);
		this.node = node;
	}
	
	@Override
	public void doTask(long timeNow) {
		this.node.continueWatching();
//		System.out.println("Event "+ EEventType.CONTWATCHING_EV+" happens at time "+timeNow+"." );
		//LoggerManager.getInstance().getLogger(Config.getInstance().getStatsLogName())
		//.write("This sentence goes into the log.");
	}
	
}