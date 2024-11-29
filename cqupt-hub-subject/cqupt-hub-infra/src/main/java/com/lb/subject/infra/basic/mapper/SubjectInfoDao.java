package com.lb.subject.infra.basic.mapper;

import com.lb.subject.infra.basic.entity.SubjectInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SubjectInfoDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SubjectInfo queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param subjectInfo 查询条件
     * @return 对象列表
     */
    List<SubjectInfo> queryAllByLimit(SubjectInfo subjectInfo);

    /**
     * 统计总行数
     *
     * @param subjectInfo 查询条件
     * @return 总行数
     */
    long count(SubjectInfo subjectInfo);

    /**
     * 新增数据
     *
     * @param subjectInfo 实例对象
     * @return 影响行数
     */
    int insert(SubjectInfo subjectInfo);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<SubjectInfo> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<SubjectInfo> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<SubjectInfo> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<SubjectInfo> entities);

    /**
     * 修改数据
     *
     * @param subjectInfo 实例对象
     * @return 影响行数
     */
    int update(SubjectInfo subjectInfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 根据条件查询满足条件的记录总数
     *
     * @param subjectInfo 查询条件，包含学科难度等信息
     * @param categoryId 类别ID，用于筛选符合条件的记录
     * @param labelId 标签ID，用于筛选符合条件的记录
     * @return 满足条件的记录总数
     */
    int countByCondition(@Param("subjectInfo") SubjectInfo subjectInfo,
                         @Param("categoryId") Long categoryId,
                         @Param("labelId") Long labelId);

    /**
     * 根据条件查询分页数据
     *
     * @param subjectInfo 查询条件，包含学科难度等信息
     * @param categoryId 类别ID，用于筛选符合条件的数据
     * @param labelId 标签ID，用于筛选符合条件的数据
     * @param start 分页开始位置
     * @param pageSize 每页显示的数据条数
     * @return 符合条件的分页数据列表
     */
    List<SubjectInfo> queryPage(@Param("subjectInfo") SubjectInfo subjectInfo,
                                @Param("categoryId") Long categoryId,
                                @Param("labelId")Long labelId,
                                @Param("start") int start,
                                @Param("pageSize") Integer pageSize);

}

