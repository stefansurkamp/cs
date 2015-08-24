package achievements.level0;

import org.eclipse.jdt.core.dom.CompilationUnit;

public class FirstCompile {
	
	private CompilationUnit cu;
	
	public FirstCompile(CompilationUnit cu) {
		this.cu = cu;
	}

	public boolean isComplete() {
		// returns true if first compile is not due to a class creation
		// inner length of an empty class is 3
		return (cu.toString().split("\\{")[1].length() > 3) ? true : false;
	}
	
}
