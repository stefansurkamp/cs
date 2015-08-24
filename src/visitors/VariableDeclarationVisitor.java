package visitors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

public class VariableDeclarationVisitor extends ASTVisitor {
	private List<VariableDeclarationStatement> variableDeclarationStatements = new ArrayList<VariableDeclarationStatement>();
	private List<VariableDeclarationFragment> variableDeclarationFragments = new ArrayList<VariableDeclarationFragment>();
	private List<FieldDeclaration> fieldDeclarations = new ArrayList<FieldDeclaration>();

	@Override
	public boolean visit(VariableDeclarationStatement node) {
		variableDeclarationStatements.add(node);
		return super.visit(node);
	}
	
	@Override
	public boolean visit(VariableDeclarationFragment node) {
		variableDeclarationFragments.add(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(FieldDeclaration node) {
		fieldDeclarations.add(node);
		return super.visit(node);
	}
	
	public List<VariableDeclarationStatement> getVariableDeclarationStatements() {
		return variableDeclarationStatements;
	}
	
	public List<VariableDeclarationFragment> getVariableDeclarationFragments() {
		return variableDeclarationFragments;
	}
	
	public List<FieldDeclaration> getFieldDeclarations() {
		return fieldDeclarations;
	}
}