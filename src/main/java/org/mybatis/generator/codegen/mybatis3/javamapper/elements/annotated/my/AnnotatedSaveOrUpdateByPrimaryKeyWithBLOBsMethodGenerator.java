package org.mybatis.generator.codegen.mybatis3.javamapper.elements.annotated.my;

import static org.mybatis.generator.api.dom.OutputUtilities.javaIndent;

import java.util.Iterator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.my.SaveOrUpdateByPrimaryKeyWithBLOBsMethodGenerator;

public class AnnotatedSaveOrUpdateByPrimaryKeyWithBLOBsMethodGenerator extends SaveOrUpdateByPrimaryKeyWithBLOBsMethodGenerator {

    public AnnotatedSaveOrUpdateByPrimaryKeyWithBLOBsMethodGenerator() {
        super();
    }

    @Override
    public void addMapperAnnotations(Interface interfaze, Method method) {
        FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(introspectedTable.getMyBatis3SqlProviderType());

        StringBuilder sb = new StringBuilder();
        sb.append("@SelectProvider(type="); //$NON-NLS-1$
        sb.append(fqjt.getShortName());
        sb.append(".class, method=\""); //$NON-NLS-1$
        sb.append(introspectedTable.getSaveOrUpdateByPrimaryKeyWithBLOBsStatementId());
        sb.append("\")"); //$NON-NLS-1$

        method.addAnnotation(sb.toString());

        if (introspectedTable.isConstructorBased()) {
            method.addAnnotation("@ConstructorArgs({"); //$NON-NLS-1$
        } else {
            method.addAnnotation("@Results({"); //$NON-NLS-1$
        }

        Iterator<IntrospectedColumn> iterPk = introspectedTable.getPrimaryKeyColumns().iterator();
        Iterator<IntrospectedColumn> iterNonPk = introspectedTable.getNonPrimaryKeyColumns().iterator();
        while (iterPk.hasNext()) {
            IntrospectedColumn introspectedColumn = iterPk.next();
            sb.setLength(0);
            javaIndent(sb, 1);
            sb.append(getResultAnnotation(interfaze, introspectedColumn, true,
                    introspectedTable.isConstructorBased()));
            
            if (iterPk.hasNext() || iterNonPk.hasNext()) {
                sb.append(',');
            }

            method.addAnnotation(sb.toString());
        }

        while (iterNonPk.hasNext()) {
            IntrospectedColumn introspectedColumn = iterNonPk.next();
            sb.setLength(0);
            javaIndent(sb, 1);
            sb.append(getResultAnnotation(interfaze, introspectedColumn, false,
                    introspectedTable.isConstructorBased()));
            
            if (iterNonPk.hasNext()) {
                sb.append(',');
            }

            method.addAnnotation(sb.toString());
        }

        method.addAnnotation("})"); //$NON-NLS-1$
    }

    @Override
    public void addExtraImports(Interface interfaze) {
        interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.SelectProvider")); //$NON-NLS-1$
        interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.type.JdbcType")); //$NON-NLS-1$

        if (introspectedTable.isConstructorBased()) {
            interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Arg")); //$NON-NLS-1$
            interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.ConstructorArgs")); //$NON-NLS-1$
        } else {
            interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Result")); //$NON-NLS-1$
            interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Results")); //$NON-NLS-1$
        }
    }


}
