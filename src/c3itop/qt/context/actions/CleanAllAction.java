/**
 * @author VerpHen
 * @date 2013年8月26日  下午3:49:47
 */

package c3itop.qt.context.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class CleanAllAction implements IObjectActionDelegate {

	@Override
	public void run(IAction arg0) {

		/*
		 * ResourcesPlugin.getWorkspace().build(
		 * IncrementalProjectBuilder.CLEAN_BUILD, monitor);
		 */
	}

	@Override
	public void selectionChanged(IAction arg0, ISelection arg1) {
	}

	@Override
	public void setActivePart(IAction arg0, IWorkbenchPart arg1) {
	}

}
