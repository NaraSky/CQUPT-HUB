package com.lb.subject.infra.basic.service.impl;

import com.lb.subject.infra.basic.entity.SubjectRadio;
import com.lb.subject.infra.basic.mapper.SubjectRadioDao;
import com.lb.subject.infra.basic.service.SubjectRadioService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("subjectRadioService")
public class SubjectRadioServiceImpl implements SubjectRadioService {

    @Resource
    private SubjectRadioDao subjectRadioDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SubjectRadio queryById(Long id) {
        return this.subjectRadioDao.queryById(id);
    }

    /**
     * 新增数据
     *
     * @param subjectRadio 实例对象
     * @return 实例对象
     */
    @Override
    public SubjectRadio insert(SubjectRadio subjectRadio) {
        this.subjectRadioDao.insert(subjectRadio);
        return subjectRadio;
    }

    @Override
    public void batchInsert(List<SubjectRadio> subjectRadioList) {
        this.subjectRadioDao.insertBatch(subjectRadioList);
    }

    /**
     * 修改数据
     *
     * @param subjectRadio 实例对象
     * @return 实例对象
     */
    @Override
    public SubjectRadio update(SubjectRadio subjectRadio) {
        this.subjectRadioDao.update(subjectRadio);
        return this.queryById(subjectRadio.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.subjectRadioDao.deleteById(id) > 0;
    }
}
