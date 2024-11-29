package com.lb.subject.infra.basic.service.impl;

import com.lb.subject.infra.basic.entity.SubjectMapping;
import com.lb.subject.infra.basic.mapper.SubjectMappingDao;
import com.lb.subject.infra.basic.service.SubjectMappingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("subjectMappingService")
public class SubjectMappingServiceImpl implements SubjectMappingService {

    @Resource
    private SubjectMappingDao subjectMappingDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SubjectMapping queryById(int id) {
        return this.subjectMappingDao.queryById(id);
    }

    /**
     * 新增数据
     *
     * @param subjectMapping 实例对象
     * @return 实例对象
     */
    @Override
    public SubjectMapping insert(SubjectMapping subjectMapping) {
        this.subjectMappingDao.insert(subjectMapping);
        return subjectMapping;
    }

    /**
     * 修改数据
     *
     * @param subjectMapping 实例对象
     * @return 实例对象
     */
    @Override
    public int update(SubjectMapping subjectMapping) {
        return this.subjectMappingDao.update(subjectMapping);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(int id) {
        return this.subjectMappingDao.deleteById(id) > 0;
    }

    /**
     * 查询labelId
     *
     * @param subjectMapping 查询条件
     * @return 实例对象
     */
    @Override
    public List<SubjectMapping> queryLabelId(SubjectMapping subjectMapping) {
        return this.subjectMappingDao.queryDistinctLabelId(subjectMapping);
    }
}
