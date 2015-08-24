package achievements;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import achievements.level0.CreateClassInstance;
import achievements.level0.FirstCompile;
import achievements.level0.FirstUseIfElse;
import achievements.level0.NoCompileShort;
import achievements.level1.CompileOnWeekend;
import achievements.level1.Create2dArray;
import achievements.level1.SwitchUseDefaultStatement;
import achievements.level1.UseAllLoops;
import achievements.level1.UsePrimitiveTypes;
import achievements.level2.ObjectUpperCamelCase;
import achievements.level2.UseConstructor;
import achievements.level2.UseIncrement;
import achievements.level2.UseTypecast;
import achievements.level2.VariableLowerCamelCase;
import achievements.level3.TooManyComments;
import achievements.level3.TooManyPublicVariables;
import achievements.level3.TooManyStaticMethods;
import achievements.level3.UseTernaryOperator;

public class AchievementPicker {

	private Logger logger = Logger.getAnonymousLogger();

	boolean forName(ICompilationUnit iCompilationUnit, String name) {
		CompilationUnit cu = parse(iCompilationUnit);

		switch(name) {
			// Level 0		
			case "firstCompile": {
				FirstCompile firstCompile = new FirstCompile(cu);
				return firstCompile.isComplete();
			}
			
			case "createClassInstance": {
				try {
					IResource res = iCompilationUnit.getUnderlyingResource();
					CreateClassInstance createClassInstance = new CreateClassInstance(res);
					return createClassInstance.isComplete();
				}
				catch (JavaModelException e1) {
					logger.log(Level.SEVERE, "[AchievementPicker] Error", e1);
				}
				return false;
			}
			
			case "firstUseIfElse": {
				FirstUseIfElse firstUseIfElse = new FirstUseIfElse(cu);
				return firstUseIfElse.isComplete();
			}
			
			case "noCompileShort": {
				NoCompileShort noCompileShort = new NoCompileShort();
				return noCompileShort.isComplete();
			}
			
			// Level 1
			case "create2dArray": {
				Create2dArray create2dArray = new Create2dArray(cu);
				return create2dArray.isComplete();
			}
			
			case "usePrimitiveTypes": {
				UsePrimitiveTypes usePrimitiveTypes = new UsePrimitiveTypes(cu);
				return usePrimitiveTypes.isComplete();
			}
			
			case "compileOnWeekend": {
				CompileOnWeekend compileOnWeekend = new CompileOnWeekend();
				return compileOnWeekend.isComplete();
			}
			
			case "useAllLoops": {
				UseAllLoops useAllLoops = new UseAllLoops(cu);
				return useAllLoops.isComplete();
			}
			
			case "switchUseDefaultStatement": {
				SwitchUseDefaultStatement switchUseDefaultStatement = new SwitchUseDefaultStatement(cu);
				return switchUseDefaultStatement.isComplete();
			}
			
			// Level 2
			case "useConstructor": {
				UseConstructor useConstructor = new UseConstructor(cu);
				return useConstructor.isComplete();
			}
			
			case "useTypecast": {
				UseTypecast useTypecast = new UseTypecast(cu);
				return useTypecast.isComplete();
			}
			
			case "useIncrement": {
				UseIncrement useIncrement = new UseIncrement(cu);
				return useIncrement.isComplete();
			}
			
			case "variableLowerCamelCase": {
				VariableLowerCamelCase variableLowerCamelCase = new VariableLowerCamelCase(cu);
				return variableLowerCamelCase.isComplete();
			}
			
			case "objectUpperCamelCase": {
				ObjectUpperCamelCase objectUpperCamelCase = new ObjectUpperCamelCase(cu);
				return objectUpperCamelCase.isComplete();
			}
			
			// Level 3
			case "tooManyStaticMethods": {
				TooManyStaticMethods tooManyStaticMethods = new TooManyStaticMethods(cu);
				return tooManyStaticMethods.isComplete();
			}
			
			case "compileAtNight": {
				TooManyStaticMethods tooManyStaticMethods = new TooManyStaticMethods(cu);
				return tooManyStaticMethods.isComplete();
			}
			
			case "useTernaryOperator": {
				UseTernaryOperator useTernaryOperator = new UseTernaryOperator(cu);
				return useTernaryOperator.isComplete();
			}
			
			case "tooManyPublicVariables": {
				TooManyPublicVariables tooManyPublicVariables = new TooManyPublicVariables(cu);
				return tooManyPublicVariables.isComplete();
			}
			
			case "tooManyComments": {
				TooManyComments tooManyComments = new TooManyComments(cu);
				return tooManyComments.isComplete();
			}
			
			default: {
				return false;
			}
		}
	}
	
	// parse ICompilationUnit and returns a CompilationUnit (Abstract Syntax Tree DOM)
	// source: http://www.vogella.com/tutorials/EclipseJDT/article.html (accessed on 2014-08-06)
	private static CompilationUnit parse(ICompilationUnit unit) {
		ASTParser parser = ASTParser.newParser(AST.JLS4);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(unit);
		parser.setResolveBindings(true);
		return (CompilationUnit) parser.createAST(null); // parse
	}
}
