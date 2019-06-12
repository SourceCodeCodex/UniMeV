package unimev.metamodel.entity;

public interface MProject extends ro.lrg.xcore.metametamodel.XEntity {

	@ro.lrg.xcore.metametamodel.ThisIsAProperty
	public java.lang.String toString();

	@ro.lrg.xcore.metametamodel.ThisIsAnAction(numParams = 0) 
	public void projectVisualisation ();

	org.eclipse.jdt.core.IJavaProject getUnderlyingObject();
}
