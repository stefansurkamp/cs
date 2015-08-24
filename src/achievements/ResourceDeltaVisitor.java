package achievements;

import java.util.ArrayList;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;

public class ResourceDeltaVisitor implements IResourceDeltaVisitor {

	private ArrayList<IResource> changedClasses = new ArrayList<IResource>();
	
	// only visit delta classes
	public boolean visit(IResourceDelta delta) {
		IResource res = delta.getResource();
		if(res.getName().endsWith(".java")) {
			addResource(res);
		}
		return true; // visit the children
	}
	
	public ArrayList<IResource> getChangedResources() {
		return changedClasses;
	}
	
	private void addResource(IResource res) {
		changedClasses.add(res);
	}
}