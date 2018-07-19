package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.my;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

public class SelectByPageExampleWithoutBLOBsElementGenerator extends AbstractXmlElementGenerator {

	public SelectByPageExampleWithoutBLOBsElementGenerator() {
		super();
	}

	@Override
	public void addElements(XmlElement parentElement) {
		String fqjt = introspectedTable.getExampleType();

		XmlElement answer = new XmlElement("select"); //$NON-NLS-1$

		answer.addAttribute(new Attribute("id", //$NON-NLS-1$
				introspectedTable.getSelectByPageExampleStatementId()));
		answer.addAttribute(new Attribute("resultMap", introspectedTable.getBaseResultMapId())); //$NON-NLS-1$
		answer.addAttribute(new Attribute("parameterType", fqjt)); //$NON-NLS-1$

		context.getCommentGenerator().addComment(answer);


        String dbName=null;
        if(context.getJdbcConnectionConfiguration()!=null){
        	String driverClass=context.getJdbcConnectionConfiguration().getDriverClass();
        	if(driverClass!=null && driverClass.length()>0){
        		if(driverClass.indexOf("mysql")>=0){
        			dbName="mysql";
        		}else if(driverClass.indexOf("oracle")>=0){
        			dbName="oracle";
        		}
        	}
        }
        if("oracle".equals(dbName)){
	        answer.addElement(new TextElement("SELECT * FROM (SELECT tb.*, ROWNUM rn  FROM ( ")); 
        }
		
		answer.addElement(new TextElement("select")); //$NON-NLS-1$
		XmlElement ifElement = new XmlElement("if"); //$NON-NLS-1$
		ifElement.addAttribute(new Attribute("test", "distinct")); //$NON-NLS-1$ //$NON-NLS-2$
		ifElement.addElement(new TextElement("distinct")); //$NON-NLS-1$
		answer.addElement(ifElement);

		StringBuilder sb = new StringBuilder();
		if (stringHasValue(introspectedTable.getSelectByPageExampleQueryId())) {
			sb.append('\'');
			sb.append(introspectedTable.getSelectByPageExampleQueryId());
			sb.append("' as QUERYID,"); //$NON-NLS-1$
			answer.addElement(new TextElement(sb.toString()));
		}
		answer.addElement(getBaseColumnListElement());

		sb.setLength(0);
		sb.append("from "); //$NON-NLS-1$
		sb.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
		answer.addElement(new TextElement(sb.toString()));
		answer.addElement(getUpdateByExampleIncludeElement());

		ifElement = new XmlElement("if"); //$NON-NLS-1$
		ifElement.addAttribute(new Attribute("test", "orderByClause != null")); //$NON-NLS-1$ //$NON-NLS-2$
		ifElement.addElement(new TextElement("order by ${orderByClause}")); //$NON-NLS-1$
		answer.addElement(ifElement);

		  
        if("oracle".equals(dbName)){
        	  answer.addElement(new TextElement(" ) tb WHERE ROWNUM &lt;= ${endRow} ) WHERE rn &gt;= ${startRow}")); 
       }
         
        if("mysql".equals(dbName)){
        	answer.addElement(new TextElement(" limit ${startRow},${endRow} "));
        }
		
		
		if (context.getPlugins().sqlMapSelectByPageExampleWithoutBLOBsElementGenerated(answer, introspectedTable)) {
			parentElement.addElement(answer);
		}
	}
}
