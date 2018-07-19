package org.mybatis.generator.codegen.mybatis3.javamapper.elements.sqlprovider.my;

import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

public class ProviderSelectByPageExampleWithBLOBsMethodGenerator  extends
ProviderSelectByPageExampleWithoutBLOBsMethodGenerator {

public ProviderSelectByPageExampleWithBLOBsMethodGenerator(boolean useLegacyBuilder) {
super(useLegacyBuilder);
}

@Override
public List<IntrospectedColumn> getColumns() {
return introspectedTable.getAllColumns();
}

@Override
public String getMethodName() {
return introspectedTable.getSelectByPageExampleWithBLOBsStatementId();
}

@Override
public boolean callPlugins(Method method, TopLevelClass topLevelClass) {
return context.getPlugins().providerSelectByPageExampleWithBLOBsMethodGenerated(method, topLevelClass,
        introspectedTable);
}
}


