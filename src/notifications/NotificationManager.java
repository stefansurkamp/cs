package notifications;

import java.util.ArrayList;

import org.eclipse.mylyn.commons.ui.dialogs.AbstractNotificationPopup;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Display;

import achievements.SoundPlayer;

public class NotificationManager {

	private static ArrayList<AbstractNotificationPopup> notificationList = new ArrayList<AbstractNotificationPopup>();
	private static int runningNotifications = 0;

	public static void add(AbstractNotificationPopup notification) {
		notificationList.add(notification);
	}

	public static void showNotification() {
		if(runningNotifications == 0 && notificationList.size() > 0) {
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					runningNotifications++;
					SoundPlayer soundPlayer = new SoundPlayer();
					
					if(notificationList.get(0) instanceof AchievementNotification) {
						AchievementNotification notification = (AchievementNotification) notificationList.get(0);
						notification.open();
						soundPlayer.play("achievement.wav");
	
						notification.getShell().addDisposeListener(new DisposeListener() {
							@Override
							// wait until current notification is disposed
							public void widgetDisposed(DisposeEvent e) {
								notificationList.remove(0); // removes current achievement
								runningNotifications--;
								showNotification(); // returns to start of method
							}
						});
					}
					else {
						NewLevelNotification notification = (NewLevelNotification) notificationList.get(0);
						notification.open();
						soundPlayer.play("level.wav");
	
						notification.getShell().addDisposeListener(new DisposeListener() {
							@Override
							// wait until current notification is disposed
							public void widgetDisposed(DisposeEvent e) {
								notificationList.remove(0); // removes current achievement
								runningNotifications--;
								showNotification(); // returns to start of method
							}
						});
					}
				}
			});
		}
	}
	
}
