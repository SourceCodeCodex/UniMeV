package types.groups;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;

import ro.lrg.xcore.metametamodel.Group;
import ro.lrg.xcore.metametamodel.IRelationBuilder;
import ro.lrg.xcore.metametamodel.RelationBuilder;
import unimev.metamodel.entity.MMethod;
import unimev.metamodel.entity.MType;
import unimev.metamodel.factory.Factory;

@RelationBuilder
public class MethodGroup implements IRelationBuilder<MMethod, MType>{

	@Override
	public Group<MMethod> buildGroup(MType arg0) {
		Group<MMethod> res = new Group<>();
		try {
			IMethod[] all = arg0.getUnderlyingObject().getMethods();
			for(IMethod aJDTMethod:all)
			{
				MMethod m = Factory.getInstance().createMMethod(aJDTMethod);
				res.add(m);
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		return res;
	}

}
