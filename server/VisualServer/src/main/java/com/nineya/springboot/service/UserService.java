package com.nineya.springboot.service;

import com.nineya.springboot.common.R;
import com.nineya.springboot.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @Author jackyxi
 * @Time : 2022/6/7 17:08
 */
public interface UserService extends IService<User> {

    R insertUser(User user);
}
