package projects.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.core.JarPackageFragmentRoot;
import org.eclipse.jdt.internal.core.PackageFragment;

import ro.lrg.xcore.metametamodel.ActionPerformer;
import ro.lrg.xcore.metametamodel.HListEmpty;
import ro.lrg.xcore.metametamodel.IActionPerformer;
import unimev.metamodel.entity.MProject;
import unimev.metamodel.entity.MType;
import unimev.metamodel.factory.Factory;
import visualisation.Visualisation;

@SuppressWarnings("restriction")
@ActionPerformer
public class ProjectVisualisation implements IActionPerformer<Void, MProject, HListEmpty>{

	@Override
	public Void performAction(MProject arg0, HListEmpty arg1) {
		int numberi=0;
		double su=0;
		double wu=0;
		double nu=0;
		int i=0;
		int packageNumber=0;
		int totalNumberOfPackages = 1;
		int noc = 0;
		long initialTime=System.currentTimeMillis();
		String name ="";
		IJavaProject project = arg0.getUnderlyingObject();
		String s = "var datelemele = [\n{children:\n[";
		List<PackageFragment> packages = new ArrayList<>();
		try {
			IPackageFragmentRoot[] pfr = project.getPackageFragmentRoots();
			for(IPackageFragmentRoot pi : pfr)
			{
				if(pi instanceof JarPackageFragmentRoot)
				{
					continue;
				}
				IJavaElement[] elm = pi.getChildren();
				for(IJavaElement e:elm)
				{
					if(e instanceof PackageFragment)
					{
						packages.add((PackageFragment)e);
					}
				}
			}
			boolean used = false;
			totalNumberOfPackages = packages.size();
			for(IPackageFragment p:packages)
			{
				packageNumber++;
				ICompilationUnit[] units = p.getCompilationUnits();
				boolean ni=false;
				String ss="";
				for(ICompilationUnit unit:units)
				{	
					IType[] t = unit.getAllTypes();
					for(IType t1:t)
					{					
						if(t1.isInterface())
						{
							numberi++;
							if(ni==true)
							{
								ss+=",\n";
							}
							else
							{
								ni=true;
							}
							MType mclass = Factory.getInstance().createMType(t1);
							String u=mclass.typeUniformity();
							u=u.replace("Uniformity: ", "");
							u=u.replace("Strong ", "");
							u=u.replace("Weak ", "");
							u=u.replace("Non ", "");
							u=u.replace("NOC: ", "");
							u=u.replace("\n", " ");
							i = u.indexOf(" ");
							su = Double.parseDouble(u.substring(0, i));
							u = u.substring(i+1, u.length());
							i = u.indexOf(" ");
							wu = Double.parseDouble(u.substring(0, i));
							u = u.substring(i+1, u.length());
							i = u.indexOf(" ");
							nu = Double.parseDouble(u.substring(0, i));
							u = u.substring(i+1, u.length());
							noc = Integer.parseInt(u);
							
							double dsu=su*100;
							double dwu=wu*100;
							double dnu=nu*100;
							//{package: "packet", name: "I5", su: 1.0, wu: 100.0, nu: 100.0, noc: 5},
							name = p.getElementName();
							if(name=="")
							{
								name="default package";
							}
							if(su<0)
							{
								ss+="\t{package: \""+name +"\", name: \"" + t1.getElementName()+"\",su: "+ 1 +",wu: "+1+", nu: "+1+ ",noc: "+noc +"}";
							}
							else
							{
								ss+="\t{package: \""+name +"\", name: \"" + t1.getElementName()+"\",su: "+ dsu +",wu: "+dwu+", nu: "+dnu+",noc: "+noc +"}";
							}
						}
						System.out.println("Interface: " + t1.getElementName());
					}
				}
				if(ss!="")
				{
					//{name:"default package", children: 
					if(used==true)
					{
						s+=",\n";
					}
					else
					{
						used=true;
					}
					s+="{name: \""+ name + "\", children:\n[";
					s+=ss;
					s+="]}";
				}
				System.out.println("Package: "+p.getElementName() + " "+ packageNumber + "/"+totalNumberOfPackages);
			}
			
			s+="],\nname: \"Visualisation\"}];\n";
			
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		Visualisation.visualize(s);
		
		long finalTime = System.currentTimeMillis();
		long seconds = (finalTime-initialTime)/1000;
		long minutes = seconds/60;
		if(minutes>0)
		{
			seconds/=60;
		}
		System.out.println("Finished! \nNumber of packages: "+packages.size() + " \nNumber of interfaces: "+numberi+" \nTime: "+minutes + " minutes " + seconds + "seconds");
		return null;
	}

}
