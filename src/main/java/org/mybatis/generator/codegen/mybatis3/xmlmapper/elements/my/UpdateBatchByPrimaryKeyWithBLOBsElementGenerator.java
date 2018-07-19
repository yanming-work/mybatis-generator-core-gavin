package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.my;

import java.util.Iterator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

public class UpdateBatchByPrimaryKeyWithBLOBsElementGenerator extends AbstractXmlElementGenerator  {

	public UpdateBatchByPrimaryKeyWithBLOBsElementGenerator( ) {
        super();
    }
	@Override
	public void addElements(XmlElement parentElement) {
		
		XmlElement answer = new XmlElement("update"); //$NON-NLS-1$
        answer.addAttribute(new Attribute("id", //$NON-NLS-1$
                introspectedTable.getUpdateBatchByPrimaryKeyWithBLOBsStatementId()));
        //FullyQualifiedJavaType parameterType = introspectedTable.getRules().calculateAllFieldsClass();
        // answer.addAttribute(new Attribute("parameterType", parameterType.getFullyQualifiedName()));
        answer.addAttribute(new Attribute("parameterType", "map")); 
        
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
        answer.addElement(new TextElement("<!-- 批量更新第一种方法，通过接收传进来的参数list进行循环着组装sql -->  "));
        
        answer.addElement(new TextElement("<!-- 接收list参数，循环着组装sql语句，注意for循环的写法  "));
        answer.addElement(new TextElement("separator=\";\" 代表着每次循环完，在sql后面放一个分号  "));
        answer.addElement(new TextElement("item=\"cus\" 循环List的每条的结果集  "));
        answer.addElement(new TextElement("collection=\"list\" list 即为 map传过来的参数key --> "));
        
        
        if("oracle".equals(dbName)){
        	 answer.addElement(new TextElement("<foreach collection=\"list\" index=\"index\" item=\"item\"  open=\"begin\" close=\";end;\"  separator=\";\"> ")); 
             
        }else{
        	 answer.addElement(new TextElement("<foreach collection=\"list\" index=\"index\" item=\"item\" separator=\";\"> ")); 
             
        }
        
        StringBuilder sb = new StringBuilder();
        
        sb.append("update "); //$NON-NLS-1$
        sb.append(introspectedTable
                .getAliasedFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));

        // set up for first column
        sb.setLength(0);
        sb.append("set "); //$NON-NLS-1$

        Iterator<IntrospectedColumn> iter = ListUtilities.removeGeneratedAlwaysColumns(introspectedTable
                .getNonPrimaryKeyColumns()).iterator();
        while (iter!=null && iter.hasNext()) {
            IntrospectedColumn introspectedColumn = iter.next();

            sb.append(MyBatis3FormattingUtilities
                    .getEscapedColumnName(introspectedColumn));
            sb.append(" = "); //$NON-NLS-1$
            sb.append(MyBatis3FormattingUtilities
                    .getParameterClause(introspectedColumn, "item."));

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

        /**
		 *主键 where条件
		 */
		        
        boolean and = false;
        for (IntrospectedColumn introspectedColumn : introspectedTable
                .getPrimaryKeyColumns()) {
            sb.setLength(0);
            if (and) {
                sb.append("  and "); //$NON-NLS-1$
            } else {
                sb.append(" where "); //$NON-NLS-1$
                and = true;
            }

            sb.append(MyBatis3FormattingUtilities
                    .getAliasedEscapedColumnName(introspectedColumn));
            sb.append(" = "); //$NON-NLS-1$
            sb.append(MyBatis3FormattingUtilities
                    .getParameterClause(introspectedColumn, "item."));
            answer.addElement(new TextElement(sb.toString()));
        }

        
        
        answer.addElement(new TextElement(" </foreach>  "));

        

		if (context.getPlugins().sqlMapUpdateBatchByPrimaryKeyWithBLOBsElementGenerated(answer, introspectedTable)) {
			parentElement.addElement(answer);
		}
	}
}
