package types.properties;

import ro.lrg.xcore.metametamodel.IPropertyComputer;
import ro.lrg.xcore.metametamodel.PropertyComputer;
import unimev.metamodel.entity.MType;

@PropertyComputer
public class ToString implements IPropertyComputer<String, MType>{

	@Override
	public String compute(MType arg0) {
		return arg0.getUnderlyingObject().getElementName();
	}

}