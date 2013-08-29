/**
 * @author VerpHen
 * @date 2013年8月27日  下午4:43:26
 */

package c3itop.qt.wizards;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IWorkbench;

import c3itop.wizards.pages.ProjectNameWizardPage;
import c3itop.wizards.pages.ShowNameWizardPage;

public class QtProjectWizard extends Wizard implements IWizard {

	private ISelection selection;
	private ProjectNameWizardPage projectNamePage;
	private ShowNameWizardPage showNamePage;

	public QtProjectWizard() {
		super();
		setNeedsProgressMonitor(true);
	}

	@Override
	public void addPages() {
		if (projectNamePage == null)
			projectNamePage = new ProjectNameWizardPage(selection);
		addPage(projectNamePage);

		if (showNamePage == null)
			showNamePage = new ShowNameWizardPage(selection);
		addPage(showNamePage);

	}

	@Override
	public boolean performFinish() {
		final String projectName = projectNamePage.getProjectName();
		final String projectDir = projectNamePage.getProjectDir();

		return false;
	}

	/*
	 * We will accept the selection in the workbench to see if we can initialize
	 * from it.
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}

}
