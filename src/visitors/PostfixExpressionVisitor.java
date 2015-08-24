package visitors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.PostfixExpression;

public class PostfixExpressionVisitor extends ASTVisitor {
	private List<PostfixExpression> postfixExpressions = new ArrayList<PostfixExpression>();

	@Override
	public boolean visit(PostfixExpression node) {
		postfixExpressions.add(node);
		return super.visit(node);
	}

	public List<PostfixExpression> getPostfixExpressions() {
		return postfixExpressions;
	}
}