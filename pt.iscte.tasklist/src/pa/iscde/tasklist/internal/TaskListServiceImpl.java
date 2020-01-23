package pa.iscde.tasklist.internal;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import pa.iscde.tasklist.service.TaskListService;


//	TaskList Services Implementation

public class TaskListServiceImpl implements TaskListService {

//	Getter for all tasks found
	@Override
	public Map<String, Set<Task>> getTasks() {
		Map<String, Set<Task>> tasksMap = TaskListView.getInstance().getTaskList();		
		return tasksMap;
	}

}
