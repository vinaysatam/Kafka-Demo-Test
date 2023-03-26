package com.vinay.kafka.KafkaDemoTest.service;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageProducer implements  KafkaProducer{
    private final KafkaTemplate<String,byte[]> kafkaTemplate;

    public KafkaMessageProducer(@Qualifier("producerKafka") KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendMessage(String topicName, byte[] message) {
        ProducerRecord<String,byte[]> record = new ProducerRecord<>(topicName,message);
        System.out.println("Send Messages : "+ record);
        kafkaTemplate.send(record);
    }
}
