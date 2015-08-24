package main;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

// general sources for present plugin:
// Eclipse 4.3 (Kepler) Documentation - http://help.eclipse.org/kepler/index.jsp (accessed 2014-08-06)
// Java Platform, Standard Edition 7 API Specification - http://docs.oracle.com/javase/7/docs/api/ (accessed 2014-08-06)

public class Activator extends AbstractUIPlugin {
	
	private Logger logger = Logger.getAnonymousLogger();

	public static final String PLUGIN_ID = "Java_Gamification_Plugin";

	private static Activator plugin;	// the shared instance
	
	public Activator() {
		logger.log(Level.FINE, "[Activator] Plugin starts.");
	}

	public void start(BundleContext context) {
		try {
			super.start(context);
			plugin = this;
		}
		catch (Exception e1) {
			logger.log(Level.SEVERE, "[Activator] Error", e1);
		}
		
	}
	
	public void stop(BundleContext context) {
		try {
			plugin = null;
			super.stop(context);
		}
		catch (Exception e1) {
			logger.log(Level.SEVERE, "[Activator] Error", e1);
		}
	}

	public static Activator getDefault() {
		return plugin;
	}
	
}
