package achievements;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import notifications.AchievementNotification;
import notifications.NewLevelNotification;
import notifications.NotificationManager;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.swt.widgets.Display;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonObject.Member;

public class AchievementManager {
	private JsonObject jsonObject;
	private Logger logger = Logger.getAnonymousLogger();

	// read JSON String from preferences
	public void readAchievements() {
		DataStorage dataStorage = new DataStorage();
		jsonObject = JsonObject.readFrom(dataStorage.getAchievements()); // readFrom is buffered
	}

	// write JSON String to preferences
	private void writeAchievements() {
		DataStorage dataStorage = new DataStorage();
		dataStorage.setAchievements(jsonObject.toString());
	}

	// GETTER
	private String getDescription(String name) {
		return jsonObject.get(name).asObject().get("description").asString();
	}

	private int getPoints(String name) {
		return jsonObject.get(name).asObject().get("points").asInt();
	}

	private boolean isActive(String name) {
		return jsonObject.get(name).asObject().get("active").asBoolean();
	}

	private boolean isComplete(String name) {
		return jsonObject.get(name).asObject().get("complete").asBoolean();
	}

	private ArrayList<Member> getActiveAchievements() {
		ArrayList<Member> activeAchievements = new ArrayList<Member>();
		for (Member member : jsonObject) {
			if(isActiveAndNotCompleted(member)) {
				activeAchievements.add(member);
			}
		}
		return activeAchievements;
	}

	private boolean isActiveAndNotCompleted(Member member) {
		if(isActive(member.getName()) && !isComplete(member.getName())) {
			logger.log(Level.FINE, "[AchievementManager] " + member.getValue().asObject().get("name").asString() + " is active and not completed.");
			return true;
		}
		return false;
	}

	// SETTER
	private void setDate(String name, long timestamp) {
		jsonObject.get(name).asObject().set("date", timestamp);
	}

	private void setActive(String name, boolean value) {
		jsonObject.get(name).asObject().set("active", value);
		writeAchievements();
	}

	private void setComplete(String name, boolean value) {
		jsonObject.get(name).asObject().set("complete", value);
	}

	// OTHER
	public void checkActiveAchievements(ICompilationUnit unit) {
		ArrayList<Member> activeAchievements = getActiveAchievements();
		for (final Member member : activeAchievements) {
			if(isConditionFulfilled(unit, member)) { // if condition for active achievements are fulfilled by current class
				final String memberName = member.getValue().asObject().get("name").asString();
				final String memberDescription = getDescription(member.getName());
				final int memberPoints = getPoints(member.getName());
				logger.log(Level.FINE, "[AchievementManager] Condition fulfilled for: " + memberName);

				// set new information in jsonObject and DataStorage
				setComplete(member.getName(), true);
				setDate(member.getName(), System.currentTimeMillis());
				DataStorage dataStorage = new DataStorage();
				dataStorage.addScore(memberPoints);

				// add notification to Manager
				addNotification(Display.getDefault(), member.getName(), memberName, memberDescription, memberPoints);
				writeAchievements();
			}
		}
		checkIfNewAchievementsUnlocked();
	}

	// adds an achievement notification to the list; public because FirstStartupJob needs access
	public void addNotification(final Display display, final String memberID, final String memberName, final String memberDescription, final int memberPoints) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				AchievementNotification notification = new AchievementNotification(display, memberID, memberName, memberDescription, memberPoints);
				NotificationManager.add(notification);
				logger.log(Level.FINE, "[AchievementManager] Notification added for: " + memberName);
			}
		});
	}
	
	// adds an new level notification to the list
	private void addNewLevelNotification() {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				NewLevelNotification notification = new NewLevelNotification(Display.getDefault());
				NotificationManager.add(notification);
			}
		});
	}

	private boolean isConditionFulfilled(ICompilationUnit iCompilationUnit, Member member) {
		AchievementPicker achievementPicker = new AchievementPicker();
		return achievementPicker.forName(iCompilationUnit, member.getName());
	}

	private void checkIfNewAchievementsUnlocked() {
		DataStorage dataStorage = new DataStorage();
		int score = dataStorage.getScore();
		if(score >= 280) {
			// activate 4th row if not already happened
			if(!isActive("tooManyStaticMethods")) {
				setActive("tooManyStaticMethods", true);
				setActive("compileAtNight", true);
				setActive("useTernaryOperator", true);
				setActive("tooManyPublicVariables", true);
				setActive("tooManyComments", true);

				addNewLevelNotification();
			}
		}
		else if(score >= 190) {
			// activate 3rd row if not already happened
			if(!isActive("useConstructor")) {
				setActive("useConstructor", true);
				setActive("useTypecast", true);
				setActive("useIncrement", true);
				setActive("variableLowerCamelCase", true);
				setActive("objectUpperCamelCase", true);

				addNewLevelNotification();
			}
		}
		else if(score >= 130) {
			// activate 2nd row if not already happened
			if(!isActive("create2dArray")) {
				setActive("create2dArray", true);
				setActive("usePrimitiveTypes", true);
				setActive("compileOnWeekend", true);
				setActive("useAllLoops", true);
				setActive("switchUseDefaultStatement", true);

				addNewLevelNotification();
			}
		}
	}
	
}
