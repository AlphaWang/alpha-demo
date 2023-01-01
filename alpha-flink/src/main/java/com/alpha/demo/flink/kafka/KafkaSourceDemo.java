package com.alpha.demo.flink.kafka;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaSourceDemo {

  private static final Logger LOG = LoggerFactory.getLogger(KafkaSourceDemo.class);

  public static void main(String[] args) throws Exception {

    String brokers = "localhost:9092";
    String topic = "test";

    Configuration conf = new Configuration();
    conf.setString("heartbeat.timeout", "18000000");
    conf.setString("akka.ask.timeout", "18000000");
//    LocalEnvironment env = ExecutionEnvironment.createLocalEnvironment(conf);
    StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment(conf);


    KafkaSource<String> source = KafkaSource.<String>builder()
        .setBootstrapServers(brokers)
        .setTopics(topic)
        .setGroupId("alpha-flink-group")
        .setStartingOffsets(OffsetsInitializer.earliest())
        .setValueOnlyDeserializer(new SimpleStringSchema())
        .build();

    DataStream<String> messageStream = env.fromSource(source, WatermarkStrategy.noWatermarks(), "Alpha Kafka Source");
    messageStream.rebalance().map(new MapFunction<String, String>() {
      @Override
      public String map(String value) throws Exception {
//        try {
//          throw new RuntimeException("debug.");
//        } catch (Exception e) {
//          e.printStackTrace();
//          LOG.warn("test from " + Thread.currentThread().getName(), e);
//        }
        return "KafkaSource says: " + value;
      }
    }).print();

    env.execute();
  }
}
