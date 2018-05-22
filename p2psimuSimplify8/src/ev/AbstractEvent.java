package ev;

/**
 * This module mainly defines several kinds of events that 
 * will be used in the simulation.</p>
 * 
 * All these events are derived from the AbstractEvent class.</p>
 * 
 * @author zwei@uvic.ca
 */
public abstract class AbstractEvent implements IEvent {
	protected EEventType type;
	
	public AbstractEvent(EEventType type) {
		this.type = type;
	}
	
	public EEventType getType() {
		return type;
	}
	
	@Override
	abstract public void doTask(long timeNow);
	
	@Override
	public String toString() {
		return type.toString();
	}
}
