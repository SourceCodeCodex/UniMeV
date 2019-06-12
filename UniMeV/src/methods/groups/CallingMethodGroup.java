package methods.groups;

import java.util.HashSet;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.internal.corext.callhierarchy.CallHierarchy;
import org.eclipse.jdt.internal.corext.callhierarchy.MethodWrapper;

import ro.lrg.xcore.metametamodel.Group;
import ro.lrg.xcore.metametamodel.IRelationBuilder;
import ro.lrg.xcore.metametamodel.RelationBuilder;
import unimev.metamodel.entity.MMethod;
import unimev.metamodel.factory.Factory;

@SuppressWarnings("restriction")
@RelationBuilder
public class CallingMethodGroup implements IRelationBuilder<MMethod, MMethod>{

	@Override
	public Group<MMethod> buildGroup(MMethod arg0) {
		Group<MMethod> res = new Group<>();
		IMethod method = arg0.getUnderlyingObject();
		HashSet<IMethod> methods = new HashSet<>();
		
		IMember[] members = {method};
		CallHierarchy ch = CallHierarchy.getDefault();

		MethodWrapper[] mw = ch.getCallerRoots(members);
		for(MethodWrapper m:mw)
		{
			MethodWrapper[] m2 = m.getCalls(new NullProgressMonitor());
			methods = getIMethods(m2);
		}
		
		for(IMethod aJDTMethod:methods)
		{
			MMethod m = Factory.getInstance().createMMethod(aJDTMethod);
			res.add(m);
		}
		return res;
	}

	private HashSet<IMethod> getIMethods(MethodWrapper[] methodWrappers) {
		HashSet<IMethod> c = new HashSet<IMethod>();
		for (MethodWrapper m : methodWrappers) {
			IMethod im = getIMethodFromMethodWrapper(m);
			if (im != null) {
				c.add(im);
		    }
		}
		return c;
	}
	
	private IMethod getIMethodFromMethodWrapper(MethodWrapper m) {
		try {
			IMember im = m.getMember();
			if (im.getElementType() == IJavaElement.METHOD) {
				return (IMethod)m.getMember();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
