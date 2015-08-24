package achievements.level0;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IfStatement;

import visitors.ConditionVisitor;

public class FirstUseIfElse {

	private CompilationUnit cu;
	
	public FirstUseIfElse(CompilationUnit cu) {
		this.cu = cu;
	}
	
	public boolean isComplete() {
		ConditionVisitor visitor = new ConditionVisitor();
		cu.accept(visitor);
		for(IfStatement ifStatement : visitor.getIfStatements()) {
			if(ifStatement.getElseStatement() != null) {	// if there is an else-statement 
				return true;
			}
		}
		return false;
	}
	
}
