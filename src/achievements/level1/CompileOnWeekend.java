package achievements.level1;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CompileOnWeekend {
	
	public boolean isComplete() {
		long milliseconds = System.currentTimeMillis();
		SimpleDateFormat dayOfWeekFormat = new SimpleDateFormat("E");
		SimpleDateFormat timeFormat = new SimpleDateFormat("H");
		String dayOfWeek = dayOfWeekFormat.format(new Date(milliseconds));
		int time = Integer.parseInt(timeFormat.format(new Date(milliseconds)));
		// return true if it is Friday and after 6pm or if it is Saturday or if it is Sunday
		return ((dayOfWeek.equals("Fr") && (time >= 18)) || (dayOfWeek.equals("Sa")) || (dayOfWeek.equals("So"))) ? true : false;
	}
	
}
