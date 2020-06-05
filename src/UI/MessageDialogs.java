package UI;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class MessageDialogs {
	private ProgressMonitorDialog progressDialog;

	public String openInputDialogAandGetValue(String dialogTitle, String dialogContent, String defaultValue) {
		InputDialog input = new InputDialog(Display.getCurrent().getActiveShell(), dialogTitle, dialogContent,
				defaultValue, null);
		if (input.open() != InputDialog.OK) {
			return null;
		}
		return input.getValue();
	}

	public boolean openConfirmDialog(String title, String message) {
		boolean result = MessageDialog.openConfirm(Display.getCurrent().getActiveShell(), title, message);
		return result;
	}

	public void openInformationDialog(String title, String message) {
		MessageDialog.openInformation(Display.getCurrent().getActiveShell(), title, message);
	}

	public void openErrorDialog(String title, String message) {
		MessageDialog.openError(Display.getCurrent().getActiveShell(), title, message);
	}

	public void openErrorDialog(Shell shell, String title, String message) {
		if (shell.isDisposed()) {
			return;
		}
		MessageDialog.openError(shell, title, message);
	}

	public void openProgressDialog(Shell shell, String message) {
		if (shell == null) {
			shell = Display.getDefault().getActiveShell();
		}
		progressDialog = new ProgressMonitorDialog(shell);
		try {
			progressDialog.run(false, false, new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor) {
					monitor.setTaskName(message);

				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		progressDialog.open();
	}

	public void openProgressDialog(Shell shell, String message, boolean cancelabel, IRunnableWithProgress runnable) {
		if (shell == null) {
			shell = Display.getDefault().getActiveShell();
		}
		progressDialog = new ProgressMonitorDialog(shell);
		try {
			progressDialog.run(true, cancelabel, new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor) {
					monitor.setTaskName(message);
					try {
						runnable.run(monitor);
					} catch (InvocationTargetException | InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		progressDialog.open();
	}

	public void openProgressDialogOnBackgroundThread(Shell shell, String message, boolean cancelabel,
			IRunnableWithProgress runnable) {
		if (shell == null) {
			shell = Display.getDefault().getActiveShell();
		}
		progressDialog = new ProgressMonitorDialog(shell);
		try {
			progressDialog.run(true, cancelabel, runnable);
		} catch (InvocationTargetException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		progressDialog.open();
	}

	public void closeProgressDialog() {
		this.progressDialog.close();
	}

	public void executeDisplayAsync(Runnable runnable) {
		Display.getDefault().asyncExec(runnable);
	}
}
