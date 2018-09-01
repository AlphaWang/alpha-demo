package com.alphawang.kafka.serializer;

import com.alphawang.kafka.KafkaConstant;
import com.alphawang.kafka.Message;
import com.alphawang.kafka.producer.KafkaProducerTest;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Date;
import java.util.Properties;

public class KafkaSerializerTest {

	public static void main(String[] args) {
	   KafkaProducer producer = createProducer();
	   syncSend(producer);
	}

	private static void syncSend(KafkaProducer producer) {
		Message msg = new Message()
			.setId(1L)
			.setName("test kafka msg")
			.setDate(new Date());
		ProducerRecord<String, Message> record = new ProducerRecord<>(KafkaConstant.TOPIC_OBJ, msg.getName(), msg);

		RecordMetadata meta = KafkaProducerTest.send(producer, record);
	}


	private static KafkaProducer createProducer() {
		Properties kafkaProps = new Properties();
		kafkaProps.put("bootstrap.servers", KafkaConstant.BROKER);

		// Exception in thread "main" org.apache.kafka.common.config.ConfigException:
		// Invalid value io.confluent.kafka.serializers.KafkaAvroSerializer for configuration value.serializer:
		// Class io.confluent.kafka.serializers.KafkaAvroSerializer could not be found.
		kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		kafkaProps.put("value.serializer", "io.confluent.kafka.serializers.KafkaAvroSerializer");

		kafkaProps.put("client.id", "producer-1");

		kafkaProps.put("batch.size", "1024");
		kafkaProps.put("linger.ms", "5000");

		KafkaProducer producer = new KafkaProducer(kafkaProps);

		return producer;
	}
}
