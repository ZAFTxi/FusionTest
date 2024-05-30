package com.nineya.springboot.controller;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import static org.apache.spark.sql.functions.*;
import java.util.*;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.nineya.springboot.common.R;
import com.nineya.springboot.entity.History;
import com.nineya.springboot.entity.Result;
import com.sun.rowset.internal.Row;
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

        SparkSession spark = SparkSession.builder()
                .appName("KafkaSparkStream")
                .getOrCreate();

        JavaStreamingContext jssc = new JavaStreamingContext(spark.sparkContext(), Durations.seconds(1));

        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("bootstrap.servers", "localhost:9092");
        kafkaParams.put("key.deserializer", StringDeserializer.class);
        kafkaParams.put("value.deserializer", StringDeserializer.class);
        kafkaParams.put("group.id", "sensor1");
        kafkaParams.put("auto.offset.reset", "latest");
        kafkaParams.put("enable.auto.commit", false);

        String topics = "sensorData";

        JavaInputDStream<String> kafkaStream = KafkaUtils.createDirectStream(
                jsc,
                LocationStrategies.PreferConsistent(),
                ConsumerStrategies.Subscribe(topics, kafkaParams)
        );

        JavaInputDStream<Row> messages = kafkaStream.map(record -> {
            String json = record.value();
            // JSON数据格式{"angle": 30, "speed": 50, "location": "x:10,y:20"}

            Dataset<Row> df = sqlContext.read().json(jsc.parallelize(Arrays.asList(json)));
            return df.collectAsList().get(0);
        });


        messages.foreachRDD(rdd -> rdd.foreachPartition(partition -> {
            while (partition.hasNext()) {
                Row row = partition.next();
                // 访问DataFrame中的数据
                int angle = row.getAs("angle");
                int speed = row.getAs("speed");
                String location = row.getAs("location");

            }
        }));


        SparkSession spark = SparkSession.builder().appName("Data Cleansing and Formatting").getOrCreate();

// df是已经加载的包含JSON数据的DataFrame
        Dataset<Row> df = spark.read().json("path_to_json_data");

// 数据清洗
// 去除空值
        df = df.na().drop();

// 数据类型转换，将角度转换为Double类型
        df = df.withColumn("angle", df.col("angle").cast("double"));

// 去除重复数据
        df = df.dropDuplicates();

// 修正错误数据，将速度限制在0到100之间
        df = df.withColumn("speed", when(df.col("speed").between(0, 100), df.col("speed")).otherwise(0));

// 标准化文本，去除位置信息中的空格
        df = df.withColumn("location", regexp_replace(df.col("location"), "\\s+", ""));

        df = df.filter(df.col("speed") > 50);

// 数据格式化
// 统一日期格式date字段需要格式化
        df = df.withColumn("date", to_date(df.col("date"), "yyyy-MM-dd"));

// 数值格式化，将速度保留两位小数
        df = df.withColumn("speed", round(df.col("speed"), 2));

// 输出清洗和格式化后的数据
        df.show();

        kafkaStream.map(record -> record.value())
                .foreachRDD(rdd -> rdd.foreachPartition(partition -> {
                    while (partition.hasNext()) {
                        String json = partition.next();
                        System.out.println("Received data: " + json);

                    }
                }));

        jssc.start();
        jssc.awaitTermination();

        template.convertAndSend("/topic/data", processedData);

        }

    }
}
