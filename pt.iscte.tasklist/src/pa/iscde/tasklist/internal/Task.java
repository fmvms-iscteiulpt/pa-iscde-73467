package pa.iscde.tasklist.internal;

public class Task {

	private final String tag;
	private final String description;
	private final int line;
	private final String path;
	
	/**
	 * Tag constructor
	 * 
	 * @param tag         {@link String}
	 * @param description String
	 * @param line        Integer
	 * @param path        String
	 */
	
	public Task(String tag, String description, int line, String path) {
		this.tag = tag;
		this.description = description;
		this.line = line;
		this.path = path;
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
	 * Getter for Path
	 * 
	 * @return String
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Getter for Line
	 * 
	 * @return Integer
	 */
	public int getLine() {
		return line;
	}


	@Override
	public String toString() {
		return "Task [tag=" + tag + ", description=" + description + ", path=" + path
				+ ", line=" + line + "]";
	}
}

	

