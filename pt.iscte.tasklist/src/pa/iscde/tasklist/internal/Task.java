package pa.iscde.tasklist.internal;

/**
 *  Object that represents a Task 
 */

public class Task {

	private String tag;
	private String description;
	private String path;
	private String resource;
	private int line;

	/**
	 *  Task Object
	 *  
	 * @param tag
	 * @param description
	 * @param path
	 * @param resource
	 * @param line
	 */
	public Task(String tag, String description, String path, String resource, int line) {
		this.tag = tag;
		this.description = description;
		this.path = path;
		this.resource = resource;
		this.line = line;
	}

	/**
	 * Returns the tag used to describe the task
	 * @return String tag
	 */
	public String getTag() {
		return tag;
	}
	
	/**
	 * Returns the task description
	 * @return String description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Returns the path of the java file
	 * @return String project
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * Returns the java file where the task is located
	 * @return String resource
	 */
	public String getResource() {
		return resource;
	}

	/**
	 * Returns the line number where the task is located inside the java file 
	 * @return Integer line
	 */
	public int getLine() {
		return line;
	}
	
}

	

