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

import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;


public class TaskListView implements PidescoView {

	private static TaskListView instance;
	private static final String EXT_POINT_TASK = "pt.iscte.tasklist.taskextension";
	//	arraylist containing the tasks
	private ArrayList<Task> tasklist = new ArrayList<Task>();
	private Table table;
	//	arraylist containing the tags names 
	private ArrayList<String> tags = new ArrayList<String>();
	private String[] defaultTags= {"TODO", "FIXME", "XXX"};
	
	
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
		
		viewArea.setLayout(new RowLayout(SWT.VERTICAL));
		
		// task description
		Label labelName = new Label(viewArea, SWT.ABORT);		
		labelName.setText("Give a description to your task: ");	

		Text nameClass = new Text(viewArea,SWT.BORDER);		
		nameClass.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 6, 1));
		nameClass.setToolTipText("Write your task description");
		nameClass.setEditable(true);

		// task location
		Label labelLocation = new Label(viewArea, SWT.ABORT);
		labelLocation.setText("Where is your task located?");
		
		Text nameLocation = new Text(viewArea,SWT.BORDER);		
		nameLocation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 6, 1));
		nameLocation.setToolTipText("Write your task location");
		nameLocation.setEditable(true);
		
		// task tag 
		Label tagName = new Label(viewArea, SWT.ABORT);		
		tagName.setText("Please choose one tag for your task: ");	

		Composite comp = new Composite(viewArea, SWT.NONE);
		comp.setLayout(new GridLayout(4, false));
		comp.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1));
			
		// implementing extensions
		IExtensionRegistry extRegistry = Platform.getExtensionRegistry();
		IExtensionPoint extensionPoint = extRegistry.getExtensionPoint(EXT_POINT_TASK);
		IExtension[] extensions = extensionPoint.getExtensions();
		for(IExtension e : extensions) {
			IConfigurationElement[] confElements = e.getConfigurationElements();
			for(IConfigurationElement c : confElements) {
				//	add a new tag to the tags arraylist with the name given in the extension argument "tagname"
				tags.add(c.getAttribute("tagname"));				
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
		for (String s : defaultTags) {
			tags.add(s);
		}

		//	create the buttons for the tags in the array list tags
		//	save the buttons created in an buttons array list: listbuttons 
		ArrayList<Button> listbuttons = new ArrayList<Button>();
		for(String t : tags) {
			Button b = new Button(comp, SWT.CHECK);
			listbuttons.add(b);
			b.setText(t);
			b.addSelectionListener(selectionListener);
		}
		
		//	handle the button to create a task
		Button pushButton = new Button(viewArea, SWT.PUSH);
		pushButton.setLocation(50, 50);
		pushButton.setText("Create new Task!");
		pushButton.pack();
		pushButton.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {	
				
				//	if no tag choosen it will be NA
				//	when the button is pressed it will look for the tag choosen, if more than one it will choose the one to the right
				String tag = new String("NA");
				for (Button b : listbuttons) {
					if(b.getSelection()) tag = b.getText();
				}
				
				// create the task with the arguments of the view
				Task task = new Task(tag,nameClass.getText(),nameLocation.getText());
				tasklist.add(task);

				table.removeAll();
				table.redraw();
	
				//	populate the table with the new task
				for (Task t : tasklist) {
						TableItem item = new TableItem(table, SWT.NONE);
						item.setText(0, t.getTag());
						item.setText(1, t.getDescription());
						item.setText(2, t.getLocation());
				}			
				for (int i = 0; i < 3; i++) {
					table.getColumn(i).pack();
				}
			}
		});
		
		viewArea.setLayout(new GridLayout());
		table = new Table(viewArea, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = 200;
		table.setLayoutData(data);
		String[] titles = { "Tag", "Description", "Location" };
		for (int i = 0; i < titles.length; i++) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setText(titles[i]);
		}
		for (int i = 0; i < titles.length; i++) {
			table.getColumn(i).pack();
		}
		
	}
	
	public static TaskListView getInstance() {
		return instance;
	}
	
	public ArrayList<Task> getTaskList() {
		return tasklist;
	}
	
}