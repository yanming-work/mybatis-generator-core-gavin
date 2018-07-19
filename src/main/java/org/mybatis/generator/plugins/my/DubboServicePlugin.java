package org.mybatis.generator.plugins.my;

import java.util.List;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.internal.util.StringUtility;

/**
 */
public class DubboServicePlugin extends PluginAdapter {

    private ServicePluginUtil servicePluginUtil;
    private String targetPackage;
    private String implementationPackage;
    private String targetProject;
    private String targetServicePackage;
    private boolean enableDubboService;
   
    public DubboServicePlugin() {
        super();
    }

    @Override
    public boolean validate(List<String> warnings) {
    	if (StringUtility.stringHasValue(properties.getProperty("enableDubboService")))
            this.enableDubboService = StringUtility.isTrue(properties.getProperty("enableDubboService"));
    	if(enableDubboService){
    		this.targetPackage = properties.getProperty("targetPackage");
            this.implementationPackage = properties.getProperty("implementationPackage");
            this.targetProject = properties.getProperty("targetProject");
            this.targetServicePackage= properties.getProperty("targetServicePackage");
            this.servicePluginUtil=new ServicePluginUtil(properties, context, targetPackage,implementationPackage,targetProject,"dubbo",targetServicePackage);
        	servicePluginUtil.validate(warnings);
            return true;
    	}else{
    		return false;
    	}
    	
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
    	if(enableDubboService){
    		return servicePluginUtil.contextGenerateAdditionalJavaFiles(introspectedTable);
    	}else{
    		return null;
    	}
    }
   
}
