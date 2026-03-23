# Merge Adjacent Intervals

## Level 1: Basic In-Memory Solution

### Problem
Given a collection of intervals `[start, end]`, merge all overlapping or adjacent intervals.

### Examples
```
Input:  [[1,3], [2,6], [8,10], [15,18]]
Output: [[1,6], [8,10], [15,18]]
Explanation: [1,3] and [2,6] overlap, so merge into [1,6]

Input:  [[1,4], [4,5]]
Output: [[1,5]]
Explanation: [1,4] and [4,5] are adjacent, so merge into [1,5]

Input:  [[1,4], [0,0]]
Output: [[0,0], [1,4]]
Explanation: No overlap, return sorted intervals
```

### Constraints
- `1 <= intervals.length <= 10^4`
- `intervals[i].length == 2`
- `0 <= start <= end <= 10^4`

### Solution
```java
public class Solution {
    public int[][] merge(int[][] intervals) {
        if (intervals.length <= 1) {
            return intervals;
        }

        // Sort by start time
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        List<int[]> merged = new ArrayList<>();
        int[] current = intervals[0];
        merged.add(current);

        for (int[] interval : intervals) {
            int currentEnd = current[1];
            int nextStart = interval[0];
            int nextEnd = interval[1];

            if (nextStart <= currentEnd) {
                // Overlapping or adjacent, merge
                current[1] = Math.max(currentEnd, nextEnd);
            } else {
                // Not overlapping, add new interval
                current = interval;
                merged.add(current);
            }
        }

        return merged.toArray(new int[merged.size()][]);
    }
}
```

**Time Complexity:** O(n log n) due to sorting
**Space Complexity:** O(n) for the output

---

## Level 2: Streaming Large Dataset

### Problem
Process a file containing 10GB of intervals on a machine with only 2GB of RAM. The file format is:
```
1,3
2,6
8,10
15,18
...
```

Each line represents one interval `[start,end]`.

### Constraints
- Cannot load entire file into memory
- Must use streaming/external sorting approach
- Minimize disk I/O

### Solution: External Merge Sort

```java
public class SingleMachineStreamingMerger {
    private static final long CHUNK_SIZE = 200 * 1024 * 1024; // 200MB chunks

    static class Interval implements Comparable<Interval> {
        long start, end;

        Interval(long start, long end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(Interval other) {
            return Long.compare(this.start, other.start);
        }

        @Override
        public String toString() {
            return start + "," + end;
        }
    }

    static class ChunkReader implements Comparable<ChunkReader> {
        private BufferedReader reader;
        private Interval current;

        ChunkReader(File file) throws IOException {
            this.reader = new BufferedReader(new FileReader(file));
            advance();
        }

        void advance() throws IOException {
            String line = reader.readLine();
            if (line != null) {
                String[] parts = line.split(",");
                current = new Interval(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
            } else {
                current = null;
                reader.close();
            }
        }

        boolean hasNext() {
            return current != null;
        }

        Interval peek() {
            return current;
        }

        Interval next() throws IOException {
            Interval result = current;
            advance();
            return result;
        }

        @Override
        public int compareTo(ChunkReader other) {
            return this.current.compareTo(other.current);
        }
    }

    /**
     * Step 1: Read file in chunks, sort each chunk, write to disk
     */
    public List<File> externalSort(File input, long chunkSize) throws IOException {
        List<File> sortedChunks = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(input))) {
            List<Interval> chunk = new ArrayList<>();
            long currentSize = 0;

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                chunk.add(new Interval(Long.parseLong(parts[0]), Long.parseLong(parts[1])));
                currentSize += line.length();

                if (currentSize >= chunkSize) {
                    Collections.sort(chunk);
                    sortedChunks.add(writeChunk(chunk));
                    chunk.clear();
                    currentSize = 0;
                }
            }

            // Write remaining chunk
            if (!chunk.isEmpty()) {
                Collections.sort(chunk);
                sortedChunks.add(writeChunk(chunk));
            }
        }

        return sortedChunks;
    }

    private File writeChunk(List<Interval> chunk) throws IOException {
        File tempFile = File.createTempFile("chunk_", ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            for (Interval interval : chunk) {
                writer.write(interval.toString() + "\n");
            }
        }
        return tempFile;
    }

    /**
     * Step 2: K-way merge of sorted chunks and merge intervals simultaneously
     */
    public void kWayMergeAndCombine(List<File> sortedChunks, File output) throws IOException {
        PriorityQueue<ChunkReader> pq = new PriorityQueue<>();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output))) {
            // Initialize heap with first interval from each chunk
            for (File chunk : sortedChunks) {
                ChunkReader reader = new ChunkReader(chunk);
                if (reader.hasNext()) {
                    pq.offer(reader);
                }
            }

            Interval current = null;

            while (!pq.isEmpty()) {
                ChunkReader reader = pq.poll();
                Interval interval = reader.next();

                if (current == null) {
                    current = interval;
                } else if (interval.start <= current.end) {
                    // Merge overlapping intervals
                    current.end = Math.max(current.end, interval.end);
                } else {
                    // Write completed interval
                    writer.write(current.toString() + "\n");
                    current = interval;
                }

                // Re-add reader if it has more intervals
                if (reader.hasNext()) {
                    pq.offer(reader);
                }
            }

            // Write final interval
            if (current != null) {
                writer.write(current.toString() + "\n");
            }
        }
    }

    public static void main(String[] args) throws IOException {
        SingleMachineStreamingMerger merger = new SingleMachineStreamingMerger();

        File input = new File("intervals.txt");
        File output = new File("merged.txt");

        // Step 1: External sort (create sorted chunks)
        List<File> chunks = merger.externalSort(input, CHUNK_SIZE);
        System.out.println("Created " + chunks.size() + " sorted chunks");

        // Step 2: K-way merge and combine
        merger.kWayMergeAndCombine(chunks, output);
        System.out.println("Merged intervals written to " + output);

        // Cleanup
        for (File chunk : chunks) {
            chunk.delete();
        }
    }
}
```

**Time Complexity:** O(n log n)
- Sorting phase: O(n log n)
- K-way merge: O(n log k), where k = number of chunks

**Space Complexity:** O(chunk_size + k)
- One chunk in memory at a time
- k readers in priority queue

**Estimated Time:**
- 10GB file, 200MB chunks = 50 chunks
- Sorting: ~8 minutes
- Merging: ~12 minutes
- **Total: ~20 minutes**

---

## Level 3: Distributed Processing

### Problem
Process 1TB of intervals across a distributed cluster to complete in under 10 minutes.

### Solution Architecture

```java
public class DistributedIntervalMerger {

    /**
     * Range-based partitioner to distribute intervals
     */
    public static class IntervalPartitioner {
        private long minValue;
        private long maxValue;
        private int numPartitions;

        public IntervalPartitioner(long minValue, long maxValue, int numPartitions) {
            this.minValue = minValue;
            this.maxValue = maxValue;
            this.numPartitions = numPartitions;
        }

        public int getPartition(Interval interval) {
            long range = (maxValue - minValue) / numPartitions;
            int partition = (int) ((interval.start - minValue) / range);
            return Math.min(partition, numPartitions - 1); // Handle edge case
        }

        public List<Interval> getPartitionRange(int partition) {
            long range = (maxValue - minValue) / numPartitions;
            long start = minValue + partition * range;
            long end = (partition == numPartitions - 1) ? maxValue : start + range;
            return Arrays.asList(
                new Interval(start, start),  // partition start
                new Interval(end, end)       // partition end
            );
        }
    }

    /**
     * Local merge task executed on each worker node
     */
    public static class LocalMergeTask implements Callable<PartitionResult> {
        private List<Interval> partition;
        private int partitionId;

        public LocalMergeTask(List<Interval> partition, int partitionId) {
            this.partition = partition;
            this.partitionId = partitionId;
        }

        @Override
        public PartitionResult call() {
            // Sort intervals in this partition
            Collections.sort(partition);

            // Merge overlapping intervals
            List<Interval> merged = mergeIntervals(partition);

            // Extract boundary information for cross-partition merging
            Interval first = merged.isEmpty() ? null : merged.get(0);
            Interval last = merged.isEmpty() ? null : merged.get(merged.size() - 1);

            return new PartitionResult(partitionId, merged, first, last);
        }

        private List<Interval> mergeIntervals(List<Interval> intervals) {
            if (intervals.isEmpty()) return intervals;

            List<Interval> result = new ArrayList<>();
            Interval current = intervals.get(0);

            for (int i = 1; i < intervals.size(); i++) {
                Interval next = intervals.get(i);
                if (next.start <= current.end) {
                    current.end = Math.max(current.end, next.end);
                } else {
                    result.add(current);
                    current = next;
                }
            }
            result.add(current);

            return result;
        }
    }

    /**
     * Result from processing one partition
     */
    public static class PartitionResult {
        int partitionId;
        List<Interval> mergedIntervals;
        Interval firstInterval;  // First interval in partition (for boundary check)
        Interval lastInterval;   // Last interval in partition (for boundary check)

        public PartitionResult(int partitionId, List<Interval> mergedIntervals,
                              Interval firstInterval, Interval lastInterval) {
            this.partitionId = partitionId;
            this.mergedIntervals = mergedIntervals;
            this.firstInterval = firstInterval;
            this.lastInterval = lastInterval;
        }
    }

    /**
     * Handles merging intervals across partition boundaries
     */
    public static class BoundaryMerger {
        public List<Interval> mergeBoundaries(List<PartitionResult> results) {
            // Sort results by partition ID
            results.sort(Comparator.comparing(r -> r.partitionId));

            List<Interval> crossPartitionMerges = new ArrayList<>();

            // Check adjacent partitions for overlaps
            for (int i = 0; i < results.size() - 1; i++) {
                Interval currentLast = results.get(i).lastInterval;
                Interval nextFirst = results.get(i + 1).firstInterval;

                if (currentLast != null && nextFirst != null &&
                    currentLast.end >= nextFirst.start) {
                    // Intervals cross partition boundary
                    crossPartitionMerges.add(
                        new Interval(currentLast.start, Math.max(currentLast.end, nextFirst.end))
                    );
                }
            }

            return crossPartitionMerges;
        }
    }

    /**
     * Coordinator that orchestrates distributed processing
     */
    public static void main(String[] args) throws Exception {
        // Configuration
        int numWorkers = 100;
        long minValue = 0;
        long maxValue = Long.MAX_VALUE;

        // Step 1: Read and partition data
        List<List<Interval>> partitions = partitionData("s3://bucket/intervals.txt",
                                                        numWorkers, minValue, maxValue);

        // Step 2: Distribute tasks to workers
        ExecutorService executor = Executors.newFixedThreadPool(numWorkers);
        List<Future<PartitionResult>> futures = new ArrayList<>();

        for (int i = 0; i < partitions.size(); i++) {
            futures.add(executor.submit(new LocalMergeTask(partitions.get(i), i)));
        }

        // Step 3: Collect results
        List<PartitionResult> results = new ArrayList<>();
        for (Future<PartitionResult> future : futures) {
            results.add(future.get());
        }

        // Step 4: Handle boundary merges
        BoundaryMerger boundaryMerger = new BoundaryMerger();
        List<Interval> boundaryMerges = boundaryMerger.mergeBoundaries(results);

        // Step 5: Write final results
        writeResults(results, boundaryMerges, "s3://bucket/merged-intervals.txt");

        executor.shutdown();
    }

    private static List<List<Interval>> partitionData(String inputPath, int numPartitions,
                                                      long minValue, long maxValue) {
        // Implementation: Read from S3, partition by range
        // This would use a distributed file system in practice
        throw new UnsupportedOperationException("To be implemented");
    }

    private static void writeResults(List<PartitionResult> results,
                                     List<Interval> boundaryMerges,
                                     String outputPath) {
        // Implementation: Write to S3
        throw new UnsupportedOperationException("To be implemented");
    }
}
```

**Time Complexity:** O(n log n) distributed across workers
- Each worker processes n/k intervals where k = number of workers
- Local sort: O((n/k) log (n/k))
- Boundary merge: O(k)

**Estimated Time:**
- 1TB data, 100 workers = 10GB per worker
- Local processing: ~6 minutes per worker (in parallel)
- Boundary merge: <1 minute
- **Total: ~6 minutes**

**Key Considerations:**
1. **Data Skew:** Use range partitioning based on data distribution
2. **Boundary Handling:** Critical for correctness
3. **Fault Tolerance:** Implement checkpointing for failed workers
4. **Network I/O:** Minimize data shuffling

---

## Level 4: Production Apache Spark

### Solution

```java
import org.apache.spark.sql.*;
import org.apache.spark.sql.expressions.Window;
import static org.apache.spark.sql.functions.*;

public class SparkIntervalMerger {

    public static class Interval implements java.io.Serializable {
        public long start;
        public long end;

        public Interval(long start, long end) {
            this.start = start;
            this.end = end;
        }
    }

    public static void main(String[] args) {
        SparkSession spark = SparkSession.builder()
            .appName("IntervalMerger")
            .config("spark.executor.instances", "100")
            .config("spark.executor.memory", "10g")
            .config("spark.executor.cores", "4")
            .getOrCreate();

        // Read intervals from S3/HDFS
        Dataset<Row> intervals = spark.read()
            .option("header", "false")
            .option("inferSchema", "true")
            .csv("s3://bucket/intervals.txt")
            .toDF("start", "end");

        // Approach 1: Using mapPartitions (more control)
        Dataset<Row> merged = intervals
            .repartitionByRange(100, col("start"))
            .sortWithinPartitions("start")
            .mapPartitions((MapPartitionsFunction<Row, Row>) iterator -> {
                List<Row> result = new ArrayList<>();
                if (!iterator.hasNext()) return result.iterator();

                Row current = iterator.next();
                long currentStart = current.getLong(0);
                long currentEnd = current.getLong(1);

                while (iterator.hasNext()) {
                    Row next = iterator.next();
                    long nextStart = next.getLong(0);
                    long nextEnd = next.getLong(1);

                    if (nextStart <= currentEnd) {
                        // Merge
                        currentEnd = Math.max(currentEnd, nextEnd);
                    } else {
                        // Output current, start new
                        result.add(RowFactory.create(currentStart, currentEnd));
                        currentStart = nextStart;
                        currentEnd = nextEnd;
                    }
                }
                result.add(RowFactory.create(currentStart, currentEnd));
                return result.iterator();
            }, RowEncoder.apply(intervals.schema()));

        // Approach 2: Using Window functions (SQL-style)
        Dataset<Row> withGroup = intervals
            .withColumn("prev_end",
                lag("end", 1).over(Window.orderBy("start")))
            .withColumn("is_new_group",
                when(col("start").gt(col("prev_end")), 1).otherwise(0))
            .withColumn("group_id",
                sum("is_new_group").over(Window.orderBy("start")));

        Dataset<Row> mergedAlt = withGroup
            .groupBy("group_id")
            .agg(
                min("start").alias("start"),
                max("end").alias("end")
            )
            .drop("group_id")
            .orderBy("start");

        // Write results
        merged.write()
            .mode(SaveMode.Overwrite)
            .option("compression", "snappy")
            .parquet("s3://bucket/merged-intervals");

        spark.stop();
    }
}
```

**Advantages:**
- Built-in fault tolerance (RDD lineage)
- Automatic retries on task failure
- Dynamic partition pruning
- Well-tested at scale
- Monitoring and metrics out of the box

**Performance:**
- Same ~6 minutes as custom distributed solution
- But with production-grade reliability

**When to Use:**
- Production systems requiring fault tolerance
- Teams already using Spark ecosystem
- Need for monitoring and observability
- Integration with existing data pipelines

---

## How to Answer This Question (Interview Guide)

### Phase 1: Clarifying Questions (2-3 minutes)

Always start by understanding the requirements:

#### Data Scale
- How many intervals total? (1000 vs 1 billion vs 1 trillion)
- What's the total data size? (MB, GB, TB, PB)
- What format is the input data? (CSV, JSON, Parquet)
- Where is the data stored? (local disk, S3, HDFS)

#### Performance Requirements
- What's the acceptable latency? (seconds, minutes, hours)
- Is this a one-time job or recurring?
- Real-time streaming or batch processing?
- What's the SLA?

#### Infrastructure
- Single machine or distributed cluster available?
- How much memory per machine?
- Can we use existing frameworks (Spark, Flink)?
- Any cost constraints?

#### Data Characteristics
- Are intervals already sorted?
- What's the overlap density? (sparse vs dense)
- Are intervals uniformly distributed?
- Any invalid data to handle?

### Phase 2: Progressive Solutions

Present solutions in order of increasing complexity:

#### Solution 1: Baseline (Simple, Single-machine)
**When:** Small data, no special requirements
**Time:** O(n log n)
**Approach:** Sort and linear scan

#### Solution 2: Optimized (Handle constraints)
**When:** Data doesn't fit in memory
**Time:** Same O(n log n) but streaming
**Approach:** External merge sort

#### Solution 3: Distributed (Scale out)
**When:** Very large data, time constraint
**Time:** O(n log n) parallelized
**Approach:** Range partitioning + boundary handling

#### Solution 4: Production (Framework-based)
**When:** Production system, need reliability
**Time:** Same as distributed
**Approach:** Apache Spark with fault tolerance

### Phase 3: Trade-off Analysis

Discuss trade-offs for each approach:

| Approach | Time | Space | Complexity | Reliability | Cost |
|----------|------|-------|------------|-------------|------|
| In-memory | Fast | High | Low | None | Low |
| Streaming | Medium | Low | Medium | None | Low |
| Distributed | Fast | Medium | High | Manual | High |
| Spark | Fast | Medium | Medium | Built-in | High |

### Phase 4: Follow-up Discussions

Be prepared to discuss:

#### Optimization Opportunities
- **Pre-sorted input:** Skip sorting, use merge-only
- **High overlap:** Track max_end to optimize comparisons
- **Sparse data:** Use skip-list or interval trees

#### Fault Tolerance
- Checkpointing after each partition
- Idempotent operations for retries
- Dead letter queues for invalid data

#### Incremental Updates
- Partition by time range
- Only reprocess affected partitions
- Delta processing for efficiency

#### Monitoring
- Track processing time per partition
- Monitor memory usage
- Alert on failures/retries

### Phase 5: Recommendation

Always conclude with a clear recommendation:

**For this problem (1TB, <10 min requirement):**

I recommend **Apache Spark solution** because:
1. ✅ Meets performance requirement (6 minutes)
2. ✅ Built-in fault tolerance and monitoring
3. ✅ Lower maintenance than custom distributed system
4. ✅ Battle-tested at scale by industry
5. ✅ Easier to hire for (common skillset)

**Trade-off:** Higher infrastructure cost, but worth it for production reliability.
