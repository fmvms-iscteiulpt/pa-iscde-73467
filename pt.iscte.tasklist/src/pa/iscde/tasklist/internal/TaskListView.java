package pa.iscde.tasklist.internal;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import pa.iscde.tasklist.service.TaskListService;
import pt.iscte.pidesco.extensibility.PidescoServices;
import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserListener;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

/**
 * View implemented by PidescoView showing the tasklist 
 * @author franc
 *
 */

public class TaskListView implements PidescoView {

	private static TaskListView instance;
	private static final String EXT_POINT_TASK = "pt.iscte.tasklist.taskextension";
	private Map<String, Set<Task>> taskList = new HashMap<String, Set<Task>>();
	private Table table;
	private ArrayList<String> tagList = new ArrayList<String>();
	private ArrayList<String> tagDescription = new ArrayList<String>();
	private String root;
	private List<String> tagsSelected = new ArrayList<String>();
	private String[] tableColumns = { "Description", "Resource", "Path", "Location"};

	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		instance = this;
		tagsSelected.clear();
		tagDescription.clear();
		tagList.clear();
		taskList.clear();
		ProjectBrowserServices browserService = TaskListActivator.getInstance().getBrowser();
		JavaEditorServices editorService = TaskListActivator.getInstance().getEditor();
		PidescoServices pidescoService = TaskListActivator.getInstance().getPidesco();
		ServiceRegistration<TaskListService> taskListService = TaskListActivator.getInstance().getTaskService();
		
		editorService.addListener(new JavaEditorListener() {

			@Override
			public void fileOpened(File file) {

			}

			@Override
			public void fileClosed(File file) {

			}

			@Override
			public void selectionChanged(File file, String text, int offset, int length) {

			}
		});	

		// Implementing extensions
		IExtensionRegistry extRegistry = Platform.getExtensionRegistry();
		IExtensionPoint extensionPoint = extRegistry.getExtensionPoint(EXT_POINT_TASK);
		IExtension[] extensions = extensionPoint.getExtensions();
		for(IExtension e : extensions) {
			IConfigurationElement[] confElements = e.getConfigurationElements();
			for(IConfigurationElement c : confElements) {
				//	add a new tag to tagList with the text given in the extension argument "tagname"
				tagList.add(c.getAttribute("tagname"));
				//	add a new tag description to tagDescription with the text given in the extension argument "tagdescription"
				tagDescription.add(c.getAttribute("tagdescription"));
			}
		}
		
		for (String t : tagList) { tagsSelected.add(t);}
		
		Label tagName = new Label(viewArea, SWT.ABORT);		
		tagName.setText("Select which tasks appear in the table");	
		
		Composite comp = new Composite(viewArea, SWT.NONE);
		comp.setLayout(new GridLayout(tagList.size()+1, false));
		comp.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 6, 1));
				
		// Create buttons to only show tasks with selected tags
		Button b = new Button(comp, SWT.PUSH);
		b.setText("ALL");
		b.pack();
		b.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				tagsSelected.clear();
				for (String t : tagList) {
					tagsSelected.add(t);
				}
				fileReader(new File(root));
			}
		});
		int count = 0;
		for(String t : tagList) {
			Button bt = new Button(comp, SWT.PUSH);
			bt.setText(t);
			bt.setToolTipText(tagDescription.get(count));
			count++;
			bt.pack();
			bt.addListener(SWT.Selection, new Listener() {
				@Override
				public void handleEvent(Event event) {
					tagsSelected.clear();
					tagsSelected.add(t);
					fileReader(new File(root));
				}
			});
		}
		
		//	Create a button to refresh the table, looking for new tasks in the files
		Button pushButton = new Button(viewArea, SWT.PUSH);
		pushButton.setLocation(50, 50);
		pushButton.setText("Refresh Table");
		pushButton.pack();
		pushButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {	
				fileReader(new File(root));
			}
		});
		
		// Table implementation
		viewArea.setLayout(new GridLayout());
		table = new Table(viewArea, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = 200;
		table.setLayoutData(data);
		table.addMouseListener(new MouseListener() {

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			// opens the file containing the clicked task of the table
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				TableItem[] selection = table.getSelection();
				for (TableItem i : selection) {
					Task t = new Task(i.getText(0), i.getText(1), i.getText(2), i.getText(3),3);
					TaskListActivator.getInstance().getEditor().openFile(new File(t.getPath()));
				}
			}
		});
		
		// creates the table columns
		for (int i = 0; i < tableColumns.length; i++) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setText(tableColumns[i]);
			table.getColumn(i).pack();
		}

		// gets the root path through the browserService
		root = browserService.getRootPackage().getFile().getPath();
		fileReader(new File(root));
	}
	
	
	/**
	 * Reads the file in the parameter and if it is a directory then it moves up until it reads all the files
	 * @param file
	 */
	public void fileReader(File file) {
		for (File f : file.listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				if (pathname.isDirectory())
					return true;
				String name = pathname.getName();
				int index = name.lastIndexOf(".");
				if (index == -1)
					return false;
				return name.substring(index + 1).equals("java");
			}
		})) {
			if (f.isFile()) {
				lookForTasks(f, tagsSelected);
			}else {
				fileReader(f);
			}
		}
	}
	
	/**
	 * Looks for tasks in the file, with the current selected tags and adds them to the taskList
	 * @param file
	 * @param tagsSelected
	 */
	public void lookForTasks(File file, List<String> tagsSelected) {
		
		TaskHandler taskHandler = new TaskHandler();
		
		try (BufferedReader buffer = new BufferedReader(new FileReader(file))) {
			String line;
			StringBuilder sb = new StringBuilder();
			while ((line = buffer.readLine()) != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
			}
			String everything = sb.toString();
			taskHandler.createTasks(tagsSelected, file, everything);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		taskList.put(file.getPath(), taskHandler.getTasks());
		table.removeAll();
		populateTable(taskList);
		table.redraw();

	}

	/**
	 * Inserts the Tasks on the table view
	 * @param map of the tasks
	 */
	private void populateTable(Map<String, Set<Task>> map) {
		
		for (Set<Task> s : map.values())
			for (Task t : s) {
				TableItem item = new TableItem(table, SWT.NONE);
				item.setText(0, t.getTag().toString() + t.getDescription());
				item.setText(1, t.getResource());
				item.setText(2, t.getPath());
				item.setText(3, "line " + t.getLine());
			}

		for (TableColumn column : table.getColumns()) {
			column.pack();
		}
		
	}

	/**
	 * Returns the TaskListView instance
	 * @return TaskListView instance
	 */
	public static TaskListView getInstance() {
		return instance;
	}
	
	/**
	 * Returns the taskList containing the tasks
	 * @return Map taskList
	 */
	public Map<String, Set<Task>> getTaskList() {
		return taskList;
	}
	
}