package com.nineya.springboot.service;

import com.nineya.springboot.common.R;
import com.nineya.springboot.entity.Result;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.sound.midi.SoundbankResource;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jacky_xi
 * @since 2023-05-16
 */
public interface ResultService extends IService<Result> {

     R getById(int id);
     R updateById(String Id,Result result);

     R insert(Result result);

     Result getLatest();

    R update(Result result);

//    R setResult(Result result);
}
