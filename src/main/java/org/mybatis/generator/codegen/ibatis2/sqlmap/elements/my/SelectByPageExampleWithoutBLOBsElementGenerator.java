package org.mybatis.generator.codegen.ibatis2.sqlmap.elements.my;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.ibatis2.sqlmap.elements.AbstractXmlElementGenerator;

public class SelectByPageExampleWithoutBLOBsElementGenerator  extends
AbstractXmlElementGenerator {

public SelectByPageExampleWithoutBLOBsElementGenerator() {
super();
}

@Override
public void addElements(XmlElement parentElement) {
XmlElement answer = new XmlElement("select"); //$NON-NLS-1$

answer.addAttribute(new Attribute("id", //$NON-NLS-1$
        introspectedTable.getSelectByPageExampleStatementId()));
answer.addAttribute(new Attribute(
        "resultMap", introspectedTable.getBaseResultMapId())); //$NON-NLS-1$
answer.addAttribute(new Attribute(
        "parameterClass", introspectedTable.getExampleType())); //$NON-NLS-1$

context.getCommentGenerator().addComment(answer);

answer.addElement(new TextElement("select")); //$NON-NLS-1$
XmlElement isEqualElement = new XmlElement("isEqual"); //$NON-NLS-1$
isEqualElement.addAttribute(new Attribute("property", "distinct")); //$NON-NLS-1$ //$NON-NLS-2$
isEqualElement.addAttribute(new Attribute("compareValue", "true")); //$NON-NLS-1$ //$NON-NLS-2$
isEqualElement.addElement(new TextElement("distinct")); //$NON-NLS-1$
XmlElement isParameterPresent = new XmlElement("isParameterPresent"); //$NON-NLS-1$
isParameterPresent.addElement(isEqualElement);
answer.addElement(isParameterPresent);

StringBuilder sb = new StringBuilder();
if (stringHasValue(introspectedTable
        .getSelectByPageExampleQueryId())) {
    sb.append('\'');
    sb.append(introspectedTable.getSelectByPageExampleQueryId());
    sb.append("' as QUERYID,"); //$NON-NLS-1$
    answer.addElement(new TextElement(sb.toString()));
}

answer.addElement(getBaseColumnListElement());

sb.setLength(0);
sb.append("from "); //$NON-NLS-1$
sb.append(introspectedTable
        .getAliasedFullyQualifiedTableNameAtRuntime());
answer.addElement(new TextElement(sb.toString()));

XmlElement isParameterPresenteElement = new XmlElement(
        "isParameterPresent"); //$NON-NLS-1$
answer.addElement(isParameterPresenteElement);

XmlElement includeElement = new XmlElement("include"); //$NON-NLS-1$
includeElement.addAttribute(new Attribute("refid", //$NON-NLS-1$
        introspectedTable.getIbatis2SqlMapNamespace()
                + "." + introspectedTable.getExampleWhereClauseId())); //$NON-NLS-1$
isParameterPresenteElement.addElement(includeElement);

XmlElement isNotNullElement = new XmlElement("isNotNull"); //$NON-NLS-1$
isNotNullElement
        .addAttribute(new Attribute("property", "orderByClause")); //$NON-NLS-1$ //$NON-NLS-2$
isNotNullElement
        .addElement(new TextElement("order by $orderByClause$")); //$NON-NLS-1$
isParameterPresenteElement.addElement(isNotNullElement);

if (context.getPlugins()
        .sqlMapSelectByPageExampleWithoutBLOBsElementGenerated(answer,
                introspectedTable)) {
    parentElement.addElement(answer);
}
}
}
