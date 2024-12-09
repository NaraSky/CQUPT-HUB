package com.lb.auth.infra.basic.mapper;

import com.lb.auth.infra.basic.entity.AuthRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuthRoleDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    AuthRole queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param authRole 查询条件
     * @return 对象列表
     */
    AuthRole queryAllByLimit(AuthRole authRole);

    /**
     * 统计总行数
     *
     * @param authRole 查询条件
     * @return 总行数
     */
    long count(AuthRole authRole);

    /**
     * 新增数据
     *
     * @param authRole 实例对象
     * @return 影响行数
     */
    int insert(AuthRole authRole);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<AuthRole> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<AuthRole> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<AuthRole> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<AuthRole> entities);

    /**
     * 修改数据
     *
     * @param authRole 实例对象
     * @return 影响行数
     */
    int update(AuthRole authRole);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);


    /**
     * 根据角色ID列表查询角色信息
     *
     * @param roleIdList 角色ID列表
     * @return 返回查询到的角色信息列表
     */
    List<AuthRole> queryByRoleList(@Param("list") List<Long> roleIdList);

}
