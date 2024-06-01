package com.nineya.springboot.service.impl;

import com.nineya.springboot.common.R;
import com.nineya.springboot.entity.User;
import com.nineya.springboot.mapper.UserMapper;
import com.nineya.springboot.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @Author jackyxi
 * @Time : 2022/6/7 17:08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public R insertUser(User user) {
        try {
            if (userMapper.insert(user) > 0) {
                return R.success("插入成功");
            } else {
                return R.error("插入失败");
            }
        } catch (DuplicateKeyException e) {
            return R.fatal(e.getMessage());
        }
    }
}
