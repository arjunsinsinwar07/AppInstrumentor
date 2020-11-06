
package UI;

import javax.inject.Inject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.ProcessBuilder.Redirect;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class Part {
	private Text textInstrumentorDir;
	private Text ApkName;
	private Text InstrumentApkDir;
	private Text InstrumentedApkName;

	@Inject
	public Part() {

	}

	@PostConstruct
	public void postConstruct(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setBounds(0, 0, 695, 325);

		Composite composite_1 = new Composite(composite, SWT.NONE);
		composite_1.setBounds(0, 0, 705, 325);

		Label IntrumentorJarDir = new Label(composite_1, SWT.NONE);
		IntrumentorJarDir.setBounds(10, 30, 131, 20);
		IntrumentorJarDir.setText("InstrumentorJarDir");

		textInstrumentorDir = new Text(composite_1, SWT.BORDER);
		textInstrumentorDir.setBounds(181, 27, 339, 26);
		textInstrumentorDir.setText("C:\\Users\\Admin\\Desktop\\Neonify-Resources");

		Button btnBrowseins = new Button(composite_1, SWT.NONE);
		btnBrowseins.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dirDialog = new DirectoryDialog(Display.getDefault().getActiveShell());
				dirDialog.setFilterPath(textInstrumentorDir.getText());
				String dir = dirDialog.open();

				if (dir != null)
					textInstrumentorDir.setText(dir);
			}
		});
		btnBrowseins.setBounds(541, 23, 90, 30);
		btnBrowseins.setText("Browse");

		Label lblApkname = new Label(composite_1, SWT.NONE);
		lblApkname.setBounds(10, 73, 70, 20);
		lblApkname.setText("ApkName");

		ApkName = new Text(composite_1, SWT.BORDER);
		ApkName.setBounds(181, 70, 340, 26);
		// ApkName.setText("C:\\Users\\Arjun\\Desktop\\NeonifyResources\\OriginalApks\\AlaskaAirlines.apk");

		Button btnBrowseApk = new Button(composite_1, SWT.NONE);
		btnBrowseApk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				FileDialog dirDialog = new FileDialog(Display.getDefault().getActiveShell());
				dirDialog.setFilterPath(ApkName.getText());
				String dir = dirDialog.open();

				if (dir != null)
					ApkName.setText(dir);
			}
		});
		btnBrowseApk.setBounds(541, 66, 90, 30);
		btnBrowseApk.setText("Browse");

		Label lblInstrumentapkdir = new Label(composite_1, SWT.NONE);
		lblInstrumentapkdir.setBounds(10, 128, 141, 20);
		lblInstrumentapkdir.setText("InstrumentedApkDir");

		InstrumentApkDir = new Text(composite_1, SWT.BORDER);
		InstrumentApkDir.setBounds(182, 125, 339, 26);
		InstrumentApkDir.setText("C:\\Users\\Admin\\Desktop\\Neonify-Resources\\InstrumentedApk");

		Button btnBrowseInstrumentApkDir = new Button(composite_1, SWT.NONE);
		btnBrowseInstrumentApkDir.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dirDialog = new DirectoryDialog(Display.getDefault().getActiveShell());
				dirDialog.setFilterPath(InstrumentApkDir.getText());
				String dir = dirDialog.open();

				if (dir != null)
					InstrumentApkDir.setText(dir);
			}
		});
		btnBrowseInstrumentApkDir.setBounds(541, 125, 90, 30);
		btnBrowseInstrumentApkDir.setText("Browse");

		Label lblNewLabel = new Label(composite_1, SWT.NONE);
		lblNewLabel.setBounds(10, 176, 153, 20);
		lblNewLabel.setText("InstrumentedApkName");

		InstrumentedApkName = new Text(composite_1, SWT.BORDER);
		InstrumentedApkName.setBounds(182, 173, 339, 26);
		InstrumentedApkName.setText("Test.apk");

		Button btnInstrumentapk = new Button(composite_1, SWT.NONE);
		btnInstrumentapk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (validate(textInstrumentorDir.getText(), ApkName.getText(), InstrumentApkDir.getText(),
						InstrumentedApkName.getText())) {
					showProgressDialog(textInstrumentorDir.getText(), ApkName.getText(), InstrumentApkDir.getText(),
							InstrumentedApkName.getText());
				}
			}
		});
		btnInstrumentapk.setBounds(69, 227, 119, 30);
		btnInstrumentapk.setText("InstrumentApk");

		Button InstallApp = new Button(composite_1, SWT.NONE);
		InstallApp.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (validateInstallation(InstrumentApkDir.getText(), InstrumentedApkName.getText()))
					showProgressDialogForInstall(InstrumentApkDir.getText(), InstrumentedApkName.getText());
			}
		});
		InstallApp.setBounds(217, 227, 90, 30);
		InstallApp.setText("InstallApk");

		Button browseInstrumentedApk = new Button(composite_1, SWT.NONE);
		browseInstrumentedApk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dirDialog = new FileDialog(Display.getDefault().getActiveShell());
				dirDialog.setFilterPath(InstrumentedApkName.getText());
				String dir = dirDialog.open();

				if (dir != null) {
					dir=dir.substring(dir.lastIndexOf(File.separator)+1,dir.length()).trim();
					InstrumentedApkName.setText(dir);
				}
			}

		});
		browseInstrumentedApk.setBounds(541, 166, 90, 30);
		browseInstrumentedApk.setText("Browse");

		Button btnPortfrorwardto = new Button(composite_1, SWT.NONE);
		btnPortfrorwardto.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					PortForward9899("tcp:9899", "tcp:989");
				} catch (IOException | InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnPortfrorwardto.setBounds(335, 227, 153, 30);
		btnPortfrorwardto.setText("PortFrorwardTo9899");

		Button PortForward2 = new Button(composite_1, SWT.NONE);
		PortForward2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					PortForward9898("tcp:9898", "9899");
				} catch (IOException | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		PortForward2.setBounds(517, 227, 153, 30);
		PortForward2.setText("PortForwardTo9898");
		
		Button InstallWithoutInstrumented = new Button(composite_1, SWT.NONE);
		InstallWithoutInstrumented.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Display display = Display.getDefault();
				InstallWithoutInstrumentedApk shell=new InstallWithoutInstrumentedApk(display);
				shell.open();
				
			}
		});
		InstallWithoutInstrumented.setBounds(243, 279, 212, 25);
		InstallWithoutInstrumented.setText("InstallWithoutInstrumentedApk");

	}

	private void startProcess(String instrumentorDir, String ApkName, String instrumentedApkDir,
			String instrumentedApkName) throws IOException, InterruptedException {
		String adbPath = System.getenv("ANDROID_HOME");
		adbPath = String.valueOf(adbPath) + File.separator + "platform-tools" + File.separator + "adb.exe";

		String instrumentorJarPath = instrumentorDir + File.separator + "Instrumentor.jar";
		String InstrumentedApkNamePath = instrumentedApkDir + File.separator + instrumentedApkName;
		String commands[] = { "java", "-jar", instrumentorJarPath, ApkName, InstrumentedApkNamePath };
		ProcessBuilder pb = new ProcessBuilder(commands);
		pb.directory(new File(instrumentorDir));
		File log = new File("log.txt");
		pb.redirectErrorStream(true);
		pb.redirectOutput(Redirect.appendTo(log));
		Process process = pb.start();
		process.waitFor();
		BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream(), StandardCharsets.UTF_8));
		String line;
		while ((line = br.readLine()) != null) {

			System.out.println(line);
		}
		 	
		 
	}

	private void showProgressDialog(final String instrumentorDir, final String apkName, final String instrumentedApkDir,
			final String instrumentrdApkName) {
		MessageDialogs msd = new MessageDialogs();
		msd.openProgressDialog(Display.getDefault().getActiveShell(), "Instrumenting Application! - Please Wait ...",
				true, new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				monitor.beginTask("Instrumenting Application! - Please Wait ...", -1);

				try {

					startProcess(instrumentorDir, apkName, instrumentedApkDir, instrumentrdApkName);
				} catch (IOException | InterruptedException e) {

					e.printStackTrace();
				}

				monitor.done();

			}
		});
		msd.closeProgressDialog();
	}

	private void showProgressDialogForInstall(final String instrumentedApkDir, final String instrumentrdApkName) {
		MessageDialogs msd = new MessageDialogs();
		msd.openProgressDialog(Display.getDefault().getActiveShell(), "Instrumenting Application! - Please Wait ...",
				true, new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				monitor.beginTask(
						"Installing Application" + " " + instrumentrdApkName + " " + " - Please Wait ...", -1);

				try {
					installApp(instrumentedApkDir, instrumentrdApkName);

				} catch (IOException | InterruptedException e) {

					e.printStackTrace();
				}

				monitor.done();

			}
		});
		msd.closeProgressDialog();
	}

	public void installApp(String instrumentedApkDir, String instrumentedApkName)
			throws IOException, InterruptedException {
		String adbPath = System.getenv("ANDROID_HOME");
		adbPath = String.valueOf(adbPath) + File.separator + "platform-tools" + File.separator + "adb.exe";
		String instrumentedApkPath = instrumentedApkDir + File.separator + instrumentedApkName;
		String commands[] = { adbPath, "install", instrumentedApkPath };
		ProcessBuilder pb = new ProcessBuilder(commands);
		pb.directory(new File("C:\\Users\\Admin\\Desktop\\Neonify-Resources"));
		File log = new File("log");
		pb.redirectErrorStream(true);
		pb.redirectOutput(Redirect.appendTo(log));
		Process process = pb.start();
		process.waitFor();
		BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
		String line;
		while ((line = br.readLine()) != null) {

			System.out.println(line);
		}

	}

	public boolean validate(String instrumentorDir, String apkName, String instrumentApkDir,
			String instrumentedApkName) {
		if (instrumentorDir.equals("")) {
			MessageDialog.openInformation(Display.getDefault().getActiveShell(), "Info",
					"InstrumentorDirectory cannot be empty");
			textInstrumentorDir.setFocus();
			return false;
		}

		if (apkName.equals("")) {
			MessageDialog.openInformation(Display.getDefault().getActiveShell(), "Info", "ApkName cannot be empty");
			ApkName.setFocus();
			return false;
		}

		if (instrumentApkDir.equals("")) {
			MessageDialog.openInformation(Display.getDefault().getActiveShell(), "Info",
					"InstrumentApkDirectory cannot be empty");
			InstrumentApkDir.setFocus();
			return false;
		}

		if (instrumentedApkName.equals("")) {
			MessageDialog.openInformation(Display.getDefault().getActiveShell(), "Info",
					"InstrumentedApk Name cannot be empty");
			InstrumentedApkName.setFocus();
			return false;
		}

		return true;
	}

	public boolean validateInstallation(String instrumentApkDir, String instrumentedApkName) {

		if (instrumentApkDir.equals("")) {
			MessageDialog.openInformation(Display.getDefault().getActiveShell(), "Info",
					"InstrumentApkDirectory cannot be empty");
			InstrumentApkDir.setFocus();
			return false;
		}

		if (instrumentedApkName.equals("")) {
			MessageDialog.openInformation(Display.getDefault().getActiveShell(), "Info",
					"InstrumentedApk Name cannot be empty");
			InstrumentedApkName.setFocus();
			return false;
		}

		return true;
	}

	public void PortForward9899(String port1, String port2) throws IOException, InterruptedException {
		String command = "cmd /c start cmd.exe /k adb " + " forward" + " tcp:9899" + " tcp:9899";
		Process process = Runtime.getRuntime().exec(command);
		process.waitFor();
		BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
		String line;
		while ((line = br.readLine()) != null) {
			System.out.println(line);
		}

	}

	public void PortForward9898(String port1, String port2) throws IOException, InterruptedException {
		String command = "cmd /c start cmd.exe /k adb " + " forward" + " tcp:9898" + " tcp:9898";
		Process process = Runtime.getRuntime().exec(command);
		process.waitFor();
		BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
		String line;
		while ((line = br.readLine()) != null) {
			System.out.println(line);
		}

	}
}