package achievements.level1;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.SwitchCase;

import visitors.SwitchVisitor;

public class SwitchUseDefaultStatement {

private CompilationUnit cu;
	
	public SwitchUseDefaultStatement(CompilationUnit cu) {
		this.cu = cu;
	}

	public boolean isComplete() {
		SwitchVisitor visitor = new SwitchVisitor();
		cu.accept(visitor);
		for(SwitchCase switchCase : visitor.getSwitchCases()) {	// check if one of the cases is default
			if(switchCase.isDefault()) {
				return true;
			}
		}
		return false;
	}
	
}
