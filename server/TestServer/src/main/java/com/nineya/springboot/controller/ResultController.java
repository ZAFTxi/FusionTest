package com.nineya.springboot.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nineya.springboot.entity.Result;
import com.nineya.springboot.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jacky_xi
 * @since 2023-05-12
 */
@RestController
public class ResultController {
    @Autowired
    ResultService resultService;
    @RequestMapping(value = "/result/getAll", method = RequestMethod.OPTIONS)
    public ResponseEntity handleOptionsRequest() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Allow", "GET, POST, PUT, DELETE, OPTIONS");
        System.out.printf("success!");
        return new ResponseEntity(headers, HttpStatus.OK);
    }
    @ResponseBody
    @RequestMapping(value = "/result/getAll",produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public String getAll(HttpServletRequest req, HttpSession session){

        JSONObject resultData = new JSONObject();
        JSONArray array=new JSONArray(1);
        JSONObject data = new JSONObject();
        JSONObject item = new JSONObject();
        Result result = (Result)resultService.getLatest();
        if(result != null){
            item.put("a",result.getId());
            item.put("b",result.getCounter());
            item.put("c",result.getCrashnum());
            item.put("d",result.getFusionerror());

        }else{
            item.put("a","N/A");
            item.put("b","0");
            item.put("c","0");
            item.put("d","0");
        }
        array.add(0,item);
        data.put("rows",array);

        resultData.put("status",0);
        resultData.put("msg", "");
        resultData.put("data", data);

        System.out.println(resultData.toJSONString());
        return resultData.toJSONString();
    }
}
