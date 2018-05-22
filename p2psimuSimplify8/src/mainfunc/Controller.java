package mainfunc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import Model.Segment;
import Model.UserGroup;
import queue.EventQueue;
import queue.ListElement;
import log.ILogger;
import log.LoggerManager;
import cfg.Config;
import ev.IEvent;
import ev.MyEvent;
import ev.StartEvent;
import ev.StatisticsEvent;

/**
 * This module is used to define Simulator which is the core of the simulator.
 * </p>
 * 
 * @author zwei@uvic.ca
 */
public class Controller {
	protected static Random random = new Random();

	// the configuration
	protected Config config = Config.getInstance();

	// the loggers
	protected ILogger statsLogger = LoggerManager.getInstance().getLogger(config.getStatsLogName());

	private EventQueue eventQueue = new EventQueue();
	private long timeNow = 0; // the current simulation time, in ms

	public static List<Segment> segmentList = new ArrayList<Segment>();
	public final static int segmentSize = 125;

	public static Map<String, Integer> pcCount = new HashMap<String, Integer>();
	public static Map<String, Integer> moCount = new HashMap<String, Integer>();

	static {
		int totalNumOfSeg = 20;
		for (int i = 0; i < totalNumOfSeg; i++) {
			segmentList.add(new Segment(String.valueOf(i)));
			pcCount.put(String.valueOf(i), 0);
			moCount.put(String.valueOf(i), 0);
		}
	}

	// ************************

	public long getTimeNow() {
		return timeNow;
	}

	public void scheduleEvent(long offset, IEvent obj) {
		eventQueue.insertObject(getTimeNow() + offset, obj);
	}

	public Map<String, Integer> getPcCount() {
		return pcCount;
	}

	public void setPcCount(Map<String, Integer> pcCount) {
		this.pcCount = pcCount;
	}

	public Map<String, Integer> getMoCount() {
		return moCount;
	}

	public void setMoCount(Map<String, Integer> moCount) {
		this.moCount = moCount;
	}

	// run the simulation
	public void run() {
		bwcStatistics();
		newUsers(0.8f);
		while (!eventQueue.isEmpty()) {
			ListElement element = eventQueue.getNextEvent();
			timeNow = eventQueue.getTimeNow();
			// check if the end time is reached
			if (timeNow > config.getRunTime())
				break;
			// do the task in the event queue
			// 执行同一时间点内所有事件
			while (element != null) {
				element.getStore().doTask(timeNow);
				element = element.getNext();
			}
		}
	}

	public void newUsers(float typeRation) {
		int[] userNum = { 10, 20, 50, 100, 10, 24, 50, 100, 20, 200 };
//		String[] inputType = { "seq", "vcr", "seq", "vcr", "seq", "seq", "vcr", "seq", "vcr", "seq" };
		String[] inputType = { "seq", "vcr", "seq", "seq", "vcr", "seq", "vcr", "vcr", "vcr", "seq" };
//		int[] userNum = { 10, 20, 50, 100, 10, 24, 50, 100, 20, 200 };
//		String[] inputType = { "seq", "seq", "seq", "seq", "seq", "seq", "seq", "seq", "seq", "seq" };
		int uploadBandWitdh = 100;
		int segDuration = 60000;
		int numIndex = 0;
		for (int i = 0; i < userNum.length; i++) {
			UserGroup userG = new UserGroup(String.valueOf(i));
			int pcNum = (int) ((int) userNum[numIndex] * typeRation);
			int moNum = userNum[numIndex] - pcNum;
			int id = 0;
			while (pcNum > 0) {
				String nodeId = i + String.valueOf(id);
				// Node node = new Node(uploadBandWitdh, 0, "PC", this, nodeId);
				Node node = new Node(uploadBandWitdh, 0, "PC", this, nodeId);
				IEvent startEvent = new StartEvent(node);
				int ariveTime = segDuration * i * 2 + segDuration / 2;
				scheduleEvent(segDuration * i * 2 + segDuration / 2, startEvent);
				id++;
				pcNum--;
				userG.addNode(node);
			}
			while (moNum > 0) {
				String nodeId = i + String.valueOf(id);
				// Node node = new Node(uploadBandWitdh, 0, "Mobile", this, nodeId);
				Node node = new Node(uploadBandWitdh, 0, "Mobile", this, nodeId, inputType[i]);
				IEvent startEvent = new StartEvent(node);
				scheduleEvent(segDuration * i * 2 + segDuration / 2, startEvent);
				id++;
				moNum--;
				userG.addNode(node);
			}
			numIndex++;
		}
	}

	public void bwcStatistics() {
		int segDuration = 60000;
		long insertTime = 0;
		for (; insertTime < 40 * segDuration;) {
			IEvent event = new StatisticsEvent(segmentList, pcCount, moCount, segmentSize, this);
			scheduleEvent(insertTime, event);
			insertTime += segDuration;
		}
	}

	// a new node is joining
	private void nodeJoin(int uploadBW, String nodeType) {
		Node node1 = new Node(uploadBW, 0, nodeType, this);
		System.out.println("A new user arrives: " + node1 + ".");
		node1.startWathing(0);
	}

	// an example of scheduling an event that will happen at time 999
	private void startMyFirstEvent() {
		IEvent myFirstEvent = new MyEvent();
		scheduleEvent(999, myFirstEvent);
	}

	/*
	 * Better be in this way: private void nodeJoinAt(int uploadBW, String nodeType,
	 * long joinTime) { Node node1 = new Node(uploadBW, 0, nodeType, this);
	 * node1.startWathing();//should be scheduled at joinTime }
	 */
	/*
	 * for (int i=0;i<1000;++i) { nodeJoin(500,0,"mobile",this); }
	 */

}
