package visitors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.ForStatement;

public class ForVisitor extends ASTVisitor {
	private List<ForStatement> forStatements = new ArrayList<ForStatement>();
	private List<EnhancedForStatement> enhancedForStatements = new ArrayList<EnhancedForStatement>();


	@Override
	public boolean visit(ForStatement node) {
		forStatements.add(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(EnhancedForStatement node) {
		enhancedForStatements.add(node);
		return super.visit(node);
	}
	
	public List<ForStatement> getForStatements() {
		return forStatements;
	}
	
	public List<EnhancedForStatement> getEnhancedForStatements() {
		return  enhancedForStatements;
	}
}