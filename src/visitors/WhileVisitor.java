package visitors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

public class WhileVisitor extends ASTVisitor {
	private List<WhileStatement> whileStatements = new ArrayList<WhileStatement>();
	private List<DoStatement> doStatements = new ArrayList<DoStatement>();

	@Override
	public boolean visit(WhileStatement node) {
		whileStatements.add(node);
		return super.visit(node);
	}
	
	@Override
	public boolean visit(DoStatement node) {
		doStatements.add(node);
		return super.visit(node);
	}

	public List<WhileStatement> getWhileStatements() {
		return whileStatements;
	}

	public List<DoStatement> getDoStatements() {
		return doStatements;
	}
}