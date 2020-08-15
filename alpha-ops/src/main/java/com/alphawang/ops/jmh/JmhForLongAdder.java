package com.alphawang.ops.jmh;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
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
public class JmhForLongAdder {
    
    
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void testAtomicLong(Blackhole blackhole) {
        AtomicLong atomicLong = new AtomicLong(0);
        for (int i = 0; i < 100_000; i++) {
            atomicLong.incrementAndGet();
            System.out.println("1- " + atomicLong.get());
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void testLongAdder() {
        LongAdder longAdder = new LongAdder();
        for (int i = 0; i < 100_000; i++) {
            longAdder.increment();
            System.out.println("2- " + longAdder.longValue());
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
            .include(JmhForLongAdder.class.getSimpleName())
            .forks(1)
            .result("jmh.result.json")
            .resultFormat(ResultFormatType.JSON)
            .build();
        
        new Runner(opt).run();
    }
}
