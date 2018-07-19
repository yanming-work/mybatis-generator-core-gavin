package org.mybatis.generator.plugins.my;

import java.util.List;
import java.util.Properties;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

/**
 */
public class MybatisServicePlugin extends PluginAdapter {

    private ServicePluginUtil servicePluginUtil;
    private String targetPackage;
    private String implementationPackage;
    private String targetProject;
   
    public MybatisServicePlugin() {
        super();
    }

    @Override
    public boolean validate(List<String> warnings) {
    	this.targetPackage = properties.getProperty("targetPackage");
        this.implementationPackage = properties.getProperty("implementationPackage");
        this.targetProject = properties.getProperty("targetProject");
        this.servicePluginUtil=new ServicePluginUtil(properties, context, targetPackage,implementationPackage,targetProject,null,null);
    	servicePluginUtil.validate(warnings);
        return true;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        return servicePluginUtil.contextGenerateAdditionalJavaFiles(introspectedTable);
    }

   
}
