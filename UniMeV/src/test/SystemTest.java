package test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.ui.dialogs.IOverwriteQuery;
import org.eclipse.ui.internal.wizards.datatransfer.ZipLeveledStructureProvider;
import org.eclipse.ui.wizards.datatransfer.ImportOperation;
import org.junit.BeforeClass;
import org.junit.Test;

import unimev.metamodel.entity.MMethod;
import unimev.metamodel.entity.MType;
import unimev.metamodel.factory.Factory;
import utilities.Preferences;

@SuppressWarnings("restriction")
public class SystemTest {
	private static IJavaProject javaProject;

	@BeforeClass
	public static void setup() throws CoreException, IOException, InvocationTargetException, InterruptedException
	{
		String projectName = "TestWorkspace";
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProjectDescription newProjectDescription = workspace.newProjectDescription(projectName);
		IProject newProject = workspace.getRoot().getProject(projectName);
		newProject.create(newProjectDescription, null);
		newProject.open(null);
		
		SystemTest st = new SystemTest();
		URL location = st.getClass().getProtectionDomain().getCodeSource().getLocation();
		
		ZipFile zipFile = new ZipFile(location.getPath()+"//src//test//resources//TestWorkSpace.zip");
		IOverwriteQuery overwriteQuery = new IOverwriteQuery() {
		    public String queryOverwrite(String file) { return ALL; }
		};
		ZipLeveledStructureProvider provider = new ZipLeveledStructureProvider(zipFile);
		List<Object> fileSystemObjects = new ArrayList<Object>();
		Enumeration<? extends ZipEntry> entries = zipFile.entries();
		while (entries.hasMoreElements()) {
		    fileSystemObjects.add((Object)entries.nextElement());
		}
		ImportOperation importOperation = new ImportOperation(newProject.getFullPath(), new ZipEntry(projectName), provider, overwriteQuery, fileSystemObjects);
		importOperation.setCreateContainerStructure(false);
		importOperation.run(new NullProgressMonitor());
		
		javaProject = JavaCore.create(newProject);
		Preferences.withRedefinedMethods=false;
	}
	
	@Test
	public void test_normal() throws JavaModelException{
		IType type = javaProject.findType("normal.A");
		IMethod[] methods = type.getMethods();
		IMethod imethod = methods[0];
		MMethod method = Factory.getInstance().createMMethod(imethod);
		double strongUniformity = method.strongUniformityMethod();
		double weakUniformity = method.weakUniformityMethod();
		double nonUniformity = method.nonUniformityMethod();
		assertEquals(0.5, strongUniformity, 0);
		assertEquals(0.5, nonUniformity, 0);
		assertEquals(0, weakUniformity, 0);
		
		MType mclass = Factory.getInstance().createMType(type);
		strongUniformity = mclass.strongUniformityType();
		weakUniformity = mclass.weakUniformityType();
		nonUniformity = mclass.nonUniformityType();
		assertEquals(0.5, strongUniformity, 0);
		assertEquals(0.5, nonUniformity, 0);
		assertEquals(0, weakUniformity, 0);
	}
	
	@Test
	public void test_normal2() throws JavaModelException{
		IType type = javaProject.findType("normal2.A");
		IMethod[] methods = type.getMethods();
		IMethod imethod = methods[0];
		MMethod method = Factory.getInstance().createMMethod(imethod);
		double strongUniformity = method.strongUniformityMethod();
		double weakUniformity = method.weakUniformityMethod();
		double nonUniformity = method.nonUniformityMethod();
		assertEquals(0.33, strongUniformity, 0.01);
		assertEquals(0.33, nonUniformity, 0.01);
		assertEquals(0.33, weakUniformity, 0.01);

		type = javaProject.findType("normal2.B");
		methods = type.getMethods();
		imethod = methods[0];
		method = Factory.getInstance().createMMethod(imethod);
		strongUniformity = method.strongUniformityMethod();
		weakUniformity = method.weakUniformityMethod();
		nonUniformity = method.nonUniformityMethod();
		assertEquals(0.5, strongUniformity, 0.01);
		assertEquals(0.5, nonUniformity, 0.01);
		assertEquals(0, weakUniformity, 0.01);
		
		Preferences.withRedefinedMethods=true;
		type = javaProject.findType("normal2.B");
		methods = type.getMethods();
		imethod = methods[0];
		method = Factory.getInstance().createMMethod(imethod);
		strongUniformity = method.strongUniformityMethod();
		weakUniformity = method.weakUniformityMethod();
		nonUniformity = method.nonUniformityMethod();
		assertEquals(0.66, strongUniformity, 0.01);
		assertEquals(0.33, nonUniformity, 0.01);
		assertEquals(0, weakUniformity, 0.01);
	}
	
	@Test
	public void test_exceptions() throws JavaModelException
	{
		IType type = javaProject.findType("exceptions.A");
		IMethod[] methods = type.getMethods();
		IMethod imethod = methods[0];
		MMethod method = Factory.getInstance().createMMethod(imethod);
		double strongUniformity = method.strongUniformityMethod();
		double weakUniformity = method.weakUniformityMethod();
		double nonUniformity = method.nonUniformityMethod();
		assertEquals(-2.0, strongUniformity, 0.01);
		assertEquals(-2.0, nonUniformity, 0.01);
		assertEquals(-2.0, weakUniformity, 0.01);
		
		MType mclass = Factory.getInstance().createMType(type);
		strongUniformity = mclass.strongUniformityType();
		weakUniformity = mclass.weakUniformityType();
		nonUniformity = mclass.nonUniformityType();
		assertEquals(-1.0, strongUniformity, 0);
		assertEquals(-1.0, nonUniformity, 0);
		assertEquals(-1.0, weakUniformity, 0);
		
		type = javaProject.findType("exceptions.B");
		methods = type.getMethods();
		imethod = methods[0];
		method = Factory.getInstance().createMMethod(imethod);
		strongUniformity = method.strongUniformityMethod();
		weakUniformity = method.weakUniformityMethod();
		nonUniformity = method.nonUniformityMethod();
		assertEquals(-2.0, strongUniformity, 0.01);
		assertEquals(-2.0, nonUniformity, 0.01);
		assertEquals(-2.0, weakUniformity, 0.01);
		
		mclass = Factory.getInstance().createMType(type);
		strongUniformity = mclass.strongUniformityType();
		weakUniformity = mclass.weakUniformityType();
		nonUniformity = mclass.nonUniformityType();
		assertEquals(-1.0, strongUniformity, 0);
		assertEquals(-1.0, nonUniformity, 0);
		assertEquals(-1.0, weakUniformity, 0);
		
		type = javaProject.findType("exceptions.C");
		methods = type.getMethods();
		imethod = methods[0];
		method = Factory.getInstance().createMMethod(imethod);
		strongUniformity = method.strongUniformityMethod();
		weakUniformity = method.weakUniformityMethod();
		nonUniformity = method.nonUniformityMethod();
		assertEquals(-1.0, strongUniformity, 0.01);
		assertEquals(-1.0, nonUniformity, 0.01);
		assertEquals(-1.0, weakUniformity, 0.01);
		
		mclass = Factory.getInstance().createMType(type);
		strongUniformity = mclass.strongUniformityType();
		weakUniformity = mclass.weakUniformityType();
		nonUniformity = mclass.nonUniformityType();
		assertEquals(-1.0, strongUniformity, 0);
		assertEquals(-1.0, nonUniformity, 0);
		assertEquals(-1.0, weakUniformity, 0);
		
		type = javaProject.findType("exceptions.D");
		mclass = Factory.getInstance().createMType(type);
		strongUniformity = mclass.strongUniformityType();
		weakUniformity = mclass.weakUniformityType();
		nonUniformity = mclass.nonUniformityType();
		assertEquals(-1.0, strongUniformity, 0);
		assertEquals(-1.0, nonUniformity, 0);
		assertEquals(-1.0, weakUniformity, 0);
	}
}
