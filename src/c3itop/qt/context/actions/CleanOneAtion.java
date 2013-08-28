/**
 * @author VerpHen
 * @date 2013年8月26日  下午3:49:38
 */

package c3itop.qt.context.actions;

import java.io.IOException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class CleanOneAtion implements IObjectActionDelegate {

	/*
	 * ((IProject) selection[i]).build(IncrementalProjectBuilder.CLEAN_BUILD,
	 * new SubProgressMonitor(monitor, 1));
	 */

	@Override
	public void run(IAction arg0) {

		Runtime runtime = Runtime.getRuntime();
		try {
			runtime.exec("cmd /c mkdir 333");

			runtime.exec("cmd /c calc");

			runtime.exec("cmd /c echo 'nihao Qt' >34.bat");

			runtime.exec("cmd /c mkdir 4444");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void selectionChanged(IAction arg0, ISelection arg1) {
	}

	@Override
	public void setActivePart(IAction arg0, IWorkbenchPart arg1) {
	}

}
