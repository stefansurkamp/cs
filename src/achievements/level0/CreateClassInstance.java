package achievements.level0;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;

import visitors.ClassInstanceCreationVisitor;

public class CreateClassInstance {

	private IResource iResource;
	private Logger logger = Logger.getAnonymousLogger();

	public CreateClassInstance(IResource iResource) {
		this.iResource = iResource;
	}

	public boolean isComplete() {
		IProject currentProject = iResource.getProject();
		ArrayList<String> classNames = new ArrayList<String>();
		IPackageFragment[] packages;
		try {	// partial source: http://www.programcreek.com/2011/08/code-to-parse-a-java-project/ (accessed on 2014-07-28)
			packages = JavaCore.create(currentProject).getPackageFragments();

			for(IPackageFragment mypackage : packages) {	// Loop over all containing packages
				if(mypackage.getKind() == IPackageFragmentRoot.K_SOURCE) {	// if package has Source files (not binary)
					for(ICompilationUnit unit : mypackage.getCompilationUnits()) {	// get class files
						String className = unit.getElementName().split(".java")[0];
						classNames.add(className);
					}

					ArrayList<String> classInstances = new ArrayList<String>();
					for(ICompilationUnit unit : mypackage.getCompilationUnits()) {	// parse class files for instance creations
						ASTParser parser = ASTParser.newParser(AST.JLS4);
						parser.setKind(ASTParser.K_COMPILATION_UNIT);
						parser.setSource(unit);
						parser.setResolveBindings(true);
						CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);

						ClassInstanceCreationVisitor visitor = new ClassInstanceCreationVisitor();
						compilationUnit.accept(visitor);
						for(ClassInstanceCreation cic : visitor.getClassInstanceCreations()) {
							classInstances.add(cic.getType().toString());
						}

						for(String classInstance : classInstances) {
							if(classNames.contains(classInstance)) {
								return true;
							}
						}
					}
				}
			}
		}
		catch (JavaModelException e1) {
			logger.log(Level.SEVERE, "[AchievementConditions] Error", e1);
		}
		return false;
	}
	
}
