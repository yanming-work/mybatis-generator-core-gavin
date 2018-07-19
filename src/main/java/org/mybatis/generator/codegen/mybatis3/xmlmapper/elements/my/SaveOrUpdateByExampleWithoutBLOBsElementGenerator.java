package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.my;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

public class SaveOrUpdateByExampleWithoutBLOBsElementGenerator extends AbstractXmlElementGenerator {
	private boolean isSimple;
	public SaveOrUpdateByExampleWithoutBLOBsElementGenerator(boolean isSimple) {
		super();
		this.isSimple = isSimple;
	}

	@Override
	public void addElements(XmlElement parentElement) {
		XmlElement answer = new XmlElement("insert"); //$NON-NLS-1$

        answer.addAttribute(new Attribute("id", //$NON-NLS-1$
                introspectedTable.getSaveOrUpdateByExampleStatementId()));
        FullyQualifiedJavaType parameterType = introspectedTable.getRules()
                .calculateAllFieldsClass();
        // answer.addAttribute(new Attribute("parameterType", parameterType.getFullyQualifiedName()));
        answer.addAttribute(new Attribute("parameterType", "map")); 
        
        context.getCommentGenerator().addComment(answer);

        
        
        answer.addElement(new TextElement("<selectKey keyProperty=\"count\" resultType=\"long\" order=\"BEFORE\"> "));
        
        answer.addElement(new TextElement("select count(*) from "+introspectedTable
                .getAliasedFullyQualifiedTableNameAtRuntime()+"  "));
        
        answer.addElement(getUpdateByExampleIncludeElement());

        answer.addElement(new TextElement("</selectKey>"));
        answer.addElement(new TextElement("<if test=\"count > 0\">"));
        StringBuilder sb = new StringBuilder();
        
        sb.append("update "); //$NON-NLS-1$
        sb.append(introspectedTable
                .getAliasedFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));

        // set up for first column
        sb.setLength(0);
        sb.append("set "); //$NON-NLS-1$
        Iterator<IntrospectedColumn> iter;
        if (isSimple) {
            iter = ListUtilities.removeGeneratedAlwaysColumns(introspectedTable.getNonPrimaryKeyColumns()).iterator();
        } else {
            iter = ListUtilities.removeGeneratedAlwaysColumns(introspectedTable.getBaseColumns()).iterator();
        }
        while (iter!=null && iter.hasNext()) {
            IntrospectedColumn introspectedColumn = iter.next();
            
            sb.append(MyBatis3FormattingUtilities
                    .getAliasedEscapedColumnName(introspectedColumn));
            sb.append(" = "); //$NON-NLS-1$
            sb.append(MyBatis3FormattingUtilities.getParameterClause(
                    introspectedColumn, "record.")); //$NON-NLS-1$

            if (iter.hasNext()) {
                sb.append(',');
            }

            answer.addElement(new TextElement(sb.toString()));

            // set up for the next column
            if (iter.hasNext()) {
                sb.setLength(0);
                OutputUtilities.xmlIndent(sb, 1);
            }
        }
        

        answer.addElement(getUpdateByExampleIncludeElement());

        answer.addElement(new TextElement("</if>"));
        answer.addElement(new TextElement("<if test=\"count==0\">"));
        
        
        StringBuilder insertClause = new StringBuilder();

        insertClause.append("insert into "); //$NON-NLS-1$
        insertClause.append(introspectedTable
                .getFullyQualifiedTableNameAtRuntime());
        insertClause.append(" ("); //$NON-NLS-1$

        StringBuilder valuesClause = new StringBuilder();
        valuesClause.append("values ("); //$NON-NLS-1$

        List<String> valuesClauses = new ArrayList<String>();
        List<IntrospectedColumn> columns = ListUtilities.removeIdentityAndGeneratedAlwaysColumns(introspectedTable.getAllColumns());
        for (int i = 0; i < columns.size(); i++) {
            IntrospectedColumn introspectedColumn = columns.get(i);

            insertClause.append(MyBatis3FormattingUtilities
                    .getEscapedColumnName(introspectedColumn));
            valuesClause.append(MyBatis3FormattingUtilities
                    .getParameterClause(introspectedColumn,"record."));
            if (i + 1 < columns.size()) {
                insertClause.append(", "); //$NON-NLS-1$
                valuesClause.append(", "); //$NON-NLS-1$
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

        insertClause.append(')');
        answer.addElement(new TextElement(insertClause.toString()));

        valuesClause.append(')');
        valuesClauses.add(valuesClause.toString());

        for (String clause : valuesClauses) {
            answer.addElement(new TextElement(clause));
        }
        
        answer.addElement(new TextElement("</if>"));
        

		if (context.getPlugins().sqlMapSaveOrUpdateByExampleWithoutBLOBsElementGenerated(answer, introspectedTable)) {
			parentElement.addElement(answer);
		}
	}
}
