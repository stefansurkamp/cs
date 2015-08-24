package achievements.level2;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import visitors.MethodDeclarationVisitor;

public class UseConstructor {

private CompilationUnit cu;
	
	public UseConstructor(CompilationUnit cu) {
		this.cu = cu;
	}

	public boolean isComplete() {
		MethodDeclarationVisitor visitor = new MethodDeclarationVisitor();
		cu.accept(visitor);
		for(MethodDeclaration methodDeclaration : visitor.getMethodDeclarations()) {	// check if one of the methods is a constructor
			if(methodDeclaration.isConstructor()) {
				return true;
			}
		}
		return false;
	}
	
}
