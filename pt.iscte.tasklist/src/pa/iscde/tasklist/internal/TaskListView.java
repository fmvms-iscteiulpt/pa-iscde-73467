package pa.iscde.tasklist.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.osgi.framework.BundleContext;


import pt.iscte.pidesco.extensibility.PidescoView;



public class TaskListView implements PidescoView {

	private static TaskListView instance;
	
	private Map<String, Set<Task>> taskList = new HashMap<String, Set<Task>>();
	
	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
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
		namePackage.setSize(300, 20);
		
		//field description
		Label labelName = new Label(viewArea, SWT.ABORT);		
		labelName.setText("Describe your task: ");	
		
		Text nameClass = new Text(viewArea,SWT.BORDER);		
		nameClass.setToolTipText("Write your class name");
		nameClass.setEditable(true);
		namePackage.setEditable(true);
		
		Button createClass = new Button(viewArea,SWT.CHECK);
		Button createPackage = new Button(viewArea,SWT.CHECK);
		createClass.setSelection(true);
		
		
		Button pushButton = new Button(viewArea, SWT.PUSH);
		pushButton.setLocation(50, 50);
		pushButton.setText("Im a Push Button");
		pushButton.pack();
		pushButton.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				System.out.println("-----testing--------");
			}
		});
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