package com.alpha.demo.flink.kafka;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;

public class FlinkKafkaProducerDemo {

  public static void main(String[] args) throws Exception {
    String topic = "";
    String bootstrapServers = "";

    StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
    DataStream<String> messageStream = env.addSource(new SimpleStringGenerator());

    messageStream.addSink(new FlinkKafkaProducer(bootstrapServers, topic, new SimpleStringSchema()));

    env.execute();
  }

  public static class SimpleStringGenerator implements SourceFunction<String> {
    private static final long serialVersionUID = 2174904787118597072L;
    boolean running = true;
    long i = 0;
    @Override
    public void run(SourceContext<String> ctx) throws Exception {
      while(running) {
        ctx.collect("element-"+ (i++));
        Thread.sleep(10);
      }
    }

    @Override
    public void cancel() {
      running = false;
    }
  }



}
