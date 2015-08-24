package achievements.level2;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import visitors.VariableDeclarationVisitor;

public class ObjectUpperCamelCase {

private CompilationUnit cu;
	
	public ObjectUpperCamelCase(CompilationUnit cu) {
		this.cu = cu;
	}

	public boolean isComplete() {
		VariableDeclarationVisitor typeDeclarationVisitor = new VariableDeclarationVisitor();
		cu.accept(typeDeclarationVisitor);
		
		// only retrieve variable declarations of actual objects (split at "=new")
		for(VariableDeclarationFragment typeDeclarationStatement : typeDeclarationVisitor.getVariableDeclarationFragments()) {
			if(typeDeclarationStatement.toString().split("=new").length == 2) {	// these are actual objects
				String objectName = typeDeclarationStatement.toString().split("=new")[0];
				
				// has min length && starts with uppercase && ends with lowercase
				if(objectName.length() > 8 && Character.isUpperCase(objectName.charAt(0)) && Character.isLowerCase(objectName.charAt(objectName.length()-1))) {
					boolean currentObjectIsValid = true;
					for(int i = 1; i < objectName.length()-1; i++) {
						if(Character.isUpperCase(objectName.charAt(i))) {
							if(i == 1) {	// false, because two consecutive chars are uppercase
								currentObjectIsValid = false;
							}
							else if(Character.isUpperCase(objectName.charAt(i+1))) {	// false, because two consecutive chars are uppercase
								currentObjectIsValid = false;
							}
						}						
					}
					if(currentObjectIsValid) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
}
