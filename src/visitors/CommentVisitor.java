package visitors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.BlockComment;

public class CommentVisitor extends ASTVisitor {
	private List<BlockComment> blockComments = new ArrayList<BlockComment>();
	
	@Override
	public boolean visit(BlockComment node) {
		blockComments.add(node);
		return super.visit(node);
	}

	public List<BlockComment> getBlockComments() {
		return blockComments;
	}
}
