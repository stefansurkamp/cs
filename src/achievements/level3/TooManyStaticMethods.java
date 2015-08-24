package achievements.level3;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import visitors.MethodDeclarationVisitor;

public class TooManyStaticMethods {

private CompilationUnit cu;
	
	public TooManyStaticMethods(CompilationUnit cu) {
		this.cu = cu;
	}

	public boolean isComplete() {
		MethodDeclarationVisitor visitor = new MethodDeclarationVisitor();
		cu.accept(visitor);
		float methodCount = 0;
		float staticMethodCount = 0;
		for(MethodDeclaration methodDeclaration : visitor.getMethodDeclarations()) {
			if((!methodDeclaration.getName().toString().equals("main")) && (!methodDeclaration.isConstructor())) {	// do not count main and constructor
				methodCount++;	// increment method count
				for(Object modifier : methodDeclaration.modifiers()) {
					if(modifier.toString().equals("static")) {
						staticMethodCount++;	// increment static method count because current method is static
					}
				}
			}
		}
		// return true if there are at least five methods and the static/overall method ratio is >= 0.4
		return ((methodCount >= 5) && (staticMethodCount / methodCount >= 0.4)) ? true : false;
	}
	
}
