package unimev.metamodel.entity;

public interface MPackage extends ro.lrg.xcore.metametamodel.XEntity {

	@ro.lrg.xcore.metametamodel.ThisIsAProperty
	public java.lang.String toString();

	@ro.lrg.xcore.metametamodel.ThisIsAnAction(numParams = 0) 
	public void packageVisualisation ();

	org.eclipse.jdt.internal.core.PackageFragment getUnderlyingObject();
}
