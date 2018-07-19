package org.mybatis.generator.plugins.my;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaElement;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;

/**
 * 统一Mapper(BaseDao)生成
 *
 * @author Gavin·Yan
 *         date: 2018/05/08 15:20
 */
public class MapperPlugin extends PluginAdapter {
    private String interfaceName;
    private String interfaceNoKeyName;
    private boolean deleteMethod = true;

    private FullyQualifiedJavaType interfaceType;
    private FullyQualifiedJavaType interfaceNoKeyType;

    private FullyQualifiedJavaType E;
    private FullyQualifiedJavaType M;
    private FullyQualifiedJavaType MB;
    private FullyQualifiedJavaType MLIST;
    private FullyQualifiedJavaType MBLIST;
    private FullyQualifiedJavaType ID;


    @Override
    public boolean validate(List<String> warnings) {
        interfaceName = this.properties.getProperty("interfaceName");
        interfaceNoKeyName = this.properties.getProperty("interfaceNoKeyName");
        String deleteMethod = this.properties.getProperty("deleteMethod");
        if ("FALSE".equals(deleteMethod.toUpperCase())) {
            this.deleteMethod = Boolean.getBoolean(deleteMethod);
        } else {
            this.deleteMethod = true;
        }

        E = new FullyQualifiedJavaType("E");
        M = new FullyQualifiedJavaType("M");
        MB = new FullyQualifiedJavaType("MB");
        MLIST = new FullyQualifiedJavaType("List<M>");
        MBLIST = new FullyQualifiedJavaType("List<MB>");
        ID = new FullyQualifiedJavaType("ID");

        String interfacePack = context.getJavaClientGeneratorConfiguration().getTargetPackage();
        interfaceType = new FullyQualifiedJavaType(interfacePack + "." + interfaceName);
        interfaceNoKeyType= new FullyQualifiedJavaType(interfacePack + "." + interfaceNoKeyName);
        return true;
    }

    /**
     * 生成的Mapper接口
     *
     * @param interfaze
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if (deleteMethod) {
            // 清空导入的包
            interfaze.clearImportedTypes();
            interfaze.clearMethod();

            //获取实体类
            FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
            FullyQualifiedJavaType entityTypeWithBLOBs = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()+"WithBLOBs");

            
            //注解的添加
            FullyQualifiedJavaType serviceType = new FullyQualifiedJavaType("org.springframework.stereotype.Repository");
            interfaze.addImportedType(serviceType);
            interfaze.addAnnotation("@Repository");

            //import接口
            // 解决多个表，而重复生成IMaper的问题
            interfaceType.clearTypeArgument();
            interfaze.addImportedType(interfaceType);

            FullyQualifiedJavaType exampleType = new FullyQualifiedJavaType(introspectedTable.getExampleType());
            interfaze.addImportedType(exampleType);

            if (introspectedTable.getPrimaryKeyColumns() == null || introspectedTable.getPrimaryKeyColumns().size()<=0 ) {
                interfaze.addImportedType(new FullyQualifiedJavaType("该表没设置主键"));
                if (introspectedTable.hasBLOBColumns()) {
                	 interfaze.addSuperInterface(
                             new FullyQualifiedJavaType(interfaceNoKeyType.getShortName()
                                     + "<"
                                     + entityType.getShortName()
                                     + ","+entityTypeWithBLOBs.getShortName()
                                     + "," + exampleType.getShortName()
                                     + ">"));
            	}else{
            		 interfaze.addSuperInterface(
                             new FullyQualifiedJavaType(interfaceNoKeyType.getShortName()
                                     + "<"
                                     + entityType.getShortName()
                                     + "," + entityType.getShortName()
                                     + "," + exampleType.getShortName()
                                     + ">"));
            	}
               

                
            } else {
                interfaze.addImportedType(introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType());
                if (introspectedTable.hasBLOBColumns()) {
                	interfaze.addSuperInterface(
                            new FullyQualifiedJavaType(interfaceType.getShortName()
                                    + "<"
                                    + entityType.getShortName()
                                    + ","+entityTypeWithBLOBs.getShortName()
                                    + "," + exampleType.getShortName()
                                    + "," + introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType().getShortName()
                                    + ">"));
                }else{
                	interfaze.addSuperInterface(
                        new FullyQualifiedJavaType(interfaceType.getShortName()
                                + "<"
                                + entityType.getShortName()
                                + "," + entityType.getShortName()
                                + "," + exampleType.getShortName()
                                + "," + introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType().getShortName()
                                + ">"));
                }

               
            }
            //import实体类
           
            if (introspectedTable.hasBLOBColumns()) {
            	 interfaze.addImportedType(entityTypeWithBLOBs);
            	 interfaze.addImportedType(entityType);
        	}else{
        		 interfaze.addImportedType(entityType);
        	}
            
            return true;
        } else return super.clientGenerated(interfaze, topLevelClass, introspectedTable);
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        if (deleteMethod) {
            List<GeneratedJavaFile> files = new ArrayList<GeneratedJavaFile>();
            Interface interface1 = new Interface(interfaceType);
          
            if (introspectedTable.getPrimaryKeyColumns() == null || introspectedTable.getPrimaryKeyColumns().size()<=0 ) {
            	 interface1 = new Interface(interfaceNoKeyType);
            	// 添加泛型支持
            	 interfaceNoKeyType.addTypeArgument(new FullyQualifiedJavaType("M,MB, E"));
            }else{
            	 // 添加泛型支持
                interfaceType.addTypeArgument(new FullyQualifiedJavaType("M,MB,E, ID extends Serializable"));
            }
                
            interface1.setVisibility(JavaVisibility.PUBLIC);

            // 导入必要的类
            interface1.addImportedType(new FullyQualifiedJavaType("java.io.Serializable"));
            interface1.addImportedType(new FullyQualifiedJavaType("java.util.List"));
            interface1.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Param"));
            
           
            // 添加方法并加注释
            Method method =null;
            
            
            method = countByExample(introspectedTable);
            interface1.addMethod(method);

            method = deleteByExample(introspectedTable);
            interface1.addMethod(method);
           

            method = insert(introspectedTable);
            interface1.addMethod(method);

            method = insertSelective(introspectedTable);
            interface1.addMethod(method);

            method = insertBatch(introspectedTable);
            interface1.addMethod(method);

            method = insertBatchSelective(introspectedTable);
            interface1.addMethod(method);
            
            
            
            method = insertWithBLOBs(introspectedTable);
            interface1.addMethod(method);

            method = insertSelectiveWithBLOBs(introspectedTable);
            interface1.addMethod(method);

            method = insertBatchWithBLOBs(introspectedTable);
            interface1.addMethod(method);

            method = insertBatchSelectiveWithBLOBs(introspectedTable);
            interface1.addMethod(method);
            
            

            method = selectByExampleWithBLOBs(introspectedTable);
            interface1.addMethod(method);

            method = selectByExample(introspectedTable);
            interface1.addMethod(method);

           

            method = updateByExample(introspectedTable);
            interface1.addMethod(method);

            method = updateByExampleSelective(introspectedTable);
            interface1.addMethod(method);

            method = updateByExampleWithBLOBs(introspectedTable);
            interface1.addMethod(method);
            
            if (introspectedTable.getPrimaryKeyColumns() != null && introspectedTable.getPrimaryKeyColumns().size()>0 ) {
                //有主键的才有这两个方法
	            method = deleteByPrimaryKey(introspectedTable);
	            interface1.addMethod(method);
	            
	            method = selectByPrimaryKey(introspectedTable);
	            interface1.addMethod(method);
	            
	            method = selectByPrimaryKeyWithBLOBs(introspectedTable);
	            interface1.addMethod(method);
	            


	            method = updateByPrimaryKeySelective(introspectedTable);
	            interface1.addMethod(method);

	            method = updateByPrimaryKeyWithBLOBs(introspectedTable);
	            interface1.addMethod(method);

	            method = updateByPrimaryKey(introspectedTable);
	            interface1.addMethod(method);
            }
            
            method = selectByPageExample(introspectedTable);
            interface1.addMethod(method);
            
            method = selectByPageExampleWithBLOBs(introspectedTable);
            interface1.addMethod(method);
            
            method = saveOrUpdateByExample(introspectedTable);
            interface1.addMethod(method);
            
            method = saveOrUpdateByExampleWithBLOBs(introspectedTable);
            interface1.addMethod(method);
            
            
            
            method = updateBatchByPrimaryKey(introspectedTable);
            interface1.addMethod(method);
            
            method = updateBatchByPrimaryKeyWithBLOBs(introspectedTable);
            interface1.addMethod(method);
            
            
            method = saveOrUpdateByPrimaryKey(introspectedTable);
            interface1.addMethod(method);
            
            method = saveOrUpdateByPrimaryKeyWithBLOBs(introspectedTable);
            interface1.addMethod(method);
           
            

            addExampleClassComment(interface1);

            String project = context.getJavaClientGeneratorConfiguration().getTargetProject();
            GeneratedJavaFile file = new GeneratedJavaFile(interface1, project, context.getJavaFormatter());
            files.add(file);
            return files;
        } else return super.contextGenerateAdditionalJavaFiles(introspectedTable);
    }

    private void addExampleClassComment(JavaElement javaElement) {
        javaElement.addJavaDocLine("/**");
        javaElement.addJavaDocLine(" * 通用BaseMapper<M,MB,  E, ID>");
        javaElement.addJavaDocLine(" * M:实体类");
        javaElement.addJavaDocLine(" * M:实体类(含Blob类型参数)");
        javaElement.addJavaDocLine(" * E:Example");
        javaElement.addJavaDocLine(" * ID:主键的变量类型");
        javaElement.addJavaDocLine(" *");
        javaElement.addJavaDocLine(" * @author Gavin·Yan");
        javaElement.addJavaDocLine(" *         ");
        javaElement.addJavaDocLine(" *         date: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        javaElement.addJavaDocLine(" */");
    }

    /**
     * 添加方法
     */
    protected Method countByExample(IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setName("countByExample");
        method.setReturnType( new FullyQualifiedJavaType("long"));
        method.addParameter(new Parameter(E, "example"));
        method.setVisibility(JavaVisibility.PUBLIC);
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        return method;
    }

    /**
     * 添加方法
     */
    protected Method deleteByExample(IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setName("deleteByExample");
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.addParameter(new Parameter(E, "example"));
        method.setVisibility(JavaVisibility.PUBLIC);
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        return method;
    }

    /**
     * 添加方法
     */
    protected Method deleteByPrimaryKey(IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setName("deleteByPrimaryKey");
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.addParameter(new Parameter(ID, "id"));
        method.setVisibility(JavaVisibility.PUBLIC);
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        return method;
    }

    /**
     * 添加方法
     */
    protected Method insert(IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setName("insert");
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.addParameter(new Parameter(M, "record"));
        method.setVisibility(JavaVisibility.PUBLIC);
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        return method;
    }

    /**
     * 添加方法
     */
    protected Method insertSelective(IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setName("insertSelective");
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.addParameter(new Parameter(M, "record"));
        method.setVisibility(JavaVisibility.PUBLIC);
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        return method;
    }

    /**
     * 添加方法
     */
    protected Method insertBatch(IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setName("insertBatch");
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.addParameter(new Parameter(MLIST, "record"));
        method.setVisibility(JavaVisibility.PUBLIC);
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        return method;
    }

    /**
     * 添加方法
     */
    protected Method insertBatchSelective(IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setName("insertBatchSelective");
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.addParameter(new Parameter(MLIST, "record"));
        method.setVisibility(JavaVisibility.PUBLIC);
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        return method;
    }
    
    
    
    
    /**
     * 添加方法
     */
    protected Method insertWithBLOBs(IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setName("insertWithBLOBs");
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.addParameter(new Parameter(MB, "record"));
        method.setVisibility(JavaVisibility.PUBLIC);
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        return method;
    }

    /**
     * 添加方法
     */
    protected Method insertSelectiveWithBLOBs(IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setName("insertSelectiveWithBLOBs");
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.addParameter(new Parameter(MB, "record"));
        method.setVisibility(JavaVisibility.PUBLIC);
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        return method;
    }

    /**
     * 添加方法
     */
    protected Method insertBatchWithBLOBs(IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setName("insertBatchWithBLOBs");
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.addParameter(new Parameter(MBLIST, "record"));
        method.setVisibility(JavaVisibility.PUBLIC);
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        return method;
    }

    /**
     * 添加方法
     */
    protected Method insertBatchSelectiveWithBLOBs(IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setName("insertBatchSelectiveWithBLOBs");
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.addParameter(new Parameter(MBLIST, "record"));
        method.setVisibility(JavaVisibility.PUBLIC);
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        return method;
    }
    

    /**
     * 添加方法
     */
    protected Method selectByExampleWithBLOBs(IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setName("selectByExampleWithBLOBs");
        method.setReturnType(new FullyQualifiedJavaType("List<MB>"));
        method.addParameter(new Parameter(E, "example"));
        method.setVisibility(JavaVisibility.PUBLIC);
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        return method;
    }

    /**
     * 添加方法
     */
    protected Method selectByExample(IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setName("selectByExample");
        method.setReturnType(new FullyQualifiedJavaType("List<M>"));
        method.addParameter(new Parameter(E, "example"));
        method.setVisibility(JavaVisibility.PUBLIC);
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        return method;
    }
    
    

    /**
     * 添加方法
     */
    protected Method selectByPrimaryKey(IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setName("selectByPrimaryKey");
        method.setReturnType(new FullyQualifiedJavaType("M"));
        method.addParameter(new Parameter(ID, "id"));
        method.setVisibility(JavaVisibility.PUBLIC);
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        return method;
    }
    
    
    protected Method selectByPrimaryKeyWithBLOBs(IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setName("selectByPrimaryKeyWithBLOBs");
        method.setReturnType(new FullyQualifiedJavaType("MB"));
        method.addParameter(new Parameter(ID, "id"));
        method.setVisibility(JavaVisibility.PUBLIC);
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        return method;
    }
    
    

    /**
     * 添加方法
     */
    protected Method updateByPrimaryKeySelective(IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setName("updateByPrimaryKeySelective");
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.addParameter(new Parameter(M, "record"));
        method.setVisibility(JavaVisibility.PUBLIC);
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        return method;
    }

    /**
     * 添加方法
     */
    protected Method updateByPrimaryKeyWithBLOBs(IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setName("updateByPrimaryKeyWithBLOBs");
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.addParameter(new Parameter(MB, "record"));
        method.setVisibility(JavaVisibility.PUBLIC);
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        return method;
    }

    /**
     * 添加方法
     */
    protected Method updateByPrimaryKey(IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setName("updateByPrimaryKey");
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.addParameter(new Parameter(M, "record"));
        method.setVisibility(JavaVisibility.PUBLIC);
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        return method;
    }

    /**
     * 添加方法
     */
    protected Method updateByExample(IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setName("updateByExample");
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.setVisibility(JavaVisibility.PUBLIC);

        Parameter record = new Parameter(M, "record");
        record.addAnnotation("@Param(\"record\")");
        method.addParameter(record);
        Parameter example = new Parameter(E, "example");
        example.addAnnotation("@Param(\"example\")");
        method.addParameter(example);

        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        return method;
    }

    /**
     * 添加方法
     */
    protected Method updateByExampleSelective(IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setName("updateByExampleSelective");
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.setVisibility(JavaVisibility.PUBLIC);

        Parameter record = new Parameter(M, "record");
        record.addAnnotation("@Param(\"record\")");
        method.addParameter(record);
        Parameter example = new Parameter(E, "example");
        example.addAnnotation("@Param(\"example\")");
        method.addParameter(example);

        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        return method;
    }

    /**
     * 添加方法
     */
    protected Method updateByExampleWithBLOBs(IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setName("updateByExampleWithBLOBs");
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.setVisibility(JavaVisibility.PUBLIC);

        Parameter record = new Parameter(MB, "record");
        record.addAnnotation("@Param(\"record\")");
        method.addParameter(record);
        Parameter example = new Parameter(E, "example");
        example.addAnnotation("@Param(\"example\")");
        method.addParameter(example);

        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        return method;
    }

	
	
    /**
     * 添加方法
     */
    protected Method selectByPageExample(IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setName("selectByPageExample");
        method.setReturnType(new FullyQualifiedJavaType("List<M>"));
        
        Parameter rowStart = new Parameter(new FullyQualifiedJavaType("int"), "startRow");
        rowStart.addAnnotation("@Param(\"startRow\")");
        method.addParameter(rowStart);
        Parameter rowEnd = new Parameter(new FullyQualifiedJavaType("int"), "endRow");
        rowEnd.addAnnotation("@Param(\"endRow\")");
        method.addParameter(rowEnd);
        Parameter example = new Parameter(E, "example");
        example.addAnnotation("@Param(\"example\")");
        method.addParameter(example);
        
        
        Parameter distinct = new Parameter(new FullyQualifiedJavaType("boolean"), "distinct");
        distinct.addAnnotation("@Param(\"distinct\")");
        method.addParameter(distinct);
        
        Parameter orderByClause = new Parameter(new FullyQualifiedJavaType("String"), "orderByClause");
        orderByClause.addAnnotation("@Param(\"orderByClause\")");
        method.addParameter(orderByClause);
        
        method.setVisibility(JavaVisibility.PUBLIC);
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        return method;
    }
    
    /**
     * 添加方法
     */
    protected Method selectByPageExampleWithBLOBs(IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setName("selectByPageExampleWithBLOBs");
        method.setReturnType(new FullyQualifiedJavaType("List<MB>"));
        
        Parameter rowStart = new Parameter(new FullyQualifiedJavaType("int"), "startRow");
        rowStart.addAnnotation("@Param(\"startRow\")");
        method.addParameter(rowStart);
        Parameter rowEnd = new Parameter(new FullyQualifiedJavaType("int"), "endRow");
        rowEnd.addAnnotation("@Param(\"endRow\")");
        method.addParameter(rowEnd);
        Parameter example = new Parameter(E, "example");
        example.addAnnotation("@Param(\"example\")");
        Parameter distinct = new Parameter(new FullyQualifiedJavaType("boolean"), "distinct");
        distinct.addAnnotation("@Param(\"distinct\")");
        method.addParameter(distinct);
        
        Parameter orderByClause = new Parameter(new FullyQualifiedJavaType("String"), "orderByClause");
        orderByClause.addAnnotation("@Param(\"orderByClause\")");
        method.addParameter(orderByClause);
        
        
        method.addParameter(example);
        method.setVisibility(JavaVisibility.PUBLIC);
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        return method;
    }
    
    
    /**
     * 添加方法
     */
    protected Method saveOrUpdateByExample(IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setName("saveOrUpdateByExample");
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());

        Parameter record = new Parameter(M, "record");
        record.addAnnotation("@Param(\"record\")");
        method.addParameter(record);
        Parameter example = new Parameter(E, "example");
        example.addAnnotation("@Param(\"example\")");
        method.addParameter(example);

        method.setVisibility(JavaVisibility.PUBLIC);
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        return method;
    }
    /**
    * 添加方法
    */
   protected Method saveOrUpdateByExampleWithBLOBs(IntrospectedTable introspectedTable) {
       Method method = new Method();
       method.setName("saveOrUpdateByExampleWithBLOBs");
       method.setReturnType(FullyQualifiedJavaType.getIntInstance());

       Parameter record = new Parameter(MB, "record");
       record.addAnnotation("@Param(\"record\")");
       method.addParameter(record);
       Parameter example = new Parameter(E, "example");
       example.addAnnotation("@Param(\"example\")");
       method.addParameter(example);

       method.setVisibility(JavaVisibility.PUBLIC);
       context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
       return method;
   }
	
   
   
   /*****
    * 
    */
   
   /**
    * 添加方法
    */
   protected Method updateBatchByPrimaryKey(IntrospectedTable introspectedTable) {
       Method method = new Method();
       method.setName("updateBatchByPrimaryKey");
       method.setReturnType(FullyQualifiedJavaType.getIntInstance());

       method.addParameter(new Parameter(MLIST, "record"));

       method.setVisibility(JavaVisibility.PUBLIC);
       context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
       return method;
   }
   /**
   * 添加方法
   */
  protected Method updateBatchByPrimaryKeyWithBLOBs(IntrospectedTable introspectedTable) {
      Method method = new Method();
      method.setName("updateBatchByPrimaryKeyWithBLOBs");
      method.setReturnType(FullyQualifiedJavaType.getIntInstance());

      method.addParameter(new Parameter(MBLIST, "record"));

      method.setVisibility(JavaVisibility.PUBLIC);
      context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
      return method;
  }
  
  /***
   * 
   */
  
  /**
   * 添加方法
   */
  protected Method saveOrUpdateByPrimaryKey(IntrospectedTable introspectedTable) {
      Method method = new Method();
      method.setName("saveOrUpdateByPrimaryKey");
      method.setReturnType(FullyQualifiedJavaType.getIntInstance());

      method.addParameter(new Parameter(M, "record"));

      method.setVisibility(JavaVisibility.PUBLIC);
      context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
      return method;
  }
  /**
  * 添加方法
  */
 protected Method saveOrUpdateByPrimaryKeyWithBLOBs(IntrospectedTable introspectedTable) {
     Method method = new Method();
     method.setName("saveOrUpdateByPrimaryKeyWithBLOBs");
     method.setReturnType(FullyQualifiedJavaType.getIntInstance());

     method.addParameter(new Parameter(MB, "record"));

     method.setVisibility(JavaVisibility.PUBLIC);
     context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
     return method;
 }
}
