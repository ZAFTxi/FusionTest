package com.nineya.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nineya.springboot.common.R;
import com.nineya.springboot.entity.Configuration;
import com.nineya.springboot.entity.Result;
import com.nineya.springboot.mapper.ConfigurationMapper;
import com.nineya.springboot.service.ConfigurationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

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
public class ConfigurationServiceImpl extends ServiceImpl<ConfigurationMapper, Configuration> implements ConfigurationService {
    @Autowired
    ConfigurationMapper configurationMapper;
    @Override
    public R insert(Configuration configuration) {
        try {
            if (configurationMapper.insert(configuration) > 0) {
                return R.success("插入成功");
            } else {
                return R.error("插入失败");
            }
        } catch (DuplicateKeyException e) {
            return R.fatal(e.getMessage());
        }
    }

    @Override
    public R getById(String id) {
        QueryWrapper<Configuration> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("Id", id);
        Configuration configuration = configurationMapper.selectOne(queryWrapper);
        return R.success(null, configuration);

    }


}
