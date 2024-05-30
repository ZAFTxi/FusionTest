package com.nineya.springboot.kafkatools;

import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.sun.rowset.internal.Row;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.concurrent.CompletableFuture;



@SpringBootApplication
public class KafkaSparkApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaSparkApplication.class, args);
    }

    @Autowired
    private SimpMessagingTemplate template;

    @Bean
    public ServiceCall<NotUsed, CompletionStage<Streams>> streams() {
        return request -> {
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


                    System.out.println("Angle: " + angle + ", Speed: " + speed + ", Location: " + location);
                }
            }));

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

            return CompletableFuture.completedFuture(null);
        };
    }
}
