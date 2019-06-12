package unimev.metamodel.entity;

public interface MType extends ro.lrg.xcore.metametamodel.XEntity {

	@ro.lrg.xcore.metametamodel.ThisIsAProperty
	public java.lang.Double weakUniformityType();

	@ro.lrg.xcore.metametamodel.ThisIsAProperty
	public java.lang.Double strongUniformityType();

	@ro.lrg.xcore.metametamodel.ThisIsAProperty
	public java.lang.Double nonUniformityType();

	@ro.lrg.xcore.metametamodel.ThisIsAProperty
	public java.lang.String typeUniformity();

	@ro.lrg.xcore.metametamodel.ThisIsAProperty
	public java.lang.String toString();

	@ro.lrg.xcore.metametamodel.ThisIsARelationBuilder
	public ro.lrg.xcore.metametamodel.Group<MMethod> methodGroup();

	org.eclipse.jdt.core.IType getUnderlyingObject();
}
