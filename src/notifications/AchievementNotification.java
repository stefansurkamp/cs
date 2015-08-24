package notifications;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.mylyn.commons.ui.dialogs.AbstractNotificationPopup;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

// partial source: http://krishnanmohan.wordpress.com/category/mylyn-notifications/ (accessed on 2014-08-06)
public class AchievementNotification extends AbstractNotificationPopup {

	private Display display;
	private String achievementID;
	private String achievementName;
	private String achievementDescription;
	private String achievementPoints;
	private Logger logger = Logger.getAnonymousLogger();

	public AchievementNotification(Display display, String memberID, String aName, String aDescription, int memberPoints) {
		super(display);
		this.display = display;
		achievementID = memberID;
		achievementName = aName;
		achievementDescription = aDescription;
		achievementPoints = "You receive " + Integer.toString(memberPoints) + " points!";
	}

	public String toString() {
		return achievementName;
	}
	
	@Override
	protected void createContentArea(Composite composite) {
		composite.setLayout(new GridLayout(4, false));
		try {
			// add picture
			URL location = getClass().getResource("/img/notifications/" + achievementID + ".png");
			InputStream inputStream = location.openStream();
			final Image ideaImage = new Image(display, inputStream);			
			Label label = new Label(composite, SWT.NONE);
			label.setImage(ideaImage);
			
			// set text
			StyledText text = new StyledText(composite, 0);
			text.setText(achievementName + "\n" + achievementDescription + "\n\n" + achievementPoints);

			// set achievement name bold
			StyleRange titleStyle = new StyleRange();
			titleStyle.start = 0;
			titleStyle.length = achievementName.length();
			titleStyle.fontStyle = SWT.BOLD;
			text.setStyleRange(titleStyle);
		}
		catch (IOException e1) {
			logger.log(Level.SEVERE, "[AchievementNotification] Error", e1);
		}
	}

	@Override
	protected String getPopupShellTitle() {
		return "New achievement!";
	}

	@Override
	protected Image getPopupShellImage(int maximumHeight) {
		return null;
	}

}