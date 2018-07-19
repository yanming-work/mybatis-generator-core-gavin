package org.mybatis.generator.plugins.my;

import java.io.Serializable;
import java.util.List;

/**
 * 通用IMapper<M, E, ID>
 * M:实体类
 * E:Example
 * ID:主键的变量类型
 *
 */
public interface BaseNoKeyMapper<M,MB, E> {
    /**
     * 查询数量
     *
     * @param example 条件对象
     */
    long countByExample(E example);

    /**
     * 根据条件删除
     *
     * @param example 条件对象
     */
    int deleteByExample(E example);

    

    /**
     * 添加对象所有字段
     *
     * @param record 插入字段对象(必须含ID）
     */
    int insert(M record);

    /**
     * 添加对象对应字段
     *
     * @param record 插入字段对象(必须含ID）
     */
    int insertSelective(M record);

    /**
     * 添加对象集合对应字段
     *
     * @param record 插入字段对象(必须含ID）
     */
    int insertBatchSelective(List<M> record);

    /**
     * 添加对象集合对应字段
     *
     * @param record 插入字段对象(必须含ID）
     */
    int insertBatch(List<M> record);
    
    


    /**
     * 添加对象所有字段
     *
     * @param record 插入字段对象(必须含ID）
     */
    int insertWithBLOBs(MB record);

    /**
     * 添加对象对应字段
     *
     * @param record 插入字段对象(必须含ID）
     */
    int insertSelectiveWithBLOBs(MB record);

    /**
     * 添加对象集合对应字段
     *
     * @param record 插入字段对象(必须含ID）
     */
    int insertBatchSelectiveWithBLOBs(List<MB> record);

    /**
     * 添加对象集合对应字段
     *
     * @param record 插入字段对象(必须含ID）
     */
    int insertBatchWithBLOBs(List<MB> record);


    /**
     * 根据条件查询（二进制大对象）
     *
     * @param example 条件对象
     */
    List<M> selectByExample(E example);


    /**
     * 根据ID修改对应字段
     *
     * @param record 修改字段对象(必须含ID）
     */
    int updateByPrimaryKeySelective(M record);

    /**
     * 根据ID修改所有字段(必须含ID）
     *
     * @param record 修改字段对象(必须含ID）
     */
    int updateByPrimaryKey(M record);

    /**
     * 根据ID修改字段（包含二进制大对象）
     *
     * @param record 修改字段对象(必须含ID）
     */
    int updateByPrimaryKeyWithBLOBs(MB record);

    /**
     * 根据条件查询（包含二进制大对象）
     *
     * @param example 条件对象
     */
    List<MB> selectByExampleWithBLOBs(E example);

    /**
     * 根据条件修改字段 （包含大字段）
     * @param record 修改字段对象(必须含ID）
     * @param example 条件对象
     * @return
     */
      // int updateByExampleWithBLOBs(@Param("record") MB record, @Param("example") E example);

    /**
     * 根据条件修改对应字段
     * @param record 修改字段对象 (JOPO)
     * @param example 条件对象
     * @return
     */
     //  int updateByExampleSelective(@Param("record") M record, @Param("example") E example);

    /**
     * 根据条件修改所有字段
     * @param record 修改字段对象 (JOPO)
     * @param example 条件对象
     * @return
     */
     // int updateByExample(@Param("record") M record, @Param("example") E example);
    
    List<M> selectByPageExample(int startRow,int endRow,E example,boolean distinct,String orderByClause);
    
    List<MB> selectByPageExampleWithBLOBs(int startRow,int endRow,E example,boolean distinct,String orderByClause);


    int saveOrUpdateByExample(M record,E example);
    
    int saveOrUpdateByExampleWithBLOBs(MB record,E example);


}
