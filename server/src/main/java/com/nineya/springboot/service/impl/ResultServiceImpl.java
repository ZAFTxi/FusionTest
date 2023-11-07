package com.nineya.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nineya.springboot.common.R;
import com.nineya.springboot.entity.Result;
import com.nineya.springboot.mapper.ResultMapper;
import com.nineya.springboot.service.ResultService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @Author jackyxi
 * @Time : 2022/6/7 17:08
 */
@Service
public class ResultServiceImpl extends ServiceImpl<ResultMapper, Result> implements ResultService {
    @Autowired
    private ResultMapper resultMapper;
    @Override
    public R getById(int id) {
        QueryWrapper<Result> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("Id", id);
        List<Result> ResultList = resultMapper.selectList(queryWrapper);
        return R.success(null, ResultList);
    }

    @Override
    public R updateById(String Id, Result result) {
        if (result != null) {
            resultMapper.updateById(result);
            return R.success(null,result);
        } else {
            return R.error("Return is null!!");
        }



    }

    @Override
    public R insert(Result result) {
        try {
            if (resultMapper.insert(result) > 0) {
                return R.success("插入成功");
            } else {
                return R.error("插入失败");
            }
        } catch (DuplicateKeyException e) {
            return R.fatal(e.getMessage());
        }
    }


    @Override
    public Result getLatest() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        QueryWrapper<Result> queryWrapper = new QueryWrapper<>();
        queryWrapper.le("datetime", timestamp) // 添加查询条件
                .orderByDesc("datetime") // 添加排序条件
                .last("LIMIT 1"); // 添加分页条件
        return resultMapper.selectOne(queryWrapper);
    }

    @Override
    public R update(Result result) {
        if (result != null) {
            resultMapper.updateById(result);
            return R.success(null,result);
        } else {
            return R.error("Return is null!!");
        }
    }

//    @Override
//    public R setResult(Result result) {
//        resultMapper.update(result,)
//        return null;
//    }
}
