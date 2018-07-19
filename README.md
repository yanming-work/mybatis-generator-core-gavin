# mybatis-generator-core-gavin
修改mybatis-generator-core 1.3.6 
MybatisGenerator

此代码是基于官方的MybatisGenerator(http://repo1.maven.org/maven2/org/mybatis/generator/mybatis-generator-core/)作了二次开发修改

MyBatis代码生成器，生成Model、Dao、*Mapper.xml等

借鉴Mybatis官网下载地址如下： http://repo1.maven.org/maven2/org/mybatis/generator/mybatis-generator-core/

源码修改记录:

1、Mapper.xml空格改成四个空格
修改详情：

org.mybatis.generator.api.dom.OutputUtilities

mybatis-generator 里面我觉得首先最应该改的就是 OutputUtilities 这个类，它里面有个 xmlIndent 方法是用来控制生成的 xml 文件中空格的缩进，默认是两个空格
但四个空格对于我们来说已经深入骨髓了，所以必须改。 在 sb.append( "  " ) 里面增加两个空格就可以了。 

2、修改dao包下的名称，由原来的XXXMapper改成XXXDao
修改详情：
org.mybatis.generator.api.IntrospectedTable

calculateJavaClientAttributes方法(大概820行)
 sb.append("Mapper");注释掉改成 sb.append("Dao");
 
 
 3、修改Mybatis的Model生成JavaDoc注释内容：(这个暂时没有修改)
1)generatorConfig.xml配置里面设置成：<property name="suppressAllComments" value="false"/>
代码修改详情：
org.mybatis.generator.internal.DefaultCommentGenerator里面的 添加注释代码去掉，改成

------------------------------ code start ----------------------------------------
field.addJavaDocLine("/**");
field.addJavaDocLine(" * @Fields "+field.getName()+" "+introspectedColumn.getRemarks());
field.addJavaDocLine(" */");

------------------------------ code end ---------------------------------------------




4、org.mybatis.generator.internal.DefaultCommentGenerator修改XXXDao.java(XXXMapper.java)的接口方法的Javadoc注释
搜索org.mybatis.generator.internal.DefaultCommentGenerator如下方法，并注释方法里面内容，
public void addGeneralMethodComment(Method method,
            IntrospectedTable introspectedTable)

改成：

method.addJavaDocLine("/**");
method.addJavaDocLine(" * @Title " + method.getName());
for (Parameter parameter : method.getParameters()) {
     method.addJavaDocLine(" * @param " + parameter.getName());
}
String returnType = method.getReturnType().toString();
returnType = returnType.lastIndexOf(".") != -1 ? returnType.substring(returnType.lastIndexOf(".") + 1) : returnType;
method.addJavaDocLine(" * @return " + returnType);
method.addJavaDocLine(" */");



5、注释掉Model里面Getter和Setter方法的注释，根据需要自己加上配置 (这个暂时没有修改)
搜索org.mybatis.generator.internal.DefaultCommentGenerator如下方法：
public void addGetterComment(Method method,
            IntrospectedTable introspectedTable,
            IntrospectedColumn introspectedColumn)

public void addSetterComment(Method method,
            IntrospectedTable introspectedTable,
            IntrospectedColumn introspectedColumn)



6、注释掉Mapper.xml里面的<!-- 注释 -->,实际使用未用到(这个暂时没有修改)
搜索org.mybatis.generator.internal.DefaultCommentGenerator如下方法：
public void addComment(XmlElement xmlElement) 


7、XML里面每个SQL增加一个换行
org.mybatis.generator.api.dom.xml.XmlElement里面getFormattedContent里面加上：
if(indentLevel == 1) {
    //每个insert/update/select之间插入一个空行
    OutputUtilities.newLine(sb);
}


8、修改mybatis的mapper.xml文件里面insert和update不根据字段判断的非动态SQL
org.mybatis.generator.codegen.mybatis3.xmlmapper.XMLMapperGenerator
里面的getSqlMapElement方法  (这个暂时没有修改)


注释掉：
addInsertElement(answer);
addUpdateByPrimaryKeyWithoutBLOBsElement(answer);

对应的是mapper.xml里面的
<insert id="insert" parameterType="xxx" ></insert>
<update id="updateByPrimaryKey" parameterType="xxx" ></update>


9、添加给Example类序列化的方法 ，org.mybatis.generator.plugins.SerializablePlugin 类加上 modelExampleClassGenerated 方法

 public boolean modelExampleClassGenerated(TopLevelClass topLevelClass,IntrospectedTable introspectedTable){  
        makeSerializable(topLevelClass, introspectedTable);  
        return true;  
    }  
不过问题还没有完，Example里还有内部类，如果不序列化还是会报错。发现另一个插件类，也是包里自带的。发现了宝藏，这里竟然有对内部类的操作。org.mybatis.generator.plugins.CaseInsensitiveLikePlugin把上面的方法改成
/** 
     * 添加给Example类序列化的方法 
     * @param topLevelClass 
     * @param introspectedTable 
     * @return 
     */  
    @Override  
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass,IntrospectedTable introspectedTable){  
        makeSerializable(topLevelClass, introspectedTable);  
  
        for (InnerClass innerClass : topLevelClass.getInnerClasses()) {  
            if ("GeneratedCriteria".equals(innerClass.getType().getShortName())) { //$NON-NLS-1$  
                innerClass.addSuperInterface(serializable);  
            }  
            if ("Criteria".equals(innerClass.getType().getShortName())) { //$NON-NLS-1$  
                innerClass.addSuperInterface(serializable);  
            }  
            if ("Criterion".equals(innerClass.getType().getShortName())) { //$NON-NLS-1$  
                innerClass.addSuperInterface(serializable);  
            }  
        }  
  
        return true;  
    }  
  





10、增加dao的方法，例如增加批量写入，分页查询等
修改org.mybatis.generator.api.IntrospectedTable 类的 calculateXmlAttributes() 方法
/**
         * 自定义
         */
        setInsertBatchStatementId("insertBatch");
        setInsertBatchSelectiveStatementId("insertBatchSelective");
        selectByPageStatementId("selectByPage");


类后面，新增方法
 /**
     * 批量插入
     *
     * @param
     */
    public String getInsertBatchStatementId() {
        return internalAttributes
                .get(InternalAttribute.ATTR_INSERT_BATCH_STATEMENT_ID);
    }

    public void setInsertBatchStatementId(String s) {
        internalAttributes.put(
                InternalAttribute.ATTR_INSERT_BATCH_STATEMENT_ID, s);
    }

    public String getInsertBatchSelectiveStatementId() {
        return internalAttributes
                .get(InternalAttribute.ATTR_INSERT_BATCH_SELECTIVE_STATEMENT_ID);
    }

    public void setInsertBatchSelectiveStatementId(String s) {
        internalAttributes.put(
                InternalAttribute.ATTR_INSERT_BATCH_SELECTIVE_STATEMENT_ID, s);
    }
      public String getSelectByPageExampleStatementId() {
        return internalAttributes
                .get(InternalAttribute.ATTR_SELECT_BY_PAGE_EXAMPLE_STATEMENT_ID);
    }

    public void setSelectByPageExampleStatementId(String s) {
        internalAttributes.put(
                InternalAttribute.ATTR_SELECT_BY_PAGE_EXAMPLE_STATEMENT_ID, s);
    }
    
    修改 protected enum InternalAttribute{}，增加，ATTR_INSERT_BATCH_STATEMENT_ID，ATTR_INSERT_BATCH_SELECTIVE_STATEMENT_ID，ATTR_SELECT_BY_PAGE_EXAMPLE_STATEMENT_ID
    
    
新增类 org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.my.InsertBatchElementGenerator ；
 	org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.my.InsertBatchSelectiveElementGenerator ；
	org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.my.SelectByPageExampleGenerator；

修改org.mybatis.generator.codegen.mybatis3.xmlmapper.XMLMapperGenerator类的 getSqlMapElement()方法,增加
		addInsertBatchElement(answer);
        addInsertBatchSelectiveElement(answer);
        addSelectByPageExample(answer);
     
    修改 org.mybatis.generator.internal.rules.Rules类，增加
       /**
     * 自定义
     */
    
     boolean generateSelectByPageExample();   
     
     修改org.mybatis.generator.internal.DefaultCommentGenerator类的addGeneralMethodComment()方法，增加
     


源码修改之后，使用说明


<property name="suppressAllComments" value="false"/>配置生成注释，默认注释已经修改 
javaModelGenerator里面targetProject可以配置非src，以便于区分源码和业务代码，如：targetProject="target" 
table里面配置集成的父类可用，该属性也可以再javaModelGenerator里面配置公用 
<property name="rootClass" value="xxx.BaseModel"/> 
table里面配置插入返回主键配置<generatedKey column="id" sqlStatement="MySql" identity="true"/> 
=======
# mybatis-generator-core-gavin
修改mybatis-generator-core 1.3.6 
MybatisGenerator

此代码是基于官方的MybatisGenerator(http://repo1.maven.org/maven2/org/mybatis/generator/mybatis-generator-core/)作了二次开发修改

MyBatis代码生成器，生成Model、Dao、*Mapper.xml等

借鉴Mybatis官网下载地址如下： http://repo1.maven.org/maven2/org/mybatis/generator/mybatis-generator-core/

源码修改记录:

1、Mapper.xml空格改成四个空格
修改详情：

org.mybatis.generator.api.dom.OutputUtilities

mybatis-generator 里面我觉得首先最应该改的就是 OutputUtilities 这个类，它里面有个 xmlIndent 方法是用来控制生成的 xml 文件中空格的缩进，默认是两个空格
但四个空格对于我们来说已经深入骨髓了，所以必须改。 在 sb.append( "  " ) 里面增加两个空格就可以了。 

2、修改dao包下的名称，由原来的XXXMapper改成XXXDao
修改详情：
org.mybatis.generator.api.IntrospectedTable

calculateJavaClientAttributes方法(大概820行)
 sb.append("Mapper");注释掉改成 sb.append("Dao");
 
 
 3、修改Mybatis的Model生成JavaDoc注释内容：(这个暂时没有修改)
1)generatorConfig.xml配置里面设置成：<property name="suppressAllComments" value="false"/>
代码修改详情：
org.mybatis.generator.internal.DefaultCommentGenerator里面的 添加注释代码去掉，改成

------------------------------ code start ----------------------------------------
field.addJavaDocLine("/**");
field.addJavaDocLine(" * @Fields "+field.getName()+" "+introspectedColumn.getRemarks());
field.addJavaDocLine(" */");

------------------------------ code end ---------------------------------------------




4、org.mybatis.generator.internal.DefaultCommentGenerator修改XXXDao.java(XXXMapper.java)的接口方法的Javadoc注释
搜索org.mybatis.generator.internal.DefaultCommentGenerator如下方法，并注释方法里面内容，
public void addGeneralMethodComment(Method method,
            IntrospectedTable introspectedTable)

改成：

method.addJavaDocLine("/**");
method.addJavaDocLine(" * @Title " + method.getName());
for (Parameter parameter : method.getParameters()) {
     method.addJavaDocLine(" * @param " + parameter.getName());
}
String returnType = method.getReturnType().toString();
returnType = returnType.lastIndexOf(".") != -1 ? returnType.substring(returnType.lastIndexOf(".") + 1) : returnType;
method.addJavaDocLine(" * @return " + returnType);
method.addJavaDocLine(" */");



5、注释掉Model里面Getter和Setter方法的注释，根据需要自己加上配置 (这个暂时没有修改)
搜索org.mybatis.generator.internal.DefaultCommentGenerator如下方法：
public void addGetterComment(Method method,
            IntrospectedTable introspectedTable,
            IntrospectedColumn introspectedColumn)

public void addSetterComment(Method method,
            IntrospectedTable introspectedTable,
            IntrospectedColumn introspectedColumn)



6、注释掉Mapper.xml里面的<!-- 注释 -->,实际使用未用到(这个暂时没有修改)
搜索org.mybatis.generator.internal.DefaultCommentGenerator如下方法：
public void addComment(XmlElement xmlElement) 


7、XML里面每个SQL增加一个换行
org.mybatis.generator.api.dom.xml.XmlElement里面getFormattedContent里面加上：
if(indentLevel == 1) {
    //每个insert/update/select之间插入一个空行
    OutputUtilities.newLine(sb);
}


8、修改mybatis的mapper.xml文件里面insert和update不根据字段判断的非动态SQL
org.mybatis.generator.codegen.mybatis3.xmlmapper.XMLMapperGenerator
里面的getSqlMapElement方法  (这个暂时没有修改)


注释掉：
addInsertElement(answer);
addUpdateByPrimaryKeyWithoutBLOBsElement(answer);

对应的是mapper.xml里面的
<insert id="insert" parameterType="xxx" ></insert>
<update id="updateByPrimaryKey" parameterType="xxx" ></update>


9、添加给Example类序列化的方法 ，org.mybatis.generator.plugins.SerializablePlugin 类加上 modelExampleClassGenerated 方法

 public boolean modelExampleClassGenerated(TopLevelClass topLevelClass,IntrospectedTable introspectedTable){  
        makeSerializable(topLevelClass, introspectedTable);  
        return true;  
    }  
不过问题还没有完，Example里还有内部类，如果不序列化还是会报错。发现另一个插件类，也是包里自带的。发现了宝藏，这里竟然有对内部类的操作。org.mybatis.generator.plugins.CaseInsensitiveLikePlugin把上面的方法改成
/** 
     * 添加给Example类序列化的方法 
     * @param topLevelClass 
     * @param introspectedTable 
     * @return 
     */  
    @Override  
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass,IntrospectedTable introspectedTable){  
        makeSerializable(topLevelClass, introspectedTable);  
  
        for (InnerClass innerClass : topLevelClass.getInnerClasses()) {  
            if ("GeneratedCriteria".equals(innerClass.getType().getShortName())) { //$NON-NLS-1$  
                innerClass.addSuperInterface(serializable);  
            }  
            if ("Criteria".equals(innerClass.getType().getShortName())) { //$NON-NLS-1$  
                innerClass.addSuperInterface(serializable);  
            }  
            if ("Criterion".equals(innerClass.getType().getShortName())) { //$NON-NLS-1$  
                innerClass.addSuperInterface(serializable);  
            }  
        }  
  
        return true;  
    }  
  





10、增加dao的方法，例如增加批量写入，分页查询等
修改org.mybatis.generator.api.IntrospectedTable 类的 calculateXmlAttributes() 方法
/**
         * 自定义
         */
        setInsertBatchStatementId("insertBatch");
        setInsertBatchSelectiveStatementId("insertBatchSelective");
        selectByPageStatementId("selectByPage");


类后面，新增方法
 /**
     * 批量插入
     *
     * @param
     */
    public String getInsertBatchStatementId() {
        return internalAttributes
                .get(InternalAttribute.ATTR_INSERT_BATCH_STATEMENT_ID);
    }

    public void setInsertBatchStatementId(String s) {
        internalAttributes.put(
                InternalAttribute.ATTR_INSERT_BATCH_STATEMENT_ID, s);
    }

    public String getInsertBatchSelectiveStatementId() {
        return internalAttributes
                .get(InternalAttribute.ATTR_INSERT_BATCH_SELECTIVE_STATEMENT_ID);
    }

    public void setInsertBatchSelectiveStatementId(String s) {
        internalAttributes.put(
                InternalAttribute.ATTR_INSERT_BATCH_SELECTIVE_STATEMENT_ID, s);
    }
      public String getSelectByPageExampleStatementId() {
        return internalAttributes
                .get(InternalAttribute.ATTR_SELECT_BY_PAGE_EXAMPLE_STATEMENT_ID);
    }

    public void setSelectByPageExampleStatementId(String s) {
        internalAttributes.put(
                InternalAttribute.ATTR_SELECT_BY_PAGE_EXAMPLE_STATEMENT_ID, s);
    }
    
    修改 protected enum InternalAttribute{}，增加，ATTR_INSERT_BATCH_STATEMENT_ID，ATTR_INSERT_BATCH_SELECTIVE_STATEMENT_ID，ATTR_SELECT_BY_PAGE_EXAMPLE_STATEMENT_ID
    
    
新增类 org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.my.InsertBatchElementGenerator ；
 	org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.my.InsertBatchSelectiveElementGenerator ；
	org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.my.SelectByPageExampleGenerator；

修改org.mybatis.generator.codegen.mybatis3.xmlmapper.XMLMapperGenerator类的 getSqlMapElement()方法,增加
		addInsertBatchElement(answer);
        addInsertBatchSelectiveElement(answer);
        addSelectByPageExample(answer);
     
    修改 org.mybatis.generator.internal.rules.Rules类，增加
       /**
     * 自定义
     */
    
     boolean generateSelectByPageExample();   
     
     修改org.mybatis.generator.internal.DefaultCommentGenerator类的addGeneralMethodComment()方法，增加
     


源码修改之后，使用说明


<property name="suppressAllComments" value="false"/>配置生成注释，默认注释已经修改 
javaModelGenerator里面targetProject可以配置非src，以便于区分源码和业务代码，如：targetProject="target" 
table里面配置集成的父类可用，该属性也可以再javaModelGenerator里面配置公用 
<property name="rootClass" value="xxx.BaseModel"/> 
table里面配置插入返回主键配置<generatedKey column="id" sqlStatement="MySql" identity="true"/> 
