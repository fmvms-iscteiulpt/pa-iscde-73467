package pa.iscde.tasklist.service;

import java.util.ArrayList;
import java.util.Set;

import pa.iscde.tasklist.internal.Task;


public interface TaskListService {

	/**
	 * Create a new Task object in the table
	 */
	
	void createTask();

	
	/**
	 * Getter for a set of all taks in the table
	 * @return Set of {@link Task} Objects
	 */
		
	ArrayList<Task> getTasks();
	
	
	/**
	 * Update the task table
	 */
	
	void updateTable();
	
}
