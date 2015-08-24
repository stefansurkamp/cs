package achievements.level3;

import org.eclipse.jdt.core.dom.CompilationUnit;

import visitors.ConditionVisitor;

public class UseTernaryOperator {

private CompilationUnit cu;
	
	public UseTernaryOperator(CompilationUnit cu) {
		this.cu = cu;
	}

	public boolean isComplete() {
		ConditionVisitor visitor = new ConditionVisitor();
		cu.accept(visitor);
		return (visitor.getConditionalExpressions().size() > 0) ? true : false;
	}
	
}
