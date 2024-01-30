package com.backend.config;

import com.example.common.EmailRequest;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.awt.datatransfer.StringSelection;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String server;

    @Bean
    public Map<String,Object> producerConfig(){
        Map<String, Object> map = new HashMap<>();

        map.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        map.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        map.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return map;
    }

    @Bean
    public KafkaTemplate<String, EmailRequest> kafkaEmailTemplate(){
        DefaultKafkaProducerFactory<String, EmailRequest> factory = new DefaultKafkaProducerFactory<>(producerConfig());

        return new KafkaTemplate<>(factory);
    }
}
