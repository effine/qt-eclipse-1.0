/**
 * @author VerpHen
 * @date 2013年8月27日  下午4:43:26
 */

package c3itop.qt.wizards;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import c3itop.wizards.pages.ProjectNameWizardPage;
import c3itop.wizards.pages.ShowNameWizardPage;

public class QtProjectWizard extends Wizard implements INewWizard {

	private ISelection selection;
	private ProjectNameWizardPage projectNamePage;
	private ShowNameWizardPage showNamePage;

	public QtProjectWizard() {
		super();
		// setNeedsProgressMonitor(true);
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

		/* 获取工作区 */
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject project = root.getProject(projectName);
		IWorkspace workspace = root.getWorkspace();
		IProjectDescription description = workspace
				.newProjectDescription(project.getName());
		description.setLocation(null);

		try {
			NullProgressMonitor monitor = new NullProgressMonitor();
			project.create(description, monitor);
			project.open(IResource.BACKGROUND_REFRESH, new SubProgressMonitor(
					monitor, 1000));
		} catch (CoreException e) {
			e.printStackTrace();
		}

		/* 刷新本地资源 */
		// project.refreshLocal(IResource.DEPTH_INFINITE, null);

		return true;
	}

	/*
	 * We will accept the selection in the workbench to see if we can initialize
	 * from it.
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}

}
