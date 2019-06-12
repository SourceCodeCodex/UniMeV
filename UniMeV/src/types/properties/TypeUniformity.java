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
public class TypeUniformity implements IPropertyComputer<String, MType>{

	@Override
	public String compute(MType arg0) {
		double su=0;
		double tsu=0;
		double wu=0;
		double twu=0;
		double nu=0;
		double tnu=0;
		int length=1;
		int i=0;
		int noc=0;
		int tnoc=0;
		IMethod[] methods;
		IType clas = arg0.getUnderlyingObject();
		try {
			methods = clas.getMethods();
			length = methods.length;
			for(IMethod method:methods)
			{
				MMethod mmethod = Factory.getInstance().createMMethod(method);
				String u = mmethod.methodUniformity();
				u=u.replace("Uniformity: ", "");
				u=u.replace("Strong ", "");
				u=u.replace("Weak ", "");
				u=u.replace("Non ", "");
				u=u.replace("NOC: ", "");
				u=u.replace("\n", " ");
				i = u.indexOf(" ");
				su = Double.parseDouble(u.substring(0, i));
				u = u.substring(i+1, u.length());
				i = u.indexOf(" ");
				wu = Double.parseDouble(u.substring(0, i));
				u = u.substring(i+1, u.length());
				i = u.indexOf(" ");
				nu = Double.parseDouble(u.substring(0, i));
				u = u.substring(i+1, u.length());
				noc = Integer.parseInt(u);
				if(su<0 || wu<0 || nu<0)
				{
					su=0;
					wu=0;
					nu=0;
					noc=0;
					length--;
				}
				tsu+=su;
				twu+=wu;
				tnu+=nu;
				tnoc+=noc;
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		if(length==0)
		{
			return "Strong Uniformity: -1\nWeak Uniformity: -1\nNon Uniformity: -1\nNOC: "+tnoc; //there are no methods or methods or calls on methods have all return errors
		}
		return "Strong Uniformity: "+tsu/length + "\nWeak Uniformity: " + twu/length + "\nNon Uniformity: "+tnu/length+ "\nNOC: "+tnoc;	
	}

}
