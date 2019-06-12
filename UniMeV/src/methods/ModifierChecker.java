package methods;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;

public final class ModifierChecker{
	private boolean forbidden = false;
	private static ModifierChecker instance=null;
	
	private ModifierChecker()
	{
		
	}
	
	/**
	 * 
	 * @return an instance of ModifierChecker
	 */
	public static ModifierChecker getInstance()
	{
		if(instance==null)
		{
			instance=new ModifierChecker();
		}
		return instance;
	}
	
	/**
	 * Checks if a method has private/static modifier
	 * 
	 * @param m - the IMethod to be checked
	 * @return true/false
	 */
	public boolean checkModifier(IMethod m)
	{
		forbidden=false;
		ASTParser parser = ASTParser.newParser(AST.JLS10);
		ICompilationUnit compUnit = m.getCompilationUnit();
		parser.setSource(compUnit);
		parser.setResolveBindings(true);
		ASTNode node = parser.createAST(null);
		
		//TODO: m.getFlags()
		node.accept(new ASTVisitor() {
			public boolean visit(MethodDeclaration node)
			{
				if(node.getName().toString().equals(m.getElementName()))
				{
					IMethodBinding bbb = node.resolveBinding();
					IMethod c= (IMethod)bbb.getJavaElement();
					boolean ok = c.equals(m);
					if(ok==true)
					{
						int mod = bbb.getModifiers();
						if(Modifier.isFinal(mod) || Modifier.isStatic(mod) || Modifier.isPrivate(mod))
						{
							forbidden=true;
							return false;
						}
					}
				}
				return false;
			}
		});
		return forbidden;
	}
	
}