package com.alphawang.kafka.consumer;

import com.alphawang.kafka.KafkaConstant;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;

import java.util.List;
import java.util.Properties;

/**
 * 消费者可以订阅主题，
 * 或者为自己分配分区。
 *
 * 二选一。
 */
@Slf4j
public class KafkaPartitionConsumer {

    public static void main(String[] args) {
        KafkaConsumer consumer = createConsumer();

        List<PartitionInfo> partitions = consumer.partitionsFor(KafkaConstant.TOPIC_SIMPLE);

        if (partitions != null) {
            List<TopicPartition> topicPartitions = Lists.newArrayList();
            for (PartitionInfo partition : partitions) {
                topicPartitions.add(new TopicPartition(partition.topic(), partition.partition()));
            }

            // 指定分区
            consumer.assign(topicPartitions);

            consume(consumer);
        }
    }

    private static void consume(KafkaConsumer consumer) {
        while (true) {
            ConsumerRecords<String, String> consumerRecords = consumer.poll(1000);

            for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                log.info("CONSUMING topic = {}, partition = {}, offset = {}, key = {}, value = {}", consumerRecord.topic(),
                    consumerRecord.partition(),
                    consumerRecord.offset(), consumerRecord.key(), consumerRecord.value());
            }

            consumer.commitSync();
        }
    }

    private static KafkaConsumer createConsumer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", KafkaConstant.BROKER);
        props.put("group.id", "consumer-1");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);

        return consumer;
    }
}
