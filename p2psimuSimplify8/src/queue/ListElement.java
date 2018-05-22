package queue;

import ev.IEvent;

/**
 * @author zwei@uvic.ca
 */
public class ListElement {
	private IEvent store;
	private ListElement next;
	
	public ListElement(IEvent obj) {
		store = obj;
		next = null;
	}
	
	public IEvent getStore() {
		return store;
	}
	
	public ListElement getNext() {
		return next;
	}
	
	public void setNext(ListElement next) {
		this.next = next;
	}
}
