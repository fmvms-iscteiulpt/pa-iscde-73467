package pa.iscde.tasklist.internal;

import java.util.ArrayList;
import pa.iscde.tasklist.service.TaskListService;


//	TaskList Services Implementation

public class TaskListServiceImpl implements TaskListService {

//	Getter for all tasks found
	@Override
	public ArrayList<Task> getTasks() {
		ArrayList<Task> tasksMap = TaskListView.getInstance().getTaskList();		
		return tasksMap;
	}

}
