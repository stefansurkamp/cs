package achievements;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.equinox.security.storage.ISecurePreferences;
import org.eclipse.equinox.security.storage.SecurePreferencesFactory;
import org.eclipse.equinox.security.storage.StorageException;

public class DataStorage {

	private ISecurePreferences preferences;
	private Logger logger = Logger.getAnonymousLogger();

	public DataStorage() {
		preferences = SecurePreferencesFactory.getDefault();
		if(!preferences.nodeExists("achievements")) {
			createEmptyPrefs();
		}
	}
	
	public void createEmptyPrefs() {
		try {
			if(!preferences.nodeExists("points")) {
				logger.log(Level.FINE, "[DataStorage] points do not exist yet.");
				preferences.node("points");
				preferences.node("points").putInt("score", 0, true);
			}
	
			if(!preferences.nodeExists("achievements")) {
				System.out.println("NODEDOESNOTEXIST");
				logger.log(Level.FINE, "[DataStorage] achievements do not exist yet.");
				preferences.node("achievements");
				// following line should be read by file: achievements.json
				String jsonString = "{\"firstStartup\":{\"name\":\"Ready for takeoff\",\"description\":\"You have successfully installed the plug-in!\",\"comment\":\"plugin installed\",\"points\":100,\"date\":0,\"active\":true,\"complete\":false},\"firstCompile\":{\"name\":\"Maiden flight\",\"description\":\"Compile a project for the first time.\",\"comment\":\"first compile\",\"points\":5,\"date\":0,\"active\":true,\"complete\":false},\"createClassInstance\":{\"name\":\"Creator\",\"description\":\"Create a class instance.\",\"comment\":\"class instance created\",\"points\":15,\"date\":0,\"active\":true,\"complete\":false},\"firstUseIfElse\":{\"name\":\"Either or...\",\"description\":\"Create a condition.\",\"comment\":\"first if else statement\",\"points\":10,\"date\":0,\"active\":true,\"complete\":false},\"noCompileShort\":{\"name\":\"Relax!\",\"description\":\"Just take a break.\",\"comment\":\"difference between current and last compile over 24h\",\"points\":10,\"date\":0,\"active\":true,\"complete\":false},\"create2dArray\":{\"name\":\"Second dimension\",\"description\":\"Create a multidimensional data type\\nwhich is able to hold several elements\\nof the same type.\",\"comment\":\"created a 2d array\",\"points\":15,\"date\":0,\"active\":false,\"complete\":false},\"usePrimitiveTypes\":{\"name\":\"Absolutely primitive\",\"description\":\"Use four primitive data types.\",\"comment\":\"use four types out of boolean, byte, char, double, float, int, long, short\",\"points\":25,\"date\":0,\"active\":false,\"complete\":false},\"compileOnWeekend\":{\"name\":\"Lone wolf\",\"description\":\"Don't you want to spend your weekend differently?\",\"comment\":\"compile on a Friday evening, Saturday or Sunday\",\"points\":10,\"date\":0,\"active\":false,\"complete\":false},\"useAllLoops\":{\"name\":\"Again and again\",\"description\":\"Use the three basic loops.\",\"comment\":\"use dowhile, while and for loops\",\"points\":20,\"date\":0,\"active\":false,\"complete\":false},\"switchUseDefaultStatement\":{\"name\":\"When nothing else fits\",\"description\":\"Use default in a switch statement.\",\"comment\":\"use default statement in switch\",\"points\":20,\"date\":0,\"active\":false,\"complete\":false},\"useConstructor\":{\"name\":\"Constructor\",\"description\":\"Use a constructor.\",\"comment\":\"use a constructor\",\"points\":10,\"date\":0,\"active\":false,\"complete\":false},\"useTypecast\":{\"name\":\"Metamorphosis\",\"description\":\"Convert one data type to another.\",\"comment\":\"use any typecast\",\"points\":20,\"date\":0,\"active\":false,\"complete\":false},\"useIncrement\":{\"name\":\"Augmentation\",\"description\":\"Use an instruction,\\nwhich increases a value by one.\",\"comment\":\"use pre or post increment on any variable\",\"points\":20,\"date\":0,\"active\":false,\"complete\":false},\"variableLowerCamelCase\":{\"name\":\"Small camel\",\"description\":\"Use the lower camel case for a variable name.\",\"comment\":\"use lowerCamelCase for a variable which is longer than 8 characters\",\"points\":35,\"date\":0,\"active\":false,\"complete\":false},\"objectUpperCamelCase\":{\"name\":\"Big camel\",\"description\":\"Use the upper camel case for a variable name.\",\"comment\":\"use UpperCamelCase for an object which is longer than 8 characters\",\"points\":35,\"date\":0,\"active\":false,\"complete\":false},\"tooManyStaticMethods\":{\"name\":\"Bad statics\",\"description\":\"You should really be concerned\\nabout your use of static accesses.\",\"comment\":\">=40% of at least 5 methods are static\",\"points\":5,\"date\":0,\"active\":false,\"complete\":false},\"compileAtNight\":{\"name\":\"Night Owl\",\"description\":\"You don't need sleep, huh?\",\"comment\":\"compile between 3am and 6am\",\"points\":25,\"date\":0,\"active\":false,\"complete\":false},\"useTernaryOperator\":{\"name\":\"Conditions for advanced learners\",\"description\":\"Use the ternary operator.\",\"comment\":\"use ternary operator\",\"points\":20,\"date\":0,\"active\":false,\"complete\":false},\"tooManyPublicVariables\":{\"name\":\"Admission free for everyone!\",\"description\":\"You probably should not have so many public variables.\",\"comment\":\">=40% of at least 5 variables are public\",\"points\":5,\"date\":0,\"active\":false,\"complete\":false},\"tooManyComments\":{\"name\":\"Dead code\",\"description\":\"Your code looks like a battlefield.\",\"comment\":\">= 40% of at least 50 lines are commented out\",\"points\":5,\"date\":0,\"active\":false,\"complete\":false}}";
				preferences.node("achievements").put("achievements", jsonString, true);
			}
			else {
				System.out.println("NODE DOES EXIST !!!!!!!!!! KEK");
			}
	
			if(!preferences.nodeExists("timeLastCompile")) {
				logger.log(Level.FINE, "[DataStorage] timeLastCompile does not exist yet.");
				preferences.node("timeLastCompile");
				preferences.node("timeLastCompile").putLong("timestamp", 0, true);
			}
	
			if(!preferences.nodeExists("primitiveTypes")) {
				preferences.node("primitiveTypes");
				logger.log(Level.FINE, "[DataStorage] primitiveTypes do not exist yet.");
				String[] primitiveTypes = {"boolean", "byte", "char", "double", "float","int", "long", "short"};
				for(String type : primitiveTypes) {
					preferences.node("primitiveTypes").putBoolean(type, false, true);
				}
			}
	
			if(!preferences.nodeExists("loopTypes")) {
				preferences.node("loopTypes");
				logger.log(Level.FINE, "[DataStorage] loopTypes do not exist yet");
				String[] loopTypes = {"doWhile", "for", "while"};
				for(String type : loopTypes) {
					preferences.node("loopTypes").putBoolean(type, false, true);
				}
			}
		}
		catch(StorageException e1) {
			logger.log(Level.SEVERE, "[DataStorage] Error", e1);
		}
	}
	
	public void clearPrefs() {
		for(String name : preferences.childrenNames()) {
			preferences.node(name).removeNode();
		}
	}

	// GETTER
	public int getScore() {
		try {
			return preferences.node("points").getInt("score", 0);
		}
		catch(StorageException e1) {
			logger.log(Level.SEVERE, "[DataStorage] Error", e1);
		}
		return 0;
	}

	public String getAchievements() {
		try {
			return preferences.node("achievements").get("achievements", null);
		}
		catch(StorageException e1) {
			logger.log(Level.SEVERE, "[DataStorage] Error", e1);
		}
		return null;
	}

	public long getTimestamp() {
		try {
			return preferences.node("timeLastCompile").getLong("timestamp", 0);
		}
		catch(StorageException e1) {
			logger.log(Level.SEVERE, "[DataStorage] Error", e1);
		}
		return 0;
	}
	
	public boolean[] getPrimitiveTypes() {
		boolean[] result = new boolean[8];
		try {
			String[] primitiveTypes = {"boolean", "byte", "char", "double", "float","int", "long", "short"};
			int position = 0;
			for(String currentValue : primitiveTypes) {
				if(preferences.node("primitiveTypes").getBoolean(currentValue, false)) {
					result[position] = true;
				}
				position++;
			}
		}
		catch(StorageException e1) {
			logger.log(Level.SEVERE, "[DataStorage] Error", e1);
		}
		return result;
	}

	public boolean[] getLoopTypes() {
		boolean[] result = new boolean[3];
		try {
			String[] loopTypes = {"doWhile", "for", "while"};
			int position = 0;
			for(String currentValue : loopTypes) {
				if(preferences.node("loopTypes").getBoolean(currentValue, false)) {
					result[position] = true;		
				}
				position++;
			}
		}
		catch(StorageException e1) {
			logger.log(Level.SEVERE, "[DataStorage] Error", e1);
		}
		return result;
	}
	
	// SETTER
	public void addScore(int amount) {
		try {
			preferences.node("points").putInt("score", getScore()+amount, true);
			preferences.flush();
		}
		catch (StorageException | IOException e1) {
			logger.log(Level.SEVERE, "[DataStorage] Error", e1);
		}
	}
	
	public void setAchievements(String jsonString) {
		try {
			preferences.node("achievements").put("achievements", jsonString, true);
			preferences.flush();
		}
		catch (StorageException | IOException e1) {
			logger.log(Level.SEVERE, "[DataStorage] Error", e1);
		}
	}

	public void setTimestamp() {
		try {
			preferences.node("timeLastCompile").putLong("timestamp", System.currentTimeMillis(), true);
			preferences.flush();
		}
		catch (StorageException | IOException e1) {
			logger.log(Level.SEVERE, "[DataStorage] Error", e1);
		}
	}

	public void setPrimitiveType(String name, boolean value) {
		try {
			preferences.node("primitiveTypes").putBoolean(name, value, true);
			preferences.flush();
		}
		catch (StorageException | IOException e1) {
			logger.log(Level.SEVERE, "[DataStorage] Error", e1);
		}
	}
	
	public void setLoopType(String name, boolean value) {
		try {
			preferences.node("loopTypes").putBoolean(name, value, true);
			preferences.flush();;
		}
		catch (StorageException | IOException e1) {
			logger.log(Level.SEVERE, "[DataStorage] Error", e1);
		}
	}
	
}
