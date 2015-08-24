package achievements.level2;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;

import visitors.PostfixExpressionVisitor;
import visitors.PrefixExpressionVisitor;

public class UseIncrement {

private CompilationUnit cu;
	
	public UseIncrement(CompilationUnit cu) {
		this.cu = cu;
	}

	public boolean isComplete() {
		PostfixExpressionVisitor postfixExpressionVisitor = new PostfixExpressionVisitor();
		cu.accept(postfixExpressionVisitor);
		// check for postfix increments, e.g. x++
		for(PostfixExpression postfixExpression : postfixExpressionVisitor.getPostfixExpressions()) {
			if(postfixExpression.getOperator() == PostfixExpression.Operator.INCREMENT) {
				return true;
			}
		}
		// check for prefix increments, e.g. ++x
		PrefixExpressionVisitor prefixExpressionVisitor = new PrefixExpressionVisitor();
		cu.accept(prefixExpressionVisitor);
		for(PrefixExpression prefixExpression : prefixExpressionVisitor.getPrefixExpressions()) {
			if(prefixExpression.getOperator() == PrefixExpression.Operator.INCREMENT) {
				return true;
			}
		}
		return false;	
	}
	
}
