package com.nineya.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nineya.springboot.common.R;
import com.nineya.springboot.entity.History;
import com.nineya.springboot.mapper.HistoryMapper;
import com.nineya.springboot.service.HistoryService;
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
public class HistoryServiceImpl extends ServiceImpl<HistoryMapper, History> implements HistoryService {
    @Autowired
    public HistoryMapper historyMapper;


    @Override
    public R getAll() {
        QueryWrapper<History> queryWrapper = new QueryWrapper<>();
        List<History> TestTaskList = historyMapper.selectList(queryWrapper);
        return R.success(null, TestTaskList);
    }

    @Override
    public R insert(History history) {
        try {
            if (historyMapper.insert(history) > 0) {
                return R.success("插入成功");
            } else {
                return R.error("插入失败");
            }
        } catch (DuplicateKeyException e) {
            return R.fatal(e.getMessage());
        }
    }

    @Override
    public R deleteById(String id) {
        try {
            if (historyMapper.deleteById(id) > 0) {
                return R.success("删除成功");
            } else {
                return R.error("删除失败");
            }
        } catch (DuplicateKeyException e) {
            return R.fatal(e.getMessage());
        }
    }
}
