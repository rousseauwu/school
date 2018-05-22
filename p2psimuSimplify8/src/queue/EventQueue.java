package queue;

import ev.IEvent;

/**
 * This module is very important for simulation.</p>
 * 
 * Basically, it implements the event queue that will be used in the simulation.
 * The implementation currently is of a splay-tree.</p>
 * 
 * @author zwei@uvic.ca
 */
public class EventQueue {
	private long timeNow;

	private TreeNode root;
	private TreeNode node;
	
	public EventQueue() {
		timeNow = 0;
		
		root = null;
        node = new TreeNode();
	}
	
	public boolean isEmpty() {
        return root == null;
    }

    public long getTimeNow() {
        return timeNow;
    }

    public ListElement getNextEvent() {
        ListElement t = null;
        if (root != null) {
            root = splay(0, root);
            t = root.elements();
            timeNow = root.getPosition();
            root = deleteNode(root.getPosition(), root, false);
        }
        return t;
    }
    
    public void insertObject(long point, IEvent obj) {
        root = insertNode(point, root, obj);
	}
	
    private TreeNode insertNode(long i, TreeNode t, IEvent obj) {
	    TreeNode n;
		if (t == null) {
	        n = new TreeNode(i, obj);
	        n.setLeft(null);
	        n.setRight(null);
	        return n;
	    }
	    t = splay(i, t);
	    if (i < t.getPosition()) {
	        n = new TreeNode(i, obj);
	        n.setLeft(t.getLeft());
	        n.setRight(t);
	        t.setLeft(null);
	        return n;
	    } else if (i > t.getPosition()) {
	        n = new TreeNode(i, obj);
	        n.setRight(t.getRight());
	        n.setLeft(t);
	        t.setRight(null);
	        return n;
	    } else {
	        t.addElement(obj);
	        return t;
	    }
	}

	private TreeNode splay(long i, TreeNode t) {
        if (t == null) {
        	return t;
        }
        
        node.setLeft(null);
        node.setRight(null);
        TreeNode l = node;
        TreeNode r = node;

        while (true) {
            if (i < t.getPosition()) {
                if (t.getLeft() == null) {
                	break;
                }
                if (i < t.getLeft().getPosition()) {
                    TreeNode y = t.getLeft();
                    t.setLeft(y.getRight());
                    y.setRight(t);
                    t = y;
                    if (t.getLeft() == null) {
                    	break;
                    }
                }
                r.setLeft(t);
                r = t;
                t = t.getLeft();
            } else if (i > t.getPosition()) {
                if (t.getRight() == null) {
                	break;
                }
                if (i > t.getRight().getPosition()) {
                    TreeNode y = t.getRight();
                    t.setRight(y.getLeft());
                    y.setLeft(t);
                    t = y;
                    if (t.getRight() == null) {
                    	break;
                    }
                }
                l.setRight(t);
                l = t;
                t = t.getRight();
            } else {
            	break;
            }
        }
        
        l.setRight(t.getLeft());
        r.setLeft(t.getRight());
        t.setLeft(node.getRight());
        t.setRight(node.getLeft());

        return t;
    }

	public TreeNode deleteNode(long i, TreeNode t, boolean doSplay) {
	    if (t == null) {
	    	return null;
	    }
	    if (doSplay) {
	    	t = splay(i,t);
	    }
	    if (i == t.getPosition()) {
	    	TreeNode x = null;
	        if (t.getLeft() == null) {
	            x = t.getRight();
	        } else {
	            x = splay(i, t.getLeft());
	            x.setRight(t.getRight());
	        }
	        return x;
	    }
	    return t;
	}
}
