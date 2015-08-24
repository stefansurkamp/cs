package visitors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.PrimitiveType;

public class PrimitiveTypeVisitor extends ASTVisitor {
	private List<PrimitiveType> primitiveTypes = new ArrayList<PrimitiveType>();

	@Override
	public boolean visit(PrimitiveType node) {
		primitiveTypes.add(node);
		return super.visit(node);
	}
	
	public List<PrimitiveType> getPrimitiveTypes() {
		return primitiveTypes;
	}

}
