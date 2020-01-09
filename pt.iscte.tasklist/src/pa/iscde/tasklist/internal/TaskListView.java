package pa.iscde.tasklist.internal;

import java.awt.event.ActionListener;
import java.io.File;
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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.osgi.framework.BundleContext;

import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;


public class TaskListView implements PidescoView {

	private static TaskListView instance;
	private static final String EXT_POINT_TASK = "pt.iscte.tasklist.taskextension";
	private Map<String, Set<Task>> taskList = new HashMap<String, Set<Task>>();
	private Table table;
	String[] tags =  {"TODO", "FIXME", "XXX"};
	
	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		instance = this;
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
		
		/*viewArea.setLayout(new RowLayout(SWT.HORIZONTAL));
		Label label = new Label(viewArea, SWT.NONE);
		label.setImage(imageMap.get("smiley.png"));
		Text text = new Text(viewArea, SWT.BORDER);
		text.setText("hello world");
		*/

		
		viewArea.setLayout(new RowLayout(SWT.VERTICAL));
				
		//field package
		Label labelPackage = new Label(viewArea, SWT.ABORT);		
		labelPackage.setText("Task name: ");	
		
		Text namePackage = new Text(viewArea,SWT.BORDER);		
		namePackage.setEditable(false);
		namePackage.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 6, 1));
		namePackage.setToolTipText("Write your task name");

		
		//field description
		Label labelName = new Label(viewArea, SWT.ABORT);		
		labelName.setText("Give your task a description: ");	
		
		Text nameClass = new Text(viewArea,SWT.BORDER);		
		nameClass.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 6, 1));
		nameClass.setToolTipText("Write your task description");
		nameClass.setEditable(true);
		namePackage.setEditable(true);
		
		Label labelLocation = new Label(viewArea, SWT.ABORT);
		labelLocation.setText("Where is your task located?");
		Text nameLocation = new Text(viewArea,SWT.BORDER);		
		nameLocation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 6, 1));
		nameLocation.setToolTipText("Write your task location");
		nameLocation.setEditable(true);
		
		
		
		Label tagName = new Label(viewArea, SWT.ABORT);		
		tagName.setText("Choose your task tag: ");	
		///////TAGS
		Composite composite0 = new Composite(viewArea, SWT.NONE);
		composite0.setLayout(new GridLayout(4, false));
		composite0.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1));
		
		
		Button todo = new Button(composite0, SWT.CHECK);
		todo.setText(" TODO  ");
		todo.setSelection(true);
		
		Button fixme = new Button(composite0, SWT.CHECK);
		fixme.setText(" FIXME  ");

		Button xxx = new Button(composite0, SWT.CHECK);
		xxx.setText(" XXX");

		
		todo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				todo.setSelection(true);
				fixme.setSelection(false);
				xxx.setSelection(false);
			}
		});
		
		fixme.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				todo.setSelection(false);
				fixme.setSelection(true);
				xxx.setSelection(false);
			}
		});
		
		xxx.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				todo.setSelection(false);
				fixme.setSelection(false);
				xxx.setSelection(true);
			}
		});
		
		
		
		IExtensionRegistry extRegistry = Platform.getExtensionRegistry();
		IExtensionPoint extensionPoint = extRegistry.getExtensionPoint(EXT_POINT_TASK);
		IExtension[] extensions = extensionPoint.getExtensions();
		for(IExtension e : extensions) {
			IConfigurationElement[] confElements = e.getConfigurationElements();
			for(IConfigurationElement c : confElements) {
				String s = c.getAttribute("tagname");
				Button tagextension = new Button(composite0, SWT.CHECK);
				tagextension.setText(s);
				
			}
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		Button pushButton = new Button(viewArea, SWT.PUSH);
		pushButton.setLocation(50, 50);
		pushButton.setText("Create new Task!");
		pushButton.pack();
		pushButton.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
			
				IExtensionRegistry extRegistry = Platform.getExtensionRegistry();
				IExtensionPoint extensionPoint = extRegistry.getExtensionPoint(EXT_POINT_TASK);
				IExtension[] extensions = extensionPoint.getExtensions();
				for(IExtension e : extensions) {
					IConfigurationElement[] confElements = e.getConfigurationElements();
					for(IConfigurationElement c : confElements) {
						String s = c.getAttribute("tagname");
						String d = c.getAttribute("tagdescription");
						System.out.println(s);
						System.out.println(d);
					}
				}
				System.out.println("-----testing--------");
				
			}
		});
		
		viewArea.setLayout(new GridLayout());
		table = new Table(viewArea, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = 200;
		table.setLayoutData(data);
		String[] titles = { "Tag", "Description", "Resource", "Path", "Location" };
		for (int i = 0; i < titles.length; i++) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setText(titles[i]);
		}
		TableColumn column = new TableColumn(table, SWT.NONE);
		column.setText("Offset");
		column.setResizable(false);
		for (int i = 0; i < titles.length; i++) {
			table.getColumn(i).pack();
		}
		
	}
		
	
	
	public static TaskListView getInstance() {
		return instance;
	}
	
	public Map<String, Set<Task>> getTaskList() {
		return taskList;
	}
	
	public void update() {
		
	}
	
	public void createTask() {
		
	}
	
}