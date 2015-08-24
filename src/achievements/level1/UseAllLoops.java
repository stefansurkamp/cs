package achievements.level1;

import org.eclipse.jdt.core.dom.CompilationUnit;

import achievements.DataStorage;
import visitors.ForVisitor;
import visitors.WhileVisitor;

public class UseAllLoops {

private CompilationUnit cu;
	
	public UseAllLoops(CompilationUnit cu) {
		this.cu = cu;
	}

	public boolean isComplete() {
		DataStorage dataStorage = new DataStorage();
		int loopCount = 0;		

		// doWhile loop
		if(dataStorage.getLoopTypes()[0]) {
			loopCount++;
		}
		else {
			WhileVisitor whileVisitor = new WhileVisitor();
			cu.accept(whileVisitor);
			if(whileVisitor.getDoStatements().size() > 0) {
				dataStorage.setLoopType("doWhile", true);
				loopCount++;
			}
		}
		
		// for loop
		if(dataStorage.getLoopTypes()[1]) {
			loopCount++;
		}
		else {
			ForVisitor forVisitor = new ForVisitor();
			cu.accept(forVisitor);
			if(forVisitor.getForStatements().size() > 0 || forVisitor.getEnhancedForStatements().size() > 0) {
				dataStorage.setLoopType("for", true);
				loopCount++;
			}
		}
		
		// while loop
		if(dataStorage.getLoopTypes()[2]) {
			loopCount++;
		}
		else {
			WhileVisitor whileVisitor = new WhileVisitor();
			cu.accept(whileVisitor);
			if(whileVisitor.getWhileStatements().size() > 0) {
				dataStorage.setLoopType("while", true);
				loopCount++;
			}
		}
		return (loopCount == 3) ? true : false;
	}
	
}
