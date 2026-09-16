package com.nm.tranproc.api.config;

import com.nm.tranproc.api.request.Request;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

  @Bean
  public ProducerFactory<String, Request> producerFactory() {

    Map<String, Object> properties = new HashMap<>();

    properties.put(
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
        "10.154.2.87:9092"
    );

    properties.put(
        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
        StringSerializer.class
    );

    properties.put(
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
        JacksonJsonSerializer.class
    );

    return new DefaultKafkaProducerFactory<>(properties);
  }

  @Bean
  public KafkaTemplate<String, Request> kafkaTemplate(
      ProducerFactory<String, Request> producerFactory) {

    return new KafkaTemplate<>(producerFactory);
  }
}