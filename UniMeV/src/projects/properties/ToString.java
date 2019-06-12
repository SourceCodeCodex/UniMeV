package projects.properties;

import ro.lrg.xcore.metametamodel.IPropertyComputer;
import ro.lrg.xcore.metametamodel.PropertyComputer;
import unimev.metamodel.entity.MProject;

@PropertyComputer
public class ToString implements IPropertyComputer<String, MProject>{

	@Override
	public String compute(MProject arg0) {
		return arg0.getUnderlyingObject().getElementName();
	}
}
