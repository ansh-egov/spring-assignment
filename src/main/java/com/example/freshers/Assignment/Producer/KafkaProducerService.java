package com.example.freshers.Assignment.Producer;

import com.example.freshers.Assignment.models.User;
import com.example.freshers.Assignment.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private final KafkaTemplate<String, User> kafkaTemplate;
    private final UserServices userServices;

    @Value("${kafka.topic.assignment-topic}")
    private String assignmentTopic;

    @Value("${kafka.topic.assignment-topic-update}")
    private String assignmentTopicUpdate;

    @Autowired
    public KafkaProducerService(KafkaTemplate<String, User> kafkaTemplate, UserServices userServices) {
        this.kafkaTemplate = kafkaTemplate;
        this.userServices = userServices;
    }

    public void createUserByKafka(User user){
        if(userServices.getUserByNameAndMobileNumber(user.getName(),user.getMobileNumber())){
            kafkaTemplate.send(assignmentTopic,user);
        }else{
            throw new IllegalArgumentException("User with the same name and mobile number already exists");
        }
    }

    public void updateUserByKafka(User user){
        kafkaTemplate.send(assignmentTopicUpdate,user);
    }
}
