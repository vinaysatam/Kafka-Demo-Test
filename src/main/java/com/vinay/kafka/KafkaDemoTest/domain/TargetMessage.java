package com.vinay.kafka.KafkaDemoTest.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vinay.kafka.KafkaDemoTest.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@AllArgsConstructor
public class TargetMessage {

    private long id;
    private String idCode;
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate localCurrentDate;

    @JsonFormat(pattern ="MM/dd/yyyy HH:mm:ss")
    private LocalDateTime localDateTime;
    @EqualsAndHashCode.Exclude
    private String currentDate;
    @EqualsAndHashCode.Exclude
    private String dateTime;

    public TargetMessage(long id, String idCode, LocalDate localCurrentDate, LocalDateTime localDateTime) {
        this.id = id;
        this.idCode = idCode;
        this.localCurrentDate = localCurrentDate;
        this.localDateTime = localDateTime;
        this.currentDate = localCurrentDate.format(Constants.DATE_FORMATTER);
        this.dateTime = localDateTime.format(Constants.DATE_TIME_FORMATTER);
    }
}
