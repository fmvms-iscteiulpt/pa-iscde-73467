package pt.iscte.tasklist.testExt;

import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;

import pt.iscte.pidesco.extensibility.PidescoView;

public class TaskTestView implements PidescoView{

	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		// TODO Auto-generated method stub
		viewArea.setLayout(new RowLayout(SWT.HORIZONTAL));
		Label label = new Label(viewArea, SWT.NONE);
		label.setImage(imageMap.get("smiley.png"));
		Text text = new Text(viewArea, SWT.BORDER);
		text.setText("Hello world");
	}

	
}
