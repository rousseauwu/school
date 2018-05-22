package mainfunc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import Model.Segment;
import ev.ContWatchingEvent;
import ev.IEvent;
import ev.MyEvent;

public class Node {
	private static int numOfNode = 0;
	private int totalNumOfSeg = 20;
	private static long segDuration = 60000; // miliseconds

	private String nodeId;
	private int uploadBW;
	private int watchingSeg;
	private String nodeType = "PC"; // PC/mobile

	private Controller controller;

	private List<Segment> cache;
	private String inputType = "seq";

	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public int getTotalNumOfSeg() {
		return totalNumOfSeg;
	}

	public void setTotalNumOfSeg(int totalNumOfSeg) {
		this.totalNumOfSeg = totalNumOfSeg;
	}

	public static long getSegDuration() {
		return segDuration;
	}

	public static void setSegDuration(long segDuration) {
		Node.segDuration = segDuration;
	}

	public static int getNumOfNode() {
		return numOfNode;
	}

	public static void setNumOfNode(int numOfNode) {
		Node.numOfNode = numOfNode;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public int getUploadBW() {
		return uploadBW;
	}

	public void setUploadBW(int uploadBW) {
		this.uploadBW = uploadBW;
	}

	public int getWatchingSeg() {
		return watchingSeg;
	}

	public void setWatchingSeg(int watchingSeg) {
		this.watchingSeg = watchingSeg;
	}

	public Node(int uploadBW, int watchingSeg, String nodeType, Controller controller) {
		super();
		this.nodeId = "101";
		this.uploadBW = uploadBW;
		this.watchingSeg = watchingSeg;
		this.nodeType = nodeType;
		this.controller = controller;
		Node.setNumOfNode(Node.getNumOfNode() + 1);
		this.cache = new ArrayList<Segment>();
	}

	public Node(int uploadBW, int watchingSeg, String nodeType, Controller controller, String nodeId) {
		super();
		this.nodeId = nodeId;
		this.uploadBW = uploadBW;
		this.watchingSeg = watchingSeg;
		this.nodeType = nodeType;
		this.controller = controller;
		this.cache = new ArrayList<Segment>();
	}

	public Node(int uploadBW, int watchingSeg, String nodeType, Controller controller, String nodeId,
			String inputType) {
		super();
		this.nodeId = nodeId;
		this.uploadBW = uploadBW;
		this.watchingSeg = watchingSeg;
		this.nodeType = nodeType;
		this.controller = controller;
		this.cache = new ArrayList<Segment>();
		this.inputType = inputType;
	}

	@Override
	public String toString() {
		return "Node [nodeId=" + nodeId + ", uploadBW=" + uploadBW + ", watchingSeg=" + watchingSeg + ", nodeType="
				+ nodeType + "]";
	}

	public void startWathing(int startseg) {
		this.watchingSeg = startseg;
		cache.add(new Segment(String.valueOf(watchingSeg)));
		cacheChange(String.valueOf(watchingSeg), true);
		// int nextWathingSeg = this.watchingSeg + 1;
		IEvent contWatchingEvent = new ContWatchingEvent(this);
		this.controller.scheduleEvent(segDuration, contWatchingEvent);
	}

	private int cacheLimit = 7;

	public void continueWatching() {
		// System.out.println("nodeid#" + this.getNodeId() + " Finished Watching
		// Segment" + this.watchingSeg + ".");
		if (this.watchingSeg >= this.getTotalNumOfSeg() - 1) {
			// System.out.println("Reach the end of the Movie!");
			// 清理缓存
			// while(cache.size()>0) {
			// Segment segFirst = cache.get(0);
			// cache.remove(0);
			// cacheChange(segFirst.getSegmentId(), false);
			// }
		} else {
			this.watchingSeg++;
//			 fifo(watchingSeg);
			waterleveling(watchingSeg);
			// System.out.println("Continue Watching Segment " + this.watchingSeg + ".");
			IEvent contWatchingEvent = new ContWatchingEvent(this);
			this.controller.scheduleEvent(segDuration, contWatchingEvent);
		}
	}

	public void fifo(int watchingSeg) {
		if (cache.size() == cacheLimit) {
			// Segment segLast = cache.get(cache.size() - 1);
			// int targetSegId = Integer.parseInt(segLast.getSegmentId()) + 1;
			Segment segFirst = cache.get(0);
			cache.remove(0);
			cacheChange(segFirst.getSegmentId(), false);
			cache.add(new Segment(String.valueOf(watchingSeg)));
			cacheChange(String.valueOf(watchingSeg), true);
		} else {
			cache.add(new Segment(String.valueOf(watchingSeg)));
			cacheChange(String.valueOf(watchingSeg), true);
		}
	}

	public void waterleveling(int watchingSeg) {
		if (cache.size() == cacheLimit) {
			int removeIndex = findMax();
			cacheChange(cache.get(removeIndex).getSegmentId(), false);
			cache.remove(removeIndex);
			cache.add(new Segment(String.valueOf(watchingSeg)));
			cacheChange(String.valueOf(watchingSeg), true);
		} else {
			cache.add(new Segment(String.valueOf(watchingSeg)));
			cacheChange(String.valueOf(watchingSeg), true);
		}
	}

	public int findMax() {
		int maxCount = 0;
		int minId = 0;
		int minIndex = 0;
		Map<String, Integer> pcCount = this.getController().getPcCount();
		Map<String, Integer> moCount = this.getController().getMoCount();
		for (int i = 0; i < cache.size(); i++) {
			int comId = Integer.parseInt(cache.get(i).getSegmentId());
			int count = pcCount.get(cache.get(i).getSegmentId()) + moCount.get(cache.get(i).getSegmentId());
			if (count > maxCount) {
				maxCount = count;
				minId = comId;
				minIndex = i;
			} else if (count == maxCount && minId > comId) {
				minId = comId;
				minIndex = i;
			}
		}
		return minIndex;
	}

	public void cacheChange(String segmentId, boolean action) {
		List<Segment> seglist = this.getController().segmentList;
		Segment segment = null;
		for (Segment seg : seglist) {
			if (seg.getSegmentId().equals(segmentId)) {
				segment = seg;
				break;
			}
		}
		if (segment != null) {
			Map<String, Node> nodeMap = segment.getNodeMap();
			if (action == true) {
				nodeMap.put(this.nodeId, this);
			} else {
				nodeMap.remove(this.nodeId);
			}
		}
	}

}
