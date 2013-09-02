/**
 * @author VerpHen
 * @date 2013年8月27日  下午4:43:26
 */

package c3itop.qt.wizards;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import c3itop.wizards.pages.ProjectNameWizardPage;
import c3itop.wizards.pages.ShowNameWizardPage;

public class QtProjectWizard extends Wizard implements INewWizard {

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

		IRunnableWithProgress rp = new IRunnableWithProgress() {

			@Override
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException, InterruptedException {

				try {
					doFinish(projectDir, projectDir, monitor);
				} catch (CoreException e) {
					e.printStackTrace();
				} finally {
					monitor.done();
				}
			}
		};

		try {
			getContainer().run(true, false, rp);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * The worker method. It will find the container, create the file if missing
	 * or just replace its contents, and open the editor on the newly created
	 * file.
	 */

	private void doFinish(String containerName, String fileName,
			IProgressMonitor monitor) throws CoreException {
		// create a sample file
		monitor.beginTask("Creating " + fileName, 2);
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IResource resource = root.findMember(new Path(containerName));
		if (!resource.exists() || !(resource instanceof IContainer)) {
			/*
			 * throw CoreException("Container \"" + containerName +
			 * "\" does not exist.");
			 */
			System.out
					.println("----------------------------------------------------");
			System.out.println("Container  ~~~  " + containerName
					+ "  ~~~ does not exist .");
		}
		IContainer container = (IContainer) resource;
		final IFile file = container.getFile(new Path(fileName));
		try {
			InputStream stream = openContentStream();
			if (file.exists()) {
				file.setContents(stream, true, true, monitor);
			} else {
				file.create(stream, true, monitor);
			}
			stream.close();
		} catch (IOException e) {
		}
		monitor.worked(1);
		monitor.setTaskName("Opening file for editing...");
		getShell().getDisplay().asyncExec(new Runnable() {
			public void run() {
				IWorkbenchPage page = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage();
				try {
					IDE.openEditor(page, file, true);
				} catch (PartInitException e) {
				}
			}
		});
		monitor.worked(1);
	}

	/**
	 * Initialize file contents with a sample text.
	 */
	private InputStream openContentStream() {

		return this.getClass().getResourceAsStream(
				"/c3itop/qt/template/CppTemplate.cpp");
		// return new ByteArrayInputStream(contents.getBytes());
	}

	/**
	 * We will accept the selection in the workbench to see if we can initialize
	 * from it.
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}

}
