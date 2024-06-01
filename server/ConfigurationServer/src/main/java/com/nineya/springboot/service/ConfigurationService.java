package com.nineya.springboot.service;

import com.nineya.springboot.entity.Configuration;
import com.nineya.springboot.common.R;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @Author jackyxi
 * @Time : 2022/6/7 17:08
 */
public interface ConfigurationService extends IService<Configuration> {

    R insert(Configuration configuration);

    R getById(String id);
}
