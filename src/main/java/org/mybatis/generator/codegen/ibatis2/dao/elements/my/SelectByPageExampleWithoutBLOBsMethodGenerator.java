package org.mybatis.generator.codegen.ibatis2.dao.elements.my;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.ibatis2.dao.elements.AbstractDAOElementGenerator;

public class SelectByPageExampleWithoutBLOBsMethodGenerator  extends
AbstractDAOElementGenerator {

private boolean generateForJava5;

public SelectByPageExampleWithoutBLOBsMethodGenerator(boolean generateForJava5) {
super();
this.generateForJava5 = generateForJava5;
}

@Override
public void addImplementationElements(TopLevelClass topLevelClass) {
Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
Method method = getMethodShell(importedTypes);

if (generateForJava5) {
    method.addSuppressTypeWarningsAnnotation();
    method.addAnnotation("@Override"); //$NON-NLS-1$
}

StringBuilder sb = new StringBuilder();
sb.append(method.getReturnType().getShortName());
sb.append(" list = "); //$NON-NLS-1$
sb.append(daoTemplate.getQueryForListMethod(introspectedTable
        .getIbatis2SqlMapNamespace(), introspectedTable
        .getSelectByPageExampleStatementId(), "example")); //$NON-NLS-1$
method.addBodyLine(sb.toString());
method.addBodyLine("return list;"); //$NON-NLS-1$

if (context.getPlugins()
        .clientSelectByPageExampleWithoutBLOBsMethodGenerated(method,
                topLevelClass, introspectedTable)) {
    topLevelClass.addImportedTypes(importedTypes);
    topLevelClass.addMethod(method);
}
}

@Override
public void addInterfaceElements(Interface interfaze) {
if (getExampleMethodVisibility() == JavaVisibility.PUBLIC) {
    Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
    Method method = getMethodShell(importedTypes);

    if (context.getPlugins()
            .clientSelectByPageExampleWithoutBLOBsMethodGenerated(method,
                    interfaze, introspectedTable)) {
        interfaze.addImportedTypes(importedTypes);
        interfaze.addMethod(method);
    }
}
}

private Method getMethodShell(Set<FullyQualifiedJavaType> importedTypes) {
FullyQualifiedJavaType type = new FullyQualifiedJavaType(
        introspectedTable.getExampleType());
importedTypes.add(type);
importedTypes.add(FullyQualifiedJavaType.getNewListInstance());

Method method = new Method();
method.setVisibility(getExampleMethodVisibility());

FullyQualifiedJavaType returnType = FullyQualifiedJavaType
        .getNewListInstance();

if (generateForJava5) {
    FullyQualifiedJavaType fqjt;
    if (introspectedTable.getRules().generateBaseRecordClass()) {
        fqjt = new FullyQualifiedJavaType(introspectedTable
                .getBaseRecordType());
    } else if (introspectedTable.getRules().generatePrimaryKeyClass()) {
        fqjt = new FullyQualifiedJavaType(introspectedTable
                .getPrimaryKeyType());
    } else {
        throw new RuntimeException(getString("RuntimeError.12")); //$NON-NLS-1$
    }

    importedTypes.add(fqjt);
    returnType.addTypeArgument(fqjt);
}

method.setReturnType(returnType);

method.setName(getDAOMethodNameCalculator()
        .getSelectByPageExampleWithoutBLOBsMethodName(introspectedTable));
method.addParameter(new Parameter(type, "example")); //$NON-NLS-1$

for (FullyQualifiedJavaType fqjt : daoTemplate.getCheckedExceptions()) {
    method.addException(fqjt);
    importedTypes.add(fqjt);
}

context.getCommentGenerator().addGeneralMethodComment(method,
        introspectedTable);

return method;
}


}
