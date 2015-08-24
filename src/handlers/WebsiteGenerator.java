package handlers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonObject.Member;

import achievements.DataStorage;

public class WebsiteGenerator {

	private StringBuilder output = new StringBuilder();
	private ArrayList<Member> achievementList = new ArrayList<Member>();
	private Logger logger = Logger.getAnonymousLogger();
	
	public WebsiteGenerator() {
		try {
			// get location information of images
			BundleContext bc = FrameworkUtil.getBundle(WebsiteGenerator.class).getBundleContext();
			achievementList = getAllAchievements();
			String location = "";
			
			// load every single achievement - essential because otherwise no images are shown
			for(Member achievement: achievementList) {
				URL configURL = bc.getBundle().getResource("/img/html/" + achievement.getName() + ".png");
				File configFile = new File(FileLocator.toFileURL(configURL).getPath());
				location = configFile.toString().substring(0, configFile.toString().length() - achievement.getName().length() - 4);
			}

			// replace '\' by '/' to sustain markup validity
			location = location.replace("\\", "/");
			
			// append HTML head
			output.append("<!DOCTYPE html><html><head><title>Your points and achievements</title><base href=\"");
			output.append(location);
			output.append("\"><meta http-equiv=\"content-type\" content=\"text/html;charset=ISO-8859-1\"><style type=\"text/css\">html, body, img{margin: 0;padding: 0;}body{margin: 10px;background-color: #32B0FF;font-family: Arial, Helvetica, sans-serif;}h1{font-size: 200%;}#score{position: relative;width: 960px;font-size: 300%;left: 50%;margin-left: -480px;padding-top: 200px;text-align: center;padding-bottom: 200px;}.divider{position: relative;width: 960px;left: 50%;margin-left: -480px;border-top: 1px solid black;padding-bottom: 40px;}#achievements{position: relative;width: 960px;left: 50%;margin-left: -480px;padding-bottom: 100px;}.achievementRow{width: 100%;height: 280px;}.achievement{width: 172px;height: 210px;float: left;margin-left: 10px;margin-right: 10px;margin-top: 20px;}.achievementInfo{text-align: center;height: 15px;padding-bottom: 15px;}.achievementName{font-weight: bold;}.achievementImage{margin-top: 5px;margin-left: 5px;margin-right: 5px;width: 162px;height: 162px;}.active{opacity: 0.3;-webkit-filter: grayscale(100%);-moz-filter: grayscale(100%);-ms-filter: grayscale(100%);-o-filter: grayscale(100%);filter: grayscale(100%);filter: url(grayscale.svg);/* Firefox 4+ */filter: gray;/* IE 6-9 */}.inactive{opacity: 0.05;-webkit-filter: grayscale(100%);-moz-filter: grayscale(100%);-ms-filter: grayscale(100%);-o-filter: grayscale(100%);filter: grayscale(100%);filter: url(grayscale.svg);/* Firefox 4+ */filter: gray;/* IE 6-9 */}.points {font-size: small; background-color: #2F8AC4;padding: 3px;border-radius: 3px;-moz-border-radius: 3px;white-space: nowrap;display: inline-block;}</style></head>");
		    
			// apend HTML body
			output.append(generateHtmlBody());
		}
		catch(IOException e1) {
			logger.log(Level.SEVERE, "[WebsiteGenerator] Error", e1);
		}
	}

	public void createPage() {
		try {
			File temp = File.createTempFile(Long.toString(System.currentTimeMillis()),".html");
			FileWriter writer = new FileWriter(temp);
			writer.write(output.toString());
			writer.close();
			PlatformUI.getWorkbench().getBrowserSupport().getExternalBrowser().openURL(temp.toURI().toURL());
			temp.deleteOnExit();
		}
		catch (IOException | PartInitException e1) {
			logger.log(Level.SEVERE, "[WebsiteGenerator] Error", e1);
		}
	}
	
	private String generateHtmlBody() {
		StringBuilder stringBuilder = new StringBuilder(8500);
		stringBuilder.append("<body><div id=\"score\">You have " + getCurrentScore() + " points.</div><div class=\"divider\"></div><div id=\"achievements\"><h1>Your achievements</h1>You'll get further information to your unlocked achievements if you hover over them with your mouse.<br><br>");

		// add achievements
		int currentPos = 0;
		for (int rows = 0; rows < 4; rows++) {
			stringBuilder.append("<div class=\"achievementRow\">");

			for (int entries = 0; entries < 5; entries++, currentPos++) {
				stringBuilder.append("<div class=\"achievement");
				if(!isAchievementComplete(currentPos)) {
					stringBuilder.append(" " + getAchievementStatus(currentPos));
				};
				stringBuilder.append("\"><div class=\"achievementImage\">");
				stringBuilder.append("<img width=\"162\" height=\"162\" src=\"" + getAchievementID(currentPos) + ".png\" alt=\"");
				if(getAchievementStatus(currentPos).equals("active")) {
					stringBuilder.append(getAchievementDescription(currentPos) + "\"");
					stringBuilder.append(" title=\"" + getAchievementDescription(currentPos));
				}
				stringBuilder.append("\"></div><div class=\"achievementInfo\">");
				stringBuilder.append("<div class=\"achievementName\">" + getAchievementName(currentPos) + "</div>");
				stringBuilder.append("<div class=\"points\">" + getAchievementPoints(currentPos) + " points</div>");
				if(isAchievementComplete(currentPos)) {
					stringBuilder.append("<br><small>(" + getAchievementDate(currentPos) + ")</small>");
				}
				stringBuilder.append("</div></div>");
			}
			
			stringBuilder.append("</div>");
		}

		stringBuilder.append("</div>");
		stringBuilder.append("</body>");
		stringBuilder.append("</html>");

		return stringBuilder.toString();
	}

	private String getAchievementID(int currentPos) {
		return achievementList.get(currentPos).getName();
	}

	private String getCurrentScore() {
		DataStorage dataStorage = new DataStorage();
		return Integer.toString(dataStorage.getScore());
	}

	private String getAchievementDate(int currentPos) {
		 long milliseconds = achievementList.get(currentPos).getValue().asObject().get("date").asLong();
		 SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		 Date resultdate = new Date(milliseconds);
		 return sdf.format(resultdate);
	}

	private String getAchievementDescription(int currentPos) {
		return achievementList.get(currentPos).getValue().asObject().get("description").asString();
	}

	private String getAchievementName(int currentPos) {
		return achievementList.get(currentPos).getValue().asObject().get("name").asString();
	}

	private String getAchievementStatus(int currentPos) {
		return (achievementList.get(currentPos).getValue().asObject().get("active").asBoolean() == true) ? "active" : "inactive";
	}

	private boolean isAchievementComplete(int currentPos) {
		return (achievementList.get(currentPos).getValue().asObject().get("complete").asBoolean() == true) ? true : false;
	}
	
	private String getAchievementPoints(int currentPos) {
		return String.valueOf(achievementList.get(currentPos).getValue().asObject().get("points").asInt());
	}

	private ArrayList<Member> getAllAchievements() {
		ArrayList<Member> allAchievements = new ArrayList<Member>();
		DataStorage dataStorage = new DataStorage();
		JsonObject jsonObject = JsonObject.readFrom(dataStorage.getAchievements()); // readFrom is buffered
		for (Member member : jsonObject) {
			allAchievements.add(member);
		}
		return allAchievements;
	}
}
