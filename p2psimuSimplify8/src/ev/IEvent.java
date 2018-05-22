package ev;

/**
 * Interface for all event implementations.</p>
 * 
 * @author zwei@uvic.ca
 */
public interface IEvent {

	/**
	 * Do task when timer fires.
	 */
	public void doTask(long timeNow);
	
}
