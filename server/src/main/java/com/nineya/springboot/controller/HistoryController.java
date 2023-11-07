package com.nineya.springboot.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nineya.springboot.common.R;
import com.nineya.springboot.entity.History;
import com.nineya.springboot.entity.Result;
import com.nineya.springboot.service.HistoryService;
import com.nineya.springboot.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jacky_xi
 * @since 2023-05-12
 */
@RestController
@CrossOrigin
@ResponseBody
public class HistoryController {

    @Autowired
    HistoryService historyService;
    @Autowired
    ResultService resultService;
    @RequestMapping(value = "/history/getAll",produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public String getAll(HttpServletRequest req, HttpSession session) {

        List<History> HistoryList = (List<History>) historyService.getAll().getData();
        JSONObject result = new JSONObject();
        JSONArray array=new JSONArray(3);
        JSONObject data = new JSONObject();
        int i=0;
        for (History history:HistoryList){
            String date = history.getId().substring(6,history.getId().length());
            JSONObject item = new JSONObject();
            String Id = history.getId();
            Result resultEntity = resultService.getById(Id);
            item.put("id",Id);
            item.put("Date",date);
            item.put("Counter",resultEntity.getCounter());
            item.put("Crasher",resultEntity.getCrashnum());
            item.put("Fusionerror",resultEntity.getFusionerror());
            item.put("Time",history.getTime());
            item.put("status",history.getStatus());
            array.add(i,item);
            i++;
        }
        data.put("rows",array);
        result.put("status",0);
        result.put("msg", "");
        result.put("data", data);

        System.out.println(result.toJSONString());
        return result.toJSONString();
    }


    @PostMapping(value ="/history/delete",produces = "application/json;charset=UTF-8")
    public R DeleteById(HttpServletRequest req, HttpSession session) throws IOException, InterruptedException {
        String Id = req.getParameter("id");
        historyService.deleteById(Id);
        try {
            //使当前线程休眠4秒
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return R.success("删除成功");
    }

}
