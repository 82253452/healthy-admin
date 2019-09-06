package com.zlsx.comzlsx.mapper;

import com.zlsx.comzlsx.domain.Classify;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface ClassifyMapper extends Mapper<Classify> {
    int updateBatch(List<Classify> list);

    int batchInsert(@Param("list") List<Classify> list);

    int insertOrUpdate(Classify record);

    int insertOrUpdateSelective(Classify record);
}