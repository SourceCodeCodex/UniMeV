package unimev.metamodel.entity;

public interface MMethod extends ro.lrg.xcore.metametamodel.XEntity {

	@ro.lrg.xcore.metametamodel.ThisIsAProperty
	public java.lang.Double weakUniformityMethod();

	@ro.lrg.xcore.metametamodel.ThisIsAProperty
	public java.lang.Double nonUniformityMethod();

	@ro.lrg.xcore.metametamodel.ThisIsAProperty
	public java.lang.Double strongUniformityMethod();

	@ro.lrg.xcore.metametamodel.ThisIsAProperty
	public java.lang.String methodUniformity();

	@ro.lrg.xcore.metametamodel.ThisIsAProperty
	public java.lang.String toString();

	/**

 This relation builder provides a group with methods in supertypes that redefine the specified MMethod.
 @author bogdan.igna


	*/
	@ro.lrg.xcore.metametamodel.ThisIsARelationBuilder
	public ro.lrg.xcore.metametamodel.Group<MMethod> redefinedMethodGroup();

	/**

 This relation builder provides a group with MTypes of the callers of the MMethod.
 @author bogdan.igna


	*/
	@ro.lrg.xcore.metametamodel.ThisIsARelationBuilder
	public ro.lrg.xcore.metametamodel.Group<MType> invocationGroup();

	/**

 This relation builder provides a group with methods in subtypes that redefine the specified MMethod.
 @author bogdan.igna


	*/
	@ro.lrg.xcore.metametamodel.ThisIsARelationBuilder
	public ro.lrg.xcore.metametamodel.Group<MMethod> redefiningMethodGroup();

	@ro.lrg.xcore.metametamodel.ThisIsARelationBuilder
	public ro.lrg.xcore.metametamodel.Group<MMethod> callingMethodGroup();

	org.eclipse.jdt.core.IMethod getUnderlyingObject();
}
