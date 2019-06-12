package unimev.metamodel.impl;

import unimev.metamodel.entity.*;
import projects.properties.ToString;
import projects.actions.ProjectVisualisation;

public class MProjectImpl implements MProject {

	private org.eclipse.jdt.core.IJavaProject underlyingObj_;

	private static final ToString ToString_INSTANCE = new ToString();
	private static final ProjectVisualisation ProjectVisualisation_INSTANCE = new ProjectVisualisation();

	public MProjectImpl(org.eclipse.jdt.core.IJavaProject underlyingObj) {
		underlyingObj_ = underlyingObj;
	}

	@Override
	public org.eclipse.jdt.core.IJavaProject getUnderlyingObject() {
		return underlyingObj_;
	}

	@Override
	@ro.lrg.xcore.metametamodel.ThisIsAProperty
	public java.lang.String toString() {
		return ToString_INSTANCE.compute(this);
	}

	@Override
	@ro.lrg.xcore.metametamodel.ThisIsAnAction
	public void projectVisualisation() {
		 ProjectVisualisation_INSTANCE.performAction(this, ro.lrg.xcore.metametamodel.HListEmpty.getInstance());
	}

	public boolean equals(Object obj) {
		if (null == obj || !(obj instanceof MProjectImpl)) {
			return false;
		}
		MProjectImpl iObj = (MProjectImpl)obj;
		return underlyingObj_.equals(iObj.underlyingObj_);
	}

	public int hashCode() {
		return 97 * underlyingObj_.hashCode();
	}
}
