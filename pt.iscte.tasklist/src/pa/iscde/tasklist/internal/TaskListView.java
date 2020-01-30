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


import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserListener;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;


public class TaskListView implements PidescoView {

	private static TaskListView instance;
	private static final String EXT_POINT_TASK = "pt.iscte.tasklist.taskextension";
	//	map containing the list of tasks
	private Map<String, Set<Task>> taskList = new HashMap<String, Set<Task>>();
//	private ArrayList<Task> tasklist = new ArrayList<Task>();
	Table table;
	//	arraylist containing the tags names 
	private ArrayList<String> taglist = new ArrayList<String>();
	private ArrayList<String> tagdescription = new ArrayList<String>();
	private String root;
	List<String> tags = new ArrayList<String>();


	
	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		instance = this;
		tags.clear();
		tagdescription.clear();
		taglist.clear();
//		tasklist.clear();
		taskList.clear();
		TaskListActivator.getInstance().getEditor().addListener(new JavaEditorListener() {

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
		
		
		// task tag 
		Label tagName = new Label(viewArea, SWT.ABORT);		
		tagName.setText("Select which tasks appear in the table");	

		
			
		// implementing extensions
		IExtensionRegistry extRegistry = Platform.getExtensionRegistry();
		IExtensionPoint extensionPoint = extRegistry.getExtensionPoint(EXT_POINT_TASK);
		IExtension[] extensions = extensionPoint.getExtensions();
		for(IExtension e : extensions) {
			IConfigurationElement[] confElements = e.getConfigurationElements();
			for(IConfigurationElement c : confElements) {
				//	add a new tag to the tags arraylist with the name given in the extension argument "tagname"
				taglist.add(c.getAttribute("tagname"));
				tagdescription.add(c.getAttribute("tagdescription"));
			}
		}
		
		// handle the tags button selection
		SelectionListener selectionListener = new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Button button = ((Button) e.widget);
				if(button.getSelection()) {
					button.setSelection(true); 
				} else { button.setSelection(false);
				}
			}
		};

		//	add the default tags to the array list tags
//		for (String s : defaultTags) {
//			tags.add(s);
//		}

		for (String t : taglist) {
			tags.add(t);
		}
		
		Composite comp = new Composite(viewArea, SWT.NONE);
		comp.setLayout(new GridLayout(taglist.size()+1, false));
		comp.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 6, 1));
		
		//	create the buttons for the tags in the array list tags
		//	save the buttons created in an buttons array list: listbuttons 
//		ArrayList<Button> listbuttons = new ArrayList<Button>();
//		for(String t : tags) {
//			Button b = new Button(comp, SWT.CHECK);
//			listbuttons.add(b);
//			b.setText(t);
//			b.addSelectionListener(selectionListener);
//		}
		
		//create buttons to only show tasks with tags
		Button b = new Button(comp, SWT.PUSH);
		b.setText("ALL");
		b.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				tags.clear();
				for (String t : taglist) {
					tags.add(t);
				}
				fileReader(new File(root));
			}
		});
		int count =0;
		for(String t : taglist) {
			Button bt = new Button(comp, SWT.PUSH);
			bt.setText(t);
			bt.setToolTipText(tagdescription.get(count));
			count++;
			bt.addListener(SWT.Selection, new Listener() {
				@Override
				public void handleEvent(Event event) {
					tags.clear();
					tags.add(t);
					fileReader(new File(root));
				}
			});
		}
		
		//	handle the button to create a task
		Button pushButton = new Button(viewArea, SWT.PUSH);
		pushButton.setLocation(50, 50);
		pushButton.setText("Refresh Table");
		pushButton.pack();
		pushButton.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {	
				
				//	if no tag choosen it will be NA
				//	when the button is pressed it will look for the tag choosen, if more than one it will choose the one to the right
//				String tag = new String("NA");
//				for (Button b : listbuttons) {
//					if(b.getSelection()) tag = b.getText();
//				}
				
				fileReader(new File(root));
				
				// create the task with the arguments of the view
//				Task task = new Task(tag,nameClass.getText(),nameLocation.getText());
//				tasklist.add(task);
//
//				table.removeAll();
//				table.redraw();
//	
//				//	populate the table with the new task
//				for (Task t : tasklist) {
//						TableItem item = new TableItem(table, SWT.NONE);
//						item.setText(0, t.getTag());
//						item.setText(1, t.getDescription());
//						item.setText(2, t.getLocation());
//				}			
//				for (int i = 0; i < 3; i++) {
//					table.getColumn(i).pack();
//				}
				System.out.println(root);
			}
		});
		
//		viewArea.setLayout(new GridLayout());
//		table = new Table(viewArea, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
//		table.setLinesVisible(true);
//		table.setHeaderVisible(true);
//		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
//		data.heightHint = 200;
//		table.setLayoutData(data);
//		String[] titles = { "Tag", "Description", "Location" };
//		for (int i = 0; i < titles.length; i++) {
//			TableColumn column = new TableColumn(table, SWT.NONE);
//			column.setText(titles[i]);
//		}
//		for (int i = 0; i < titles.length; i++) {
//			table.getColumn(i).pack();
//		}
		
		/////started doing 2 development
		
		BundleContext context = TaskListActivator.getContext();
		ServiceReference<ProjectBrowserServices> serviceReference = context
				.getServiceReference(ProjectBrowserServices.class);
		ProjectBrowserServices projServ = context.getService(serviceReference);

//		projServ.addListener(new ProjectBrowserListener.Adapter() {
//			@Override
//			public void doubleClick(SourceElement element) {
//				new Label(viewArea, SWT.NONE).setText(element.getName());
//				viewArea.layout();
//			}
//		});
		
		
	

		
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

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				System.out.println("clicked table!");
				TableItem[] selection = table.getSelection();
				for (TableItem i : selection) {
					Task t = new Task(i.getText(0), i.getText(1), i.getText(2), i.getText(3),3);
					TaskListActivator.getInstance().getEditor().openFile(new File(t.getPath()));
				}
					
			}
		});
		
		String[] titles = { "Description", "Resource", "Path", "Location"};
		for (int i = 0; i < titles.length; i++) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setText(titles[i]);
		}

		for (int i = 0; i < titles.length; i++) {
			table.getColumn(i).pack();
		}
		
		root = projServ.getRootPackage().getFile().getPath();
		fileReader(new File(root));
		
	}
	
	
	
	private void fileReader(File file) {
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
				updateTable(f, tags);
			}else {
				fileReader(f);
			}
		}
	}
	
	
	/**
	 * Updates the Tasks Table in the view
	 * 
	 * @param file
	 */
	public void updateTable(File file, List<String> tags) {
		
		

		TaskHandler taskHandler = new TaskHandler();
		
		try (BufferedReader buffer = new BufferedReader(new FileReader(file))) {
			System.out.println(file.getName());
			System.out.println(file.getParentFile().getName());
			
			String line;
			StringBuilder sb = new StringBuilder();

			while ((line = buffer.readLine()) != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
			}
			String everything = sb.toString();
			taskHandler.createTasks(tags, file, everything);

		} catch (IOException e) {
			e.printStackTrace();
		}

		taskList.put(file.getPath(), taskHandler.getTasks());
		table.removeAll();
		storeTasksInTable(taskList);
		table.redraw();

	}

	/**
	 * Inserts the Tasks on the table view
	 * 
	 * @param map of Tasks
	 */
	private void storeTasksInTable(Map<String, Set<Task>> map) {
		
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

	public static TaskListView getInstance() {
		return instance;
	}
	
	public Map<String, Set<Task>> getTaskList() {
		return taskList;
	}
	
}