package org.mybatis.generator.codegen.ibatis2.dao.elements.my;

import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.ibatis2.dao.elements.AbstractDAOElementGenerator;

public class SelectByPageExampleWithBLOBsMethodGenerator extends
AbstractDAOElementGenerator {

private boolean generateForJava5;

public SelectByPageExampleWithBLOBsMethodGenerator(boolean generateForJava5) {
super();
this.generateForJava5 = generateForJava5;
}

@Override
public void addImplementationElements(TopLevelClass topLevelClass) {
Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
Method method = getMethodShell(importedTypes);

if (generateForJava5) {
    method.addAnnotation("@Override"); //$NON-NLS-1$
    method.addSuppressTypeWarningsAnnotation();
}

StringBuilder sb = new StringBuilder();
sb.append(method.getReturnType().getShortName());
sb.append(" list = "); //$NON-NLS-1$
sb.append(daoTemplate.getQueryForListMethod(introspectedTable
        .getIbatis2SqlMapNamespace(), introspectedTable
        .getSelectByPageExampleWithBLOBsStatementId(), "example")); //$NON-NLS-1$
method.addBodyLine(sb.toString());
method.addBodyLine("return list;"); //$NON-NLS-1$

if (context.getPlugins()
        .clientSelectByPageExampleWithBLOBsMethodGenerated(method,
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
            .clientSelectByPageExampleWithBLOBsMethodGenerated(method,
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
    if (introspectedTable.getRules().generateRecordWithBLOBsClass()) {
        fqjt = new FullyQualifiedJavaType(introspectedTable
                .getRecordWithBLOBsType());
    } else {
        // the blob fields must be rolled up into the base class
        fqjt = new FullyQualifiedJavaType(introspectedTable
                .getBaseRecordType());
    }

    importedTypes.add(fqjt);
    returnType.addTypeArgument(fqjt);
}
method.setReturnType(returnType);

method.setName(getDAOMethodNameCalculator()
        .getSelectByPageExampleWithBLOBsMethodName(introspectedTable));
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
