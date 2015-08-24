package achievements.level2;

import org.eclipse.jdt.core.dom.CompilationUnit;

import visitors.CastExpressionVisitor;

public class UseTypecast {

private CompilationUnit cu;
	
	public UseTypecast(CompilationUnit cu) {
		this.cu = cu;
	}

	public boolean isComplete() {
		CastExpressionVisitor visitor = new CastExpressionVisitor();
		cu.accept(visitor);
		return (visitor.getCastExpressions().size() > 0) ? true : false;
	}
	
}
