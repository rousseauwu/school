package ev;

import java.util.Random;

import mainfunc.Node;

/**
 * @author wujiahong 用户开始点播事件
 */
public class StartEvent extends AbstractEvent {

	Node node;
	public static Random r = new Random();

	public StartEvent(Node node) {
		super(EEventType.MY_EV);
		this.node = node;
	}

	@Override
	public void doTask(long timeNow) {
		// 随机点播,0,5,10
//		 this.node.startWathing(r.nextInt(3));
		// 从头观看
		int startSeg = random();
		this.node.startWathing(startSeg);
//		this.node.startWathing(0);
//		 System.out.println("nodeid:"+this.node.getNodeId()+" start watching.");
	}

	public int random() {
		//0,6,12
		int startSeg = 6;
		if (this.node.getInputType().equals("vcr")) {
			this.node.setWatchingSeg(startSeg);
			this.node.setTotalNumOfSeg(startSeg + 5);
			return startSeg;
		}
		return 0;
	}

}
