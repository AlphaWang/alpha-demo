package com.alphawang.kafka.consumer;

import com.alphawang.kafka.KafkaConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;

import java.util.Collections;
import java.util.Properties;
import java.util.Set;

@Slf4j
public class KafkaTopicConsumer {

	public static void main(String[] args) {
		KafkaConsumer<String, String> consumer = createConsumer();

		consumer.subscribe(Collections.singletonList(KafkaConstant.TOPIC_SIMPLE));

		consume(consumer);

		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				log.error("Staring exit");
				consumer.wakeup();
			}
		});
	}

	private static void consume(KafkaConsumer<String, String> consumer) {
		try {
			// 持续轮询
			while (true) {
				// 必须持续poll, 否则会被认为已死掉
				ConsumerRecords<String, String> records = consumer.poll(1000);

				Set<TopicPartition> topicPartitions = consumer.assignment();
				for (TopicPartition tp: topicPartitions)     {
					System.out.println("Committing offset at position:" + consumer.position(tp));
				}

				// poll 返回的是ConsumerRecord集合
				// 包含主题信息、记录所在分区信息、记录在分区里的偏移量、记录的键值对。
				for (ConsumerRecord<String, String> record : records) {
					log.info("CONSUMING topic = {}, partition = {}, offset = {}, key={}, value={}",
						record.topic(), record.partition(), record.offset(), record.key(), record.value());
				}

				log.info("CommitASync()");
				consumer.commitAsync();
			}
		} catch (WakeupException wakeupEx) {
			log.error("WakeupException: 被另一个程序退出consume", wakeupEx);
		} finally {
			try {
				log.info("退出程序之前CommitSync()");
				consumer.commitSync();
			} finally {
				log.info("退出程序之前调用close()关闭消费者，立即出发rebalance");
				consumer.close();
			}
		}
	}

	private static KafkaConsumer createConsumer() {
		Properties props = new Properties();
		props.put("bootstrap.servers", KafkaConstant.BROKER);
		props.put("group.id", "consumer-group-1");
		// ﻿earliest, ﻿latest
		props.put("auto.offset.reset", "latest");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);

		return consumer;
	}

}
