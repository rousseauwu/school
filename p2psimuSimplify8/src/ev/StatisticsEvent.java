package ev;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import Model.Segment;
import cfg.Config;
import mainfunc.Controller;
import mainfunc.Node;

public class StatisticsEvent extends AbstractEvent {

	public List<ArrayList<Node>> nodeList;
	public List<Segment> segmentList;
	public Map<String, Integer> pcCount;
	public Map<String, Integer> moCount;

	public Map<String, Integer> lastPcCount;
	public Map<String, Integer> lastMoCount;

	public Map<String, Integer> needSegMap;
	public int segmentSize;
	private Controller controller;

	public StatisticsEvent(List<Segment> segmentList, Map<String, Integer> pcCount, Map<String, Integer> moCount,
			int segmentSize,Controller controller) {
		super(EEventType.MY_EV);
		this.segmentList = segmentList;
		this.pcCount = pcCount;
		this.moCount = moCount;
		this.segmentSize = segmentSize;
		this.needSegMap = new HashMap<String, Integer>();
		this.controller = controller;
	}

	@Override
	public void doTask(long timeNow) {
		lastPcCount = (Map<String, Integer>) ((HashMap<String, Integer>) pcCount).clone();
		lastMoCount = (Map<String, Integer>) ((HashMap<String, Integer>) moCount).clone();
		int needNum = 0;
		for (Segment segment : segmentList) {
			List<Node> pc = new ArrayList<Node>();
			List<Node> mo = new ArrayList<Node>();
			Map<String, Node> nodeMap = segment.getNodeMap();
			for (Map.Entry<String, Node> entry : nodeMap.entrySet()) {
				Node node = entry.getValue();
				if (node != null) {
					if (node.getNodeType().equals("PC")) {
						pc.add(node);
					} else {
						mo.add(node);
					}
				}
			}
			String pclist = "";
			String molist = "";
			Iterator<Node> it1 = pc.iterator();
			while (it1.hasNext()) {
				pclist += String.valueOf(it1.next().getNodeId());
				pclist += ",";
			}
			Iterator<Node> it2 = mo.iterator();
			while (it2.hasNext()) {
				molist += String.valueOf(it2.next().getNodeId());
				molist += ",";
			}
//			System.out.println("PC:" + segment.getSegmentId() + "###" + pclist);
//			System.out.println("mo:" + segment.getSegmentId() + "###" + molist);

//			System.out.println("#"+pc.size()+mo.size());
//			System.out.println("#"+pc.size());
			pcCount.put(segment.getSegmentId(), pc.size());
			moCount.put(segment.getSegmentId(), mo.size());

			int needCount = pc.size() + mo.size();
			int existCount = lastPcCount.get(segment.getSegmentId()) + lastMoCount.get(segment.getSegmentId());
			if(needCount-existCount-lastPcCount.get(segment.getSegmentId())>0) {
//				needNum += needCount - existCount - (lastPcCount.get(segment.getSegmentId())+lastMoCount.get(segment.getSegmentId()));
				int a = needCount - existCount-lastPcCount.get(segment.getSegmentId());
				needNum += (needCount - existCount-lastPcCount.get(segment.getSegmentId()));
			}
		}

//		System.out.println("server's bandwidth consumption:" + needNum * segmentSize + "k/s.");
		System.out.println(needNum * segmentSize);
//		System.out.println(controller.getTimeNow());
	}

}
