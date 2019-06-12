package unimev.metamodel.factory;

import ro.lrg.xcore.metametamodel.XEntity;
import unimev.metamodel.entity.*;
import unimev.metamodel.impl.*;

public class Factory {
   protected static Factory singleInstance = new Factory();
   public static Factory getInstance() { return singleInstance;}
   protected Factory() {}
   private LRUCache<Object, XEntity> lruCache_ = new LRUCache<>(1000);
   public void setCacheCapacity(int capacity) {
       lruCache_.setCapacity(capacity);
   }
   public void clearCache() {lruCache_.clear();}
   public MPackage createMPackage(org.eclipse.jdt.internal.core.PackageFragment obj) {
       XEntity instance = lruCache_.get(obj);
        if (null == instance) {
           instance = new MPackageImpl(obj);
           lruCache_.put(obj, instance);
        }
        return (MPackage)instance;
    }
   public MType createMType(org.eclipse.jdt.core.IType obj) {
       XEntity instance = lruCache_.get(obj);
        if (null == instance) {
           instance = new MTypeImpl(obj);
           lruCache_.put(obj, instance);
        }
        return (MType)instance;
    }
   public MMethod createMMethod(org.eclipse.jdt.core.IMethod obj) {
       XEntity instance = lruCache_.get(obj);
        if (null == instance) {
           instance = new MMethodImpl(obj);
           lruCache_.put(obj, instance);
        }
        return (MMethod)instance;
    }
   public MProject createMProject(org.eclipse.jdt.core.IJavaProject obj) {
       XEntity instance = lruCache_.get(obj);
        if (null == instance) {
           instance = new MProjectImpl(obj);
           lruCache_.put(obj, instance);
        }
        return (MProject)instance;
    }
}
