/**
 * @author VerpHen
 * @date 2013��8��13��  ����1:56:41
 */

package c3itop.wizards.pages;

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
	private Text projectDir;

	private ISelection selection;

	public ProjectNameWizardPage(ISelection selection) {
		super("Wizardpage");
		this.selection = selection;
		setTitle("Qt Code Project");
		setDescription("Create a new Qt Code Application Project .");
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);

		setControl(container);

		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setBounds(10, 21, 82, 24);
		lblNewLabel.setText("&Project name:");

		projectName = new Text(container, SWT.BORDER);
		projectName.setBounds(98, 21, 354, 23);

		Label lblDirectory = new Label(container, SWT.NONE);
		lblDirectory.setBounds(10, 89, 58, 20);
		lblDirectory.setText("&Location:");

		projectDir = new Text(container, SWT.BORDER);
		projectDir.setBounds(81, 86, 279, 23);

		Button btnBrowse = new Button(container, SWT.NONE);
		btnBrowse.setBounds(366, 83, 86, 28);
		btnBrowse.setText("B&rowse...");

		Button btnCreateProjectIn = new Button(container, SWT.CHECK);
		btnCreateProjectIn.setText("Create Project In Workspace");
		btnCreateProjectIn.setBounds(10, 60, 201, 17);
	}

	/* 返回工程名 */
	public String getProjectName() {
		return projectName.getText();
	}

	/* 返回工程目录 */
	public String getProjectDir() {
		return projectDir.getText();
	}
}
