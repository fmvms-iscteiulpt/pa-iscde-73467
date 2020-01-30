package pa.iscde.tasklist.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import pa.iscde.tasklist.service.TaskListService;
import pt.iscte.pidesco.extensibility.PidescoServices;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

/**
 *  Task list Activator
 */

public class TaskListActivator implements BundleActivator {

	private static TaskListActivator instance;
	private static BundleContext context;
	private JavaEditorServices editorService;
	private ProjectBrowserServices browserService;
	private ServiceRegistration<TaskListService> taskService;
	private PidescoServices pidescoService;

	public static BundleContext getContext() {
		return context;
	}

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		TaskListActivator.context = bundleContext;
		ServiceReference<ProjectBrowserServices> browserReference = context.getServiceReference(ProjectBrowserServices.class);
		ServiceReference<JavaEditorServices> editorReference = context.getServiceReference(JavaEditorServices.class);
		ServiceReference<PidescoServices> pidescoReference = context.getServiceReference(PidescoServices.class);
		taskService = context.registerService(TaskListService.class, new TaskListServiceImpl(), null);
		browserService = context.getService(browserReference);
		editorService = context.getService(editorReference);
		pidescoService = context.getService(pidescoReference);
		instance = this;
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		instance = null;
		TaskListActivator.context = null;
	}

	/**
	 * Returns the task list activator instance
	 * @return TaskListActivator instance
	 */
	public static TaskListActivator getInstance() {
		return instance;
	}
	
	/**
	 * Returns the Browser Service
	 * @return ProjectBrowserServices browserService
	 */
	public ProjectBrowserServices getBrowser() {
		return browserService;
	}

	/**
	 * Returns the Java Editor Service
	 * @return JavaEditorServices editorService
	 */
	public JavaEditorServices getEditor() {
		return editorService;
	}

	/**
	 * Returns the Pidesco Service
	 * @return PidescoServices pidescoService
	 */
	public PidescoServices getPidesco() {
		return pidescoService;
	}
	
	/**
	 * Returns the Task List Service
	 * @return TaskListService taskService
	 */
	public ServiceRegistration<TaskListService> getTaskService() {
		return taskService;
	}

}
