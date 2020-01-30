package pa.iscde.tasklist.internal;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import pa.iscde.tasklist.service.TaskListService;

/**
 * TaskList Services Implementation
 * @author franc
 *
 */

public class TaskListServiceImpl implements TaskListService {

	/**
	 * Returns all the tasks found
	 * @return Map taskMap with all the tasks found
	 */
	@Override
	public Map<String, Set<Task>> getTasks() {
		Map<String, Set<Task>> tasksMap = TaskListView.getInstance().getTaskList();		
		return tasksMap;
	}

}
