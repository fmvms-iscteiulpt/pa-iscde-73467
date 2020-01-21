package pt.iscte.tasklist.testExt;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import pa.iscde.tasklist.service.TaskListService;

public class Activator implements BundleActivator {

	private static Activator instance;
	private TaskListService taskserv;

	public static Activator getInstance() {
		return instance;
	}

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		instance = this;

		ServiceReference<TaskListService> taskServiceReference = bundleContext
				.getServiceReference(TaskListService.class);
		taskserv = bundleContext.getService(taskServiceReference);
		
		

	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
	}
	
	public TaskListService getTaskService() {
		return taskserv;
	}

}
