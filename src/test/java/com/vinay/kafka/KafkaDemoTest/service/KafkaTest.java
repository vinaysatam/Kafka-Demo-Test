package com.vinay.kafka.KafkaDemoTest.service;

import com.vinay.kafka.KafkaDemoTest.domain.TargetMessage;
import com.vinay.kafka.KafkaDemoTest.util.Constants;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@EmbeddedKafka(topics = Constants.TOPIC_NAME, partitions = 1)
public class KafkaTest {
    @Autowired
    @Qualifier("kafkaTemplateTest")
    private KafkaTemplate< String, byte[] > kafkaTemplate;

    @ Autowired
    @Qualifier("embeddedKafka")
    private EmbeddedKafkaBroker embeddedKafka;

    @Autowired
    GenerateMessageService generateMessageService;

    @Autowired
    KafkaMessageConsumer kafkaMessageConsumer;

    private Consumer<String,byte[]> createConsumer() {
        Map<String,Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, embeddedKafka.getBrokersAsString());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);
        return new DefaultKafkaConsumerFactory<String,byte[]>(props).createConsumer();
    }

    @Test
    void testKafka()throws InterruptedException {

        List<byte[]> list = generateMessageService.generateMessages(1L);
        // send a message
        kafkaTemplate.send(Constants.TOPIC_NAME, list.get(0));

        // wait for the consumer to receive the message
        Consumer<String,byte[]> consumer = createConsumer();
        consumer.subscribe(Collections.singleton(Constants.TOPIC_NAME));
        ConsumerRecords< String,byte[]> records = KafkaTestUtils.getRecords(consumer);
        Assert.assertTrue(records.count()==1);

        // assert that the message was received correctly
        ConsumerRecord<String,byte[]> record = records.iterator().next();
        Assert.assertTrue(record.key()==null);
        TargetMessage targetMessage = kafkaMessageConsumer.convertByteArrayToObject(record);
        Assert.assertTrue(targetMessage.getId()==0);
        Assert.assertTrue(targetMessage.getIdCode().equals("VAS_0"));
        consumer.close();
    }
}