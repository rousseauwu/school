package Model;

import java.util.ArrayList;
import java.util.List;

import mainfunc.Node;

public class UserGroup {
	private String groupId;
	private List<Node> nodeList;
	private int cacheSize;
	public UserGroup(String groupId) {
		super();
		this.groupId = groupId;
		this.nodeList = new ArrayList<Node>();
	}
	public UserGroup() {
		super();
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public List<Node> getNodeList() {
		return nodeList;
	}
	public void setNodeList(List<Node> nodeList) {
		this.nodeList = nodeList;
	}
	
	public void addNode(Node e) {
		nodeList.add(e);
	}
}
