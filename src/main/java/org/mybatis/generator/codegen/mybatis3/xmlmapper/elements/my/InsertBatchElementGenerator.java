/**
 * Copyright 2006-2016 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.my;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;
import org.mybatis.generator.config.GeneratedKey;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class InsertBatchElementGenerator extends AbstractXmlElementGenerator {


    public InsertBatchElementGenerator( ) {
        super();
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("insert"); //$NON-NLS-1$

        answer.addAttribute(new Attribute(
                "id", introspectedTable.getInsertBatchStatementId())); //$NON-NLS-1$


        answer.addAttribute(new Attribute("parameterType", "java.util.List"));

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
        

        GeneratedKey gk = introspectedTable.getGeneratedKey();
        if (gk != null) {
            IntrospectedColumn introspectedColumn = introspectedTable
                    .getColumn(gk.getColumn());
            // if the column is null, then it's a configuration error. The
            // warning has already been reported
            if (introspectedColumn != null) {
                if (gk.isJdbcStandard()) {
                    answer.addAttribute(new Attribute(
                            "useGeneratedKeys", "true")); //$NON-NLS-1$ //$NON-NLS-2$
                    answer.addAttribute(new Attribute(
                            "keyProperty", introspectedColumn.getJavaProperty())); //$NON-NLS-1$
                    answer.addAttribute(new Attribute(
                            "keyColumn", introspectedColumn.getActualColumnName())); //$NON-NLS-1$
                } else {
                    answer.addElement(getSelectKey(introspectedColumn, gk));
                }
            }
        }

        StringBuilder insertClause = new StringBuilder();
        StringBuilder valuesClause = new StringBuilder();

        insertClause.append("insert into "); //$NON-NLS-1$
        insertClause.append(introspectedTable
                .getFullyQualifiedTableNameAtRuntime());
        insertClause.append(" ("); //$NON-NLS-1$
        
        
        if("oracle".equals(dbName)){
        	valuesClause.append(" SELECT   "); 
        }else{
        	valuesClause.append(" ( "); //$NON-NLS-1$
        }
        
        
        List<String> valuesClauses = new ArrayList<String>();
        List<IntrospectedColumn> columns = ListUtilities.removeIdentityAndGeneratedAlwaysColumns(introspectedTable.getNonBLOBColumns());
        for (int i = 0; i < columns.size(); i++) {
            IntrospectedColumn introspectedColumn = columns.get(i);

            insertClause.append(MyBatis3FormattingUtilities
                    .getEscapedColumnName(introspectedColumn));
            valuesClause.append(MyBatis3FormattingUtilities
                    .getParameterClause(introspectedColumn, "item."));
            if (i + 1 < columns.size()) {
                insertClause.append(", ");
                valuesClause.append(", ");
            }

            if (valuesClause.length() > 80) {
                answer.addElement(new TextElement(insertClause.toString()));
                insertClause.setLength(0);
                OutputUtilities.xmlIndent(insertClause, 1);

                valuesClauses.add(valuesClause.toString());
                valuesClause.setLength(0);
                OutputUtilities.xmlIndent(valuesClause, 1);
            }
        }

        insertClause.append(" ) ");
        answer.addElement(new TextElement(insertClause.toString()));
        
        
        if("oracle".equals(dbName)){
        	answer.addElement(new TextElement(" SELECT  A.*  FROM (  "));
        }else{
        	answer.addElement(new TextElement(" values "));
        }
        
        if("oracle".equals(dbName)){
        	valuesClause.append(" FROM DUAL ");
        }else{
        	valuesClause.append(" ) ");
        }
        
        valuesClauses.add(valuesClause.toString());
      
        
          
			XmlElement innerForEach = new XmlElement("foreach");
	        innerForEach.addAttribute(new Attribute("collection", "list"));
	        innerForEach.addAttribute(new Attribute("item", "item"));
	        innerForEach.addAttribute(new Attribute("index", "index"));
		if("oracle".equals(dbName)){
	       
	        innerForEach.addAttribute(new Attribute("separator", "UNION ALL"));
		}else{
		    innerForEach.addAttribute(new Attribute("separator", ","));
		}
        


        for (String clause : valuesClauses) {
            innerForEach.addElement(new TextElement(clause));
        }
     
        answer.addElement(innerForEach);
        
        if("oracle".equals(dbName)){
        	 answer.addElement(new TextElement(" ) A "));
        }
        
        if (context.getPlugins().sqlMapInsertElementGenerated(answer,
                introspectedTable)) {
            parentElement.addElement(answer);
        }
    }
}
