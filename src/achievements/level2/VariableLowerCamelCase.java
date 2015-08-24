package achievements.level2;

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import visitors.VariableDeclarationVisitor;

public class VariableLowerCamelCase {

private CompilationUnit cu;
	
	public VariableLowerCamelCase(CompilationUnit cu) {
		this.cu = cu;
	}

	public boolean isComplete() {
		ArrayList<String> variableNames = new ArrayList<String>();
		VariableDeclarationVisitor variableDeclarationVisitor = new VariableDeclarationVisitor();
		cu.accept(variableDeclarationVisitor);
		
		// fill list with potential variables
		for(VariableDeclarationFragment variableDeclarationFragment : variableDeclarationVisitor.getVariableDeclarationFragments()) {
			String varName = variableDeclarationFragment.toString().split("=")[0];	// split at "=new" to only get the variable name
			// has min length && starts with lowercase && ends with lowercase && is not an object creation ("=new" creates a new object)
			if(varName.length() > 8 && Character.isLowerCase(varName.charAt(0)) && Character.isLowerCase(varName.charAt(varName.length()-1)) && variableDeclarationFragment.toString().split("=new").length == 1) {
				variableNames.add(varName);
			}
		}
		
		// check variable spelling
		for(String varName : variableNames) {
			boolean currentVarIsValid = true;
			for(int i = 1; i < varName.length()-1; i++) {
				if(Character.isUpperCase(varName.charAt(i))) {
					if(Character.isUpperCase(varName.charAt(i+1))){
						currentVarIsValid = false;
					}
				}						
			}
			if(currentVarIsValid) {
				return true;
			}
		}
		return false;	
	}
	
}
