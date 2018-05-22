package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mainfunc.Node;

public class Segment {
	private String segmentId;
	private Map<String,Node> nodeMap;
	public Segment() {
		super();
	}
	public Segment(String segmentId) {
		super();
		this.segmentId = segmentId;
		this.nodeMap = new HashMap<String,Node>();
	}
	public String getSegmentId() {
		return segmentId;
	}
	public void setSegmentId(String segmentId) {
		this.segmentId = segmentId;
	}
	public Map<String, Node> getNodeMap() {
		return nodeMap;
	}
	public void setNodeMap(Map<String, Node> nodeMap) {
		this.nodeMap = nodeMap;
	}
	
}
