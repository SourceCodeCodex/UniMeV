package methods.groups;

import java.util.HashSet;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.LambdaExpression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.internal.core.LambdaMethod;
import org.eclipse.jdt.internal.core.ResolvedSourceMethod;

import ro.lrg.xcore.metametamodel.Group;
import ro.lrg.xcore.metametamodel.IRelationBuilder;
import ro.lrg.xcore.metametamodel.RelationBuilder;
import unimev.metamodel.entity.MMethod;
import unimev.metamodel.entity.MType;
import unimev.metamodel.factory.Factory;
/**
 * This relation builder provides a group with MTypes of the callers of the MMethod.
 * @author bogdan.igna
 *
 */
@SuppressWarnings("restriction")
@RelationBuilder
public class InvocationGroup implements IRelationBuilder<MType, MMethod>{

	@Override
	public Group<MType> buildGroup(MMethod arg0) {
		Group<MType> invocations = new Group<>();
		Group<MMethod> callingMethods = arg0.callingMethodGroup();
		HashSet<IMethod> methods = new HashSet<>();
		for(MMethod m:callingMethods.getElements())
		{
			methods.add(m.getUnderlyingObject());
		}
		
		ASTParser parser = ASTParser.newParser(AST.JLS10);
		for(IMethod m:methods)
		{
			ICompilationUnit compUnit = m.getCompilationUnit();
			if(compUnit==null)
			{
				continue;
			}
			parser.setSource(compUnit);
			parser.setResolveBindings(true);
			ASTNode node = parser.createAST(null);
			if(m instanceof ResolvedSourceMethod)
			{
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
							node.getBody().accept(new ASTVisitor() {
								public boolean visit(MethodInvocation node) {
									if(node.getName().toString().equals(arg0.getUnderlyingObject().getElementName()))
									{
										IMethodBinding ccc = node.resolveMethodBinding();
										IMethod c2= (IMethod)ccc.getJavaElement();
										if(c2.equals(arg0.getUnderlyingObject()))
										{
											if(node.getExpression()==null)
											{//apel cu this fara efectiv "this"
												return false;
											}
											ITypeBinding bind = node.getExpression().resolveTypeBinding();
											IJavaElement je = bind.getJavaElement();
											if(!(je instanceof IType))
											{
												return false;
											}
											IType type = (IType)je;
											MType cls = Factory.getInstance().createMType(type);
											invocations.add(cls);
										}
									}
									return false;
								}
							});
						}
					}
					return false;
				}
			});
			}
			else if(m instanceof LambdaMethod)
			{
				node.accept(new ASTVisitor() {
					public boolean visit(LambdaExpression node)
					{
//						if(node.equals(ew))
						{
							IMethodBinding bbb = node.resolveMethodBinding();
							IMethod c= (IMethod)bbb.getJavaElement();
							
							boolean ok = c.getKey().equals(m.getKey());//c.equals(m);
							if(ok==true)
							{
							
								node.accept(new ASTVisitor() {
									public boolean visit(MethodInvocation node) {
										if(node.getName().toString().equals(arg0.getUnderlyingObject().getElementName()))
										{
											IMethodBinding ccc = node.resolveMethodBinding();
											IMethod c2= (IMethod)ccc.getJavaElement();
											if(c2.equals(arg0.getUnderlyingObject()))
											{
												if(node.getExpression()==null)
												{//apel cu this fara efectiv "this"
													return false;
												}
												ITypeBinding bind = node.getExpression().resolveTypeBinding();
												IJavaElement je = bind.getJavaElement();
												if(!(je instanceof IType))
												{
													return false;
												}
												IType type = (IType)je;
												MType cls = Factory.getInstance().createMType(type);
												invocations.add(cls);
											}
										}
										return false;
									}
								});
							}
						}
						return false;
					}
				});
			}
		}
		return invocations;
	}
}