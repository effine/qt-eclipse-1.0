/**
 * @author VerpHen
 * @date 2013��8��13��  ����1:56:41
 */

package c3itop.qt.wizards.pages;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.util.BidiUtils;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.internal.ide.IIDEHelpContextIds;
import org.eclipse.ui.internal.ide.dialogs.ProjectContentsLocationArea;
import org.eclipse.ui.internal.ide.dialogs.ProjectContentsLocationArea.IErrorMessageReporter;

public class QtProjectWizardPageOne extends WizardPage {

	Text projectNameField;
	private String initialProjectFieldValue; // initial value stores
	private ProjectContentsLocationArea locationArea;
	private static final int SIZING_TEXT_FIELD_WIDTH = 250;

	public String getProjectNameField() {
		if (projectNameField != null)
			return projectNameField.getText();
		return "main";
	}

	public QtProjectWizardPageOne(ISelection selection) {
		super("Wizardpage");
		setTitle("Qt Code Project");
		setDescription("Create a new Qt Code Application Project .");
	}

	private Listener nameModifyListener = new Listener() {
		public void handleEvent(Event e) {
			setLocationForSelection();
			boolean valid = validatePage();
			setPageComplete(valid);
		}
	};

	@SuppressWarnings("restriction")
	@Override
	public void createControl(final Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		initializeDialogUnits(parent);

		PlatformUI.getWorkbench().getHelpSystem()
				.setHelp(container, IIDEHelpContextIds.NEW_PROJECT_WIZARD_PAGE);

		container.setLayout(new GridLayout());
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		createProjectNameGroup(container);
		locationArea = new ProjectContentsLocationArea(getErrorReporter(),
				container);
		if (initialProjectFieldValue != null) {
			locationArea.updateProjectName(initialProjectFieldValue);
		}
		// Scale the button based on the rest of the dialog
		setButtonLayoutData(locationArea.getBrowseButton());
		setPageComplete(validatePage());
		setErrorMessage(null);// Show description on opening
		setMessage(null);
		setControl(container);
		Dialog.applyDialogFont(container);

	}

	private final void createProjectNameGroup(Composite parent) {
		// project specification group
		Composite projectGroup = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		projectGroup.setLayout(layout);
		projectGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// new project label
		Label projectLabel = new Label(projectGroup, SWT.NONE);
		projectLabel
				.setText(IDEWorkbenchMessages.WizardNewProjectCreationPage_nameLabel);
		projectLabel.setFont(parent.getFont());

		// new project name entry field
		projectNameField = new Text(projectGroup, SWT.BORDER);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = SIZING_TEXT_FIELD_WIDTH;
		projectNameField.setLayoutData(data);
		projectNameField.setFont(parent.getFont());

		// Set the initial value first before listener
		// to avoid handling an event during the creation.
		if (initialProjectFieldValue != null) {
			projectNameField.setText(initialProjectFieldValue);
		}
		projectNameField.addListener(SWT.Modify, nameModifyListener);
		BidiUtils.applyBidiProcessing(projectNameField, BidiUtils.BTD_DEFAULT);
	}

	private IErrorMessageReporter getErrorReporter() {
		return new IErrorMessageReporter() {
			/*
			 * 
			 * @see
			 * org.eclipse.ui.internal.ide.dialogs.ProjectContentsLocationArea
			 * .IErrorMessageReporter#reportError(java.lang.String)
			 */
			public void reportError(String errorMessage, boolean infoOnly) {
				if (infoOnly) {
					setMessage(errorMessage, IStatus.INFO);
					setErrorMessage(null);
				} else
					setErrorMessage(errorMessage);
				boolean valid = errorMessage == null;
				if (valid) {
					valid = validatePage();
				}
				setPageComplete(valid);
			}
		};
	}

	/**
	 * Returns whether this page's controls currently all contain valid values.
	 * 
	 * @return <code>true</code> if all controls are valid, and
	 *         <code>false</code> if at least one is invalid
	 */
	protected boolean validatePage() {
		IWorkspace workspace = IDEWorkbenchPlugin.getPluginWorkspace();
		String projectFieldContents = getProjectNameFieldValue();

		if (projectFieldContents.equals("")) {
			setErrorMessage(null);
			setMessage(IDEWorkbenchMessages.WizardNewProjectCreationPage_projectNameEmpty);
			return false;
		}

		IStatus nameStatus = workspace.validateName(projectFieldContents,
				IResource.PROJECT);
		if (!nameStatus.isOK()) {
			setErrorMessage(nameStatus.getMessage());
			return false;
		}

		IProject handle = getProjectHandle();
		if (handle.exists()) {
			setErrorMessage(IDEWorkbenchMessages.WizardNewProjectCreationPage_projectExistsMessage);
			return false;
		}

		IProject project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(getProjectNameFieldValue());
		locationArea.setExistingProject(project);

		String validLocationMessage = locationArea.checkValidLocation();
		// there is no destination location given
		if (validLocationMessage != null) {
			setErrorMessage(validLocationMessage);
			return false;
		}

		setErrorMessage(null);
		setMessage(null);
		return true;
	}

	/**
	 * Set the location to the default location if we are set to useDefaults.
	 */
	@SuppressWarnings("restriction")
	void setLocationForSelection() {
		locationArea.updateProjectName(getProjectNameFieldValue());
		QtProjectWizardPageTwo twoPage = new QtProjectWizardPageTwo(null);
	}

	/**
	 * Returns the value of the project name field with leading and trailing
	 * spaces removed.
	 * 
	 * @return the project name in the field
	 */
	private String getProjectNameFieldValue() {
		if (projectNameField == null) {
			return "";
		}
		return projectNameField.getText().trim();
	}

	/**
	 * Creates a project resource handle for the current project name field
	 * value. The project handle is created relative to the workspace root.
	 * <p>
	 * This method does not create the project resource; this is the
	 * responsibility of <code>IProject::create</code> invoked by the new
	 * project resource wizard.
	 * </p>
	 * 
	 * @return the new project resource handle
	 */
	public IProject getProjectHandle() {
		return ResourcesPlugin.getWorkspace().getRoot()
				.getProject(getProjectName());
	}

	/**
	 * Returns the current project name as entered by the user, or its
	 * anticipated initial value.
	 * 
	 * @return the project name, its anticipated initial value, or
	 *         <code>null</code> if no project name is known
	 */
	public String getProjectName() {
		if (projectNameField == null) {
			return initialProjectFieldValue;
		}
		return getProjectNameFieldValue();
	}
}
