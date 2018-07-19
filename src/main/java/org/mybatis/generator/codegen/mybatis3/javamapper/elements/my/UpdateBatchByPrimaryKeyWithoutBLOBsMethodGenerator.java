package org.mybatis.generator.codegen.mybatis3.javamapper.elements.my;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.AbstractJavaMapperMethodGenerator;

public class UpdateBatchByPrimaryKeyWithoutBLOBsMethodGenerator  extends
AbstractJavaMapperMethodGenerator {

public UpdateBatchByPrimaryKeyWithoutBLOBsMethodGenerator() {
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
if (introspectedTable.getRules().generateBaseRecordClass()) {
    listType = new FullyQualifiedJavaType(introspectedTable
            .getBaseRecordType());
} else if (introspectedTable.getRules().generatePrimaryKeyClass()) {
    listType = new FullyQualifiedJavaType(introspectedTable
            .getPrimaryKeyType());
} else {
    throw new RuntimeException(getString("RuntimeError.12")); //$NON-NLS-1$
}

importedTypes.add(listType);
returnType.addTypeArgument(listType);
method.setReturnType(returnType);

method.setName(introspectedTable.getUpdateBatchByPrimaryKeyStatementId());
method.addParameter(new Parameter(type, "example")); //$NON-NLS-1$

context.getCommentGenerator().addGeneralMethodComment(method,
        introspectedTable);

addMapperAnnotations(interfaze, method);

if (context.getPlugins()
        .clientUpdateBatchByPrimaryKeyWithoutBLOBsMethodGenerated(method,
                interfaze, introspectedTable)) {
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
