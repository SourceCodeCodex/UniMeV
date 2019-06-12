package packages.properties;

import ro.lrg.xcore.metametamodel.IPropertyComputer;
import ro.lrg.xcore.metametamodel.PropertyComputer;
import unimev.metamodel.entity.MPackage;

@PropertyComputer
public class ToString implements IPropertyComputer<String, MPackage>{

	@SuppressWarnings("restriction")
	@Override
	public String compute(MPackage arg0) {
		String name = arg0.getUnderlyingObject().getElementName();
		if(name == "")
		{
			name="default package";
		}
		return name;
	}
}