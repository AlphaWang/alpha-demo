package com.alphawang.kafka.serializer;

import com.alphawang.kafka.KafkaConstant;
import com.alphawang.kafka.Message;
import com.alphawang.kafka.producer.KafkaProducerTest;
//import io.confluent.kafka.serializers.KafkaAvroSerializer;
//import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Date;
import java.util.Properties;

/**
 * $ cat ~/tools/confluent-3.2.1/etc/schema-registry/schema-registry.properties
 *
 * listeners=http://0.0.0.0:8081
 * kafkastore.connection.url=localhost:2181
 * kafkastore.topic=_schemas
 * debug=false
 *
 * ~/tools/confluent-3.2.1/bin/schema-registry-start  ~/tools/confluent-3.2.1/etc/schema-registry/schema-registry.properties
 */
@Slf4j
public class KafkaAvroSerializerTest {

    public static void main(String[] args) {
        KafkaProducer producer = createProducer();
        syncSend(producer);
    }

    private static void syncSend(KafkaProducer producer) {
        Message msg = new Message()
            .setId(1L)
            .setName("test kafka msg")
            .setDate(new Date());
        ProducerRecord<String, Message> record = new ProducerRecord<>(KafkaConstant.TOPIC_AVRO, msg.getName(), msg);

        RecordMetadata meta = KafkaProducerTest.send(producer, record);

        log.info("----- produce: " + meta);
    }

    private static KafkaProducer createProducer() {
        Properties kafkaProps = new Properties();
        kafkaProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstant.BROKER);

        kafkaProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        //		kafkaProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
        //		kafkaProps.put(KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");  // schema-registry server

        kafkaProps.put(ProducerConfig.CLIENT_ID_CONFIG, "producer-avro");

        kafkaProps.put(ProducerConfig.BATCH_SIZE_CONFIG, "1024");
        kafkaProps.put(ProducerConfig.LINGER_MS_CONFIG, "5000");

        KafkaProducer producer = new KafkaProducer(kafkaProps);

        return producer;
    }
}
