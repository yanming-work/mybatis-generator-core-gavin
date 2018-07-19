package org.mybatis.generator.codegen.mybatis3.javamapper.elements.sqlprovider.my;

import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

public class ProviderSaveOrUpdateByExampleWithBLOBsMethodGenerator  extends
ProviderSaveOrUpdateByExampleWithoutBLOBsMethodGenerator {

public ProviderSaveOrUpdateByExampleWithBLOBsMethodGenerator(boolean useLegacyBuilder) {
super(useLegacyBuilder);
}

@Override
public List<IntrospectedColumn> getColumns() {
return introspectedTable.getAllColumns();
}

@Override
public String getMethodName() {
return introspectedTable.getSaveOrUpdateByExampleWithBLOBsStatementId();
}

@Override
public boolean callPlugins(Method method, TopLevelClass topLevelClass) {
return context.getPlugins().providerSaveOrUpdateByExampleWithBLOBsMethodGenerated(method, topLevelClass,
        introspectedTable);
}
}


