package unimev.metamodel.impl;

import unimev.metamodel.entity.*;
import packages.properties.ToString;
import packages.actions.PackageVisualisation;

public class MPackageImpl implements MPackage {

	private org.eclipse.jdt.internal.core.PackageFragment underlyingObj_;

	private static final ToString ToString_INSTANCE = new ToString();
	private static final PackageVisualisation PackageVisualisation_INSTANCE = new PackageVisualisation();

	public MPackageImpl(org.eclipse.jdt.internal.core.PackageFragment underlyingObj) {
		underlyingObj_ = underlyingObj;
	}

	@Override
	public org.eclipse.jdt.internal.core.PackageFragment getUnderlyingObject() {
		return underlyingObj_;
	}

	@Override
	@ro.lrg.xcore.metametamodel.ThisIsAProperty
	public java.lang.String toString() {
		return ToString_INSTANCE.compute(this);
	}

	@Override
	@ro.lrg.xcore.metametamodel.ThisIsAnAction
	public void packageVisualisation() {
		 PackageVisualisation_INSTANCE.performAction(this, ro.lrg.xcore.metametamodel.HListEmpty.getInstance());
	}

	public boolean equals(Object obj) {
		if (null == obj || !(obj instanceof MPackageImpl)) {
			return false;
		}
		MPackageImpl iObj = (MPackageImpl)obj;
		return underlyingObj_.equals(iObj.underlyingObj_);
	}

	public int hashCode() {
		return 97 * underlyingObj_.hashCode();
	}
}
