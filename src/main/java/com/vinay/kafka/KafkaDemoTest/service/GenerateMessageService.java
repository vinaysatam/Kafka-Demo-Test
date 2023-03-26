package com.vinay.kafka.KafkaDemoTest.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vinay.kafka.KafkaDemoTest.domain.TargetMessage;
import com.vinay.kafka.KafkaDemoTest.exception.CustomException;
import com.vinay.kafka.KafkaDemoTest.util.Constants;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class GenerateMessageService {
    private final ObjectMapper mapper;
    private final KafkaProducer kafkaProducer;

    public GenerateMessageService(ObjectMapper mapper, KafkaProducer kafkaProducer) {
        this.mapper = mapper;
        this.kafkaProducer = kafkaProducer;
    }

    public void generateAndSendMsgToKafka(Long msgCount){
        List<byte[]> avroList = generateMessages(msgCount);
        avroList.forEach(msg->kafkaProducer.sendMessage(Constants.TOPIC_NAME,msg));
    }

    public List<byte[]> generateMessages(Long msgCount){
        List<TargetMessage> list = getTargetMessages(msgCount);
        List<byte[]> avroList = new ArrayList<>();
        for(TargetMessage targetMessage : list){
            System.out.println("Target Message : "+ targetMessage);
            GenericRecord record  = convertIntoGenericRecord(targetMessage);
            avroList.add(genericRecordToBypeArray(record));
        }
        return  avroList;
    }

    private byte[] genericRecordToBypeArray(GenericRecord record) {
        System.out.println("GenericRecord record : "+ record);
        DatumWriter<GenericRecord> writer = new SpecificDatumWriter<>(Constants.SCHEMA);
        byte[] avroByte;
        try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream()){
            Encoder encoder = EncoderFactory.get().binaryEncoder(outputStream,null);
            writer.write(record,encoder);
            encoder.flush();
            avroByte = outputStream.toByteArray();
            return avroByte;
        }catch(IOException e){
            throw new CustomException("Failed To Parse : "+e.getLocalizedMessage());
        }
    }

    private GenericRecord convertIntoGenericRecord(TargetMessage targetMessage) {
        GenericRecord genericRecord = new GenericData.Record(Constants.SCHEMA);
        genericRecord.put("id",targetMessage.getId());
        genericRecord.put("idCode",targetMessage.getIdCode());
        genericRecord.put("currentDate",targetMessage.getCurrentDate());
        genericRecord.put("dateTime",targetMessage.getDateTime());
        return genericRecord;
    }

    private static List<TargetMessage> getTargetMessages(Long msgCount) {
        List<TargetMessage> list = new ArrayList<>();
        long limit = Constants.count+ msgCount;
        for(long var = Constants.count;var < limit;var++){
            TargetMessage targetMessage = new TargetMessage(var,"VAS_"+var,LocalDate.now(),LocalDateTime.now());
            list.add(targetMessage);
        }
        Constants.count = limit + 1;
        return list;
    }
}
