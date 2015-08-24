package main;

import handlers.WebsiteGenerator;

import java.util.logging.Level;
import java.util.logging.Logger;

import notifications.NotificationManager;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.progress.UIJob;

import com.eclipsesource.json.JsonObject;

import achievements.AchievementManager;
import achievements.DataStorage;

public class FirstStartupJob extends UIJob {

	private Logger logger = Logger.getAnonymousLogger();
	
	public FirstStartupJob(String name) {
		super(name);
	}

	@Override
	public IStatus runInUIThread(IProgressMonitor monitor) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				DataStorage dataStorage = new DataStorage();
				JsonObject jsonObject = JsonObject.readFrom(dataStorage.getAchievements()); // readFrom is buffered
				if(!jsonObject.get("firstStartup").asObject().get("complete").asBoolean()) {
					logger.log(Level.FINE, "[FirstStartupJob] firstStartup is not complete.");
					jsonObject.get("firstStartup").asObject().set("complete", true);
					jsonObject.get("firstStartup").asObject().set("date", System.currentTimeMillis());
					dataStorage.addScore(jsonObject.get("firstStartup").asObject().get("points").asInt());
					dataStorage.setAchievements(jsonObject.toString());

					AchievementManager achievementManager = new AchievementManager();
					achievementManager.addNotification(Display.getDefault(), "firstStartup", "Ready for takeoff", "You have successfully installed the plug-in!", 100);
					NotificationManager.showNotification();

					WebsiteGenerator wg = new WebsiteGenerator();
					wg.createPage();
				}
			}
		});
		return Status.OK_STATUS;
	}

}
