package main;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;

public class ListenerInitializer implements org.eclipse.ui.IStartup {
	
	private Logger logger = Logger.getAnonymousLogger();

	@Override
	public void earlyStartup() {
		// Plugin is loaded with eclipse workspace
		logger.log(Level.FINE, "[ListenerInititalizer] change listener initialized.");
		
		// The following three lines delete the saved achievements, points, etc.
		// DataStorage dataStorage = new DataStorage();
		// dataStorage.clearPrefs();
		// dataStorage.createEmptyPrefs();

		// create Jobs to do all the tasks one after another (necessary to avoid a crash on startup because resources are not yet fully loaded)
		// partial source: http://stackoverflow.com/questions/25005882/eclipse-plugin-run-code-right-after-startup (accessed on 2014-07-29)
		Job addResourceChangeListenerJob = new Job("addListenerJob") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				IWorkspace workspace = ResourcesPlugin.getWorkspace();
				workspace.addResourceChangeListener(new ResourceChangeListener(), IResourceChangeEvent.POST_BUILD);
				return Status.OK_STATUS;
			}
		};

		addResourceChangeListenerJob.addJobChangeListener(new IJobChangeListener() {
			@Override
			public void sleeping(IJobChangeEvent event) {}

			@Override
			public void scheduled(IJobChangeEvent event) {}

			@Override
			public void running(IJobChangeEvent event) {}

			@Override
			public void done(IJobChangeEvent event) {
				FirstStartupJob firstStartupJob = new FirstStartupJob("firstStartupJob");
				firstStartupJob.setPriority(Job.SHORT);
				firstStartupJob.schedule();
			}

			@Override
			public void awake(IJobChangeEvent event) {}

			@Override
			public void aboutToRun(IJobChangeEvent event) {}
		});
		
		addResourceChangeListenerJob.setPriority(Job.SHORT);
		addResourceChangeListenerJob.schedule();
	}

}
