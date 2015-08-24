package achievements.level0;

import achievements.DataStorage;

public class NoCompileShort {

	public boolean isComplete() {
		DataStorage dataStorage = new DataStorage();
		long lastTimestamp = dataStorage.getTimestamp();
		long currentTimestamp = System.currentTimeMillis();
		if((Long.compare(lastTimestamp, 0L) != 0) && (currentTimestamp - lastTimestamp > 86400000)) {	// 86400000ms = one day
			return true;
		}
		dataStorage.setTimestamp();
		return false;
	}
	
}
