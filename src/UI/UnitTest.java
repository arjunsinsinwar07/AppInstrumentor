package UI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.plugin.mobility.Global;
import com.plugin.mobility.MobileObject;
import com.plugin.mobility.driver.By;
import com.plugin.mobility.driver.MODE;
import com.plugin.mobility.driver.MobilityAndroidDriver;
import com.plugin.mobility.driver.interfaces.WebElement;

public class UnitTest extends Shell {
	private Text ByText;
	private Text ByInnerText;
	private Text ById;
	private Text ByXpath;
	private static MobilityAndroidDriver _androidDriver;
private	List<WebElement> global_element=new ArrayList<WebElement>();
private Text senkeys;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			UnitTest shell = new UnitTest(display);
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
	public UnitTest(Display display) {
		super(display, SWT.SHELL_TRIM);
		
		Composite composite = new Composite(this, SWT.NONE);
		composite.setBounds(10, 0, 414, 251);
		
		Label lblText = new Label(composite, SWT.NONE);
		lblText.setBounds(31, 27, 55, 15);
		lblText.setText("Text");
		
		ByText = new Text(composite, SWT.BORDER);
		ByText.setBounds(88, 27, 184, 21);
		
		Button findElemntByText = new Button(composite, SWT.NONE);
		findElemntByText.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				try {
					setup();
				} catch (IOException | InterruptedException e2) {
					
					e2.printStackTrace();
				}
				try {
				
					List<WebElement> ele = _androidDriver.findElements("*", By.text(ByText.getText()));
					System.out.println("Total element found "+ele.size());
					global_element.clear();
					global_element.addAll(ele);
					if(global_element.size()>=1) {
						int size=global_element.size();
					global_element.get(size-1).HighlightElement();
					}else {
						global_element.get(0).HighlightElement();
					}
						
					//ele.get(0).HighlightElement();
				} catch (Exception e1) {
				
					e1.printStackTrace();
				}
			}
		});
		findElemntByText.setBounds(278, 23, 136, 25);
		findElemntByText.setText("FindElementByText");
		
		Label lblInnertext = new Label(composite, SWT.NONE);
		lblInnertext.setBounds(31, 61, 55, 15);
		lblInnertext.setText("innerText");
		
		ByInnerText = new Text(composite, SWT.BORDER);
		ByInnerText.setBounds(88, 55, 184, 21);
		
		Button findElementByInnerText = new Button(composite, SWT.NONE);
		findElementByInnerText.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					setup();
				} catch (IOException | InterruptedException e2) {
					
					e2.printStackTrace();
				}
				try {
					 if (_androidDriver.IsHybridView()) { _androidDriver.switchContext(MODE.HYBRID); }
					List<WebElement> ele = _androidDriver.findElements("*", By.innerText(ByInnerText.getText()));
					System.out.println("Total element found "+ele.size());
					global_element.clear();
					global_element.addAll(ele);
					global_element.get(0).HighlightElement();
					
				} catch (Exception e1) {
					
					e1.printStackTrace();
				}
			}
		});
		findElementByInnerText.setBounds(278, 54, 136, 25);
		findElementByInnerText.setText("findElementByInnerText");
		
		Label lblId = new Label(composite, SWT.NONE);
		lblId.setBounds(31, 92, 55, 15);
		lblId.setText("Id");
		
		ById = new Text(composite, SWT.BORDER);
		ById.setBounds(88, 82, 184, 21);
		
		Button findElementById = new Button(composite, SWT.NONE);
		findElementById.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					setup();
				} catch (IOException | InterruptedException e2) {
					
					e2.printStackTrace();
				}
				try {

					List<WebElement> ele = _androidDriver.findElements("*", By.id(ById.getText()));
					System.out.println("Total element found "+ele.size());
					global_element.clear();
					global_element.addAll(ele);
					global_element.get(0).HighlightElement();
					
				} catch (Exception e1) {
					
					e1.printStackTrace();
				}
			}
		});
		findElementById.setBounds(278, 85, 136, 25);
		findElementById.setText("findElementById");
		
		Label lblXpath = new Label(composite, SWT.NONE);
		lblXpath.setBounds(31, 124, 55, 15);
		lblXpath.setText("xpath");
		
		ByXpath = new Text(composite, SWT.BORDER);
		ByXpath.setBounds(88, 118, 184, 21);
		
		Button findElementByXpath = new Button(composite, SWT.NONE);
		findElementByXpath.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					setup();
				} catch (IOException | InterruptedException e2) {
				
					e2.printStackTrace();
				}
				try {
					
					List<WebElement> ele = _androidDriver.findElements("*", By.xpath(ByXpath.getText()));
					System.out.println("Total element found "+ele.size());
					global_element.clear();
					global_element.addAll(ele);
					global_element.get(0).HighlightElement();
					
				} catch (Exception e1) {
										e1.printStackTrace();
				}
			}
		});
		findElementByXpath.setBounds(278, 114, 136, 25);
		findElementByXpath.setText("findElementByXpath");
		
		Button Highlight = new Button(composite, SWT.NONE);
		Highlight.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					global_element.get(0).HighlightElement();
				} catch (Exception e1) {
					
					e1.printStackTrace();
				}
			}
		});
		Highlight.setBounds(24, 178, 75, 25);
		Highlight.setText("Highlight");
		
		Button Click = new Button(composite, SWT.NONE);
		Click.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					global_element.get(0).click();
				} catch (Exception e1) {
					
					e1.printStackTrace();
				}
			}
		});
		Click.setBounds(169, 178, 75, 25);
		Click.setText("Click");
		
		Button Sendkeys = new Button(composite, SWT.NONE);
		Sendkeys.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					global_element.get(0).sendKeys(Sendkeys.getText());
				} catch (Exception e1) {
					
					e1.printStackTrace();
				}
			}
		});
		Sendkeys.setBounds(169, 207, 75, 25);
		Sendkeys.setText("SendKeys");
		
		senkeys = new Text(composite, SWT.BORDER);
		senkeys.setBounds(24, 209, 139, 21);
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("SWT Application");
		setSize(450, 300);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	public static void setup() throws IOException, InterruptedException {
		Global.mobileObject = new MobileObject("localhost", 9898);
		_androidDriver = new MobilityAndroidDriver(Global.mobileObject);
	}
}
