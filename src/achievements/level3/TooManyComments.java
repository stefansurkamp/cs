package achievements.level3;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jdt.core.dom.BlockComment;
import org.eclipse.jdt.core.dom.Comment;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import visitors.CommentVisitor;
import visitors.TypeDeclarationVisitor;

public class TooManyComments {

	private CompilationUnit cu;
	private Logger logger = Logger.getAnonymousLogger();

	public TooManyComments(CompilationUnit cu) {
		this.cu = cu;
	}

	public boolean isComplete() {
		float commentLength = 0;
		float classLength = cu.getLineNumber(cu.getLength()-1);
		int startLineClass = 0;
		int endLineClass = (int)classLength;

		// if there is only one class declaration, get start and end lines
		TypeDeclarationVisitor tdv = new TypeDeclarationVisitor();
		cu.accept(tdv);
		if(tdv.getTypeDeclarations().size() == 1) {
			TypeDeclaration typeDeclaration = tdv.getTypeDeclarations().get(0);
			startLineClass = cu.getLineNumber(typeDeclaration.getStartPosition()); 
			endLineClass = cu.getLineNumber(typeDeclaration.getStartPosition() + typeDeclaration.getLength() - 1);
			classLength = endLineClass - startLineClass + 1;
		}

		// if comment contents are important, comments have to accept the visitor as well: comment.accept(visitor)
		// partial source: http://www.programcreek.com/2013/03/get-internal-comments-by-using-eclipse-jdt-astparser/ (accessed on 2014-07-29)

		// one-line comments
		@SuppressWarnings("unchecked")
		List<Comment> commentList = cu.getCommentList();
		for(Comment comment : (List<Comment>) commentList) {
			// starts with "//" && is inside the class
			if((comment.toString().startsWith("//")) && (cu.getLineNumber(comment.getStartPosition()) > startLineClass) && (cu.getLineNumber(comment.getStartPosition()) < endLineClass)) {	
				logger.log(Level.FINE, "[AchievementConditions] one-line comment on line " + cu.getLineNumber(comment.getStartPosition()));
				commentLength++;
			}
		}

		// block comments
		CommentVisitor visitor = new CommentVisitor();
		cu.accept(visitor);	
		for(BlockComment blockComment : visitor.getBlockComments()) {
			int startLineComment = cu.getLineNumber(blockComment.getStartPosition());
			if(startLineComment == -1) {
				startLineComment = 1;
			}
			int nodeLength = blockComment.getLength();
			int endLineComment = cu.getLineNumber(blockComment.getStartPosition() + nodeLength - 1);
			if((startLineComment > startLineClass) && (endLineComment < endLineClass)) {
				commentLength += (endLineComment - startLineComment) + 1;
			}
		}

		System.out.println(commentLength/classLength);

		// return true if the class length is at least 50 lines and the comment/overall line ratio is >= 0.4
		return ((classLength >= 50) && (commentLength / classLength >= 0.4)) ? true : false;
	}
	
}
