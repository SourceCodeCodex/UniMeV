package methods.properties;

import java.util.List;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.ITypeHierarchy;
import org.eclipse.jdt.core.JavaModelException;

import methods.ModifierChecker;
import ro.lrg.xcore.metametamodel.Group;
import ro.lrg.xcore.metametamodel.IPropertyComputer;
import ro.lrg.xcore.metametamodel.PropertyComputer;
import unimev.metamodel.entity.MMethod;
import unimev.metamodel.entity.MType;
import utilities.Preferences;

@PropertyComputer
public class NonUniformityMethod implements IPropertyComputer<Double, MMethod>{

	//NU-a single class in the set (down)
	@Override
	public Double compute(MMethod arg0) {
		int i=0; //i-number of calls
		IMethod m = arg0.getUnderlyingObject();
		if(ModifierChecker.getInstance().checkModifier(m)==true)
		{
			return -2.0; //error code for private/static modifier
		}
		Group<MType> types = arg0.invocationGroup();
		IType baseClass = m.getDeclaringType();
		for(MMethod met:arg0.redefiningMethodGroup().getElements())
		{
			Group<MType> cs=met.invocationGroup();
			for(MType c:cs.getElements())
			{
				types.add(c);
			}
		}
		
		Group<MType> altTypes = new Group<>();
		if(Preferences.withRedefinedMethods==true) //look up?
		{
			List<MMethod> methods2 = arg0.redefinedMethodGroup().getElements();
			for(MMethod met:methods2)
			{
				Group<MType> cs=met.invocationGroup();
				for(MType c:cs.getElements())
				{
					altTypes.add(c);
				}
			}
		}
		if(types.getElements().size()==0)
		{
			return -1.0; //error code for not having any calls
		}
		try {
			ITypeHierarchy hierarchy = baseClass.newTypeHierarchy(null);
			for(MType cls : types.getElements())
			{
				IType type = cls.getUnderlyingObject();
				if(!type.equals(baseClass) &&  hierarchy.getSubclasses(type).length==0)
				{
					i++;
				}
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		double d = (double)i/(double)(types.getElements().size()+altTypes.getElements().size());
		return d;
	}

}
