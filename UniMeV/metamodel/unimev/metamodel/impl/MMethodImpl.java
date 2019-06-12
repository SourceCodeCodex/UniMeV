package unimev.metamodel.impl;

import unimev.metamodel.entity.*;
import methods.properties.WeakUniformityMethod;
import methods.properties.NonUniformityMethod;
import methods.properties.StrongUniformityMethod;
import methods.properties.MethodUniformity;
import methods.properties.ToString;
import methods.groups.RedefinedMethodGroup;
import methods.groups.InvocationGroup;
import methods.groups.RedefiningMethodGroup;
import methods.groups.CallingMethodGroup;

public class MMethodImpl implements MMethod {

	private org.eclipse.jdt.core.IMethod underlyingObj_;

	private static final WeakUniformityMethod WeakUniformityMethod_INSTANCE = new WeakUniformityMethod();
	private static final NonUniformityMethod NonUniformityMethod_INSTANCE = new NonUniformityMethod();
	private static final StrongUniformityMethod StrongUniformityMethod_INSTANCE = new StrongUniformityMethod();
	private static final MethodUniformity MethodUniformity_INSTANCE = new MethodUniformity();
	private static final ToString ToString_INSTANCE = new ToString();
	private static final RedefinedMethodGroup RedefinedMethodGroup_INSTANCE = new RedefinedMethodGroup();
	private static final InvocationGroup InvocationGroup_INSTANCE = new InvocationGroup();
	private static final RedefiningMethodGroup RedefiningMethodGroup_INSTANCE = new RedefiningMethodGroup();
	private static final CallingMethodGroup CallingMethodGroup_INSTANCE = new CallingMethodGroup();

	public MMethodImpl(org.eclipse.jdt.core.IMethod underlyingObj) {
		underlyingObj_ = underlyingObj;
	}

	@Override
	public org.eclipse.jdt.core.IMethod getUnderlyingObject() {
		return underlyingObj_;
	}

	@Override
	@ro.lrg.xcore.metametamodel.ThisIsAProperty
	public java.lang.Double weakUniformityMethod() {
		return WeakUniformityMethod_INSTANCE.compute(this);
	}

	@Override
	@ro.lrg.xcore.metametamodel.ThisIsAProperty
	public java.lang.Double nonUniformityMethod() {
		return NonUniformityMethod_INSTANCE.compute(this);
	}

	@Override
	@ro.lrg.xcore.metametamodel.ThisIsAProperty
	public java.lang.Double strongUniformityMethod() {
		return StrongUniformityMethod_INSTANCE.compute(this);
	}

	@Override
	@ro.lrg.xcore.metametamodel.ThisIsAProperty
	public java.lang.String methodUniformity() {
		return MethodUniformity_INSTANCE.compute(this);
	}

	@Override
	@ro.lrg.xcore.metametamodel.ThisIsAProperty
	public java.lang.String toString() {
		return ToString_INSTANCE.compute(this);
	}

	@Override
	/**

 This relation builder provides a group with methods in supertypes that redefine the specified MMethod.
 @author bogdan.igna


	*/
	@ro.lrg.xcore.metametamodel.ThisIsARelationBuilder
	public ro.lrg.xcore.metametamodel.Group<MMethod> redefinedMethodGroup() {
		return RedefinedMethodGroup_INSTANCE.buildGroup(this);
	}

	@Override
	/**

 This relation builder provides a group with MTypes of the callers of the MMethod.
 @author bogdan.igna


	*/
	@ro.lrg.xcore.metametamodel.ThisIsARelationBuilder
	public ro.lrg.xcore.metametamodel.Group<MType> invocationGroup() {
		return InvocationGroup_INSTANCE.buildGroup(this);
	}

	@Override
	/**

 This relation builder provides a group with methods in subtypes that redefine the specified MMethod.
 @author bogdan.igna


	*/
	@ro.lrg.xcore.metametamodel.ThisIsARelationBuilder
	public ro.lrg.xcore.metametamodel.Group<MMethod> redefiningMethodGroup() {
		return RedefiningMethodGroup_INSTANCE.buildGroup(this);
	}

	@Override
	@ro.lrg.xcore.metametamodel.ThisIsARelationBuilder
	public ro.lrg.xcore.metametamodel.Group<MMethod> callingMethodGroup() {
		return CallingMethodGroup_INSTANCE.buildGroup(this);
	}

	public boolean equals(Object obj) {
		if (null == obj || !(obj instanceof MMethodImpl)) {
			return false;
		}
		MMethodImpl iObj = (MMethodImpl)obj;
		return underlyingObj_.equals(iObj.underlyingObj_);
	}

	public int hashCode() {
		return 97 * underlyingObj_.hashCode();
	}
}
