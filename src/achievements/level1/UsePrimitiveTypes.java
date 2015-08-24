package achievements.level1;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.PrimitiveType;

import achievements.DataStorage;
import visitors.PrimitiveTypeVisitor;

public class UsePrimitiveTypes {

private CompilationUnit cu;
	
	public UsePrimitiveTypes(CompilationUnit cu) {
		this.cu = cu;
	}

	public boolean isComplete() {
		DataStorage dataStorage = new DataStorage();
		int primitiveTypeCount = 0;		
		String[] primitiveTypes = {"boolean", "byte", "char", "double", "float","int", "long", "short"};
		PrimitiveType.Code[] primitiveTypeCodes = {	PrimitiveType.BOOLEAN, PrimitiveType.BYTE,
													PrimitiveType.CHAR, PrimitiveType.DOUBLE,
													PrimitiveType.FLOAT, PrimitiveType.INT,
													PrimitiveType.LONG, PrimitiveType.SHORT	};
		for(int i = 0; i < primitiveTypes.length; i++) {
			if(dataStorage.getPrimitiveTypes()[i]) {	// already used: count up
				primitiveTypeCount++;	
			}
			else {	// not yet used: parse class for primitive
				PrimitiveTypeVisitor visitor = new PrimitiveTypeVisitor();
				cu.accept(visitor);
				for(PrimitiveType primitiveType : visitor.getPrimitiveTypes()) {
					if(primitiveType.getPrimitiveTypeCode() == primitiveTypeCodes[i]) {
						dataStorage.setPrimitiveType(primitiveTypes[i], true);
						primitiveTypeCount++;
						break;
					}
				}
			}
		}
		return (primitiveTypeCount >= 4) ? true : false;
	}
	
}
