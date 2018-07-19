package org.mybatis.generator.plugins.my;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaElement;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.internal.util.StringUtility;

public class ServicePluginUtil  {

	private String  whatService;
	
    private FullyQualifiedJavaType slf4jLogger;
    private FullyQualifiedJavaType slf4jLoggerFactory;
    private FullyQualifiedJavaType serviceType;
    private FullyQualifiedJavaType daoType;
    private FullyQualifiedJavaType interfaceType;
    private FullyQualifiedJavaType pojoType;
    private FullyQualifiedJavaType pojoTypeWithBLOBs;
    private FullyQualifiedJavaType pojoCriteriaType;
    private FullyQualifiedJavaType listType;
    private FullyQualifiedJavaType autowired;
    private FullyQualifiedJavaType service;
    private FullyQualifiedJavaType returnType;
    private FullyQualifiedJavaType pageType;
    private FullyQualifiedJavaType pagehelperType;
    private String servicePack;
    private String serviceImplPack;
    private String project;
    private String pojoUrl;
    
    private String daoServicePack;

    private FullyQualifiedJavaType daoInterfaceType;
    
    private List<Method> methods;
    /**
     * 是否添加注解
     */
    private boolean enableAnnotation = true;
    private boolean enableInsert = false;
    private boolean enableInsertSelective = false;
    private boolean enableDeleteByPrimaryKey = false;
    private boolean enableDeleteByExample = false;
    private boolean enableUpdateByExample = false;
    private boolean enableUpdateByExampleSelective = false;
    private boolean enableUpdateByPrimaryKey = false;
    private boolean enableUpdateByPrimaryKeySelective = false;
    private boolean enableInsertBatch = false;
    private boolean enableInsertBatchSelective = false;
    private boolean enableSelectByPageExample = false;
    private boolean enableSelectByPageHelperExample = false;
    
    private boolean enableSaveOrUpdateByExample = false;
    private boolean enableUpdateBatchByPrimaryKey = false;
    private boolean enableSaveOrUpdateByPrimaryKey = false;
    private Properties properties;
    private Context context; 
    
    public ServicePluginUtil(Properties properties,Context context,String servicePack,String serviceImplPack,String project,String whatService,String daoServicePack) {
        // 默认是slf4j
        slf4jLogger = new FullyQualifiedJavaType("org.slf4j.Logger");
        slf4jLoggerFactory = new FullyQualifiedJavaType("org.slf4j.LoggerFactory");
        methods = new ArrayList<Method>();
        this.properties=properties;
        this.context=context;
        this.whatService=whatService;
        this.servicePack=servicePack;
        this.serviceImplPack=serviceImplPack;
        this.project=project;
        this.daoServicePack=daoServicePack;
    }

    public boolean validate(List<String> warnings) {
        if (StringUtility.stringHasValue(properties.getProperty("enableAnnotation")))
            enableAnnotation = StringUtility.isTrue(properties.getProperty("enableAnnotation"));

        String enableInsert = properties.getProperty("enableInsert");

        String enableUpdateByExampleSelective = properties.getProperty("enableUpdateByExampleSelective");

        String enableInsertSelective = properties.getProperty("enableInsertSelective");

        String enableUpdateByPrimaryKey = properties.getProperty("enableUpdateByPrimaryKey");

        String enableDeleteByPrimaryKey = properties.getProperty("enableDeleteByPrimaryKey");

        String enableDeleteByExample = properties.getProperty("enableDeleteByExample");

        String enableUpdateByPrimaryKeySelective = properties.getProperty("enableUpdateByPrimaryKeySelective");

        String enableUpdateByExample = properties.getProperty("enableUpdateByExample");

        
        String enableInsertBatch = properties.getProperty("enableInsertBatch");
        
        String enableInsertBatchSelective = properties.getProperty("enableInsertBatchSelective");
        
        String enableSelectByPageExample= properties.getProperty("enableSelectByPageExample");
        
        String enableSelectByPageHelperExample= properties.getProperty("enableSelectByPageHelperExample");
        
        String enableSaveOrUpdateByExample= properties.getProperty("enableSaveOrUpdateByExample");
        
        String enableUpdateBatchByPrimaryKey= properties.getProperty("enableUpdateBatchByPrimaryKey");
        
        String enableSaveOrUpdateByPrimaryKey= properties.getProperty("enableSaveOrUpdateByPrimaryKey");
        
        
        
        
        
        if (StringUtility.stringHasValue(enableInsert))
            this.enableInsert = StringUtility.isTrue(enableInsert);
        if (StringUtility.stringHasValue(enableUpdateByExampleSelective))
            this.enableUpdateByExampleSelective = StringUtility.isTrue(enableUpdateByExampleSelective);
        if (StringUtility.stringHasValue(enableInsertSelective))
            this.enableInsertSelective = StringUtility.isTrue(enableInsertSelective);
        if (StringUtility.stringHasValue(enableUpdateByPrimaryKey))
            this.enableUpdateByPrimaryKey = StringUtility.isTrue(enableUpdateByPrimaryKey);
        if (StringUtility.stringHasValue(enableDeleteByPrimaryKey))
            this.enableDeleteByPrimaryKey = StringUtility.isTrue(enableDeleteByPrimaryKey);
        if (StringUtility.stringHasValue(enableDeleteByExample))
            this.enableDeleteByExample = StringUtility.isTrue(enableDeleteByExample);
        if (StringUtility.stringHasValue(enableUpdateByPrimaryKeySelective))
            this.enableUpdateByPrimaryKeySelective = StringUtility.isTrue(enableUpdateByPrimaryKeySelective);
        if (StringUtility.stringHasValue(enableUpdateByExample))
            this.enableUpdateByExample = StringUtility.isTrue(enableUpdateByExample);
        if (StringUtility.stringHasValue(enableInsertBatch))
            this.enableInsertBatch = StringUtility.isTrue(enableInsertBatch);
      
        if (StringUtility.stringHasValue(enableInsertBatchSelective))
            this.enableInsertBatchSelective = StringUtility.isTrue(enableInsertBatchSelective);
      
        if (StringUtility.stringHasValue(enableSelectByPageExample))
            this.enableSelectByPageExample = StringUtility.isTrue(enableSelectByPageExample);
      
        if (StringUtility.stringHasValue(enableSelectByPageHelperExample))
            this.enableSelectByPageHelperExample = StringUtility.isTrue(enableSelectByPageHelperExample);
      
        if (StringUtility.stringHasValue(enableSaveOrUpdateByExample))
            this.enableSaveOrUpdateByExample = StringUtility.isTrue(enableSaveOrUpdateByExample);
      
        if (StringUtility.stringHasValue(enableUpdateBatchByPrimaryKey))
            this.enableUpdateBatchByPrimaryKey = StringUtility.isTrue(enableUpdateBatchByPrimaryKey);
      
        if (StringUtility.stringHasValue(enableSaveOrUpdateByPrimaryKey))
            this.enableSaveOrUpdateByPrimaryKey = StringUtility.isTrue(enableSaveOrUpdateByPrimaryKey);
      
        


        pojoUrl = context.getJavaModelGeneratorConfiguration().getTargetPackage();

        if (enableAnnotation) {
            autowired = new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired");
            if("dubbo".equals(whatService) && daoServicePack!=null){
            	service = new FullyQualifiedJavaType("com.alibaba.dubbo.config.annotation.Service");
            }else{
            	service = new FullyQualifiedJavaType("org.springframework.stereotype.Service");
            }
            
        }
        
        
        return true;
    }

    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        List<GeneratedJavaFile> files = new ArrayList<GeneratedJavaFile>();
        String table = introspectedTable.getBaseRecordType();
        String tableName = table.replaceAll(this.pojoUrl + ".", "");
        
        if("dubbo".equals(whatService)  && daoServicePack!=null){
        	interfaceType = new FullyQualifiedJavaType(servicePack + "." + tableName + "RestService");
        	daoInterfaceType = new FullyQualifiedJavaType(daoServicePack + "." + tableName + "Service");
        }else{
        	interfaceType = new FullyQualifiedJavaType(servicePack + "." + tableName + "Service");

        }
        
        // mybatis
        daoType = new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType());

        // logger.info(toLowerCase(daoType.getShortName()));
        if("dubbo".equals(whatService)){
        	serviceType = new FullyQualifiedJavaType(serviceImplPack + "." + tableName + "RestServiceImpl");
        }else{
        	serviceType = new FullyQualifiedJavaType(serviceImplPack + "." + tableName + "ServiceImpl");
        }
        
        pojoType = new FullyQualifiedJavaType(pojoUrl + "." + tableName);

        
        pojoTypeWithBLOBs = new FullyQualifiedJavaType(pojoUrl + "." + tableName+"WithBLOBs");
        
        pojoCriteriaType = new FullyQualifiedJavaType(pojoUrl + "."+tableName + "Criteria");
        listType = new FullyQualifiedJavaType("java.util.List");
        pageType = new FullyQualifiedJavaType("org.mybatis.generator.plugins.my.PageView");
        pagehelperType = new FullyQualifiedJavaType("com.github.pagehelper.PageInfo");
        Interface interface1 = new Interface(interfaceType);
        TopLevelClass topLevelClass = new TopLevelClass(serviceType);
        
        boolean  isHasBLOBColumns=false;
        if (introspectedTable.hasBLOBColumns()) {
        	isHasBLOBColumns=true;
    	}
        // 导入必要的类
        addImport(interface1, topLevelClass,isHasBLOBColumns);

        // 接口
        addService(interface1, introspectedTable, tableName, files);
        // 实现类
       
        addServiceImpl(topLevelClass, introspectedTable, tableName, files);
        
       
        addLogger(topLevelClass);

        return files;
    }

    /**
     * 添加接口
     *
     * @param tableName
     * @param files
     */
    protected void addService(Interface interface1, IntrospectedTable introspectedTable, String tableName, List<GeneratedJavaFile> files) {

        interface1.setVisibility(JavaVisibility.PUBLIC);
        boolean isHasKey=true;
        boolean  isHasBLOBColumns=introspectedTable.hasBLOBColumns();
        if (introspectedTable.getPrimaryKeyColumns() == null || introspectedTable.getPrimaryKeyColumns().size()<=0 ) {
        	isHasKey=false;
        }
        
        // 添加方法
        Method countByExampleMethod = countByExample(introspectedTable, tableName);
        countByExampleMethod.setReturnType(new FullyQualifiedJavaType("long"));
        countByExampleMethod.removeAllBodyLines();
        interface1.addMethod(countByExampleMethod);
        
		if( isHasKey){
			
			 Method selectByPrimaryKeyMethod = selectByPrimaryKey(introspectedTable, tableName);
		     selectByPrimaryKeyMethod.removeAllBodyLines();
		     interface1.addMethod(selectByPrimaryKeyMethod);
		     
		     if (isHasBLOBColumns) {
					Method selectByPrimaryKeyMethodWithBLOBs = selectByPrimaryKeyWithBLOBs(introspectedTable, tableName);
					selectByPrimaryKeyMethodWithBLOBs.removeAllBodyLines();
				     interface1.addMethod(selectByPrimaryKeyMethodWithBLOBs);
		    }
		     
		     
		}
       

        Method selectByExampleMethod = selectByExample(introspectedTable, tableName);
        selectByExampleMethod.removeAllBodyLines();
        interface1.addMethod(selectByExampleMethod);
        
        if (isHasBLOBColumns) {
        	Method selectByExampleMethodWithBLOBs = selectByExampleWithBLOBs(introspectedTable, tableName);
            selectByExampleMethodWithBLOBs.removeAllBodyLines();
            interface1.addMethod(selectByExampleMethodWithBLOBs);
        	
        }

        if (enableDeleteByPrimaryKey && isHasKey) {
        	Method method = getOtherInteger("deleteByPrimaryKey", introspectedTable, tableName, 2);
        	method.setReturnType(new FullyQualifiedJavaType("int"));
        	method.removeAllBodyLines();
            interface1.addMethod(method);
        }
        if (enableUpdateByPrimaryKeySelective && isHasKey) {
        	
        	Method method = getOtherInteger("updateByPrimaryKeySelective", introspectedTable, tableName, 1);
        	method.setReturnType(new FullyQualifiedJavaType("int"));
            method.removeAllBodyLines();
            interface1.addMethod(method);
        }
        if (enableUpdateByPrimaryKey && isHasKey) {
        	
        	Method method = getOtherInteger("updateByPrimaryKey", introspectedTable, tableName, 1);
        	method.setReturnType(new FullyQualifiedJavaType("int"));
            method.removeAllBodyLines();
            interface1.addMethod(method);
            if (isHasBLOBColumns) {
            	Method methodWithBLOBs = getOtherInteger("updateByPrimaryKeyWithBLOBs", introspectedTable, tableName,1);
                methodWithBLOBs.setReturnType(new FullyQualifiedJavaType("int"));
                methodWithBLOBs.removeAllBodyLines();
                interface1.addMethod(methodWithBLOBs);
        	}
        }
        if (enableDeleteByExample) {
        	
        	Method  method = getOtherInteger("deleteByExample", introspectedTable, tableName, 3);
        	method.setReturnType(new FullyQualifiedJavaType("int"));
            method.removeAllBodyLines();
            interface1.addMethod(method);
        }
        if (enableUpdateByExampleSelective) {
        	 Method method = getOtherInteger("updateByExampleSelective", introspectedTable, tableName, 4);
            method.setReturnType(new FullyQualifiedJavaType("int"));
            method.removeAllBodyLines();
            interface1.addMethod(method);
        }
        if (enableUpdateByExample) {
        	Method  method = getOtherInteger("updateByExample", introspectedTable, tableName, 4);
        	method.setReturnType(new FullyQualifiedJavaType("int"));
            method.removeAllBodyLines();
            interface1.addMethod(method);
        }
        if (enableInsert) {
        	Method method = getOtherInsertboolean("insert", introspectedTable, tableName,false);
            method.setReturnType(new FullyQualifiedJavaType("int"));
            method.removeAllBodyLines();
            interface1.addMethod(method);
            if (isHasBLOBColumns) {
            	Method methodWithBLOBs = getOtherInsertboolean("insertWithBLOBs", introspectedTable, tableName,true);
                methodWithBLOBs.setReturnType(new FullyQualifiedJavaType("int"));
                methodWithBLOBs.removeAllBodyLines();
                interface1.addMethod(methodWithBLOBs);
        	}
            
        }
        if (enableInsertSelective) {
        	
        	Method method = getOtherInsertboolean("insertSelective", introspectedTable, tableName,false);
            method.setReturnType(new FullyQualifiedJavaType("int"));
            method.removeAllBodyLines();
            interface1.addMethod(method);
            
            if (isHasBLOBColumns) {
            	
            	Method methodWithBLOBs = getOtherInsertboolean("insertSelectiveWithBLOBs", introspectedTable, tableName,true);
                methodWithBLOBs.setReturnType(new FullyQualifiedJavaType("int"));
                methodWithBLOBs.removeAllBodyLines();
                interface1.addMethod(methodWithBLOBs);
                
            }
        }
        
        if (enableInsertBatch) {
        	Method method = getOtherInteger("insertBatch", introspectedTable, tableName,5);
            method.setReturnType(new FullyQualifiedJavaType("int"));
            method.removeAllBodyLines();
            interface1.addMethod(method);
            
            if (isHasBLOBColumns) {
            	
            	Method methodWithBLOBs = getOtherInteger("insertBatchWithBLOBs", introspectedTable, tableName,5);
                methodWithBLOBs.setReturnType(new FullyQualifiedJavaType("int"));
                methodWithBLOBs.removeAllBodyLines();
                interface1.addMethod(methodWithBLOBs);
            }
        }
        if (enableInsertBatchSelective) {
        	Method method = getOtherInteger("insertBatchSelective", introspectedTable, tableName,5);
            method.setReturnType(new FullyQualifiedJavaType("int"));
            method.removeAllBodyLines();
            interface1.addMethod(method);
            
            if (isHasBLOBColumns) {
            	
            	Method methodWithBLOBs = getOtherInteger("insertBatchSelectiveWithBLOBs", introspectedTable, tableName,5);
                methodWithBLOBs.setReturnType(new FullyQualifiedJavaType("int"));
                methodWithBLOBs.removeAllBodyLines();
                interface1.addMethod(methodWithBLOBs);
            }
        }
        
        
   
        
        if (enableUpdateByExample) {
        	Method  method = getOtherInteger("updateByExample", introspectedTable, tableName,4);
            method.setReturnType(new FullyQualifiedJavaType("int"));
            method.removeAllBodyLines();
            interface1.addMethod(method);
            
            
            
        }
        
        
        if (enableSelectByPageExample) {
        	 Method method = getOtherInteger("selectByPageExample", introspectedTable, tableName,6);
            //method.setReturnType(new FullyQualifiedJavaType("PageView<" + tableName + ">"));
            method.removeAllBodyLines();
            interface1.addMethod(method);
        }
        
        if (enableSelectByPageHelperExample) {
        	
        	Method method = getOtherInteger("selectByPageHelperExample", introspectedTable, tableName,7);
        	//method.setReturnType(new FullyQualifiedJavaType("PageInfo<" + tableName + ">"));
            method.removeAllBodyLines();
            interface1.addMethod(method);
        }
        
        if (enableSaveOrUpdateByExample ) {
        	
        	 Method method = getOtherInteger("saveOrUpdateByExample", introspectedTable, tableName,4);
            method.setReturnType(new FullyQualifiedJavaType("int"));
            method.removeAllBodyLines();
            interface1.addMethod(method);
            
            
            if (isHasBLOBColumns) {
            	
            	Method methodWithBLOBs = getOtherInteger("saveOrUpdateByExampleWithBLOBs", introspectedTable, tableName,4);
                methodWithBLOBs.setReturnType(new FullyQualifiedJavaType("int"));
                methodWithBLOBs.removeAllBodyLines();
                interface1.addMethod(methodWithBLOBs);
            }
            
        }
     
        if (enableUpdateBatchByPrimaryKey && isHasKey) {
        	
        	Method method = getOtherInteger("updateBatchByPrimaryKey", introspectedTable, tableName,5);
            method.setReturnType(new FullyQualifiedJavaType("int"));
            method.removeAllBodyLines();
            interface1.addMethod(method);
            
            if (isHasBLOBColumns) {
            	
            	Method methodWithBLOBs = getOtherInteger("updateBatchByPrimaryKeyWithBLOBs", introspectedTable, tableName,5);
                methodWithBLOBs.setReturnType(new FullyQualifiedJavaType("int"));
                methodWithBLOBs.removeAllBodyLines();
                interface1.addMethod(methodWithBLOBs);
            }
        }
        
        
        if (enableSaveOrUpdateByPrimaryKey && isHasKey) {
        	Method method = getOtherInteger("saveOrUpdateByPrimaryKey", introspectedTable, tableName,1);
            method.setReturnType(new FullyQualifiedJavaType("int"));
            method.removeAllBodyLines();
            interface1.addMethod(method);
            
            if (isHasBLOBColumns) {
            	Method methodWithBLOBs = getOtherInteger("saveOrUpdateByPrimaryKeyWithBLOBs", introspectedTable, tableName,1);
                methodWithBLOBs.setReturnType(new FullyQualifiedJavaType("int"));
                methodWithBLOBs.removeAllBodyLines();
                interface1.addMethod(methodWithBLOBs);
            }
        }
        
        
        

        GeneratedJavaFile file = new GeneratedJavaFile(interface1, project, context.getJavaFormatter());
        files.add(file);
    }
    
    
    
    
  

    /**
     * 添加实现类
     *
     * @param introspectedTable
     * @param tableName
     * @param files
     */
    protected void addServiceImpl(TopLevelClass topLevelClass, IntrospectedTable introspectedTable, String tableName, List<GeneratedJavaFile> files) {
    	boolean isHasKey=true;
    	boolean isHasBLOBColumns=introspectedTable.hasBLOBColumns();
        if (introspectedTable.getPrimaryKeyColumns() == null || introspectedTable.getPrimaryKeyColumns().size()<=0 ) {
        	isHasKey=false;
        }
    	topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        // 设置实现的接口
        topLevelClass.addSuperInterface(interfaceType);

        if (enableAnnotation) {
        	//topLevelClass.addAnnotation("@Service");
        	
        	if("dubbo".equals(whatService)  && daoServicePack!=null){
        		topLevelClass.addAnnotation("@Service");
                
        	}else{
        		topLevelClass.addAnnotation("@Service(\""+toLowerCaseFirstOne(tableName)+"Service\")");
        	}
            topLevelClass.addImportedType(service);
        }
        // 添加引用dao
        addField(topLevelClass, tableName);
        // 添加方法
        topLevelClass.addMethod(countByExample(introspectedTable, tableName));
        if(isHasKey){
        	 	topLevelClass.addMethod(selectByPrimaryKey(introspectedTable, tableName));
        	if (isHasBLOBColumns) {
        		 topLevelClass.addMethod(selectByPrimaryKeyWithBLOBs(introspectedTable, tableName));
         	}
        }
       
        topLevelClass.addMethod(selectByExample(introspectedTable, tableName));
        
        if (isHasBLOBColumns) {
        	topLevelClass.addMethod(selectByExampleWithBLOBs(introspectedTable, tableName));
            
        }
         /**
         * type 的意义 pojo 1 ;key 2 ;example 3 ;pojo+example 4;List<pojo> 5   分页 pageNum, pageSize,example,distinct,orderByClause 6 分页pagehelper 7
         */
        if (enableDeleteByPrimaryKey && isHasKey) {
            topLevelClass.addMethod(getOtherInteger("deleteByPrimaryKey", introspectedTable, tableName, 2));
        }
        if (enableUpdateByPrimaryKeySelective && isHasKey) {
            topLevelClass.addMethod(getOtherInteger("updateByPrimaryKeySelective", introspectedTable, tableName, 1));
        }
        if (enableUpdateByPrimaryKey && isHasKey) {
            topLevelClass.addMethod(getOtherInteger("updateByPrimaryKey", introspectedTable, tableName, 1));
            if (isHasBLOBColumns) {
            	 topLevelClass.addMethod(getOtherInteger("updateByPrimaryKeyWithBLOBs", introspectedTable, tableName, 1));
            }
        }
        if (enableDeleteByExample) {
            topLevelClass.addMethod(getOtherInteger("deleteByExample", introspectedTable, tableName, 3));
        }
        if (enableUpdateByExampleSelective) {
            topLevelClass.addMethod(getOtherInteger("updateByExampleSelective", introspectedTable, tableName, 4));
        }
        if (enableUpdateByExample) {
            topLevelClass.addMethod(getOtherInteger("updateByExample", introspectedTable, tableName, 4));
            if (isHasBLOBColumns) {
            	 topLevelClass.addMethod(getOtherInteger("updateByExampleWithBLOBs", introspectedTable, tableName, 4));
            }
        }
        if (enableInsert) {
        	returnType=new FullyQualifiedJavaType("int");
        	Method method = getOtherInsertboolean("insert", introspectedTable, tableName,false);
            method.setReturnType(returnType);
            topLevelClass.addMethod(method);
            if (isHasBLOBColumns) {
            	Method method2 = getOtherInsertboolean("insertWithBLOBs", introspectedTable, tableName,true);
                method2.setReturnType(returnType);
                topLevelClass.addMethod(method2);
                
            }
            returnType=null;
        }
        if (enableInsertSelective) {
        	returnType=new FullyQualifiedJavaType("int");
        	 Method method = getOtherInsertboolean("insertSelective", introspectedTable, tableName,false);
             method.setReturnType(returnType);
             topLevelClass.addMethod(method);
               if (isHasBLOBColumns) {
            	   Method method2 = getOtherInsertboolean("insertSelectiveWithBLOBs", introspectedTable, tableName,true);
                   method2.setReturnType(returnType);
                   topLevelClass.addMethod(method2);
                
            }
            returnType=null;
        }
        
        if (enableInsertBatch) {
        	 topLevelClass.addMethod(getOtherInteger("insertBatch", introspectedTable, tableName,5));
             
             if (isHasBLOBColumns) {
            	
            	 topLevelClass.addMethod(getOtherInteger("insertBatchWithBLOBs", introspectedTable, tableName,5));
                
            }
        }
        if (enableInsertBatchSelective) {
            topLevelClass.addMethod(getOtherInteger("insertBatchSelective", introspectedTable, tableName,5));
            if (isHasBLOBColumns) {
            	
            	 topLevelClass.addMethod(getOtherInteger("insertBatchSelectiveWithBLOBs", introspectedTable, tableName,5));
                
            }
        }
        
        
        
        
        if (enableSelectByPageExample) {
            topLevelClass.addMethod(getOtherInteger("selectByPageExample", introspectedTable, tableName,6));
        }
        
        if (enableSelectByPageHelperExample) {
            topLevelClass.addMethod(getOtherInteger("selectByPageHelperExample", introspectedTable, tableName,7));
        }
        
        if (enableSaveOrUpdateByExample) {
            topLevelClass.addMethod(getOtherInteger("saveOrUpdateByExample", introspectedTable, tableName,4));

            if (isHasBLOBColumns) {
            	 topLevelClass.addMethod(getOtherInteger("saveOrUpdateByExampleWithBLOBs", introspectedTable, tableName,4));
            }
        }
        
        if (enableUpdateBatchByPrimaryKey  && isHasKey) {
            topLevelClass.addMethod(getOtherInteger("updateBatchByPrimaryKey", introspectedTable, tableName,5));
       
            if (isHasBLOBColumns) {
            	 topLevelClass.addMethod(getOtherInteger("updateBatchByPrimaryKeyWithBLOBs", introspectedTable, tableName,5));
            }
            
        }
        
        if (enableSaveOrUpdateByPrimaryKey  && isHasKey) {
            topLevelClass.addMethod(getOtherInteger("saveOrUpdateByPrimaryKey", introspectedTable, tableName,1));
       
            if (isHasBLOBColumns) {
            		topLevelClass.addMethod(getOtherInteger("saveOrUpdateByPrimaryKeyWithBLOBs", introspectedTable, tableName,1));
            }
        }
        
        
        // 生成文件
        GeneratedJavaFile file = new GeneratedJavaFile(topLevelClass, project, context.getJavaFormatter());
        files.add(file);
    }

    /**
     * 添加字段
     *
     * @param topLevelClass
     */
    protected void addField(TopLevelClass topLevelClass, String tableName) {
        // 添加 dao
        Field field = new Field();
        
        if("dubbo".equals(whatService)  && daoInterfaceType!=null){
        	field.setName(toLowerCase(daoInterfaceType.getShortName())); // DubboService设置变量名
            topLevelClass.addImportedType(daoInterfaceType);
            field.setType(daoInterfaceType); // 类型
        }else{
        	field.setName(toLowerCase(daoType.getShortName())); // 设置变量名
            topLevelClass.addImportedType(daoType);
            field.setType(daoType); // 类型
        }
        
        
        field.setVisibility(JavaVisibility.PRIVATE);
        if (enableAnnotation) {
            field.addAnnotation("@Autowired");
        }
        topLevelClass.addField(field);
    }

    /**
     * 添加方法
     */
    protected Method selectByPrimaryKey(IntrospectedTable introspectedTable, String tableName) {
        Method method = new Method();
        method.setName("selectByPrimaryKey");
    	method.setReturnType(pojoType);
        
        if (introspectedTable.getRules().generatePrimaryKeyClass()) {
            FullyQualifiedJavaType type = new FullyQualifiedJavaType(introspectedTable.getPrimaryKeyType());
            method.addParameter(new Parameter(type, "key"));
        } else {
            for (IntrospectedColumn introspectedColumn : introspectedTable.getPrimaryKeyColumns()) {
                FullyQualifiedJavaType type = introspectedColumn.getFullyQualifiedJavaType();
                method.addParameter(new Parameter(type, introspectedColumn.getJavaProperty()));
            }
        }
        method.setVisibility(JavaVisibility.PUBLIC);
        StringBuilder sb = new StringBuilder();
        sb.append("return this.");
        if("dubbo".equals(whatService)  && daoInterfaceType!=null){
	        sb.append(toLowerCaseFirstOne(daoInterfaceType.getShortName())+".");
        }else{
	        // method.addBodyLine("try {");
	        sb.append(getDaoShort());
        }
        
        sb.append("selectByPrimaryKey");
        sb.append("(");
        for (IntrospectedColumn introspectedColumn : introspectedTable.getPrimaryKeyColumns()) {
            sb.append(introspectedColumn.getJavaProperty());
            sb.append(",");
        }
        sb.setLength(sb.length() - 1);
        sb.append(");");
        method.addBodyLine(sb.toString());
        // method.addBodyLine("} catch (Exception e) {");
        // method.addBodyLine("logger.error(\"Exception: \", e);");
        // method.addBodyLine("return null;");
        // method.addBodyLine("}");
        return method;
    }
    
    
    
    
    
    /**
     * 添加方法
     */
    protected Method selectByPrimaryKeyWithBLOBs(IntrospectedTable introspectedTable, String tableName) {
        Method method = new Method();
        method.setName("selectByPrimaryKeyWithBLOBs");
    	method.setReturnType(pojoTypeWithBLOBs);
        if (introspectedTable.getRules().generatePrimaryKeyClass()) {
            FullyQualifiedJavaType type = new FullyQualifiedJavaType(introspectedTable.getPrimaryKeyType());
            method.addParameter(new Parameter(type, "key"));
        } else {
            for (IntrospectedColumn introspectedColumn : introspectedTable.getPrimaryKeyColumns()) {
                FullyQualifiedJavaType type = introspectedColumn.getFullyQualifiedJavaType();
                method.addParameter(new Parameter(type, introspectedColumn.getJavaProperty()));
            }
        }
        method.setVisibility(JavaVisibility.PUBLIC);
        StringBuilder sb = new StringBuilder();
        sb.append("return this.");
        if("dubbo".equals(whatService)  && daoInterfaceType!=null){
	        sb.append(toLowerCaseFirstOne(daoInterfaceType.getShortName())+".");
        }else{
	        sb.append(getDaoShort());
        }
        
        sb.append("selectByPrimaryKeyWithBLOBs");
        sb.append("(");
        for (IntrospectedColumn introspectedColumn : introspectedTable.getPrimaryKeyColumns()) {
            sb.append(introspectedColumn.getJavaProperty());
            sb.append(",");
        }
        sb.setLength(sb.length() - 1);
        sb.append(");");
        method.addBodyLine(sb.toString());
        // method.addBodyLine("} catch (Exception e) {");
        // method.addBodyLine("logger.error(\"Exception: \", e);");
        // method.addBodyLine("return null;");
        // method.addBodyLine("}");
        return method;
    }
    

    /**
     * 添加方法
     */
    protected Method countByExample(IntrospectedTable introspectedTable, String tableName) {
        Method method = new Method();
        method.setName("countByExample");
        method.setReturnType(new FullyQualifiedJavaType("long"));
        String example="criteria";
        if(tableName!=null && tableName.length()>0){
        	if(toLowerCaseFirstOne(tableName)!=null){
        		example=toLowerCaseFirstOne(tableName)+"Criteria";
        	}
        }
        
        method.addParameter(new Parameter(pojoCriteriaType, example));
        method.setVisibility(JavaVisibility.PUBLIC);
        StringBuilder sb = new StringBuilder();
        sb.append("long count = this.");
        if("dubbo".equals(whatService)  && daoInterfaceType!=null){
            sb.append(toLowerCaseFirstOne(daoInterfaceType.getShortName())+".");
        }else{
            sb.append(getDaoShort());
        }
        
        sb.append("countByExample");
        sb.append("(");
        sb.append(""+example+"");
        sb.append(");");
        method.addBodyLine(sb.toString());
        
        method.addBodyLine("logger.debug(\"count: {}\", count);");
        method.addBodyLine("return count;");
        return method;
    }

    /**
     * 添加方法
     */
    protected Method selectByExample(IntrospectedTable introspectedTable, String tableName) {
        Method method = new Method();
        method.setName("selectByExample");
    	method.setReturnType(new FullyQualifiedJavaType("List<" + tableName + ">"));
       
        String example="criteria";
        if(tableName!=null && tableName.length()>0){
        	if(toLowerCaseFirstOne(tableName)!=null){
        		example=toLowerCaseFirstOne(tableName)+"Criteria";
        	}
        }
        
        method.addParameter(new Parameter(pojoCriteriaType, example));
        method.setVisibility(JavaVisibility.PUBLIC);
        StringBuilder sb = new StringBuilder();
        sb.append("return this.");
        if("dubbo".equals(whatService)  && daoInterfaceType!=null){
        	
        	 sb.append(toLowerCaseFirstOne(daoInterfaceType.getShortName())+".");
        }else{
	        sb.append(getDaoShort());
        }
	        sb.append("selectByExample");
	        sb.append("(");
	        sb.append(""+example+"");
	        sb.append(");");
        method.addBodyLine(sb.toString());
        return method;
    }
    
    
    

    /**
     * 添加方法
     */
    protected Method selectByExampleWithBLOBs(IntrospectedTable introspectedTable, String tableName) {
        Method method = new Method();
        method.setName("selectByExampleWithBLOBs");
        
        method.setReturnType(new FullyQualifiedJavaType("List<" + tableName + "WithBLOBs>"));
       
        String example="criteria";
        if(tableName!=null && tableName.length()>0){
        	if(toLowerCaseFirstOne(tableName)!=null){
        		example=toLowerCaseFirstOne(tableName)+"Criteria";
        	}
        }
        
        method.addParameter(new Parameter(pojoCriteriaType, example));
        method.setVisibility(JavaVisibility.PUBLIC);
        StringBuilder sb = new StringBuilder();
        sb.append("return this.");
        if("dubbo".equals(whatService)  && daoInterfaceType!=null){
        	
        	 sb.append(toLowerCaseFirstOne(daoInterfaceType.getShortName())+".");
	         
        }else{
	        sb.append(getDaoShort());
        }
        sb.append("selectByExampleWithBLOBs");
        sb.append("(");
        sb.append(""+example+"");
        sb.append(");");
        method.addBodyLine(sb.toString());
        return method;
    }
    
    
    /**
     * 添加方法
     */
    protected Method getOtherInteger(String methodName, IntrospectedTable introspectedTable, String tableName, int type) {
        Method method = new Method();
        method.setName(methodName);
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        String params = addParams(introspectedTable, method, type);
        method.setVisibility(JavaVisibility.PUBLIC);
        StringBuilder sb = new StringBuilder();
        // method.addBodyLine("try {");
        String criteria=toLowerCaseFirstOne(tableName)+"Criteria";
        String tableNameNow=tableName;
        if (introspectedTable.hasBLOBColumns()) {
        	tableNameNow= tableName + "WithBLOBs";
     	}
        if(type==6){
        	//分页查询
         		method.setReturnType(new FullyQualifiedJavaType("PageView<" + tableNameNow + ">"));
         		 if("dubbo".equals(whatService)  && daoInterfaceType!=null){
         			 sb.append(toLowerCaseFirstOne(daoInterfaceType.getShortName())+".selectByPageExample");
                     sb.append("(");
                     sb.append(params);
                     sb.append(");");
                 }else{
		        	 //查询总记录数
		         		 sb.append("PageView<"+tableNameNow+">  pageView = null;\r\n");
		         	
		        	 sb.append("\t\t long count = this.countByExample("+criteria+");\r\n");
		        	 sb.append("\t\t if (count>0){\r\n");
		        	 sb.append("\t\t pageView = new PageView<"+tableNameNow+">();\r\n");
		        	 sb.append("\t\t if (pageSize<=0){\r\n");
		        	 sb.append("\t\t 	pageSize=10;\r\n");
		        	 sb.append("\t\t }\r\n");
		        	 sb.append("\t\t //计算页数\r\n");
		        	 sb.append("\t\t int pages=0;\r\n");
		        	 sb.append("\t\t if(count<pageSize){\r\n");
		        	 sb.append("\t\t 	 pages=1;\r\n");
		        	 sb.append("\t\t }else{\r\n");
		        	 sb.append("\t\t 	 pages= (int)Math.ceil(count/pageSize);\r\n");
		        	 sb.append("\t\t }\r\n");
		     	    	
		        	 sb.append("\t\t /**\r\n");
		        	 sb.append("\t\t if(count/pageSize==0){\r\n");
		        	 sb.append("\t\t 	pages=count/pageSize;\r\n");
		        	 sb.append("\t\t }else{\r\n");
		        	 sb.append("\t\t 	pages=count/pageSize +1 ;\r\n");
		        	 sb.append("\t\t }\r\n");
		        	 sb.append("\t\t **/	\r\n\r\n");
		        	 sb.append("\t\t int size=pageNum;\r\n");
		        	 sb.append("\t\t if (pageNum<=0){	\r\n");    	
		        	 sb.append("\t\t 	size=1;\r\n");
		        	 sb.append("\t\t }else if(pageNum>pages){\r\n");
		        	 sb.append("\t\t 		size=pages;\r\n");
		        	 sb.append("\t\t }\r\n");
		     	    	
		        	 sb.append("\t\t int prePage=0;\r\n");
		        	 sb.append("\t\t int nextPage=0;\r\n");
		        	 sb.append("\t\t boolean isFirstPage=false;\r\n");
		        	 sb.append("\t\t boolean isLastPage=false;\r\n");
		        	 sb.append("\t\t 	boolean isHasPreviousPage=false;\r\n");
		        	 sb.append("\t\t 	boolean isHasNextPage=false;\r\n");
		        	 sb.append("\t\t int navigatePages=7; //导航页码数  \r\n");
		        	 sb.append("\t\t int[] navigatePageNumbers=new int[navigatePages];   //所有导航页号  \r\n");
		     	    	
		     	    	
		        	 sb.append("\t\t if(size<=1){\r\n");
		        	 sb.append("\t\t 	isFirstPage=true;\r\n");
		        	 sb.append("\t\t 	isHasNextPage=true;\r\n");
		        	 sb.append("\t\t 	nextPage=2;\r\n");
		     	    		
		        	 sb.append("\t\t 	for(int i=1;i<pages;i++){\r\n");
		        	 sb.append("\t\t 		navigatePageNumbers[i-1]=i;\r\n");
		        	 sb.append("\t\t 		if(i==navigatePages){\r\n");
		        	 sb.append("\t\t 			break;\r\n");
		        	 sb.append("\t\t 		}\r\n");
		        	 sb.append("\t\t 	}\r\n");
		        	 sb.append("\t\t 	if(pages==1){\r\n");
		        	 sb.append("\t\t 			isLastPage=true;\r\n");
		        	 sb.append("\t\t 	}else{\r\n");
		        	 sb.append("\t\t 		isHasNextPage=true;\r\n");
		        	 sb.append("\t\t 	}\r\n");
		        	 sb.append("\t\t 	if(navigatePages>=pages){\r\n");
		        	 sb.append("\t\t 		for (int i=1;i<=pages;i++){\r\n");
		        	 sb.append("\t\t 				navigatePageNumbers[i-1]=i;\r\n");
		        	 sb.append("\t\t 		 }\r\n");
		        	 sb.append("\t\t 	}\r\n");
		        	 
		        	 sb.append("\t\t }else if(size==pages){\r\n");
		        	 sb.append("\t\t 	isLastPage=true;\r\n");
		        	 sb.append("\t\t 	isHasPreviousPage=true;\r\n");
		        	 sb.append("\t\t 	prePage=size-1;\r\n");
		     	    		
		        	 sb.append("\t\t 	for(int i=1;i<pages;i++){\r\n");
		        	 sb.append("\t\t 		if(pages-i>0){\r\n");
		        	 sb.append("\t\t 				navigatePageNumbers[i-1]=pages-i;\r\n");
		        	 sb.append("\t\t 			if(i==navigatePages){\r\n");
		        	 sb.append("\t\t   				break;\r\n");
		        	 sb.append("\t\t   			}\r\n");
		        	 sb.append("\t\t 			}else{\r\n");
		        	 sb.append("\t\t 			break;\r\n");
		        	 sb.append("\t\t 		}\r\n");
		     	    			
		        	 sb.append("\t\t 	}\r\n");
		        	 sb.append("\t\t }else{\r\n");
		        	 sb.append("\t\t 	isHasNextPage=true;\r\n");
		        	 sb.append("\t\t 	isHasPreviousPage=true;\r\n");
		        	 sb.append("\t\t 	prePage=size-1;\r\n");
		        	 sb.append("\t\t 	nextPage=size+1;\r\n");
		     	    		
		        	 sb.append("\t\t 	if(size<=navigatePages/2){\r\n");
		        	 sb.append("\t\t 			for (int i=1;i<=navigatePages;i++){\r\n");
		        	 sb.append("\t\t 			navigatePageNumbers[i-1]=i;\r\n");
		        	 sb.append("\t\t 		}\r\n");
		        	 sb.append("\t\t 	}else if(size>=pages-navigatePages/2){\r\n");
		        	 sb.append("\t\t 		for (int i=0;i<navigatePages;i++){\r\n");
		        	 sb.append("\t\t 			navigatePageNumbers[i]=pages-i;\r\n");
		        	 sb.append("\t\t 		}\r\n");
		        	 sb.append("\t\t 	}else{\r\n");
		        	 sb.append("\t\t 		if(navigatePages%2==0){\r\n");
		        	 sb.append("\t\t 			for (int i=0;i<navigatePages/2;i++){\r\n");
		        	 sb.append("\t\t  				navigatePageNumbers[i]=size-navigatePages/2+i;\r\n");
		        	 sb.append("\t\t  				navigatePageNumbers[i+navigatePages/2]=size+i;\r\n");
		        	 sb.append("\t\t   			}\r\n");
		        	 sb.append("\t\t 		}else{\r\n");
		        	 sb.append("\t\t 			navigatePageNumbers[navigatePages/2]=size;\r\n");
		        	 sb.append("\t\t 			for (int i=0;i<navigatePages/2;i++){\r\n");
		        	 sb.append("\t\t   				navigatePageNumbers[i]=size-navigatePages/2+i;\r\n");
		        	 sb.append("\t\t  				navigatePageNumbers[i+navigatePages/2+1]=size+i+1;\r\n");
		        	 sb.append("\t\t    			}\r\n");
		        	 sb.append("\t\t 			}\r\n");
		        	 sb.append("\t\t 		}\r\n");
		        	 sb.append("\t\t 		 if(navigatePages>=pages){\r\n");
		        	 sb.append("\t\t 			for (int i=1;i<=pages;i++){\r\n");
		        	 sb.append("\t\t 				navigatePageNumbers[i-1]=i;\r\n");
		        	 sb.append("\t\t 		 }\r\n");
		        	 sb.append("\t\t 	}\r\n");
		        	 sb.append("\t\t }\r\n");
		     	    	
		        	 sb.append("\t\t  int startRow=(size-1)*pageSize;\r\n");
		        	 sb.append("\t\t  int endRow=size*pageSize;\r\n");
		        	 String newParams=params.replace("pageNum", "startRow").replace("pageSize", "endRow");
		        	 if (introspectedTable.hasBLOBColumns()) {
		        		 sb.append("\t\t List<"+tableNameNow+"> list = this."+getDaoShort()+"selectByPageExampleWithBLOBs("+newParams+");\r\n");
		         	}else{
		         		 sb.append("\t\t List<"+tableNameNow+"> list = this."+getDaoShort()+"selectByPageExample("+newParams+");\r\n");
		             	
		         	}
		        	 
		        	 sb.append("\t\t 	if (list !=null && list.size()>0){\r\n");
		        	 sb.append("\t\t   		pageView.setPageNum(pageNum);\r\n");
		        	 sb.append("\t\t 		pageView.setPageSize(pageSize);\r\n");
		        	 sb.append("\t\t 		pageView.setSize(size);\r\n");
		        	 sb.append("\t\t 		pageView.setStartRow(startRow);\r\n");
		        	 sb.append("\t\t  		pageView.setEndRow(endRow);\r\n");
		        	 sb.append("\t\t  		pageView.setTotal(count);\r\n");
		        	 sb.append("\t\t  		pageView.setPages(pages);\r\n");
		        	 sb.append("\t\t  		pageView.setList(list);\r\n");
		        	 sb.append("\t\t   		pageView.setFirstPage(1);\r\n");
		        	 sb.append("\t\t   		pageView.setLastPage(pages);\r\n");
		        	 sb.append("\t\t    	pageView.setPrePage(prePage);\r\n");
		        	 sb.append("\t\t   		pageView.setNextPage(nextPage);\r\n");
		        	 sb.append("\t\t    	pageView.setIsFirstPage(isFirstPage);\r\n");
		        	 sb.append("\t\t    	pageView.setIsLastPage(isLastPage);\r\n");
		        	 sb.append("\t\t    	pageView.setIsHasPreviousPage(isHasPreviousPage);\r\n");
		        	 sb.append("\t\t    	pageView.setIsHasNextPage(isHasNextPage);\r\n");
		        	 sb.append("\t\t    	pageView.setNavigatePages(navigatePages);\r\n");
		        	 sb.append("\t\t    	pageView.setNavigatepageNums(navigatePageNumbers);\r\n");
		        	 sb.append("\t\t 	}\r\n");
		        	 sb.append("\t\t }\r\n");
		        	 sb.append("\t\t   return pageView;\r\n");
                 }
             method.addBodyLine(sb.toString());
        }else if (type==7){
        	//pagehelper分页查询
        	method.setReturnType(new FullyQualifiedJavaType("PageInfo<" + tableNameNow + ">"));
        	
        	 if("dubbo".equals(whatService)  && daoInterfaceType!=null){
               sb.append(toLowerCaseFirstOne(daoInterfaceType.getShortName())+".selectByPageHelperExample");
               sb.append("(");
               sb.append(params);
               sb.append(");");
             }else{
	        	sb.append("\t\t PageInfo<"+tableNameNow+"> pageInfo =null;\r\n");
	        	sb.append("\t\t PageHelper.startPage(pageNum, pageSize);\r\n");
	        	
	        	if (introspectedTable.hasBLOBColumns()) {
	                sb.append("\t\t List<"+tableNameNow+"> list = this."+getDaoShort() + "selectByExampleWithBLOBs("+criteria+");\r\n");
	            } else {
	            	sb.append("\t\t List<"+tableNameNow+"> list = this."+getDaoShort()+"selectByExample("+criteria+");\r\n");
	            	
	            }
	        
	        	
	        	sb.append("\t\t   if (list !=null && list.size()>0){\r\n");
	        	sb.append("\t\t   		pageInfo= new PageInfo<" + tableNameNow + ">(list);\r\n");
	        	sb.append("\t\t   }\r\n");
	        	sb.append("\t\t return pageInfo;\r\n");
             }
        	method.addBodyLine(sb.toString());
        }else if (type==5){
        	 if("dubbo".equals(whatService)  && daoInterfaceType!=null){
        		 sb.append(toLowerCaseFirstOne(daoInterfaceType.getShortName())+"."+methodName);
                 sb.append("(");
                 sb.append(params);
                 sb.append(");");
             }else{
	        	String objectName=tableNameNow;
	        	 if("insertBatchSelective".equals(methodName)   || "insertBatch".equals(methodName)   || "saveOrUpdateByPrimaryKey".equals(methodName)  || "updateBatchByPrimaryKey".equals(methodName)){
	        		 objectName=tableName;
	        	 }
	        	 
	        	 String listName=toLowerCaseFirstOne(objectName)+"List";
	      		sb.append("\t\t //控制一次性数量，避免超出数据库SQL内存，批量插入，每200条数据进行插入一次,\r\n");
	      		sb.append("\t\t int rm=0;\r\n");
	      		sb.append("\t\t int perCount=200,index=0;  \r\n");
	      		sb.append("\t\t int times="+listName+".size()/perCount; \r\n"); 
	      		sb.append("\t\t do  { \r\n"); 
	      		sb.append("\t\t  	List<"+objectName+"> listTemp=null;\r\n ");
	      		sb.append("\t\t  	if("+listName+".size()>=perCount){  \r\n");
	      		sb.append("\t\t  	         listTemp="+listName+".subList(0, perCount);// listTemp是分段处理逻辑的参数  \r\n");
	      		sb.append("\t\t  	     }else{ \r\n"); 
	      		sb.append("\t\t  	        listTemp="+listName+".subList(0, "+listName+".size());// listTemp是分段处理逻辑的参数\r\n");  
	      		sb.append("\t\t  	}  \r\n");
	      		sb.append("\t\t 		System.out.println(\"第\"+(index+1)+\"轮批量保存>>\");\r\n"); 
	      		sb.append("\t\t 		rm+= this.");
		        sb.append(getDaoShort());
		        sb.append(methodName);
		         
		        sb.append("(listTemp);\r\n");
	            sb.append("\t\t 		"+listName+".removeAll(listTemp);\r\n");  
	            sb.append("\t\t 		    System.out.println(\"当前剩余集合长度:>>\"+"+listName+".size()); \r\n"); 
	            sb.append("\t\t 		    index++;  \r\n");
	            sb.append("\t\t 		}\r\n");  
	            sb.append("\t\t while ("+listName+".size() >0 && index<=times); \r\n\r\n"); 
	            sb.append("\t\t return rm;\r\n");
             }
           method.addBodyLine(sb.toString());
        	
        	
        }else{
        	
        	 if (introspectedTable.hasBLOBColumns()){
        		 sb.append("return this.");
        		 if("dubbo".equals(whatService)  && daoInterfaceType!=null){
        		        sb.append(toLowerCaseFirstOne(daoInterfaceType.getShortName())+".");
        	        }else{
        	            sb.append(getDaoShort());
        	        }
        		 
	        		 if (introspectedTable.hasBLOBColumns()
		            		  && (!"updateByPrimaryKeySelective".equals(methodName) && !"deleteByPrimaryKey".equals(methodName)
		            				  && !"saveOrUpdateByPrimaryKeyWithBLOBs".equals(methodName) 	&& !"saveOrUpdateByPrimaryKey".equals(methodName)  && !"updateByPrimaryKey".equals(methodName)		  && !"updateByPrimaryKeyWithBLOBs".equals(methodName)	  	  && !"saveOrUpdateByExampleWithBLOBs".equals(methodName) 		  && !"saveOrUpdateByExample".equals(methodName)     && !"insertSelective".equals(methodName)   && !"insert".equals(methodName) 	 && !"deleteByExample".equals(methodName) && !"updateByExampleSelective".equals(methodName))) {
		                  sb.append(methodName + "WithBLOBs");
			            } else {
			                sb.append(methodName);
			            }
		            sb.append("(");
		            sb.append(params);
		            sb.append(");");
        	         method.addBodyLine(sb.toString());
        	 }else{
        		 sb.append("return this.");
        		 if("dubbo".equals(whatService)  && daoInterfaceType!=null){
        		       
        		       
        		        sb.append(toLowerCaseFirstOne(daoInterfaceType.getShortName())+".");
		                
        	        }else{
		                 sb.append(getDaoShort());
        	        }
        		 
        		 sb.append(methodName);
                 sb.append("(");
                 sb.append(params);
                 sb.append(");");
                 method.addBodyLine(sb.toString());
        	 }
        	
        	
        }
        
        
        return method;
    }

    /**
     * 添加方法
     */
    protected Method getOtherInsertboolean(String methodName, IntrospectedTable introspectedTable, String tableName,boolean isWithBLOBs) {
        Method method = new Method();
        method.setName(methodName);
        method.setReturnType(returnType);
        String record="record";
        if(tableName!=null && tableName.length()>0){
        	if(toLowerCaseFirstOne(tableName)!=null){
        		record=toLowerCaseFirstOne(tableName);
        	}
        }
        
        if(isWithBLOBs){
        	 method.addParameter(new Parameter(pojoTypeWithBLOBs, record));
        }else{
        	 method.addParameter(new Parameter(pojoType, record));	
        }
        
       
        method.setVisibility(JavaVisibility.PUBLIC);
        StringBuilder sb = new StringBuilder();
        if (returnType == null) {
            sb.append("this.");
        } else {
            sb.append("return this.");
        }
        if("dubbo".equals(whatService)  && daoInterfaceType!=null){
	        sb.append(toLowerCaseFirstOne(daoInterfaceType.getShortName())+".");
        }else{
	        sb.append(getDaoShort());
	       
        }
        sb.append(methodName);
        sb.append("(");
        sb.append(""+record+"");
        sb.append(");");
        method.addBodyLine(sb.toString());
        return method;
    }

    /**
     * type 的意义 pojo 1 key 2 example 3 pojo+example 4  list<pojo> 5  分页 pageNum, pageSize,exampledistinct,orderByClause 6  分页pagehelper 7
     */
    protected String addParams(IntrospectedTable introspectedTable, Method method, int type1) {
    	String record="record";
    	String example="criteria";
    	String objName="?";
    	String methodName=method.getName();
    	if(introspectedTable!=null && introspectedTable.getFullyQualifiedTable()!=null&& introspectedTable.getFullyQualifiedTable().getDomainObjectName()!=null&& introspectedTable.getFullyQualifiedTable().getDomainObjectName().length()>0){
    		String name= introspectedTable.getFullyQualifiedTable().getDomainObjectName();
    		if (introspectedTable.hasBLOBColumns()
    				  	&& !"saveOrUpdateByPrimaryKey".equals(methodName) 	&& !"updateByPrimaryKey".equals(methodName)	&& !"saveOrUpdateByExample".equals(methodName)  && !"insertBatchSelective".equals(methodName)  && !"insertBatch".equals(methodName) && !"insertSelective".equals(methodName)   && !"insert".equals(methodName) && !"saveOrUpdateByPrimaryKey".equals(methodName)  && !"updateBatchByPrimaryKey".equals(methodName) ) {
    			objName=name+"WithBLOBs"; 
        	}else{
        		objName=name;
        	}
    		name=toLowerCaseFirstOne(name);
    		
    		
    		if (introspectedTable.hasBLOBColumns()
    				  	&& !"saveOrUpdateByPrimaryKey".equals(methodName) && !"updateByPrimaryKey".equals(methodName)	&& !"saveOrUpdateByExample".equals(methodName) && !"insertBatchSelective".equals(methodName)  && !"insertBatch".equals(methodName) && !"insertSelective".equals(methodName)   && !"insert".equals(methodName) && !"saveOrUpdateByPrimaryKey".equals(methodName)  && !"updateBatchByPrimaryKey".equals(methodName) ) {
    			record=name+"WithBLOBs"; 
        	}else{
        		record=name;
        	}
    		example=name+"Criteria";
    	}
    	
        switch (type1) {
            case 1:
            	if (introspectedTable.hasBLOBColumns()&& !"saveOrUpdateByPrimaryKey".equals(methodName) && !"updateByPrimaryKey".equals(methodName) && !"insertBatchSelective".equals(methodName)  && !"insertBatch".equals(methodName)   && !"insertSelective".equals(methodName)   && !"insert".equals(methodName) ) {
            		method.addParameter(new Parameter(pojoTypeWithBLOBs, record));
            	}else{
            		method.addParameter(new Parameter(pojoType, record));
            	}
                
                return record;
            case 2:
                if (introspectedTable.getRules().generatePrimaryKeyClass()) {
                    FullyQualifiedJavaType type = new FullyQualifiedJavaType(introspectedTable.getPrimaryKeyType());
                    method.addParameter(new Parameter(type, "key"));
                } else {
                    for (IntrospectedColumn introspectedColumn : introspectedTable.getPrimaryKeyColumns()) {
                        FullyQualifiedJavaType type = introspectedColumn.getFullyQualifiedJavaType();
                        method.addParameter(new Parameter(type, introspectedColumn.getJavaProperty()));
                    }
                }
                StringBuffer sb = new StringBuffer();
                for (IntrospectedColumn introspectedColumn : introspectedTable.getPrimaryKeyColumns()) {
                    sb.append(introspectedColumn.getJavaProperty());
                    sb.append(",");
                }
                if(sb.length()>1 && sb.lastIndexOf(",")==sb.length()-1){
                	sb.setLength(sb.length() - 1);
                }
                
                return sb.toString();
            case 3:
                method.addParameter(new Parameter(pojoCriteriaType, example));
                return example;
            case 4:
            	
            	if (introspectedTable.hasBLOBColumns() && !"saveOrUpdateByExample".equals(methodName)) {
            		method.addParameter(0,new Parameter(pojoTypeWithBLOBs, record));
            	}else{
            		method.addParameter(0,new Parameter(pojoType, record));
            	}
            	
                method.addParameter(1, new Parameter(pojoCriteriaType, example));
                return record+","+ example;
                
            case 5:
            	
            	 method.addParameter(new Parameter(new FullyQualifiedJavaType("List<" + objName + ">"), record+"List"));
                 return record+"List";
            case 6:	
            	method.addParameter(0, new Parameter(new FullyQualifiedJavaType("int"), "pageNum"));
            	method.addParameter(1, new Parameter(new FullyQualifiedJavaType("int"), "pageSize"));
            	method.addParameter(2, new Parameter(pojoCriteriaType, example));
            	method.addParameter(3, new Parameter(new FullyQualifiedJavaType("boolean"), "distinct"));
            	method.addParameter(4, new Parameter(new FullyQualifiedJavaType("String"), "orderByClause"));
            	
            	return "pageNum,pageSize,"+example+",distinct,orderByClause";
            case 7:	
            	method.addParameter(0, new Parameter(new FullyQualifiedJavaType("int"), "pageNum"));
            	method.addParameter(1, new Parameter(new FullyQualifiedJavaType("int"), "pageSize"));
            	method.addParameter(2, new Parameter(pojoCriteriaType, example));
            	return "pageNum,pageSize,"+example;
            default:
                break;
        }
        return null;
    }

    protected void addComment(JavaElement field, String comment) {
        StringBuilder sb = new StringBuilder();
        field.addJavaDocLine("/**");
        sb.append(" * ");
        comment = comment.replaceAll("\n", "<br>\n\t * ");
        sb.append(comment);
        field.addJavaDocLine(sb.toString());
        field.addJavaDocLine(" */");
    }

    /**
     * 添加字段
     *
     * @param topLevelClass
     */
    protected void addField(TopLevelClass topLevelClass) {
        // 添加 success
        Field field = new Field();
        field.setName("success"); // 设置变量名
        field.setType(FullyQualifiedJavaType.getBooleanPrimitiveInstance()); // 类型
        field.setVisibility(JavaVisibility.PRIVATE);
        addComment(field, "执行结果");
        topLevelClass.addField(field);
        // 设置结果
        field = new Field();
        field.setName("message"); // 设置变量名
        field.setType(FullyQualifiedJavaType.getStringInstance()); // 类型
        field.setVisibility(JavaVisibility.PRIVATE);
        addComment(field, "消息结果");
        topLevelClass.addField(field);
    }

    /**
     * 添加方法
     */
    protected void addMethod(TopLevelClass topLevelClass) {
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("setSuccess");
        method.addParameter(new Parameter(FullyQualifiedJavaType.getBooleanPrimitiveInstance(), "success"));
        method.addBodyLine("this.success = success;");
        topLevelClass.addMethod(method);

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(FullyQualifiedJavaType.getBooleanPrimitiveInstance());
        method.setName("isSuccess");
        method.addBodyLine("return success;");
        topLevelClass.addMethod(method);

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("setMessage");
        method.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), "message"));
        method.addBodyLine("this.message = message;");
        topLevelClass.addMethod(method);

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(FullyQualifiedJavaType.getStringInstance());
        method.setName("getMessage");
        method.addBodyLine("return message;");
        topLevelClass.addMethod(method);
    }

    /**
     * 添加方法
     */
    protected void addMethod(TopLevelClass topLevelClass, String tableName) {
        Method method2 = new Method();
        for (int i = 0; i < methods.size(); i++) {
            Method method = new Method();
            method2 = methods.get(i);
            method = method2;
            method.removeAllBodyLines();
            //method.removeAnnotation();
            StringBuilder sb = new StringBuilder();
            sb.append("return this.");
            sb.append(getDaoShort());
            sb.append(method.getName());
            sb.append("(");
            List<Parameter> list = method.getParameters();
            for (int j = 0; j < list.size(); j++) {
                sb.append(list.get(j).getName());
                sb.append(",");
            }
            sb.setLength(sb.length() - 1);
            sb.append(");");
            method.addBodyLine(sb.toString());
            topLevelClass.addMethod(method);
        }
        methods.clear();
    }

    /**
     * BaseUsers to baseUsers
     *
     * @param tableName
     * @return
     */
    protected String toLowerCase(String tableName) {
        StringBuilder sb = new StringBuilder(tableName);
        sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
        return sb.toString();
    }

    /**
     * BaseUsers to baseUsers
     *
     * @param tableName
     * @return
     */
    protected String toUpperCase(String tableName) {
        StringBuilder sb = new StringBuilder(tableName);
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        return sb.toString();
    }

    /**
     * 导入需要的类
     */
    private void addImport(Interface interfaces, TopLevelClass topLevelClass,boolean isHasBLOBColumns) {
        if (isHasBLOBColumns) {
        	 interfaces.addImportedType(pojoTypeWithBLOBs);
        	 interfaces.addImportedType(pojoType);
    	}else{
    		 interfaces.addImportedType(pojoType);
    	}
        
        interfaces.addImportedType(pojoCriteriaType);
        interfaces.addImportedType(listType);
        if (enableSelectByPageExample) {
        	interfaces.addImportedType(pageType);
        }
        if (enableSelectByPageHelperExample) {
        	interfaces.addImportedType(pagehelperType);
        }
        topLevelClass.addImportedType(daoType);
        topLevelClass.addImportedType(interfaceType);
        
        
        if (isHasBLOBColumns) {
	       	topLevelClass.addImportedType(pojoTypeWithBLOBs);
	       	topLevelClass.addImportedType(pojoType);
	   	}else{
	   		topLevelClass.addImportedType(pojoType);
	   	}
        
        topLevelClass.addImportedType(pojoCriteriaType);
        topLevelClass.addImportedType(listType);
        if (enableSelectByPageExample) {
        	topLevelClass.addImportedType(pageType);
        }
        if (enableSelectByPageHelperExample) {
        	topLevelClass.addImportedType(pagehelperType);
        	topLevelClass.addImportedType(new FullyQualifiedJavaType("com.github.pagehelper.PageHelper"));
        }
        
        topLevelClass.addImportedType(slf4jLogger);
        topLevelClass.addImportedType(slf4jLoggerFactory);
        if (enableAnnotation) {
            topLevelClass.addImportedType(service);
            topLevelClass.addImportedType(autowired);
        }
    }

    /**
     * 导入logger
     */
    private void addLogger(TopLevelClass topLevelClass) {
        Field field = new Field();
        field.setFinal(true);
        field.setInitializationString("LoggerFactory.getLogger(" + topLevelClass.getType().getShortName() + ".class)"); // 设置值
        field.setName("logger"); // 设置变量名
        field.setStatic(true);
        field.setType(new FullyQualifiedJavaType("Logger")); // 类型
        field.setVisibility(JavaVisibility.PRIVATE);
        topLevelClass.addField(field);
    }

    private String getDaoShort() {
        return toLowerCase(daoType.getShortName()) + ".";
    }

    public boolean clientInsertMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        returnType = method.getReturnType();
        return true;
    }
    
  //首字母转小写
    public static String toLowerCaseFirstOne(String s){
      if(Character.isLowerCase(s.charAt(0)))
        return s;
      else
        return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }
    //首字母转大写
    public static String toUpperCaseFirstOne(String s){
      if(Character.isUpperCase(s.charAt(0)))
        return s;
      else
        return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }
}
