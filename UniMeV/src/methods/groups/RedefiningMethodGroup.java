package methods.groups;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.ITypeHierarchy;
import org.eclipse.jdt.core.JavaModelException;
import ro.lrg.xcore.metametamodel.Group;
import ro.lrg.xcore.metametamodel.IRelationBuilder;
import ro.lrg.xcore.metametamodel.RelationBuilder;
import unimev.metamodel.entity.MMethod;
import unimev.metamodel.factory.Factory;
/**
 * This relation builder provides a group with methods in subtypes that redefine the specified MMethod.
 * @author bogdan.igna
 *
 */
@RelationBuilder
public class RedefiningMethodGroup implements IRelationBuilder<MMethod, MMethod>{

	@Override
	public Group<MMethod> buildGroup(MMethod arg0) {
		List<IMethod> redefinedMethods = new ArrayList<>();
		Group<MMethod> mets = new Group<>();
		IMethod method = arg0.getUnderlyingObject();
		IType baseClass = method.getDeclaringType();
		try {
			//TODO: check with class in other packet
			ITypeHierarchy hierarchy = baseClass.newTypeHierarchy(null);
			IType[] types=hierarchy.getAllSubtypes(baseClass);
			for(IType type:types)
			{
				for(IMethod m:type.getMethods())
				{
					if(m.getElementName().equals(method.getElementName()) && m.getSignature().equals(method.getSignature()))
					{
						redefinedMethods.add(m);
						break;
					}
				}
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		for(IMethod met:redefinedMethods)
		{
			mets.add(Factory.getInstance().createMMethod(met));
		}
		return mets;
	}
	
}
