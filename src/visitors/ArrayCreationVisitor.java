package visitors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ArrayCreation;

public class ArrayCreationVisitor extends ASTVisitor {
	private List<ArrayCreation> arrays = new ArrayList<ArrayCreation>();

	@Override
	public boolean visit(ArrayCreation node) {
		arrays.add(node);
		return super.visit(node);
	}

	public List<ArrayCreation> getArrays() {
		return arrays;
	}
}
