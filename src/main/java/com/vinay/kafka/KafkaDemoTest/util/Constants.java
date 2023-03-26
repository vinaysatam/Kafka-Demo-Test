package com.vinay.kafka.KafkaDemoTest.util;

import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;

import java.time.format.DateTimeFormatter;

public class Constants {
    public static final String TOPIC_NAME = "testKafka";
    public static final String GROUP_ID="test-consumer-kafka";
    public static final String BOOTSTRAP_SERVERS="localhost:9092";
    public static Long count = 0L;

    public static final String DATE_PATTERN="MM/dd/yyyy";
    public static final String DATE_TIME_PATTERN="MM/dd/yyyy HH:mm:ss";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    public static final Schema SCHEMA = SchemaBuilder.record("TargetMessage")
            .fields()
            .name("id").type().longType().noDefault()
            .name("idCode").type().stringType().noDefault()
            .name("currentDate").type().stringType().noDefault()
            .name("dateTime").type().stringType().noDefault()
            .endRecord();
}
