package com.nineya.springboot.kafkatools;

@Component
public class KafkaConsumer {

    @KafkaListener(topics = "sensorData", groupId = "myGroup")
    public void listen(String message) {
        System.out.println("Received message in group myGroup: " + message);
    }
}