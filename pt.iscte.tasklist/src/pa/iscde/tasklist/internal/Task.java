package pa.iscde.tasklist.internal;

/**
 * Object that represents a Task
 * 
 * @author Francisco Silva.
 *
 */

public class Task {

	private final String tag;
	private final String location;
	private final String description;
	
	/**
	 * Task constructor
	 * 
	 * @param tag         String
	 * @param description String
	 * @param location    String
	 */
	
	public Task(String tag, String description, String location) {
		this.tag = tag;
		this.description = description;
		this.location = location;
	}

	/**
	 * Getter for Tag
	 * 
	 * @return String
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * Getter for Location
	 * 
	 * @return String
	 */
	public String getLocation() {
		return location;
	}
	
	/**
	 * Getter for Description
	 * 
	 * @return String
	 */
	public String getDescription() {
		return description;
	}
	
	@Override
	public String toString() {
		return "Task [tag=" + tag + ", description=" + description + ", location= " + location + "]";
	}
}

	

