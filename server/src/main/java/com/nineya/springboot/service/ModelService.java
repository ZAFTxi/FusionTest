package com.nineya.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nineya.springboot.common.R;
import com.nineya.springboot.entity.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @Author jackyxi
 * @Time : 2022/6/7 17:08
 */
public interface ModelService extends IService<Model> {
    R insertModel(Model model);
    R uploadModel(MultipartFile modelFile, String modelName, String modelDesc) throws IOException;
    R getAllModels(String filterName);
    R deleteModel(Integer id);
    R modifyModel(Integer id, String name, String desc);
}
