package pa.iscde.tasklist.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;




public class TaskListActivator implements BundleActivator {

	private static TaskListActivator instance;
	private static BundleContext context;
	private JavaEditorServices editor;
	private ProjectBrowserServices browser;

	public static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		TaskListActivator.context = bundleContext;
		ServiceReference<ProjectBrowserServices> browserReference = context.getServiceReference(ProjectBrowserServices.class);
		ServiceReference<JavaEditorServices> editorReference = context.getServiceReference(JavaEditorServices.class);
		browser = context.getService(browserReference);
		editor = context.getService(editorReference);
		instance = this;
	}

	public void stop(BundleContext bundleContext) throws Exception {
		TaskListActivator.context = null;
	}

	public static TaskListActivator getInstance() {
		return instance;
	}
	
	public ProjectBrowserServices getBrowser() {
		return browser;
	}

	public JavaEditorServices getEditor() {
		return editor;
	}

}
