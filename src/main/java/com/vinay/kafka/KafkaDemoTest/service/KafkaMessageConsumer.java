package com.vinay.kafka.KafkaDemoTest.service;

import com.vinay.kafka.KafkaDemoTest.domain.TargetMessage;
import com.vinay.kafka.KafkaDemoTest.exception.CustomException;
import com.vinay.kafka.KafkaDemoTest.util.Constants;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.JsonDecoder;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class KafkaMessageConsumer {

    @KafkaListener(topics = Constants.TOPIC_NAME)
    public void listen(ConsumerRecord<String, byte[]> record) throws IOException {
        String key = record.key();
        byte[] value = record.value();
        convertByteArrayToObject(record);
        /*GenericDatumReader<GenericRecord> reader = new GenericDatumReader<>(Constants.SCHEMA);
        JsonDecoder decoder = DecoderFactory.get().jsonDecoder(Constants.SCHEMA, new String(value));
        GenericRecord genericRecord = reader.read(null, decoder);
        Employee employee = new Employee();
        employee.setName(genericRecord.get("name").toString());
        employee.setAge((Integer) genericRecord.get("age"));
        employee.setPosition(genericRecord.get("position").toString());
        logger.info("Received Avro message with key '{}' and value '{}' on topic '{}'", key, value, topic);*/
    }

    public TargetMessage convertByteArrayToObject(ConsumerRecord<String, byte[]> record){
        String key = record.key();
        byte[] value = record.value();
        try {
            GenericDatumReader<GenericRecord> reader = new GenericDatumReader<>(Constants.SCHEMA);
            Decoder decoder = DecoderFactory.get().binaryDecoder(value,null);
            //JsonDecoder decoder = DecoderFactory.get().jsonDecoder(Constants.SCHEMA, new String(value));
            GenericRecord genericRecord = reader.read(null, decoder);
            TargetMessage targetMessage = TargetMessage.builder()
                    .id((Long) genericRecord.get("id"))
                    .idCode(genericRecord.get("idCode").toString())
                    .localCurrentDate(LocalDate.parse(genericRecord.get("currentDate").toString(),Constants.DATE_FORMATTER))
                    .localDateTime(LocalDateTime.parse(genericRecord.get("dateTime").toString(),Constants.DATE_TIME_FORMATTER))
                    .build();
            System.out.println("value : "+targetMessage);
            return targetMessage;
        }catch (IOException e){
            throw new CustomException("failed to decrypt "+e);
        }
    }
}
