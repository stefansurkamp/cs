package visitors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;

public class ClassInstanceCreationVisitor extends ASTVisitor {

	private List<ClassInstanceCreation> classInstanceCreations = new ArrayList<ClassInstanceCreation>();
	
	@Override
	public boolean visit(ClassInstanceCreation node) {
		classInstanceCreations.add(node);
		return super.visit(node);
	}

	public List<ClassInstanceCreation> getClassInstanceCreations() {
		return classInstanceCreations;
	}
}