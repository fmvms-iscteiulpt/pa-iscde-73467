package pa.iscde.tasklist.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import pa.iscde.tasklist.service.TaskListService;
import pt.iscte.pidesco.extensibility.PidescoServices;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

public class TaskListActivator implements BundleActivator {

	private static TaskListActivator instance;
	private static BundleContext context;
	private JavaEditorServices editor;
	private ProjectBrowserServices browser;
	private ServiceRegistration<TaskListService> task;
	private PidescoServices pidesco;

	public static BundleContext getContext() {
		return context;
	}

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		TaskListActivator.context = bundleContext;
		ServiceReference<ProjectBrowserServices> browserReference = context.getServiceReference(ProjectBrowserServices.class);
		ServiceReference<JavaEditorServices> editorReference = context.getServiceReference(JavaEditorServices.class);
		ServiceReference<PidescoServices> pidescoReference = context.getServiceReference(PidescoServices.class);
		task = context.registerService(TaskListService.class, new TaskListServiceImpl(), null);
		browser = context.getService(browserReference);
		editor = context.getService(editorReference);
		pidesco = context.getService(pidescoReference);
		instance = this;
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		instance = null;
		TaskListActivator.context = null;
	}

	public static TaskListActivator getInstance() {
		return instance;
	}
	
	/**
	 * Getter for browser Service
	 * @return ProjectBrowserServices instance
	 */
	public ProjectBrowserServices getBrowser() {
		return browser;
	}

	/**
	 * Getter for editor Service
	 * @return JavaEditorServices instance
	 */
	public JavaEditorServices getEditor() {
		return editor;
	}

	/**
	 * Getter for pidesco Service
	 * @return PidescoServices instance
	 */
	public PidescoServices getPidesco() {
		return pidesco;
	}

}
