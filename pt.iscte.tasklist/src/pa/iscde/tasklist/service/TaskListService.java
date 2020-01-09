package pa.iscde.tasklist.service;

import java.util.ArrayList;

import pa.iscde.tasklist.internal.Task;


public interface TaskListService {
	
	/**
	 * Getter for an ArrayList of Task Objects for all the taks currently in the table
	 *
	 * @return ArrayList of Task Objects
	 */
	ArrayList<Task> getTasks();
	
}
