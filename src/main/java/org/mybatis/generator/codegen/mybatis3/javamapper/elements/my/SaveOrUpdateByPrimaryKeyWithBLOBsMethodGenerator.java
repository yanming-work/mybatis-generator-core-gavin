package org.mybatis.generator.codegen.mybatis3.javamapper.elements.my;

import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.AbstractJavaMapperMethodGenerator;

public class SaveOrUpdateByPrimaryKeyWithBLOBsMethodGenerator  extends
AbstractJavaMapperMethodGenerator {

public SaveOrUpdateByPrimaryKeyWithBLOBsMethodGenerator() {
super();
}

@Override
public void addInterfaceElements(Interface interfaze) {
Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
FullyQualifiedJavaType type = new FullyQualifiedJavaType(
        introspectedTable.getExampleType());
importedTypes.add(type);
importedTypes.add(FullyQualifiedJavaType.getNewListInstance());

Method method = new Method();
method.setVisibility(JavaVisibility.PUBLIC);

FullyQualifiedJavaType returnType = FullyQualifiedJavaType
        .getNewListInstance();
FullyQualifiedJavaType listType;
if (introspectedTable.getRules().generateRecordWithBLOBsClass()) {
    listType = new FullyQualifiedJavaType(introspectedTable
            .getRecordWithBLOBsType());
} else {
    // the blob fields must be rolled up into the base class
    listType = new FullyQualifiedJavaType(introspectedTable
            .getBaseRecordType());
}

importedTypes.add(listType);
returnType.addTypeArgument(listType);
method.setReturnType(returnType);
method.setName(introspectedTable
        .getSaveOrUpdateByPrimaryKeyWithBLOBsStatementId());
method.addParameter(new Parameter(type, "example")); //$NON-NLS-1$

context.getCommentGenerator().addGeneralMethodComment(method,
        introspectedTable);

addMapperAnnotations(interfaze, method);

if (context.getPlugins()
        .clientSaveOrUpdateByPrimaryKeyWithBLOBsMethodGenerated(method, interfaze,
                introspectedTable)) {
    addExtraImports(interfaze);
    interfaze.addImportedTypes(importedTypes);
    interfaze.addMethod(method);
}
}

public void addMapperAnnotations(Interface interfaze, Method method) {
}

public void addExtraImports(Interface interfaze) {
}


}
