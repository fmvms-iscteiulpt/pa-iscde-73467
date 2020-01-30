package pa.iscde.tasklist.service;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import pa.iscde.tasklist.internal.Task;

/**
 * Task List Services
 * @author franc
 *
 */

public interface TaskListService {
	
	/**
	 * Returns a Map of Task Objects for all the tasks currently in the table
	 * @return Map of Task Objects
	 */
	Map<String, Set<Task>> getTasks();
	
}
