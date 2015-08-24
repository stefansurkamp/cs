package main;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import notifications.NotificationManager;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;

import achievements.AchievementManager;
import achievements.ResourceDeltaVisitor;

public class ResourceChangeListener implements IResourceChangeListener {

	private Logger logger = Logger.getAnonymousLogger();

	public void resourceChanged(IResourceChangeEvent event) {
		IResourceDelta[] allChanges = event.getDelta().getAffectedChildren();
		AchievementManager achievementManager = new AchievementManager();
		
		for(IResourceDelta delta : allChanges) {
			if(delta.getKind() == 4) {	// only check projects when there are actual changes (4 = CHANGED)
				try {
					// visits resource deltas = only changed classes
					// partial source: http://stackoverflow.com/questions/25139423/eclipse-plugin-return-affected-class-from-iresourcechangelistener (accessed on 2014-08-06)
					ResourceDeltaVisitor resourceDeltaVisitor = new ResourceDeltaVisitor();
					delta.accept(resourceDeltaVisitor);
					ArrayList<IResource> changedClasses = resourceDeltaVisitor.getChangedResources();

					for(IResource changedClass : changedClasses) {
						ICompilationUnit iCompilationUnit = (ICompilationUnit)JavaCore.create(changedClass);
						logger.log(Level.FINE, "[ResourceChangeListener] Parsed class: " + iCompilationUnit.getElementName());

						achievementManager.readAchievements();
						achievementManager.checkActiveAchievements(iCompilationUnit);
					}
				}
				catch (CoreException e1) {
					logger.log(Level.SEVERE, "[ResourceChangeListener] Error", e1);
				}

				NotificationManager.showNotification();
			}
		}
	}
}