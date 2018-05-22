package queue;

import ev.IEvent;

/**
 * @author zwei@uvic.ca
 */
public class TreeNode {
	private TreeNode left;
	private TreeNode right;
	private long position;
	private ListElement head;
	private ListElement tail;
	
	public TreeNode() {
		this(-1, null);
	}
	
	public TreeNode(long position, IEvent element) {
		if (element == null) {
			createTreeNode1();
		} else {
			createTreeNode2(position, element);
		}
	}
	
	public void createTreeNode1() {
        left = null;
        right = null;
        position = -1;
        head = null;
        tail = null;
	}
	
	public void createTreeNode2(long pos, IEvent element) {
        left = null;
        right = null;
        position = pos;
        head = null;
        tail = null;
        addElement(element);
	}

	public TreeNode getLeft() {
		return left;
	}
	
	public void setLeft(TreeNode treeNode) {
		left = treeNode;
	}

	public TreeNode getRight() {
		return right;
	}
	
	public void setRight(TreeNode treeNode) {
		right = treeNode;
	}

	public long getPosition() {
		return position;
	}
	
	public void setPosition(long pos) {
		position = pos;
	}
	
	public ListElement elements() {
		return head;
	}
    
	//如果是同一时间会添加到list尾部
	public void addElement(IEvent element) {
		if (head == null) {
			head = new ListElement(element);
			tail = head;
		} else {
			tail.setNext(new ListElement(element));
			tail = tail.getNext();
		}
	}
	
    public void resetTreeNode(long pos, IEvent element) {
        left = null;
        right = null;
        position = pos;
        head = null;
        tail = null;
        addElement(element);
    }
}
