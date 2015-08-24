package achievements.level3;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;

import visitors.VariableDeclarationVisitor;

public class TooManyPublicVariables {

private CompilationUnit cu;
	
	public TooManyPublicVariables(CompilationUnit cu) {
		this.cu = cu;
	}

	public boolean isComplete() {
		VariableDeclarationVisitor visitor = new VariableDeclarationVisitor();
		cu.accept(visitor);
		float variableCount = 0;
		float publicVariableCount = 0;
		
		for(FieldDeclaration variableDeclaration : visitor.getFieldDeclarations()) {
			variableCount++;	// increment variable count
			for(Object modifier : variableDeclaration.modifiers()) {
				if(modifier.toString().equals("public")) {
					publicVariableCount++;	// increment public variable count because current variable is public
				}
			}
		}
		// return true if there are at least five variables and the public/overall variable ratio is >= 0.4
		return ((variableCount >= 5) && (publicVariableCount/ variableCount >= 0.4)) ? true : false;
	}
	
}
