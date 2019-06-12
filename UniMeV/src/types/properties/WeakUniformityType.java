package types.properties;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

import ro.lrg.xcore.metametamodel.IPropertyComputer;
import ro.lrg.xcore.metametamodel.PropertyComputer;
import unimev.metamodel.entity.MMethod;
import unimev.metamodel.entity.MType;
import unimev.metamodel.factory.Factory;

@PropertyComputer
public class WeakUniformityType implements IPropertyComputer<Double, MType>{

	@Override
	public Double compute(MType arg0) {
		double s=0;
		int length=1;
		IMethod[] methods;
		IType clas = arg0.getUnderlyingObject();
		try {
			methods = clas.getMethods();
			length=methods.length;
			for(IMethod method:methods)
			{
				MMethod mmethod = Factory.getInstance().createMMethod(method);
				double d= mmethod.weakUniformityMethod();
				if(d<0)
				{
					d=0;
					length--;
				}
				s+=d;
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		if(length==0)
		{
			return -1.0; //there are no methods or methods or calls on methods have all return errors
		}
		return s/length;
	}

}
