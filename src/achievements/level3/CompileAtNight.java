package achievements.level3;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CompileAtNight {

	public boolean isComplete() {
		long milliseconds = System.currentTimeMillis();
		SimpleDateFormat timeFormat = new SimpleDateFormat("H");
		int time = Integer.parseInt(timeFormat.format(new Date(milliseconds)));
		// return true if it is between 3am and 6am
		return (time >= 3 && time < 6) ? true : false;
	}
	
}
