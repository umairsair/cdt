/*******************************************************************************
 * Copyright (c) 2006, 2016 IBM Corporation and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Mike Kucera (IBM Corporation) - initial API and implementation
 *     Markus Schorn (Wind River Systems)
 *     Thomas Corbat (IFS)
 *     Sergey Prigogin (Google)
 *******************************************************************************/
package org.eclipse.cdt.core.dom.ast.cpp;

import org.eclipse.cdt.core.dom.ast.IASTBinaryTypeIdExpression;
import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTInitializer;
import org.eclipse.cdt.core.dom.ast.IASTInitializerClause;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTProblem;
import org.eclipse.cdt.core.dom.ast.IASTProblemTypeId;
import org.eclipse.cdt.core.dom.ast.IASTReturnStatement;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.IASTToken;
import org.eclipse.cdt.core.dom.ast.IASTTypeId;
import org.eclipse.cdt.core.dom.ast.INodeFactory;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTCompositeTypeSpecifier.ICPPASTBaseSpecifier;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTEnumerationSpecifier.ScopeStyle;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTFunctionDeclarator.RefQualifier;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPUnaryTypeTransformation.Operator;
import org.eclipse.cdt.core.dom.ast.gnu.cpp.IGPPASTArrayRangeDesignator;
import org.eclipse.cdt.core.dom.parser.cpp.ICPPASTAttributeSpecifier;
import org.eclipse.cdt.core.parser.IScanner;

/**
 * Factory for AST nodes for the C++ programming language.
 *
 * @since 5.1
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ICPPNodeFactory extends INodeFactory {
	/**
	 * @since 5.5
	 */
	public ICPPASTAliasDeclaration newAliasDeclaration(IASTName aliasName, ICPPASTTypeId aliasedType);

	/**
	 * @since 5.2
	 */
	@Override
	public ICPPASTArrayDeclarator newArrayDeclarator(IASTName name);

	/**
	 * @since 6.0
	 */
	public ICPPASTArrayDesignator newArrayDesignator(ICPPASTExpression exp);

	/**
	 * @since 6.0
	 */
	public IGPPASTArrayRangeDesignator newArrayRangeDesignatorGPP(ICPPASTExpression floor, ICPPASTExpression ceiling);

	@Override
	public ICPPASTArraySubscriptExpression newArraySubscriptExpression(IASTExpression arrayExpr,
			IASTExpression subscript);

	/**
	 * @since 5.2
	 */
	public ICPPASTArraySubscriptExpression newArraySubscriptExpression(IASTExpression arrayExpr,
			IASTInitializerClause subscript);

	/**
	 * @since 5.7
	 */
	public ICPPASTAttribute newAttribute(char[] name, char[] scope, IASTToken argumentClause, boolean packExpansion);

	/**
	 * @since 6.0
	 */
	public ICPPASTAttributeList newAttributeList();

	/**
	 * @since 5.8
	 */
	public ICPPASTBaseSpecifier newBaseSpecifier(ICPPASTNameSpecifier nameSpecifier, int visibility, boolean isVirtual);

	@Override
	public ICPPASTBinaryExpression newBinaryExpression(int op, IASTExpression expr1, IASTExpression expr2);

	/**
	 * @since 5.2
	 */
	public ICPPASTBinaryExpression newBinaryExpression(int op, IASTExpression expr1, IASTInitializerClause expr2);

	/**
	 * @since 5.3
	 */
	public IASTExpression newBinaryTypeIdExpression(IASTBinaryTypeIdExpression.Operator op, IASTTypeId type1,
			IASTTypeId type2);

	/**
	 * @since 5.3
	 */
	public ICPPASTCapture newCapture();

	/**
	 * @since 6.5
	 */
	public ICPPASTInitCapture newInitCapture(ICPPASTDeclarator declarator);

	@Override
	public ICPPASTCastExpression newCastExpression(int operator, IASTTypeId typeId, IASTExpression operand);

	public ICPPASTCatchHandler newCatchHandler(IASTDeclaration decl, IASTStatement body);

	/**
	 * @since 5.7
	 */
	public ICPPASTClassVirtSpecifier newClassVirtSpecifier(ICPPASTClassVirtSpecifier.SpecifierKind kind);

	@Override
	public ICPPASTCompositeTypeSpecifier newCompositeTypeSpecifier(int key, IASTName name);

	/**
	 * @since 5.2
	 */
	public ICPPASTConstructorChainInitializer newConstructorChainInitializer(IASTName id, IASTInitializer initializer);

	/**
	 * @since 5.2
	 */
	public ICPPASTConstructorInitializer newConstructorInitializer(IASTInitializerClause[] args);

	public ICPPASTConversionName newConversionName(IASTTypeId typeId);

	/**
	 * @since 5.2
	 */
	@Override
	public ICPPASTDeclarator newDeclarator(IASTName name);

	/**
	 * @since 5.6
	 */
	public ICPPASTDecltypeSpecifier newDecltypeSpecifier(ICPPASTExpression decltypeExpression);

	public ICPPASTDeleteExpression newDeleteExpression(IASTExpression operand);

	/**
	 * @since 6.0
	 */
	public ICPPASTDesignatedInitializer newDesignatedInitializer(ICPPASTInitializerClause initializer);

	@Override
	public ICPPASTElaboratedTypeSpecifier newElaboratedTypeSpecifier(int kind, IASTName name);

	/**
	 * @since 5.2
	 * @deprecated Use {@code newEnumerationSpecifier(ScopeToken, IASTName, ICPPASTDeclSpecifier)} instead.
	 * If {@code isScoped == true} is passed {@code ScopeToken.CLASS} is assumed.
	 */
	@Deprecated
	public ICPPASTEnumerationSpecifier newEnumerationSpecifier(boolean isScoped, IASTName name,
			ICPPASTDeclSpecifier baseType);

	/**
	 * @since 6.6
	 */
	public ICPPASTEnumerationSpecifier newEnumerationSpecifier(ScopeStyle scopeStyle, IASTName name,
			ICPPASTDeclSpecifier baseType);

	public ICPPASTExplicitTemplateInstantiation newExplicitTemplateInstantiation(IASTDeclaration declaration);

	@Override
	public ICPPASTExpressionList newExpressionList();

	/**
	 * @since 5.2
	 */
	@Override
	public ICPPASTFieldDeclarator newFieldDeclarator(IASTName name, IASTExpression bitFieldSize);

	/**
	 * @since 6.0
	 */
	public ICPPASTFieldDesignator newFieldDesignator(IASTName name);

	@Override
	public ICPPASTFieldReference newFieldReference(IASTName name, IASTExpression owner);

	public ICPPASTForStatement newForStatement();

	public ICPPASTForStatement newForStatement(IASTStatement init, IASTDeclaration condition,
			IASTExpression iterationExpression, IASTStatement body);

	@Override
	public ICPPASTForStatement newForStatement(IASTStatement init, IASTExpression condition,
			IASTExpression iterationExpression, IASTStatement body);

	/**
	 * @since 5.2
	 */
	@Override
	public ICPPASTFunctionCallExpression newFunctionCallExpression(IASTExpression idExpr,
			IASTInitializerClause[] arguments);

	@Override
	public ICPPASTFunctionDeclarator newFunctionDeclarator(IASTName name);

	@Override
	public ICPPASTFunctionDefinition newFunctionDefinition(IASTDeclSpecifier declSpecifier,
			IASTFunctionDeclarator declarator, IASTStatement bodyStatement);

	public ICPPASTFunctionWithTryBlock newFunctionTryBlock(IASTDeclSpecifier declSpecifier,
			IASTFunctionDeclarator declarator, IASTStatement bodyStatement);

	public ICPPASTIfStatement newIfStatement();

	public ICPPASTIfStatement newIfStatement(IASTDeclaration condition, IASTStatement then, IASTStatement elseClause);

	@Override
	public ICPPASTIfStatement newIfStatement(IASTExpression condition, IASTStatement then, IASTStatement elseClause);

	/**
	 * @since 5.2
	 */
	@Override
	public ICPPASTInitializerList newInitializerList();

	/**
	 * @since 5.3
	 */
	public ICPPASTLambdaExpression newLambdaExpression();

	/**
	 * @since 8.1
	 */
	public IASTExpression newFoldExpressionToken();

	/**
	 * @since 8.1
	 */
	public ICPPASTFoldExpression newFoldExpression(int opToken, boolean isComma, IASTExpression lhs,
			IASTExpression rhs);

	/**
	 * @since 8.1
	 */
	public ICPPASTDeductionGuide newDeductionGuide();

	public ICPPASTLinkageSpecification newLinkageSpecification(String literal);

	@Override
	public ICPPASTLiteralExpression newLiteralExpression(int kind, String rep);

	/**
	 * @since 8.2
	 */
	public ICPPASTLiteralExpression newLiteralExpression(int kind, String rep, boolean useChar8Type);

	/**
	 * @since 6.5
	 */
	public ICPPASTLiteralExpression newLiteralExpression(int kind, String rep, char[] numericCompilerSuffixes);

	public ICPPASTNamespaceAlias newNamespaceAlias(IASTName alias, IASTName qualifiedName);

	public ICPPASTNamespaceDefinition newNamespaceDefinition(IASTName name);

	/**
	 * @since 6.0
	 */
	public ICPPASTNaryTypeIdExpression newNaryTypeIdExpression(ICPPASTNaryTypeIdExpression.Operator operator,
			ICPPASTTypeId[] operands);

	/**
	 * @since 5.2
	 */
	public ICPPASTNewExpression newNewExpression(IASTInitializerClause[] placement, IASTInitializer initializer,
			IASTTypeId typeId);

	public ICPPASTOperatorName newOperatorName(char[] name);

	/**
	 * Creates a new pack expansion expression for the given pattern.
	 * @since 5.2
	 */
	public ICPPASTPackExpansionExpression newPackExpansionExpression(IASTExpression pattern);

	@Override
	public ICPPASTParameterDeclaration newParameterDeclaration(IASTDeclSpecifier declSpec, IASTDeclarator declarator);

	public ICPPASTPointerToMember newPointerToMember(IASTName name);

	public IASTProblemTypeId newProblemTypeId(IASTProblem problem);

	/**
	 * Creates a {@link ICPPASTQualifiedName}.
	 * @since 5.7
	 */
	public ICPPASTQualifiedName newQualifiedName(ICPPASTName name);

	/**
	 * Creates an {@link ICPPASTQualifiedName} and adds name qualifiers for the
	 * elements of {@code nameQualifiers}. {@code nameQualifiers} cannot contain decltype specifiers
	 * for creation of {@link ICPPASTDecltypeSpecifier}.
	 * @since 5.11
	 */
	public ICPPASTQualifiedName newQualifiedName(String[] nameQualifiers, String name);

	/**
	 * @since 5.9
	 */
	@Override
	public ICPPASTName newName();

	/**
	 * @since 5.9
	 */
	@Override
	public ICPPASTName newName(char[] name);

	/**
	 * @since 6.1
	 */
	@Override
	public ICPPASTName newName(String name);

	/**
	 * @since 6.6
	 */
	public ICPPASTTemplateName newTemplateName(char[] templateName);

	/**
	 * @since 5.11
	 */
	public ICPPASTNamedTypeSpecifier newNamedTypeSpecifier(IASTName name);

	/**
	 * Creates a range based for statement.
	 * @since 5.3
	 */
	public ICPPASTRangeBasedForStatement newRangeBasedForStatement();

	/**
	 * Creates an lvalue or rvalue reference operator.
	 * @since 5.2
	 */
	public ICPPASTReferenceOperator newReferenceOperator(boolean isRValueReference);

	/**
	 * @since 5.2
	 */
	public IASTReturnStatement newReturnStatement(IASTInitializerClause retValue);

	@Override
	public ICPPASTSimpleDeclSpecifier newSimpleDeclSpecifier();

	/**
	 * @since 5.2
	 */
	public ICPPASTSimpleTypeConstructorExpression newSimpleTypeConstructorExpression(ICPPASTDeclSpecifier declSpec,
			IASTInitializer initializer);

	public ICPPASTSimpleTypeTemplateParameter newSimpleTypeTemplateParameter(int type, IASTName name,
			IASTTypeId typeId);

	/**
	 * Creates a new static assertion declaration with the given condition and message.
	 * @since 5.2
	 */
	public ICPPASTStaticAssertDeclaration newStaticAssertion(IASTExpression condition,
			ICPPASTLiteralExpression message);

	/**
	 * @since 6.5
	 */
	public ICPPASTStaticAssertDeclaration newStaticAssertion(IASTExpression condition);

	public ICPPASTSwitchStatement newSwitchStatement();

	public ICPPASTSwitchStatement newSwitchStatement(IASTDeclaration controller, IASTStatement body);

	@Override
	public ICPPASTSwitchStatement newSwitchStatement(IASTExpression controlloer, IASTStatement body);

	public ICPPASTTemplateDeclaration newTemplateDeclaration(IASTDeclaration declaration);

	public ICPPASTTemplatedTypeTemplateParameter newTemplatedTypeTemplateParameter(IASTName name,
			IASTExpression defaultValue);

	/**
	 * @since 6.6
	 */
	public ICPPASTTemplatedTypeTemplateParameter newTemplatedTypeTemplateParameter(int type, IASTName name,
			IASTExpression defaultValue);

	public ICPPASTTemplateId newTemplateId(IASTName templateName);

	public ICPPASTTemplateSpecialization newTemplateSpecialization(IASTDeclaration declaration);

	/**
	 * Creates a new translation unit that cooperates with the given scanner in order
	 * to track macro-expansions and location information.
	 * @param scanner the preprocessor the translation unit interacts with.
	 * @since 5.2
	 */
	@Override
	public ICPPASTTranslationUnit newTranslationUnit(IScanner scanner);

	public ICPPASTTryBlockStatement newTryBlockStatement(IASTStatement body);

	@Override
	public ICPPASTNamedTypeSpecifier newTypedefNameSpecifier(IASTName name);

	/**
	 * @since 5.2
	 */
	@Override
	public ICPPASTTypeId newTypeId(IASTDeclSpecifier declSpecifier, IASTDeclarator declarator);

	@Override
	public ICPPASTTypeIdExpression newTypeIdExpression(int operator, IASTTypeId typeId);

	/**
	 * @since 5.6
	 */
	public ICPPASTTypeTransformationSpecifier newTypeTransformationSpecifier(Operator kind, ICPPASTTypeId typeId);

	@Override
	public ICPPASTUnaryExpression newUnaryExpression(int operator, IASTExpression operand);

	public ICPPASTUsingDeclaration newUsingDeclaration(IASTName name);

	public ICPPASTUsingDirective newUsingDirective(IASTName name);

	/**
	 * @since 5.7
	 */
	public ICPPASTVirtSpecifier newVirtSpecifier(ICPPASTVirtSpecifier.SpecifierKind kind);

	public ICPPASTVisibilityLabel newVisibilityLabel(int visibility);

	public ICPPASTWhileStatement newWhileStatement();

	public ICPPASTWhileStatement newWhileStatement(IASTDeclaration condition, IASTStatement body);

	@Override
	public ICPPASTWhileStatement newWhileStatement(IASTExpression condition, IASTStatement body);

	/**
	 * @deprecated Replaced by {@link #newConstructorChainInitializer(IASTName, IASTInitializer)}
	 * @noreference This method is not intended to be referenced by clients.
	 */
	@Deprecated
	public ICPPASTConstructorChainInitializer newConstructorChainInitializer(IASTName memberInitializerId,
			IASTExpression initializerValue);

	/**
	 * @deprecated Replaced by {@link #newConstructorInitializer(IASTInitializerClause[])}.
	 * @noreference This method is not intended to be referenced by clients.
	 */
	@Deprecated
	public ICPPASTConstructorInitializer newConstructorInitializer(IASTExpression exp);

	/**
	 * @deprecated Replaced by {@link #newBaseSpecifier(ICPPASTNameSpecifier, int, boolean)}
	 * @noreference This method is not intended to be referenced by clients.
	 */
	@Deprecated
	public ICPPASTBaseSpecifier newBaseSpecifier(IASTName name, int visibility, boolean isVirtual);

	/**
	 * @deprecated Replaced by {@link #newFunctionCallExpression(IASTExpression, IASTInitializerClause[])}.
	 * @noreference This method is not intended to be referenced by clients.
	 */
	@Override
	@Deprecated
	public ICPPASTFunctionCallExpression newFunctionCallExpression(IASTExpression idExpr, IASTExpression argList);

	/**
	 * @deprecated Replaced by {@link #newNewExpression(IASTInitializerClause[], IASTInitializer, IASTTypeId)}
	 * @noreference This method is not intended to be referenced by clients.
	 */
	@Deprecated
	public ICPPASTNewExpression newNewExpression(IASTExpression placement, IASTExpression initializer,
			IASTTypeId typeId);

	/**
	 * @deprecated Replaced by {@link #newQualifiedName(ICPPASTName)}.
	 * @noreference This method is not intended to be referenced by clients.
	 */
	@Deprecated
	public ICPPASTQualifiedName newQualifiedName();

	/**
	 * @deprecated Replaced by {@link #newReferenceOperator(boolean)}.
	 * @noreference This method is not intended to be referenced by clients.
	 */
	@Deprecated
	public ICPPASTReferenceOperator newReferenceOperator();

	/**
	 * @deprecated Replaced by {@link #newSimpleTypeConstructorExpression(ICPPASTDeclSpecifier, IASTInitializer)}
	 * @noreference This method is not intended to be referenced by clients.
	 */
	@Deprecated
	public ICPPASTTypenameExpression newTypenameExpression(IASTName qualifiedName, IASTExpression expr,
			boolean isTemplate);

	/**
	 * @deprecated Use newAttributeList() instead.
	 * @noreference This method is not intended to be referenced by clients.
	 * @since 5.7
	 */
	@Deprecated
	public ICPPASTAttributeSpecifier newAttributeSpecifier();

	/**
	 * @since 6.9
	 */
	public ICPPASTStructuredBindingDeclaration newStructuredBindingDeclaration();

	/**
	 * @since 6.9
	 */
	public ICPPASTStructuredBindingDeclaration newStructuredBindingDeclaration(ICPPASTSimpleDeclSpecifier declSpecifier,
			RefQualifier refQualifier, IASTName[] names, IASTInitializer initializer);
}
