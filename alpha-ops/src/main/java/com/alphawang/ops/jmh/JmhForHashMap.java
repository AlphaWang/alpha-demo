package com.alphawang.ops.jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Warmup 用来配置预热的内容，可用于类或者方法上，越靠近执行方法的地方越准确。一般配置warmup的参数有这些：
 *
 * iterations：预热的次数。
 * time：每次预热的时间。
 * timeUnit：时间单位，默认是s。
 * batchSize：批处理大小，每次操作调用几次方法。
 * 
 * @Measurement
 * 用来控制实际执行的内容，配置的选项本warmup一样。
 */
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 50, time = 100, timeUnit = TimeUnit.MILLISECONDS)
public class JmhForHashMap {
    
    
    static class Demo {
        int id;
        String name;
        
        public Demo(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }
    
    static List<Demo> demoList;
    static {
        demoList = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            demoList.add(new Demo(i, "test"));
        }
    }
    
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void testHashMapWithoutSize(Blackhole blackhole) {
        Map map = new HashMap<>();
        for (Demo demo : demoList) {
            map.put(demo.id, demo.name);
        }
        
        blackhole.consume(map);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void testHashMapWithSize() {
        Map map = new HashMap<>((int) (demoList.size() / 0.75F) + 1);
        for (Demo demo : demoList) {
            map.put(demo.id, demo.name);
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
            .include(JmhForHashMap.class.getSimpleName())
            .forks(1)
            .result("jmh.result.json")
            .resultFormat(ResultFormatType.JSON)
            .build();
        
        new Runner(opt).run();
    }
}
