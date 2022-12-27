package com.alpha.demo.flink.kafka;

import java.util.Properties;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

public class FlinkKafkaConsumerDemo {

  public static void main(String[] args) throws Exception {
    String topic = "test";
    Properties props = new Properties();
    props.setProperty("bootstrap.servers", "localhost:9092");

    StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
    FlinkKafkaConsumer flinkKafkaConsumer = new FlinkKafkaConsumer<>(topic, new SimpleStringSchema(), props);
    flinkKafkaConsumer.setCommitOffsetsOnCheckpoints(true);
    flinkKafkaConsumer.setStartFromEarliest();


    DataStream<String> messageStream = env.addSource(flinkKafkaConsumer);

    messageStream.rebalance().map(new MapFunction<String, String>() {
      @Override
      public String map(String value) throws Exception {
        return "Kafka and Flink says: " + value;
      }
    }).print();

    env.execute();
  }

}
