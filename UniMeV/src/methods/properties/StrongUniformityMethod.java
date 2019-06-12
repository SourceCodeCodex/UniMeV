package methods.properties;

import java.util.List;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;

import methods.ModifierChecker;
import ro.lrg.xcore.metametamodel.Group;
import ro.lrg.xcore.metametamodel.IPropertyComputer;
import ro.lrg.xcore.metametamodel.PropertyComputer;
import unimev.metamodel.entity.MMethod;
import unimev.metamodel.entity.MType;
import utilities.Preferences;

@PropertyComputer
public class StrongUniformityMethod implements IPropertyComputer<Double, MMethod>{
	
	//SU-call is superclass (up)
	//redefined -> up
	//redefining -> down
	@SuppressWarnings("static-access")
	@Override
	public Double compute(MMethod arg0) {
		int i=0;
		IMethod m = arg0.getUnderlyingObject();
		if(ModifierChecker.getInstance().checkModifier(m)==true)
		{
			return -2.0; //error code for private/static modifier
		}
		Group<MType> types = arg0.invocationGroup();
		Group<MType> altTypes = new Group<>();
		IType baseClass = m.getDeclaringType();
		List<MMethod> methods = arg0.redefiningMethodGroup().getElements();
		for(MMethod met:methods)
		{
			Group<MType> cs=met.invocationGroup();
			for(MType c:cs.getElements())
			{
				types.add(c);
			}
		}
		if(types.getElements().size()==0)
		{
			return -1.0; //error code for not having any calls
		}
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
		
		
		for(MType cls : types.getElements())
		{
			IType type = cls.getUnderlyingObject();
			if(type.equals(baseClass))
			{
				i++;
			}
		}
		double d = (double)(i+altTypes.getElements().size())/(double)(types.getElements().size()+altTypes.getElements().size());
		return d;
	}
	
}
