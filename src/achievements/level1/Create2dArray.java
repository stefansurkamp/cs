package achievements.level1;

import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;

import visitors.ArrayCreationVisitor;

public class Create2dArray {
	
	private CompilationUnit cu;
	
	public Create2dArray(CompilationUnit cu) {
		this.cu = cu;
	}

	public boolean isComplete() {
		ArrayCreationVisitor visitor = new ArrayCreationVisitor();
		cu.accept(visitor);
		for(ArrayCreation arrayType : visitor.getArrays()) {
			if(arrayType.dimensions().size() == 2) {
				return true;
			}
		}
		return false;
	}
	
}
