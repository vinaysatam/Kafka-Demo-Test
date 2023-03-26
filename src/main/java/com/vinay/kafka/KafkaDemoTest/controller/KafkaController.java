package com.vinay.kafka.KafkaDemoTest.controller;

import com.vinay.kafka.KafkaDemoTest.service.GenerateMessageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {

    private final GenerateMessageService generateMessageService;

    public KafkaController(GenerateMessageService generateMessageService) {
        this.generateMessageService = generateMessageService;
    }
    @GetMapping(value="/sendMessageKafka")
    public String sendMessageKafka(@RequestParam(required = false,defaultValue = "50") Long msgCount){
        generateMessageService.generateAndSendMsgToKafka(msgCount);
        return "Done";
    }
}
