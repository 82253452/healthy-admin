package com.zlsx.comzlsx.controller;

import com.zlsx.comzlsx.domain.Classify;
import com.zlsx.comzlsx.mapper.ClassifyMapper;
import com.zlsx.comzlsx.util.common.ForeseenException;
import com.zlsx.comzlsx.util.common.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/classify")
public class ClassifyController {
    @Resource
    private ClassifyMapper classifyMapper;


    @GetMapping("/list")
    public Result list(Classify classify) throws ForeseenException {
        List<Classify> classifies = classifyMapper.selectByExample(classify);
        return Result.ok(classifies);
    }

    @GetMapping("/delete/{id}")
    public Result deleteData(@PathVariable Integer id) throws ForeseenException {
        classifyMapper.deleteByPrimaryKey(id);
        return Result.ok();
    }

    @PostMapping("/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody Classify classify) throws ForeseenException {
        classifyMapper.insertOrUpdateSelective(classify);
        return Result.ok();
    }

}
