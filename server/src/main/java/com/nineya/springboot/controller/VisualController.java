package com.nineya.springboot.controller;
import java.util.*;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nineya.springboot.common.R;
import com.nineya.springboot.entity.History;
import com.nineya.springboot.entity.Result;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Random;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jacky_xi
 * @since 2023-05-12
 */

@RestController
@ResponseBody
@CrossOrigin

public class VisualController {
    @RequestMapping(value = "/visual/getdata",produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public String getVisualData(HttpServletRequest req, HttpSession session){

        JSONObject result2 = new JSONObject();
        JSONObject data2 = new JSONObject();

        double[] s1 = {21.5, 12.2, 12.1, 10.6, 11.6, 10.9, 11.7, 14.7, 11.5, 14.2, 14.3, 11.5, 11.9, 11.8, 12.5, 12.4, 12.6, 12.4, 14.4, 12.8, 14.4, 14.6, 14.6, 15.0, 15.6, 15.7, 14.5, 14.5, 14.6, 15.1, 14.9, 16.8, 16.6, 15.0, 15.1, 16.8, 16.9, 17.2, 17.0, 16.9, 17.1, 17.0, 8.7, 16.5, 18.0, 16.8, 16.9, 12.0, 12.7, 10.6, 11.0, 9.4};
        double[] s2 = {21.2, 21.1, 21.1, 13.2, 13.2, 13.3, 13.4, 13.6, 13.8, 14.0, 14.0, 14.2, 15.1, 14.5, 15.0, 15.2, 15.6, 15.8, 16.0, 16.2, 16.5, 16.9, 17.1, 17.3, 17.8, 18.0, 12.1, 10.9, 12.3, 13.0, 12.2, 12.3, 11.8, 9.91, 10.3, 9.5, 9.18, 8.8, 8.6, 8.4, 8.4, 10.3, 9.2, 10.1, 9.1, 8.5, 8.75, 8.8, 9.1, 7.25, 8.1, 7.0};
        double[] s3 = {21.1, 21.1, 21.1, 13.2, 13.3, 13.3, 13.6, 13.8, 14.0, 14.6, 14.8, 15.0, 14.5, 14.8, 12.2, 15.3, 15.8, 16.0, 16.2, 16.5, 16.7, 17.2, 18.1, 17.6, 18.0, 18.3, 12.3, 13.0, 11.5, 12.2, 11.6, 11.8, 11.2, 10.7, 9.62, 10.2, 16.9, 9.4, 9.0, 8.4, 9.3, 8.7, 9.2, 8.8, 8.5, 8.0, 8.5, 8.4, 7.9, 8.1, 7.7, 7.5};
//        data.put("line1",s2);
        data2.put("line2",s1);
        data2.put("line1",s2);
        data2.put("line3",s3);
//        array.add(0,data);
        result2.put("status",0);
        result2.put("msg", "");
        result2.put("data", data2);

        JSONObject result1 = new JSONObject();
        JSONObject data1 = new JSONObject();

        double[] s4 ={7.8, 8.4, 9.4, 9.8, 9.3, 9.8, 9.3, 10.9, 11.5, 12.0, 19.1, 12.6, 18.5, 18.5, 18.0, 18.5, 18.0, 18.8, 18.4, 18.6, 18.9, 19.0, 19.1, 18.2, 18.2, 17.6, 15.9, 17.6, 15.6, 17.3, 15.3, 16.8, 15.2, 15.0, 14.9, 15.0, 15.0, 15.0, 15.2, 14.6, 14.5, 14.5, 14.0, 15.0, 14.6, 13.1, 12.9, 12.5, 12.5, 12.3, 13.3};
        double[] s5 ={3.3, 3.6, 3.6, 3.8, 4.0, 4.2, 4.3, 4.5, 4.6, 4.8, 4.9, 5.2, 5.4, 5.3, 6.3, 6.2, 6.8, 7.0, 7.7, 7.6, 6.2, 8.0, 8.0, 8.2, 8.6, 9.8, 9.8, 21.7, 10.2, 9.4, 9.5, 9.4, 10.5, 11.0, 11.8, 10.5, 19.6, 19.4, 13.0, 18.8, 18.5, 18.3, 17.8, 17.6, 17.1, 16.6, 16.1, 15.8, 15.6, 15.8, 14.3};
        double[] s6 ={3.2, 3.3, 9.4, 9.8, 9.3, 9.8, 9.3, 10.9, 11.5, 12.0, 19.0, 12.6, 18.4, 18.5, 18.0, 18.5, 18.0, 18.8, 18.4, 18.6, 18.9, 23.8, 23.6, 18.1, 23.0, 22.8, 15.9, 21.9, 15.6, 21.5, 15.3, 21.2, 15.1, 15.0, 14.9, 15.2, 15.0, 19.8, 15.0, 19.4, 14.6, 11.8, 18.5, 18.0, 17.8, 18.1, 16.9, 16.4, 16.0, 15.8, 16.1};

        List<Double> list1 = new ArrayList<>(Arrays.asList(Arrays.stream(s4).boxed().toArray(Double[]::new)));
        List<Double> list2 = new ArrayList<>(Arrays.asList(Arrays.stream(s5).boxed().toArray(Double[]::new)));
        List<Double> list3 = new ArrayList<>(Arrays.asList(Arrays.stream(s6).boxed().toArray(Double[]::new)));

        Collections.reverse(list1);
        Collections.reverse(list2);
        Collections.reverse(list3);

        data1.put("line1",list1);
        data1.put("line2",list2);
        data1.put("line3",list3);
//        array.add(0,data);
        result1.put("status",0);
        result1.put("msg", "");
        result1.put("data", data1);

        try {
            //使当前线程休眠15秒
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Random rand = new Random();
        // Generate a random integer between 0 and 9
        int randomInt = rand.nextInt(10);
        if(randomInt>=5){
            System.out.println(result2.toJSONString());
            return result2.toJSONString();
        }else{
            System.out.println(result1.toJSONString());
            return result1.toJSONString();
        }

    }
}
