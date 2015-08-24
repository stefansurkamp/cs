package visitors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.SwitchStatement;

public class SwitchVisitor extends ASTVisitor {
	private List<SwitchStatement> switchStatements = new ArrayList<SwitchStatement>();
	private List<SwitchCase> switchCase = new ArrayList<SwitchCase>();

	@Override
	public boolean visit(SwitchStatement node) {
		switchStatements.add(node);
		return super.visit(node);
	}
	
	@Override
	public boolean visit(SwitchCase node) {
		switchCase.add(node);
		return super.visit(node);
	}

	public List<SwitchStatement> getSwitchStatements() {
		return switchStatements;
	}

	public List<SwitchCase> getSwitchCases() {
		return switchCase;
	}
}