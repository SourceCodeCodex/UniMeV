package startup;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.internal.core.PackageFragment;
import org.eclipse.ui.IStartup;

import ro.lrg.insider.view.ToolRegistration;
import ro.lrg.insider.view.ToolRegistration.XEntityConverter;
import ro.lrg.xcore.metametamodel.XEntity;
import unimev.metamodel.factory.Factory;

@SuppressWarnings("restriction")
public class Startup implements IStartup{

	@Override
	public void earlyStartup() {
		ToolRegistration.getInstance().registerXEntityConverter(
				new XEntityConverter() {
					
					@Override
					public XEntity convert(Object element) {
						if(element instanceof IMethod)
							return Factory.getInstance().createMMethod((IMethod)element);
						else if(element instanceof IType)
								return Factory.getInstance().createMType((IType)element);
						else if(element instanceof IJavaProject)
								return Factory.getInstance().createMProject((IJavaProject)element);
						else if(element instanceof PackageFragment)
								return Factory.getInstance().createMPackage((PackageFragment)element);
						return null;
					}
				});
	}
	
}