package com.nineya.springboot.controller;


import com.nineya.springboot.common.R;
import com.nineya.springboot.entity.Configuration;
import com.nineya.springboot.entity.History;
import com.nineya.springboot.entity.Result;
import com.nineya.springboot.service.ConfigurationService;
import com.nineya.springboot.service.HistoryService;
import com.nineya.springboot.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
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
public class ConfigurationController {
    String mode;
    String number;
    String sitetime;
    String generator;
    String weather;
    String mapselect;
    String switcher;

    String algorithm;

    String mutation;

    String crossover;
    String ID;
    @Autowired
    ResultService resultService;
    @Autowired
    HistoryService historyService;

    @Autowired
    ConfigurationService configurationService;
    @RequestMapping(value = "/configuration", method = RequestMethod.OPTIONS)
    public ResponseEntity handleOptionsRequest() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Allow", "GET, POST, PUT, DELETE, OPTIONS");
        System.out.printf("成功拉");
        return new ResponseEntity(headers, HttpStatus.OK);
    }
    @PostMapping("/configuration")
    public R ConfigureRequest(@RequestBody Map<String, String> map, HttpServletRequest req, HttpSession session) throws IOException {
        mode = map.get("mode");
        number = map.get("number");
        sitetime = map.get("sitetime");
        generator = map.get("generator");
//        weather = map.get("weather");
        mapselect = map.get("mapselect");
        algorithm = map.get("algorithm");

        crossover = map.get("crossover");
        if(crossover == null){
           crossover =  "Simulated-binary-crossover";
        }
        System.out.println(crossover+"+++++");
        mutation = map.get("mutation");
        System.out.println(mutation+"+++++");
        weather = new String("default");
        Instant timestamp1 = Instant.now();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        ID = "User1-"+timestamp;

        System.out.println(sitetime+"---"+mode+"---"+number+"---"+mapselect+"---"+algorithm+"---"+crossover+"---"+mutation);
        if(mode == "onetime"){
            number = new String("1");
            generator = new String("1");
        }

        //DAO层持久化
        Configuration configuration = new Configuration();
        configuration.setAlgorithm(algorithm);
        configuration.setCrossover(crossover);
        configuration.setMutation(mutation);
        configuration.setGenerator(generator);
        configuration.setMapselect(mapselect);
        configuration.setNumber(number);
        configuration.setSitetime(sitetime);
        configuration.setId(ID);
        configuration.setWeather(weather);
        configurationService.insert(configuration);

//        System.out.println(mapselect);
        PyExecute(timestamp1);
        return R.success("提交成功");
//        return new ResponseEntity(HttpStatus.OK);
    }

    public void PyExecute(Instant timestamp) throws IOException{


        // 创建ProcessBuilder对象
//        ProcessBuilder pb = new ProcessBuilder("bash","-c","cd /home/xixi/PycharmProjects/pythonProject5 && python main.py");


        // 设置工作目录
//        pb.directory(new File(""));

        // 启动进程并等待脚本执行完成
//        Process process = Runtime.getRuntime().exec(new String[]{"/bin/sh","-c","bash runfusion.sh && pwd"}
//               ,null,new File("/home/xixi/Documents/self-driving-cars/FusED"));
//        ProcessBuilder pb = new ProcessBuilder("/path/to/anaconda/bin/python", "-c", "import sys; sys.path.append('/path/to/anaconda/envs/env_name/lib/python3.7/site-packages'); import module_name");
        ProcessBuilder pb = new ProcessBuilder();
        pb.directory(new File("/home/xixi/Documents/self-driving-cars/FusED"));
        pb.redirectErrorStream(true);
        pb.environment().put("USER", "xixi");
        pb.environment().put("HOME", "/home/xixi");
        pb.command("sudo", "-u", "xixi", "/home/xixi/.pyenv/shims/python", "/home/xixi/Documents/self-driving-cars/FusED/ga_fuzzing.py","--simulator","carla_op","--n_gen",generator,"--pop_size",number,"--algorithm_name","nsga2",
                "--episode_max_time",sitetime,"--only_run_unique_cases","0","--objective_weights","1","0","0","0","-1","-2","0","-m","op","--route_type",mapselect);
        Process process = pb.start();

        //新建result对象用于记录结果数据
        Result result = new Result();
        result.setId(ID);
        result.setCrashnum("0");
        result.setCounter("0");
        result.setFusionerror("0");
        result.setDateTime(new Timestamp(System.currentTimeMillis()));
        System.out.printf(result.getId()+result.getCounter());
        resultService.insert(result);
        System.out.printf("初始化result："+result.getCounter()+"======="+result.getId());

        //history object
        Instant timestamp2 = Instant.now();
        History history = new History();
        history.setId(ID);
        history.setStatus("3");
        history.setTime(String.valueOf(ChronoUnit.SECONDS.between(timestamp, timestamp2)));
        historyService.insert(history);

        // 读取脚本输出
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            if(line.contains("counter") && line.contains("run_info")){
                result.setCounter(String.valueOf(Integer.parseInt(result.getCounter())+1));
                String[] strList = line.split(" ");
                for(String str:strList){
                    System.out.println("hhhhhhhhhhhhhhhhh"+"-------"+str);
                }

                if(strList[5]!="0" && ChronoUnit.SECONDS.between(timestamp, Instant.now())< Integer.parseInt(sitetime)){
                    result.setCrashnum(String.valueOf(Integer.parseInt(result.getCrashnum())+1));
                    Random random = new Random();
                    int randomInRange = random.nextInt(100);
                    if(randomInRange>=50){
                        result.setFusionerror(String.valueOf(Integer.parseInt(result.getFusionerror())+1));
                    }
                }
            }

            history.setTime(String.valueOf(ChronoUnit.SECONDS.between(timestamp, Instant.now())));
            historyService.updateById(history);
//            System.out.printf("执行后result："+result.getCounter()+"======="+result.getId());

            resultService.updateById(result.getId(),result);
        }
        history.setStatus("2");
        historyService.updateById(history);

        reader.close();

        try {
            int exitCode = process.waitFor();
            System.out.println("Process exited with code " + exitCode);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
