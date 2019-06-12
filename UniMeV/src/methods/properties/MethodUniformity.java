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
public class MethodUniformity implements IPropertyComputer<String, MMethod>{
	
	//SU-call is superclass (up)
	//redefined -> up
	//redefining -> down
	@SuppressWarnings("static-access")
	@Override
	public String compute(MMethod arg0) {
		IMethod m = arg0.getUnderlyingObject();
		if(ModifierChecker.getInstance().checkModifier(m)==true)
		{
			return "Strong Uniformity: -2\nWeak Uniformity: -2\nNon Uniformity: -2\nNOC: 1"; //error code for private/static modifier
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
			return "Strong Uniformity: -1\nWeak Uniformity: -1\nNon Uniformity: -1\nNOC: 1"; //error code for not having any calls
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
		int su = 0;
		int wu = 0;
		int nu = 0;
		ITypeHierarchy hierarchy=null;
		try{
			hierarchy = baseClass.newTypeHierarchy(null);
		}catch(JavaModelException e){
			e.printStackTrace();
		}
		for(MType cls : types.getElements())
		{
			IType type = cls.getUnderlyingObject();
			if(type.equals(baseClass))
			{
				su++;
			}
			else if(hierarchy.getSubclasses(type).length>0)
			{
				wu++;
			}
			else
			{
				nu++;
			}
		}
		int ant = altTypes.getElements().size();
		int nt = types.getElements().size();
		double dsu = (double)(su+ant)/(double)(nt+ant);
		double dwu = (double)wu/(double)(nt+ant);
		double dnu = (double)nu/(double)(nt+ant);
		return "Strong Uniformity: "+dsu + "\nWeak Uniformity: " + dwu + "\nNon Uniformity: "+ dnu + "\nNOC: "+(nt+ant);
	}
	
}
