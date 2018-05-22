package ev;

/**
 * Enumeration for all available events.</p>
 * 
 * @author zwei@uvic.ca
 */
public enum EEventType {
	MY_EV("My First Event"),
	ANOTHER_EV("Another Event"),
	CONTWATCHING_EV("Continue Watching Segment");
	
	private String description;
	
	private EEventType(String str) {
		description = str;
	}
	
	@Override
	public String toString() {
		return description;
	}
}
