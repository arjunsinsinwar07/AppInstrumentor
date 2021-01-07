package UI;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class InstallWithoutInstrumentedApk extends Shell {
	private Text text;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			InstallWithoutInstrumentedApk shell = new InstallWithoutInstrumentedApk(display);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the shell.
	 * @param display
	 */
	public InstallWithoutInstrumentedApk(Display display) {
		super(display, SWT.SHELL_TRIM);
		
		Composite composite = new Composite(this, SWT.NONE);
		composite.setBounds(10, 0, 414, 162);
		
		Label lblApkName = new Label(composite, SWT.NONE);
		lblApkName.setBounds(10, 39, 55, 15);
		lblApkName.setText("Apk Name");
		
		text = new Text(composite, SWT.BORDER);
		text.setBounds(71, 36, 208, 21);
		
		Button btnInstall = new Button(composite, SWT.NONE);
		btnInstall.setBounds(138, 92, 75, 25);
		btnInstall.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				
				showProgressDialogForInstall(text.getText().toString());


			}
		});
		btnInstall.setText("Install");
		
		Button btnNewButton = new Button(composite, SWT.NONE);
		btnNewButton.setBounds(289, 34, 75, 25);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				

				FileDialog dirDialog = new FileDialog(Display.getDefault().getActiveShell());
				dirDialog.setFilterPath(text.getText());
				String dir = dirDialog.open();

				if (dir != null)
					text.setText(dir);
				
			}
		});
		btnNewButton.setText("Browse ");
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("SWT Application");
		setSize(450, 201);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	public void installApp(String apkfileDir)
			throws IOException, InterruptedException {
		String adbPath = System.getenv("ANDROID_HOME");
		adbPath = String.valueOf(adbPath) + File.separator + "platform-tools" + File.separator + "adb.exe";
		String commands[] = { adbPath, "install", apkfileDir };
		ProcessBuilder pb = new ProcessBuilder(commands);
		pb.redirectErrorStream(true);
		Process process = pb.start();
		process.waitFor();
		BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
		String line;
		while ((line = br.readLine()) != null) {

			System.out.println(line);
		}

	}
	
	private void showProgressDialogForInstall(final String apkFileDir) {
		MessageDialogs msd = new MessageDialogs();
		msd.openProgressDialog(Display.getDefault().getActiveShell(), "Instrumenting Application! - Please Wait ...",
				true, new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				monitor.beginTask(
						"Installing Application"  + " " + " - Please Wait ...", -1);

				try {
					installApp(apkFileDir);

				} catch (IOException | InterruptedException e) {

					e.printStackTrace();
				}

				monitor.done();

			}
		});
		msd.closeProgressDialog();
	}
}
