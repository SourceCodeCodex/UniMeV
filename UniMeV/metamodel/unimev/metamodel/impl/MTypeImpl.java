package unimev.metamodel.impl;

import unimev.metamodel.entity.*;
import types.properties.WeakUniformityType;
import types.properties.StrongUniformityType;
import types.properties.NonUniformityType;
import types.properties.TypeUniformity;
import types.properties.ToString;
import types.groups.MethodGroup;

public class MTypeImpl implements MType {

	private org.eclipse.jdt.core.IType underlyingObj_;

	private static final WeakUniformityType WeakUniformityType_INSTANCE = new WeakUniformityType();
	private static final StrongUniformityType StrongUniformityType_INSTANCE = new StrongUniformityType();
	private static final NonUniformityType NonUniformityType_INSTANCE = new NonUniformityType();
	private static final TypeUniformity TypeUniformity_INSTANCE = new TypeUniformity();
	private static final ToString ToString_INSTANCE = new ToString();
	private static final MethodGroup MethodGroup_INSTANCE = new MethodGroup();

	public MTypeImpl(org.eclipse.jdt.core.IType underlyingObj) {
		underlyingObj_ = underlyingObj;
	}

	@Override
	public org.eclipse.jdt.core.IType getUnderlyingObject() {
		return underlyingObj_;
	}

	@Override
	@ro.lrg.xcore.metametamodel.ThisIsAProperty
	public java.lang.Double weakUniformityType() {
		return WeakUniformityType_INSTANCE.compute(this);
	}

	@Override
	@ro.lrg.xcore.metametamodel.ThisIsAProperty
	public java.lang.Double strongUniformityType() {
		return StrongUniformityType_INSTANCE.compute(this);
	}

	@Override
	@ro.lrg.xcore.metametamodel.ThisIsAProperty
	public java.lang.Double nonUniformityType() {
		return NonUniformityType_INSTANCE.compute(this);
	}

	@Override
	@ro.lrg.xcore.metametamodel.ThisIsAProperty
	public java.lang.String typeUniformity() {
		return TypeUniformity_INSTANCE.compute(this);
	}

	@Override
	@ro.lrg.xcore.metametamodel.ThisIsAProperty
	public java.lang.String toString() {
		return ToString_INSTANCE.compute(this);
	}

	@Override
	@ro.lrg.xcore.metametamodel.ThisIsARelationBuilder
	public ro.lrg.xcore.metametamodel.Group<MMethod> methodGroup() {
		return MethodGroup_INSTANCE.buildGroup(this);
	}

	public boolean equals(Object obj) {
		if (null == obj || !(obj instanceof MTypeImpl)) {
			return false;
		}
		MTypeImpl iObj = (MTypeImpl)obj;
		return underlyingObj_.equals(iObj.underlyingObj_);
	}

	public int hashCode() {
		return 97 * underlyingObj_.hashCode();
	}
}
