package com.lb.subject.infra.basic.service.impl;

import com.lb.subject.infra.basic.entity.SubjectInfo;
import com.lb.subject.infra.basic.mapper.SubjectInfoDao;
import com.lb.subject.infra.basic.service.SubjectInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("subjectInfoService")
public class SubjectInfoServiceImpl implements SubjectInfoService {

    @Resource
    private SubjectInfoDao subjectInfoDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SubjectInfo queryById(Long id) {
        return this.subjectInfoDao.queryById(id);
    }

    /**
     * 新增数据
     *
     * @param subjectInfo 实例对象
     * @return 实例对象
     */
    @Override
    public SubjectInfo insert(SubjectInfo subjectInfo) {
        this.subjectInfoDao.insert(subjectInfo);
        return subjectInfo;
    }

    /**
     * 修改数据
     *
     * @param subjectInfo 实例对象
     * @return 实例对象
     */
    @Override
    public SubjectInfo update(SubjectInfo subjectInfo) {
        this.subjectInfoDao.update(subjectInfo);
        return this.queryById(subjectInfo.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.subjectInfoDao.deleteById(id) > 0;
    }

    /**
     * 根据条件统计符合条件的记录数
     *
     * @param subjectInfo 包含查询条件的SubjectInfo对象
     * @param categoryId 类别ID，用于筛选符合条件的记录
     * @param labelId 标签ID，用于筛选符合条件的记录
     * @return 符合条件的记录数
     */
    @Override
    public int countByCondition(SubjectInfo subjectInfo, Long categoryId, Long labelId) {
        return this.subjectInfoDao.countByCondition(subjectInfo,categoryId,labelId);
    }

    /**
     * 根据条件分页查询学科信息
     *
     * @param subjectInfo 包含查询条件的学科信息对象
     * @param categoryId 类别ID，用于筛选符合条件的学科信息
     * @param labelId 标签ID，用于筛选符合条件的学科信息
     * @param start 分页查询的起始位置
     * @param pageSize 每页显示的记录数
     * @return 分页查询到的学科信息列表
     */
    @Override
    public List<SubjectInfo> queryPage(SubjectInfo subjectInfo, Long categoryId, Long labelId, int start, Integer pageSize) {
        return this.subjectInfoDao.queryPage(subjectInfo,categoryId,labelId,start,pageSize);
    }


}
