package com.example.freshers.Assignment.Consumer;

import com.example.freshers.Assignment.Utils.UserCreationException;
import com.example.freshers.Assignment.models.User;
import com.example.freshers.Assignment.services.UserServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerService {
    ObjectMapper objectMapper;
    UserServices userServices;
    @Autowired
    public KafkaConsumerService(ObjectMapper objectMapper, UserServices userServices){
        this.objectMapper = objectMapper;
        this.userServices = userServices;
    }
    @KafkaListener(topics = "assignment-topic", groupId = "console-consumer-9782837c-0239-4cae-bfb1-b61aca3c3819")
    public void consumeMessageAndCreateUser(String message) throws JsonProcessingException, UserCreationException {
        // Process the received message
        User user = objectMapper.readValue(message,User.class);
        userServices.createUsers(user);
    }

    @KafkaListener(topics = "assignment-topic-update", groupId = "console-consumer-9782837c-0239-4cae-bfb1-b61aca3c3819")
    public void consumeMessageAndUpdateUser(String message) throws JsonProcessingException {
        // Process the received message
        User user = objectMapper.readValue(message,User.class);
        userServices.updateUser(user);
        System.out.println(user);
    }
}
