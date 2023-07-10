package com.example.freshers.Assignment.Producer;

import com.example.freshers.Assignment.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private final KafkaTemplate<String, User> kafkaTemplate;

    @Autowired
    public KafkaProducerService(KafkaTemplate<String, User> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void createUserByKafka(User user){
        kafkaTemplate.send("assignment-topic",user);
    }

    public void updateUserByKafka(User user){
        kafkaTemplate.send("assignment-topic-update",user);
    }
}
