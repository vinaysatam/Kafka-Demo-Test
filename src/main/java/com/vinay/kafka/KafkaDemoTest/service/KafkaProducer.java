package com.vinay.kafka.KafkaDemoTest.service;

public interface KafkaProducer {
    void sendMessage(String topicName,byte[] message);
}
