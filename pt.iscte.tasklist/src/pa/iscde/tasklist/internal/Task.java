package pa.iscde.tasklist.internal;

public class Task {

	private final String tag;
	private final String description;
	private final String location;
	
	
	/**
	 * Tag constructor
	 * 
	 * @param tag         {@link String}
	 * @param description String
	 * @param line        Integer
	 * @param path        String
	 */
	
	public Task(String tag, String description, String location) {
		
		this.tag = tag;
		this.description = description;
		this.location = location;
	}

	/**
	 * Getter for Tag
	 * 
	 * @return {@link String}
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * Getter for Description
	 * 
	 * @return String
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Getter for Location
	 * 
	 * @return String
	 */
	public String getLocation() {
		return location;
	}
	


	@Override
	public String toString() {
		return "Task [tag=" + tag + ", description=" + description + ", location= " + location + "]";
	}
}

	

