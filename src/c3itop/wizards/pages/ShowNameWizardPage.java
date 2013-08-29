/**
 * @author VerpHen
 * @date 2013年8月29日  上午10:31:01
 */

package c3itop.wizards.pages;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class ShowNameWizardPage extends WizardPage {

	private ISelection selection;
	private Text proName;
	private Text cppName;

	public ShowNameWizardPage(ISelection selection) {
		super("Wizardpage");
		setTitle("Qt Code Project");
		setDescription("show project default file names .");
		this.selection = selection;
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);

		setControl(container);

		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setBounds(10, 26, 105, 24);
		lblNewLabel.setText("Source Filename：");

		Label lblPro = new Label(container, SWT.NONE);
		lblPro.setBounds(10, 65, 105, 17);
		lblPro.setText("Pro Filename：");

		proName = new Text(container, SWT.BORDER);
		proName.setBounds(121, 62, 355, 23);

		cppName = new Text(container, SWT.BORDER);
		cppName.setBounds(121, 23, 355, 23);
	}
}
