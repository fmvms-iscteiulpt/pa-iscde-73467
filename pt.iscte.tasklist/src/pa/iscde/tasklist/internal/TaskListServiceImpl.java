package pa.iscde.tasklist.internal;

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
	public Set<Task> getTasks() {
		Set<Task> returnSet = new HashSet<Task>();
		Map<String, Set<Task>> tasksMap = TaskListView.getInstance().getTaskList();
		for(Set<Task> set : tasksMap.values())
			returnSet.addAll(set);
		return returnSet;
	}

	@Override
	public void updateTable() {
		TaskListView.getInstance().update();
		
	}

}
