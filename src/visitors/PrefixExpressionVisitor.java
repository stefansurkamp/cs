package visitors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.PrefixExpression;

public class PrefixExpressionVisitor extends ASTVisitor {
	private List<PrefixExpression> prefixExpressions = new ArrayList<PrefixExpression>();

	@Override
	public boolean visit(PrefixExpression node) {
		prefixExpressions.add(node);
		return super.visit(node);
	}

	public List<PrefixExpression> getPrefixExpressions() {
		return prefixExpressions;
	}
}