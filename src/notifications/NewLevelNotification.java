package notifications;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.mylyn.commons.ui.dialogs.AbstractNotificationPopup;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

// partial source: http://krishnanmohan.wordpress.com/category/mylyn-notifications/ (accessed on 2014-08-06)
public class NewLevelNotification extends AbstractNotificationPopup {

	private Display display;
	private Logger logger = Logger.getAnonymousLogger();

	public NewLevelNotification(Display display) {
		super(Display.getDefault());
		this.display = display;
	}

	public String toString() {
		return "NEW LEVEL";
	}
	
	@Override
	protected void createContentArea(Composite composite) {
		composite.setLayout(new GridLayout(4, false));

		try {
			// add picture
			URL location = getClass().getResource("/img/notifications/newlevel.png");
			InputStream inputStream = location.openStream();
			final Image ideaImage = new Image(display, inputStream);
			Label label = new Label(composite, SWT.NONE);
			label.setImage(ideaImage);

			// set text
			StyledText text = new StyledText(composite, 0);
			text.setText("Hooray!\nYou can now get new achievements.");
		}
		catch (IOException e1) {
			logger.log(Level.SEVERE, "[NewLevelNotification] Error", e1);
		}
	}

	@Override
	protected String getPopupShellTitle() {
		return "New goals unlocked!";
	}

	@Override
	protected Image getPopupShellImage(int maximumHeight) {
		return null;
	}

}