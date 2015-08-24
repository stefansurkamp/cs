package visitors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CastExpression;

public class CastExpressionVisitor extends ASTVisitor {

	private List<CastExpression> castExpressions = new ArrayList<CastExpression>();

	@Override
	public boolean visit(CastExpression node) {
		castExpressions.add(node);
		return super.visit(node);
	}

	public List<CastExpression> getCastExpressions() {
		return castExpressions;
	}
}
