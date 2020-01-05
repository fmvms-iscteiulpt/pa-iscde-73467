package pa.iscde.tasklist.internal;

import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import pt.iscte.pidesco.extensibility.PidescoView;

public class DemoView implements PidescoView {

	private static Tree tree;
	private static Image searchIcon, packageIcon, classIcon;
	private static String packageName, itemName, className;
	private static List<String> packageClass;
	private static TreeItem searchItem;
	private static boolean treeBool;
	private static int i;

	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {

		Label space;
		searchIcon = imageMap.get("find_obj.gif");
		packageIcon = imageMap.get("package.gif");
		classIcon = imageMap.get("class.gif");

		viewArea.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		viewArea.setLayout(new GridLayout(6, false));

		Label searchBarTitle = new Label(viewArea, SWT.NONE);
		searchBarTitle.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 6, 1));
		searchBarTitle.setText("Search Bar");

		Text searchBar = new Text(viewArea, SWT.BORDER);
		searchBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 6, 1));

		Button caseSensitive = new Button(viewArea, SWT.CHECK);
		caseSensitive.setText("Case Sensitive");

		space = new Label(viewArea, SWT.NONE);

		Composite composite0 = new Composite(viewArea, SWT.NONE);
		composite0.setLayout(new GridLayout(4, false));
		composite0.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 4, 1));

		Button equals = new Button(composite0, SWT.CHECK);
		equals.setText(" Equals  ");

		Button contains = new Button(composite0, SWT.CHECK);
		contains.setText(" Contains  ");

		Button startsWith = new Button(composite0, SWT.CHECK);
		startsWith.setText(" Starts With  ");

		Button endsWith = new Button(composite0, SWT.CHECK);
		endsWith.setText(" Ends With");

		space = new Label(viewArea, SWT.NONE);
		space.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 6, 1));

		Label fileNameTitle = new Label(viewArea, SWT.NONE);
		fileNameTitle.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 6, 1));
		fileNameTitle.setText("Package Name");

		Combo fileName = new Combo(viewArea, SWT.BORDER);
		fileName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 6, 1));

		space = new Label(viewArea, SWT.NONE);
		space.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 6, 1));

		Composite composite1 = new Composite(viewArea, SWT.NONE);
		composite1.setLayout(new GridLayout(6, false));
		composite1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1));

		Button type = new Button(composite1, SWT.CHECK);
		type.setText(" Type  ");

		Button method = new Button(composite1, SWT.CHECK);
		method.setText(" Method  ");

		Button constructor = new Button(composite1, SWT.CHECK);
		constructor.setText(" Constructor  ");

		Button field = new Button(composite1, SWT.CHECK);
		field.setText(" Field");

		space = new Label(viewArea, SWT.NONE);
		space.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 6, 1));

		Composite composite2 = new Composite(viewArea, SWT.NONE);
		composite2.setLayout(new GridLayout(6, false));
		composite2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 6, 1));

		Label replaceTitle = new Label(composite2, SWT.NONE);
		replaceTitle.setText("Replace: ");

		Text replace = new Text(composite2, SWT.BORDER);
		replace.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));

		space = new Label(composite2, SWT.NONE);
		space.setText("                      ");

		Label withTitle = new Label(composite2, SWT.NONE);
		withTitle.setText("With:    ");

		Text with = new Text(composite2, SWT.BORDER);
		with.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));

		space = new Label(composite2, SWT.NONE);
		space.setText("                      ");

		space = new Label(viewArea, SWT.NONE);
		space.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 6, 1));

		space = new Label(viewArea, SWT.NONE);
		space = new Label(viewArea, SWT.NONE);
		space = new Label(viewArea, SWT.NONE);

		Composite composite3 = new Composite(viewArea, SWT.NONE);
		composite3.setLayout(new GridLayout(3, false));
		composite3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 3, 1));

		Button replaceButton = new Button(composite3, SWT.NONE);
		replaceButton.setText("    Replace    ");

		Button searchButton = new Button(composite3, SWT.NONE);
		searchButton.setText("    Search     ");

		Button cancelButton = new Button(composite3, SWT.NONE);
		cancelButton.setText("    Cancel     ");

		replaceButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {

			}
		});

		searchButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {

				boolean[] conditions = new boolean[9];

				conditions[0] = caseSensitive.getSelection();
				conditions[1] = equals.getSelection();
				conditions[2] = contains.getSelection();
				conditions[3] = startsWith.getSelection();
				conditions[4] = endsWith.getSelection();
				conditions[5] = type.getSelection();
				conditions[6] = constructor.getSelection();
				conditions[7] = method.getSelection();
				conditions[8] = field.getSelection();

				
				
			}
		});

		cancelButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {

			}
		});

		tree = new Tree(viewArea, SWT.BORDER | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 6, 1));
	}

	public static void showTree(List<String> packageclass) {
		packageClass = packageclass;

		searchItem = new TreeItem(tree, SWT.NONE, 0);
		searchItem.setText("Search Results");
		searchItem.setImage(searchIcon);

		i = 0;
		treeBool = true;
		treeItem(searchItem);
	}

	private static void treeItem(TreeItem tree) {
		if (treeBool) {
			packageName = packageClass.get(i).substring(0, packageClass.get(i).lastIndexOf("..") + 2);
			itemName = packageClass.get(i).substring(0, packageClass.get(i).indexOf("."));
			className = packageClass.get(i).substring(packageClass.get(i).lastIndexOf("..") + 2);

			i++;
			treeBool = false;
		}

		for (TreeItem treeItem : tree.getItems()) {
			if (itemName.equals(treeItem.getText())) {
				packageName = packageName.substring(packageName.indexOf(".") + 1);
				if (packageName.length() != 1) {
					itemName = packageName.substring(0, packageName.indexOf("."));
					treeItem(treeItem);
				} else {
					createClassItem(treeItem);
					treeBool = true;
					if(i < packageClass.size())
						treeItem(searchItem);
					else
						return;
				}
			}
		}
		
		if(!treeBool) {
			createPackageItem(tree);
			treeItem(tree);
		}
	}

	private static void createPackageItem(TreeItem treeItem) {
		TreeItem packageItem = new TreeItem(treeItem, SWT.NONE, 0);
		packageItem.setText(itemName);
		packageItem.setImage(packageIcon);
	}

	private static void createClassItem(TreeItem packageItem) {
		TreeItem classItem = new TreeItem(packageItem, SWT.NONE, 0);
		classItem.setText(className);
		classItem.setImage(classIcon);
	}
	
}
