package com.alphawang.kafka.producer;

import com.alphawang.kafka.KafkaConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Slf4j
public class KafkaProducerTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        KafkaProducer producer = createProducer();

        //		send(producer);
        //		syncSend(producer);
        asyncSend(producer);
    }

    private static KafkaProducer createProducer() {
        Properties kafkaProps = new Properties();
        kafkaProps.put("bootstrap.servers", KafkaConstant.BROKER);
        kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        kafkaProps.put("client.id", "producer-1");

        kafkaProps.put("batch.size", "1024");
        kafkaProps.put("linger.ms", "5000");

        KafkaProducer producer = new KafkaProducer(kafkaProps);

        return producer;
    }

    /**
     * fire-and-forget
     *
     * 会忽略返回值，也会忽略发送消息时产生的异常。
     * 但是，发送消息前也可能发生异常，例如SerializationException，所以有try-catch
     */
    private static void send(KafkaProducer producer) {
        ProducerRecord<String, String> record = new ProducerRecord<>(KafkaConstant.TOPIC_SIMPLE, "key1", "value1");
        try {
            log.info("===fire-and-forget==== TOPIC {}, KEY {}, VALUE {}", record.topic(), record.key(), record.value());
            producer.send(record);

        } catch (Exception e) {
            log.error("===fire-and-forget==== EXCEPTION!", e);
            e.printStackTrace();
        }
    }

    /**
     * sync send
     *
     * 等待Future.get()
     */
    private static void syncSend(KafkaProducer producer) {
        ProducerRecord<String, String> record = new ProducerRecord<>(KafkaConstant.TOPIC_SIMPLE, "key2", "value2");
        send(producer, record);
    }

    public static RecordMetadata send(KafkaProducer producer, ProducerRecord record) {
        try {
            log.info("===sync send==== TOPIC {}, KEY {}, VALUE {}", record.topic(), record.key(), record.value());

            Future<RecordMetadata> future = producer.send(record);
            RecordMetadata metadata = future.get();

            log.info("===sync send==== RESULT {}", metadata);
            return metadata;
        } catch (Exception e) {
            log.error("===sync send==== EXCEPTION!");
            e.printStackTrace();
            return null;
        }
    }

    private static void asyncSend(KafkaProducer<String, String> producer) throws ExecutionException, InterruptedException {
        ProducerRecord<String, String> record = new ProducerRecord<>(KafkaConstant.TOPIC_SIMPLE, "key3", "value3");

        log.info("===async send==== TOPIC {}, KEY {}, VALUE {}", record.topic(), record.key(), record.value());

        Future<RecordMetadata> future = producer.send(record, new ProducerCallback());

        RecordMetadata metadata = future.get();
        log.info("===async send==== RESULT {}", metadata);
    }

    private static class ProducerCallback implements Callback {
        @Override
        public void onCompletion(RecordMetadata metadata, Exception exception) {
            log.info("callback RESULT {}", metadata);

            if (exception != null) {
                log.error("callback EXCEPTION!", exception);
                exception.printStackTrace();
            }
        }
    }

}
