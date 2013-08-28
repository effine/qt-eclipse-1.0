/**
 * @author VerpHen
 * @date 2013年8月27日  下午4:43:26
 */

package c3itop.qt.wizards;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.Wizard;

import c3itop.wizards.pages.ProjectNameWizardPage;

public class QtProjectWizard extends Wizard implements IWizard {

	private ISelection selection;
	private ProjectNameWizardPage projectName;

	@Override
	public void addPages() {

		projectName = new ProjectNameWizardPage(selection);
		addPage(projectName);

	}

	@Override
	public boolean performFinish() {

		return false;
	}

}
