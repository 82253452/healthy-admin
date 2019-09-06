package com.zlsx.comzlsx.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.zlsx.comzlsx.mapper.ClassifyMapper;
import com.zlsx.comzlsx.domain.Classify;
@Service
public class ClassifyService{

    @Resource
    private ClassifyMapper classifyMapper;

    
    public int updateBatch(List<Classify> list) {
        return classifyMapper.updateBatch(list);
    }

    
    public int batchInsert(List<Classify> list) {
        return classifyMapper.batchInsert(list);
    }

    
    public int insertOrUpdate(Classify record) {
        return classifyMapper.insertOrUpdate(record);
    }

    
    public int insertOrUpdateSelective(Classify record) {
        return classifyMapper.insertOrUpdateSelective(record);
    }

}
