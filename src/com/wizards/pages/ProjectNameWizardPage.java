/**
 * @author VerpHen
 * @date 2013��8��13��  ����1:56:41
 */

package com.wizards.pages;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class ProjectNameWizardPage extends WizardPage {

	private Text projectName;
	private Text text;
	private Text projectDirectory;

	private ISelection selection;

	public ProjectNameWizardPage(ISelection selection) {
		super("Wizardpage");
		setTitle("Project");
		setDescription("Create a new qt code project .");
		this.selection = selection;
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);

		setControl(container);

		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setBounds(10, 10, 82, 24);
		lblNewLabel.setText("&Project name:");

		projectName = new Text(container, SWT.BORDER);
		projectName.setBounds(98, 10, 449, 23);

		Group grpLocation = new Group(container, SWT.NONE);
		grpLocation.setText("Location");
		grpLocation.setBounds(10, 47, 537, 132);

		Button btn1 = new Button(grpLocation, SWT.RADIO);
		btn1.setSelection(true);
		btn1.setBounds(10, 26, 185, 17);
		btn1.setText("Create project in &workspace");

		Button btn2 = new Button(grpLocation, SWT.RADIO);
		btn2.setBounds(10, 49, 226, 17);
		btn2.setText("Create project in e&xternal location");

		Button btn3 = new Button(grpLocation, SWT.RADIO);
		btn3.setBounds(10, 72, 394, 17);
		btn3.setText("Create project in workspace with &content at external location ");

		Label lblDirectory = new Label(grpLocation, SWT.NONE);
		lblDirectory.setBounds(10, 95, 61, 17);
		lblDirectory.setText("Directory:");

		projectDirectory = new Text(grpLocation, SWT.BORDER);
		projectDirectory.setBounds(77, 95, 364, 23);

		Button btnBrowse = new Button(grpLocation, SWT.NONE);
		btnBrowse.setBounds(447, 93, 80, 27);
		btnBrowse.setText("B&rowse...");
	}
}
