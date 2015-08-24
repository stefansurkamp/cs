package visitors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.IfStatement;

public class ConditionVisitor extends ASTVisitor {
	private List<IfStatement> ifStatements = new ArrayList<IfStatement>();
	private List<ConditionalExpression> conditionalExpressions = new ArrayList<ConditionalExpression>();

	@Override
	public boolean visit(IfStatement node) {
		ifStatements.add(node);
		return super.visit(node);
	}
	
	@Override
	public boolean visit(ConditionalExpression node) {
		conditionalExpressions.add(node);
		return super.visit(node);
	}

	public List<IfStatement> getIfStatements() {
		return ifStatements;
	}
	
	public List<ConditionalExpression> getConditionalExpressions() {
		return conditionalExpressions;
	}
}