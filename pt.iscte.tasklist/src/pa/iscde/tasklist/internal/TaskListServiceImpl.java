package pa.iscde.tasklist.internal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import pa.iscde.tasklist.service.TaskListService;

public class TaskListServiceImpl implements TaskListService {

	@Override
	public void createTask() {
		TaskListView.getInstance().createTask();
		
	}

	@Override
	public ArrayList<Task> getTasks() {
		ArrayList<Task> tasksMap = TaskListView.getInstance().getTaskList();		
		return tasksMap;
	}

	@Override
	public void updateTable() {
		TaskListView.getInstance().update();
		
	}

}
