Java知识脑图：http://naotu.baidu.com/file/b38589b975d51e3851f2c3315a895b72

# Java

## JVM

### 运行时数据区

#### 程序计数器

##### 线程私有

##### 当前线程所执行的字节码的行号指示器

##### OOM: 无

#### JVM 栈

##### 线程私有

##### 存储局部变量表、操作数栈、方法出口

##### StackOverflowError

##### OOM: 动态扩展时无法申请到足够内存

如果`-Xss`栈大小设置过大，当创建大量线程时可能OOM.
（每个线程都会创建一个栈）

#### 本地方法栈

#### 方法区

##### 线程共享

##### 存储类信息、常量、静态变量、运行时常量(String.intern)

##### GC: 回收常量池、卸载类型

##### OOM: String.intern，CGLib

- 例如大量String.intern()
- 用CGLib生成大量动态类
- OSGi应用
- 大量JSP的应用 （JSP第一次运行需要便以为Javale）
`-XX:PermSize` `-XX:MaxPermSize`

#### 堆

##### 线程共享

##### 存储对象实例、数组

##### OOM

`-Xms` `-Xmx`

#### 直接内存(堆外内存)

##### NIO DirectByteBuffer

##### OOM

`-XX:MaxDirectMemorySize`

### 对象

#### 创建

##### 在常量池找类的符号引用

##### 类加载

##### 分配内存

###### 指针碰撞：适用于Compact GC，例如Serial, ParNew

当内存规整时适用


###### 空闲列表：适用于Mark-Sweep GC，例如CMS

当内存不连续时适用


###### 并发问题

####### CAS

####### TLAB: Thread Local Allocation Buffer

##### init

#### 结构

##### Header

###### Mark Word

- HashCode
- GC分代年龄
- 锁状态标志
- 线程持有的锁
- 偏向线程ID
- 偏向时间戳

###### 类型指针

##### Instance Data

##### Padding

### GC

#### 引用类型

##### 强引用

##### 软引用

发生内存溢出之前，会尝试回收

##### 弱引用

下一次垃圾回收之前，一定会被回收


##### 虚引用

#### GC回收判断

##### 引用计数法

###### 无法解决循环引用问题

##### 可达性分析

###### GC Roots

####### 虚拟机栈中，本地变量表引用的对象

####### 本地方法栈中，JNI引用的对象

####### 方法区中，类静态属性引用的对象

####### 方法区中，常量引用的对象

#### GC算法

##### 标记清除 Mark-Sweep

- 标记出所有需要回收的对象
- 标记完成后统一回收被标记的对象


###### 效率问题

###### 空间问题

##### 复制算法 Copying

- 将可用内存划分为两块；
- 当一块用完时，将存活对象复制到另一块nei'cun

###### 问题：内存使用率

###### 适用：存活率低的场景

###### 例子：Eden + 2 Suvivor

##### 标记整理 Mark-Compact

- 标记出所有需要回收的对象
- 让所有存活对象都向一端移动	

###### 例子：老年代

#### GC收集器

##### 实现

###### 枚举根节点

- 会发生停顿
- OopMap: 虚拟机知道哪些地方存放着对象yin'yong

###### 安全点

程序执行时，只有达到安全点时才能暂停：
- 方法调用
- 循环跳转
- 异常跳转


####### 抢先式中断

少用。
- 先中断全部线程
- 如果发现有线程中断的地方不在安全点上，则恢复线程


####### 主动式中断

- GC简单地设置一个标志
- 线程执行时主动轮询这个标志，当为真时则自己中断挂起

###### 安全区域

在一段代码片段中，引用关系不会发生变化，在这个区域中任意地方开始GC都是安全的。

##### Serial (Serial Old)

###### Stop The World

###### 新生代：复制算法

###### 老年代：标记整理

##### ParNew

###### 新生代：多个GC线程

###### 其余和Serial一致，STW

##### Parallel Scavenge (Parallel Old)

###### 目标：吞吐量；而其他算法关注缩短停顿时间

###### GC停顿时间会牺牲吞吐量和新生代空间

###### 适合后台运算任务，不需要太多的交互

###### 可设置MaxGCPauseMillis, GCTimeRatio

###### 其余类似ParNew

##### CMS, Concurrent Mark Sweep

###### 目标：减少回收停顿时间

###### 标记清除算法，其他收集器用标记整理

###### 步骤

####### 1.初始标记 (单线程，STW)

- Stop The World
- 标记GC roots直接关联到的对象

####### 2.并发标记 (单线程，并发)

- 耗时长，但与用户线程一起工作
- GC Roots Tracing 可达性分析

####### 3.重新标记 (多线程，STW)

- Stop The World
- 修正上一步并发标记期间发生变动的对象

####### 4.并发清除 (单线程，并发)

- 与用户线程一起工作

###### 缺点

####### CPU资源敏感，吞吐量会降低

并发阶段会占用线程

####### 无法处理浮动垃圾，可能Concurrent Mode Failure

- CMS不能等到老年代几乎满时再收集，要预留空间给浮动垃圾；
- 否则会导致预留的内存无法满足程序需要，出现Concurrent Mode Failure，临时启用Serial Old收集，停顿时间长。  

`CMSInitiatingOccupancyFraction`不宜过高

####### 标记清除，产生内存碎片

##### G1

###### 特点

####### 并行与并发

####### 分代收集

G1将堆划分为大小相等的`Region`，新生代和老年代不再是物理隔离的

####### 空间整合 -标记整理

####### 可预测的停顿

允许使用者明确指定M毫秒内GC时间不得超过N毫秒

###### 步骤

####### 1.初始标记 (单线程，STW)

####### 2.并发标记 (单线程，并发)

- 耗时长，但与用户线程一起工作
- GC Roots Tracing 可达性分析

####### 3.最终标记 (多线程，STW)

####### 4.筛选回收 (多线程)

#### 内存分配策略

##### 对象优先在Eden分配

如果Eden空间不足，则出发MinorGC


##### 大对象直接进入老年代

PretenureSizeThreshold

##### 长期存活的对象进入老年代

MaxTenuringThreshold

##### 动态对象年龄判断

如果Survivor中相同年龄对象的总大小 > Survivor的一半；则该年龄及更老的对象 直接进入lao'nian'dai

##### 空间分配担保: 可能触发FullGC

- MinorGC之前，检查老年代最大可用的连续空间，是否大于新生代所有对象总空间。如果大于，则MinorGC是安全的。
- 否则要进行一次FullGCC.

### 工具

#### 命令行工具

##### jps -l

##### jstat -class/-gc

jstat -gc vmid [interval] [count]

- class
- gc
- gccause



##### jinfo: 查看修改VM参数

修改参数：
- jinfo -flag name=value
- jinfo -flag[+|-] name

##### jmap: 生成heap dump

jmap -dump:live,format=b,file=/pang/logs/tomcat/heapdump.bin 1

##### jhat: 简单分析heap dump 

##### jstack -l: 生成thread dump

##### jcmd

#### GUI工具

##### jconsole

查看MBean


##### VisualVM

## 并发

### synchronized

#### 三种使用方式：修饰实例方法、静态方法、代码块

#### 原理

##### 同步块：Java对象头中有monitor对象；
monitorenter指令：计数器+1
monitorexit指令：计数器-1

##### 同步方法：ACC_SYNCHRONIZED 标识

#### Java6的优化

##### 偏向锁，轻量级锁，自旋锁；
锁消除，锁粗化

#### 对比ReentrantLock

##### 依赖于JVM vs. 依赖于API

##### Lock增加了高级功能：可中断等待，可实现公平锁

##### 等待通知机制：wait/notify vs. condition

##### 性能已不是选择标准

#### 对比volatile

##### volatile是线程同步的轻量级实现，只能修饰变量

##### volatile只保证可见性，不保证原子性

##### 对线程访问volatile字段不会发生阻塞，而sync会阻塞

### 线程池

#### execute() vs. submit()

##### execute不需要返回值，无法判断任务是否执行成功

#### 构造参数

#### 线程池大小设置

##### CPU密集型任务：线程池尽可能小

##### IO密集型任务：线程池尽可能大

##### 公式：
最佳线程数目 = （（线程等待时间+线程CPU时间）/线程CPU时间 ）* CPU数目
最佳线程数目 = （线程等待时间与线程CPU时间之比 + 1）* CPU数目

### Atomic原子类

#### 类型

##### 基本类型：AtomicInteger, AtomicLong

##### 数组类型：AtomicIntegerArray, Atomic ReferenceArray

AtomicIntegerArray, AtomicLongArray, Atomic ReferenceArray

##### 引用类型：AtomicReference, AtomicStampedReference

##### 对象属性修改：AtomicIntegerFieldUpdater, AtomicStampedReference

#### 原理

##### CAS + volatile

- 利用 `CAS` (compare and swap) + `volatile` 和 native 方法来保证原子操作，从而避免 `synchronized` 的高开销，执行效率大为提升。

- CAS的原理是拿期望的值和原本的一个值作比较，如果相同则更新成新的值。`UnSafe.objectFieldOffset()` 方法是一个本地方法，这个方法是用来拿到“原来的值”的内存地址，返回值是 valueOffset。另外 value 是一个volatile变量，在内存中可见，因此 JVM 可以保证任何时刻任何线程总能拿到该变量的最新值。

### AQS

### Unsafe

## 分布式

### Redis

#### AP: 最终一致性

- Redis 的主从数据是 **异步同步** 的，所以分布式的 Redis 系统并不满足「一致性」要求。
- Redis 保证「最终一致性」，从节点会努力追赶主节点，最终从节点的状态会和主节点的状态将保持一致。

#### 数据类型

##### string

set name codehole
set age 30
incr age
incrby age 5

###### SDS, Simple Dynamic String

####### capacity

####### len

####### content：以字节\0结尾

####### 扩容：< 1M 之前，加倍扩容； > 1M后，每次扩容1M 

##### hash

hset books java "think in java"


###### dict -> dictht[2] -> disctEntry

####### 扩容时机：元素的个数等于第一维数组的长度; 
bgsave时不扩容，除非达到dict_force_resize_ratio

####### 扩容：扩容 2 倍

####### 缩容：元素个数低于数组长度10%时

####### ziplist: 元素个数较小时，用ziplist节约空间

压缩列表是一块连续的内存空间，元素之间紧挨着存储，没有任何冗余空隙。

####### 渐进式rehash

它会同时保留旧数组和新数组，然后在定时任务中以及后续对 hash 的指令操作中渐渐地将旧数组中挂接的元素迁移到新数组上。这意味着要操作处于 rehash 中的字典，需要同时访问新旧两个数组结构。如果在旧数组下面找不到元素，还需要去新数组下面去寻找。

##### list

rpush books python java golang

###### 早期：元素少时用 ziplist，元素多时用 linkedlist

###### 后期：quicklist

`quicklist` 是 `ziplist` 和 `linkedlist` 的混合体，
- 它将 `linkedlist` 按段切分，
- 每一段使用 `ziplist` 来紧凑存储，多个 `ziplist` 之间使用双向指针串接起来。



##### set

 sadd books python
 

###### IntSet: 当元素都是整数并且个数较小时，使用 intset 来存储

intset 是紧凑的数组结构，同时支持 16 位、32 位和 64 位整数。

##### zset

zadd books 9.0 "think in java"

它类似于 Java 的 SortedSet 和 HashMap 的结合体，一方面它是一个 set，保证了内部 value 的唯一性，另一方面它可以给每个 value 赋予一个 score，代表这个 value 的排序权重。它的内部实现用的是一种叫做「跳跃列表」的数据结构。

###### ziplist: 元素个数较小时，用ziplist节约空间

###### hash: value -> score

hash 结构来存储 value 和 score 的对应关系

###### skiplist: 二分查找

skiplist提供指定 score 的范围来获取 value 列表的功能，二分查找

#### 原理

##### 通讯协议：RESP, Redis Serialization Protocal

单行字符串 以 + 符号开头。
多行字符串 以 $ 符号开头，后跟字符串长度。
整数值 以 : 符号开头，后跟整数的字符串形式。
错误消息 以 - 符号开头。
数组 以 * 号开头，后跟数组的长度。

##### 多路复用

###### 指令队列

Redis 会将每个客户端套接字都关联一个指令队列。客户端的指令通过队列来排队进行顺序处理，先到先服务。

###### 响应队列

Redis 服务器通过响应队列来将指令的返回结果回复给客户端。 
- 如果队列为空，那么意味着连接暂时处于空闲状态，不需要去获取写事件，也就是可以将当前的客户端描述符从write_fds里面移出来。等到队列有数据了，再将描述符放进去。避免select系统调用立即返回写事件，结果发现没什么数据可以写。出这种情况的线程会飙高 CPU。

###### epoll事件轮询API

最简单的事件轮询 API 是select函数，它是操作系统提供给用户程序的 API。
输入是读写描述符列表read_fds & write_fds，输出是与之对应的可读可写事件。

同时还提供了一个timeout参数，如果没有任何事件到来，那么就最多等待timeout时间，线程处于阻塞状态。

一旦期间有任何事件到来，就可以立即返回。时间过了之后还是没有任何事件到来，也会立即返回。拿到事件后，线程就可以继续挨个处理相应的事件。处理完了继续过来轮询。于是线程就进入了一个死循环，我们把这个死循环称为事件循环，一个循环为一个周期。

##### pipeline

客户端通过改变了读写的顺序带来的性能的巨大提升

##### 事务

###### multi/exec/discard

- 所有的指令在 exec 之前不执行，而是缓存在服务器的一个事务队列中，服务器一旦收到 exec 指令，才开执行整个事务队列，执行完毕后一次性返回所有指令的运行结果。

- 因为 Redis 的单线程特性，它不用担心自己在执行队列的时候被其它指令打搅，可以保证他们能得到的「原子性」执行

###### 隔离性

###### 不能保证原子性

Redis 的事务根本不能算「原子性」，而仅仅是满足了事务的「隔离性」，隔离性中的串行化——当前执行的事务有着不被其它事务打断的权利。

###### 结合pipeline

较少网络IO

pipe = redis.pipeline(transaction=true)
pipe.multi()
pipe.incr("books")
pipe.incr("books")
values = pipe.execute()

###### watch

服务器收到了 exec 指令要顺序执行缓存的事务队列时，Redis 会检查关键变量自 watch 之后，是否被修改了 (包括当前事务所在的客户端)。如果关键变量被人动过了，exec 指令就会返回 null 回复告知客户端事务执行失败，这个时候客户端一般会选择重试。

> watch books
OK
> incr books  # 被修改了
(integer) 1
> multi
OK
> incr books
QUEUED
> exec  # 事务执行失败
(nil)

##### 过期策略

###### 惰性策略

###### 定时扫描

Redis 默认会每秒进行十次过期扫描，过期扫描不会遍历过期字典中所有的 key，而是采用了一种简单的贪心策略。

从过期字典中随机 20 个 key；
删除这 20 个 key 中已经过期的 key；
如果过期的 key 比率超过 1/4，那就重复步骤 1；

###### 实践：过期时间随机化

#### 持久化

##### 快照

###### 触发条件

####### 异步BGSAVE

####### 同步SAVE

####### 配置文件 save 900 1

save after 900 seconds if there is at least 1 change to the dataset

####### SHUTDOWN命令时

####### 从节点SYNC时 (=BGSAVE)

###### fork子进程生成快照


- 调用 glibc 的函数fork产生一个子进程，快照持久化完全交给子进程来处理，父进程继续处理客户端请求。

###### COW (Copy On Write)

使用操作系统的 COW 机制来进行数据段页面的分离。
当父进程对其中一个页面的数据进行修改时，会将被共享的页面复制一份分离出来，然后对这个复制的页面进行修改。这时子进程相应的页面是没有变化的，还是进程产生时那一瞬间的数据。

##### AOF

- 先执行指令才将日志存盘.
- 可用 `bgrewriteaof` 指令对 AOF 日志进行瘦身。

###### 触发条件

####### always

####### every second

####### no

###### AOF重写：bgrewriteaof

##### 混合

在 Redis 重启的时候，可以先加载 rdb 的内容，然后再重放增量 AOF 日志。比 AOF 全量文件重放要快很多。

##### 持久化操作主要在从节点进行

#### 集群

##### 主从

###### slave of

##### Sentinel

######  Sentinel 集群可看成是一个 ZooKeeper 集群

###### 消息丢失

####### min-slaves-to-write 1

主节点必须至少有一个从节点在进行正常复制，否则就停止对外写服务，丧失可用性

####### min-slaves-max-lag 10

如果 10s 没有收到从节点的反馈，就意味着从节点同步不正常

##### codis

###### 用zookeeper存储槽位关系

###### 代价

- 不支持事务；
- 同样 rename 操作也很危险；
- 为了支持扩容，单个 key 对应的 value 不宜过大。
- 因为增加了 Proxy 作为中转层，所有在网络开销上要比单个 Redis 大。
- 集群配置中心使用 zk 来实现，意味着在部署上增加了 zk 运维的代价

##### cluster

###### slots

####### 16384

####### 槽位信息存储于每个节点中

######## Rax

`Rax slots_to_keys` 用来记录槽位和key的对应关系
- Radix Tree 基数树


####### 定位：crc16(key) % 16384

###### 跳转：MOVED

当客户端向一个错误的节点发出了指令，该节点会向客户端发送MOVED，告诉客户端去连正确的节点。 
 GET x
 -MOVED 3999 127.0.0.1:6381

###### 扩展性：迁移slot (同步)

- Redis 迁移的单位是槽，当一个槽正在迁移时，这个槽就处于中间过渡状态。这个槽在原节点的状态为`migrating`，在目标节点的状态为`importing`，  

- 客户端先尝试访问旧节点，如果对应的数据不在旧节点里面，旧节点会向客户端返回一个`-ASK targetNodeAddr`的重定向指令。

- 客户端收到这个重定向指令后，先去目标节点执行一个不带任何参数的`asking`指令，然后在目标节点再重新执行原先的操作指令。
(在迁移没有完成之前，这个槽位还是不归新节点管理的，它会向客户端返回一个`-MOVED`重定向指令告诉它去源节点去执行。如此就会形成 `重定向循环`。asking指令的目标就是打开目标节点的选项，告诉它下一条指令不能不理，而要当成自己的槽位来处理。)

- 迁移过程是同步的，在目标节点执行`restore指令`到原节点删除key之间，原节点的主线程会处于阻塞状态，直到key被成功删除。 >> 要尽可能避免大key

原理：
- 以原节点作为目标节点的「客户端」
- 原节点对当前的key执行dump指令得到序列化内容，然后发送指令restore携带序列化的内容作为参数
- 目标节点再进行反序列化就可以将内容恢复到目标节点的内存中
- 原节点收到后再把当前节点的key删除


####### dump

####### restore

####### remove

###### 主从复制 (异步)：SYNC snapshot + backlog队列

- slave启动时，向master发起 `SYNC` 命令。

- master收到 SYNC 后，开启 `BGSAVE` 操作，全量持久化。

- BGSAVE 完成后，master将 `snapshot` 发送给slave.

- 发送期间，master收到的来自clien新的写命令，正常响应的同时，会再存入一份到 `backlog 队列`。

- snapshot 发送完成后，master会继续发送backlog 队列信息。

- backlog 发送完成后，后续的写操作同时发给slave，保持实时地异步复制。

####### 快照同步

####### 增量同步

异步将 buffer 中的指令同步到从节点，从节点一边执行同步的指令流来达到和主节点一样的状态，一边向主节点反馈自己同步到哪里了 (偏移量)。

####### 无盘复制

无盘复制是指主服务器直接通过套接字将快照内容发送到从节点，生成快照是一个遍历的过程，主节点会一边遍历内存，一边将序列化的内容发送到从节点，从节点还是跟之前一样，先将接收到的内容存储到磁盘文件中，再进行一次性加载。


####### wait 指令

wait 指令可以让异步复制变身同步复制，确保系统的强一致性。
- `wait N t`: 等待 wait 指令之前的所有写操作同步到 N 个从库，最多等待时间 t。

###### 读写分离: READONLY 命令

- 默认情况下，某个slot对应的节点一定是一个master节点。客户端通过`MOVED`消息得知的集群拓扑结构也只会将请求路由到各个master中。

- 即便客户端将读请求直接发送到slave上，slave也会回复MOVED到master的响应。

- 为此，Redis Cluster引入了 `READONLY` 命令，客户端向slave发送READONLY命令后，slave对于读操作将不再返回moved，而是直接处理。

###### 主从切换: gossip PFAIL / FAIL

###### Failover: Master选举

如果B已被集群公认为是FAIL状态了，则其slave会发起竞选，期望成为新的master。

- 在竞选前，slave间会协商优先级，优先级高的slave更有可能更早地发起选举。优先级最重要的决定因素是`slave最后一次同步master信息的时间`，越新表示这个slave数据越新，竞选优先级越高。

- slave通过向其他master发送FAILOVER_AUTH_REQUEST消息发起竞选，master回复FAILOVER_AUTH_ACK告知是否同意。

###### 一致性: 保证朝着epoch值更大的信息收敛

保证朝着epoch值更大的信息收敛: 每一个Node都保存了集群的配置信息`clusterState`。

- `currentEpoch`表示集群中的最大版本号，集群信息每更新一次，版本号自增。
- nodes列表，表示集群所有节点信息。包括该信息的版本epoch、slots、slave列表

配置信息通过Redis Cluster Bus交互(PING / PONG, Gossip)。
- 当某个节点率先知道信息变更时，将currentEpoch自增使之成为集群中的最大值。
- 当收到比自己大的currentEpoch，则更新自己的currentEpoch使之保持最新。
- 当收到的Node epoch大于自身内部的值，说明自己的信息太旧、则更新为收到的消息。


#### 应用

##### 分布式锁

###### setnx + expire

###### set xx ex 5 nx

###### 集群问题

主节点挂掉时，从节点会取而代之，客户端上却并没有明显感知。原先第一个客户端在主节点中申请成功了一把锁，但是这把锁还没有来得及同步到从节点，主节点突然挂掉了。然后从节点变成了主节点，这个新的节点内部没有这个锁，所以当另一个客户端过来请求加锁时，立即就批准了。这样就会导致系统中同样一把锁被两个客户端同时持有

####### Redlock算法

过半节点加锁成功


##### 延时队列

###### lpush / rpush 

###### rpop / lpop -> brpop / blpop

##### 位图

位图不是特殊的数据结构，它的内容其实就是普通的字符串，也就是 byte 数组。我们可以使用普通的 get/set 直接获取和设置整个位图的内容，也可以使用位图操作 getbit/setbit 等将 byte 数组看成「位数组」来处理。

###### setbit

零存：`setbit s 4 1`
整存：`set s <string>`

###### getbit

整取：`get s`
零取：`getbit s 1`


###### bitcount统计

###### bitpos查找

###### bitfield操作多个位

##### HyperLogLog

HyperLogLog 提供不精确的去重计数方案

###### pfadd

###### pfcount

###### pfmerge

##### 布隆过滤器

布隆过滤器能准确过滤掉那些已经看过的内容，那些没有看过的新内容，它也会过滤掉极小一部分 (误判)

###### bf.add / bf.madd

###### bf.exists / bf.mexists

##### 简单限流: zset实现滑动时间窗口

###### key: clientId-actionId

###### value: ms

###### score: ms

##### 漏斗限流: redis-cell模块

###### cl.throttle key capacity count period 1

##### GeoHash

GeoHash 算法将二维的经纬度数据映射到一维的整数，这样所有的元素都将在挂载到一条线上，距离靠近的二维坐标映射到一维后的点之间距离也会很接近。

###### geoadd

###### geodist

###### geopos

###### geohash

##### 搜索key

###### keys

- 没有 offset、limit 参数，一次性吐出所有满足条件的 key。
- keys 算法是遍历算法，复杂度是 O(n)

###### scan

scan <cursor> match <regex> count <limit>
在 Redis 中所有的 key 都存储在一个很大的字典中，scan 指令返回的游标就是第一维数组的位置索引，limit 参数就表示需要遍历的槽位数
  

##### Stream

##### PubSub

#### 运维

##### 内存回收

###### 无法保证立即回收已经删除的 key 的内存

###### flushdb

##### eviction

###### LRU: Least Recently Used

当字典的某个元素被访问时，它在链表中的位置会被移动到表头。

所以链表的元素排列顺序就是元素最近被访问的时间顺序。

位于链表尾部的元素就是不被重用的元素，所以会被踢掉。

- 缺点：需要大量的额外的内存


###### 近似LRU

- **随机**采样出 5(可以配置) 个 key，
- 然后淘汰掉最旧的 key，
- 如果淘汰后内存还是超出 maxmemory，那就继续随机采样淘汰，直到内存低于 maxmemory 为止。

Redis给每个 key 增加了一个额外的小字段，这个字段的长度是 24 个 bit，也就是最后一次被访问的时间戳。



###### LFU: Least Frequently Used

##### 保护

###### rename-command flushall ""

######  spiped: SSL代理

##### 懒惰删除

###### del -> unlink

###### flushdb -> flushdb async

### ZooKeeper

#### 特性

##### 顺序一致性

对同一个客户端来说

##### 原子性

所有事务请求的处理结果在集群中所有机器上的应用情况是一致的。


##### 单一视图

客户端无论连接哪个zk服务器，看到的数据模型都是一致的


##### 可靠性

##### 实时性

#### CP (ZAB协议保证一致性)

Zookeeper Atomic Broadcast.
- 所有事务请求必须由全局唯一的Leader服务器来协调处理。
- Leader将客户端的事务请求转换成一个`事务Proposal`，将其分发给所有Follower，并等待Follower的反馈.
- 一旦超过半数的Follower进行了正确的反馈，则Leader再次向所有Follower分发`commit消息`，要求其将前一个Proposal进行提交


##### 单一主进程

- 使用单一的主进程来接收并处理所有事务请求（事务==写），
- 并采用ZAB原子广播协议，将服务器数据的状态变更以事务Proposal的形式广播到所有副本进程。

###### 单一的主进程来接收并处理所有事务请求

###### 对每个事务分配全局唯一的ZXID

####### zxid低32位：单调递增计数器

对每个事务请求，生成Proposal时，将计数器+1


####### zxid高32位：Leader周期的epoch编号

###### 数据的状态变更以事务Proposal的形式广播到所有副本进程

##### 顺序应用

必须能保证一个全局的变更序列被顺序应用，从而能处理依赖关系

##### 1. (发现)崩溃恢复模式：选举要保证新选出的Leader拥有最大的ZXID

崩溃恢复：Leader服务器出现宕机，或者因为网络原因导致Leader服务器失去了与过半 Follower的联系，那么就会进入崩溃恢复模式从而选举新的Leader。
当选举产生了新的Leader，同时集群中有过半的服务器与该Leader完成了状态同步（即数据同步）之后，Zab协议就会退出崩溃恢复模式，进入消息广播模式。

######  

则可保证新Leader一定具有所有已提交的Proposal；
同时保证丢弃已经被跳过的Proposal

##### 2. (同步) 检查是否完成数据同步

###### 对需要提交的，重新放入Proposal+Commit

###### 对于Follower上尚未提交的Proposal，回退

###### 同步阶段的引入，能有效保证Leader在新的周期中提出Proposal之前，
所有的进程都已经完成了对之前所有Proposal的提交。

##### 3. (广播) 消息广播模式：Proposal (ZXID), ACK, Commit

消息广播：所有的事务请求都会转发给Leader，Leader会为事务生成对应的Proposal，并为其分配一个全局单调递增的唯一ZXID。
当Leader接受到半数以上的Follower发送的ACK投票，它将发送Commit给所有Follower通知其对事务进行提交，Leader本身也会提交事务，并返回给处理成功给对应的客户端。

###### 1.Leader为事务生成对应的Proposal，分配ZXID

必须将每一个事务Proposal按照其ZXID的先后顺序来进行排序与处理。

###### 2.半数以上的Follower回复ACK投票

###### 3.发送Commit给所有Follower通知其对事务进行提交

###### 4.返回给处理成功给对应的客户端

###### 类似一个2PC提交，移除了中断逻辑

#### 原理

##### version保证原子性

###### version表示修改次数

###### 乐观锁

##### watcher

###### 特性

####### 一次性

####### 客户端串行执行

####### 轻量

######## 推拉结合

######## 注册watcher时只传输ServerCnxn

###### 流程

####### 客户端注册Watcher

####### 客户端将Watcher对象存到WatchManager: Map<String, Set<Watcher>>

####### 服务端存储ServerCnxn

######## watchTable: Map<String, Set<Watcher>>

######## watch2Paths: Map<Watch, Set<String>>

####### 服务端触发通知

######## 1.封装WatchedEvent

######## 2.查询并删除Watcher

######## 3.process: send response (header = -1)

####### 客户端执行回调

######## 1.SendThread接收通知， 放入EventThread

NIO

######## 2.查询并删除Watcher

######## 3.process: 执行回调

###### WatchedEvent

网络传输时序列化为 `WatcherEvent`

####### KeeperState

- SyncConnected
- Disconnected
- Expired
- AuthFailed


####### EventType

- None
- NodeCreated
- NodeDeleted
- NodeDataChanged
- NodeChildrenChanged

##### ACL

###### Scheme

####### IP:192.168.1.1:permission

####### Digest:username:sha:permission

####### World:anyone:permission

####### Super:username:sha:permission

###### Permission

####### C, Create

####### D, Delete

####### R, Read

####### W, Write

####### A, Admin

###### 权限扩展体系

####### 实现AuthenticationProvider

####### 注册

######## 系统属性 -Dzookeeper.authProvider.x=

######## zoo.cfg: authProvider.x=

##### 客户端

###### 通讯协议

####### 请求

######## RequestHeader

######### xid

记录客户端发起请求的先后顺序

######### type

- 1: OpCode.Create
- 2: delete
- 4: getData


######## Request

####### 响应

######## ReplyHeader

######### xid

原值返回


######### zxid

服务器上的最新事务ID


######### err

######## Response

###### ClientCnxn：网络IO

####### outgoingQueue

待发送的请求Packet队列

####### pendingQueue

已发送的、等待服务端响应的Packetdui'lie

####### SendThread: IO线程

####### EventThread: 事件线程

######## waitingEvents队列

##### Session

###### SessionID: 服务器myid + 时间戳

###### SessionTracker: 服务器的会话管理器

####### 内存数据结构

######## sessionById:     HashMap<Long, SessionImpl>

######## sessionWithTimeout: ConcurrentHashMap<Long, Integer>

######## sessionSets:     HashMap<Long, SessionSet>超时时间分桶

####### 分桶策略

- 将类似的会话放在同一区块进行管理。
- 按照“下次超时时间”
- 好处：清理时可批量处理

####### 会话激活

- 心跳检测
- 重新计算下一次超时时间
- 迁移到新区块


######## 客户端发送任何请求时

######## sessionTimeout / 3时，发送PING请求

####### 超时检测：独立线程，逐个清理

####### 会话清理

######## 1. isClosing设为true

######## 2.发起“会话关闭”请求

######## 3.收集需要清理的临时节点

######## 4.添加“节点删除”事务变更

######## 5.删除临时节点

######## 6.移除会话、关闭NIOServerCnxn

####### 重连

######## 连接断开

- 断开后，客户端收到None-Disconnected通知，并抛出异常`ConnectionLossException`；
- 应用需要捕获异常，并等待客户端自动完成重连；
- 客户端自动重连后，收到None-SyncConnected通知

######## 会话失效

- 自动重连时 超过了会话超时时间。
- 应用需要重新实例化ZooKeeper对象，重新恢复临时数据

######## 会话转移

- 服务端收到请求时，检查Owner 如果不是当前服务器则抛出`SessionMovedExceptio`

#### 角色

##### Leader: 读写

##### Follower: 读。参与Leader选举、过半写成功

##### Observer: 读

#### 应用

##### 配置中心：数据发布订阅

推拉结合的方式：
- 推：节点数据发生变化后，发送Watcher事件通知给客户端。
- 拉：客户端收到通知后，需要主动到服务端获取最新的数据


##### 负载均衡：域名注册、发现

##### 命名服务：全局ID生成器（顺序节点）

##### 分布式协调、通知：任务注册、任务状态记录

##### 集群管理：分布式日志收集系统、云主机管理

日志收集系统要解决：
1. 变化的日志源机器
2. 变化的收集器机器

- 注册日志收集器，非临时节点：`/logs/collectors/[host]`
- 节点值为日志源机器。
- 创建子节点，记录状态信息 `/logs/collectors/[host]/status`
- 系统监听collectors节点，当有新收集器加入，或有收集器停止汇报，则要将之前分配给该收集器的任务进行转移。

##### Master选举：

利用zk强一致性，保证客户端无法重复创建已存在的节点

##### 分布式锁

###### 排他锁

排他锁，X锁（Exclusive Locks），写锁，独占锁：
- 同时只允许一个事务，其他任何事务不能进行任何类型的操作。
- 创建临时节点


###### 共享锁

共享锁，S锁（Shared Locks），读锁：
- 加共享锁后当前事务只能进行读操作；其他事务也只能加共享锁。
- `W`操作必须在当前没有任务事务进行读写操作时才能进行。
- 创建临时顺序节点 `/s_locks/[hostname]-请求类型-序号`。
- 节点上表明是`R`还是`W`。
- 如果是`R`，且比自己序号小的节点都是`R`，则加锁成功；
- 如果是`W`，如果自己是最小节点，则加锁成功。

优化：
R节点只需要监听比他小的最后一个W节点；
W节点只需要监听比他小的最后一个节点。

##### 分布式队列

###### FIFO

- 注册临时顺序节点；
- 监听比自己小的最后一个节点。

###### Barrier

- 父节点`/queue_barrier`，值为需要等待的节点数目N。
- 监听其子节点数目；
- 统计子节点数目，如果数目小于N，则等待

##### 实例

###### Hadoop

####### ResourceManager HA

多个ResourceManager并存，但只有一个处于Active状态。
- 有父节点 `yarn-leader-election/pseudo-yarn-rm-cluster`, RM启动时会去竞争Lock**临时**子节点。
- 只有一个RM能竞争到，其他RM注册Wather


####### ResourceManager 状态存储

Active状态的RM会在初始化阶段读取 `/rmstore` 上的状态信息，并据此信息继续进行相应的chu'li

###### HBase

####### RegionServer系统容错

####### RootRegion管理

###### Kafka

####### Broker注册: /broker/ids/[brokerId]

`/broker/ids/[BrodkerId]` (临时节点)

####### Topic注册: /brokers/topics/[topic]

每个topic对应一个节点`/brokers/topics/[topic]`；
Broker启动后，会到对应Topic节点下注册自己的ID **临时节点**，并写入Topic的分区总数，`/brokers/topics/[topic]/[BrokerId] --> 2`


####### Producer负载均衡

Producer会监听`Broker的新增与减少`、`Topic的新增与减少`、`Broker与Topic关联关系的变化`

####### Consumer负载均衡:  /consumers/[groupId]/owners/[topic]/[brokerId-partitionId]

当消费者确定了对一个分区的消费权利，则将其ConsumerId写入到分区**临时节点**上：
`/consumers/[groupId]/owners/[topic]/[brokerId-partitionId] --> consumerId`

####### Consumer注册: /consumers/[groupId]/ids/[consumerId]

- 消费者启动后，注册**临时节点** `/consumers/[groupId]/ids/[consumerI]`。并将自己订阅的Topic信息写入该节点 
- 每个消费者都会监听ids子节点。
- 每个消费者都会监听Broker节点：`/brokers/ids/`

####### 消费进度offset记录: /consumers/[groupId]/offsets/[topic]/[brokerId-partitionId]

消费者重启或是其他消费者重新接管消息分区的消息消费后，能够从之前的进度开始继续进行消费：`/consumers/[groupId]/offsets/[topic]/[brokerId-partitionId] --> offset`

### Kafka

#### 集群

##### 集群成员：对等，没有中心主节点

### Memcached

## MySql

### 内核

### ETL

### 索引

#### 原理

##### B Tree

M阶B Tree: 
- 每个非叶子结点至多有M个儿子，至少有M/2个儿子；
- 根节点至少有2个儿子；
- 所有叶子节点在同一层。

##### B+ Tree

叶子节点才是真正的原始数据


##### 与二叉树的区别

- 二叉树：优化比较次数
- B/B+树：优化磁盘读写次数

#### 分类

##### 簇索引

每个表至多一个，一般为主键索引。
- B Tree上存储的就是数据本身

##### 非簇索引

- B Tree上存储的是指针

### 事务

#### select xx for update: 锁住行

#### where stock=xx: 乐观锁

### 连接池

#### 配置参数

##### maxLive (100)

##### maxIdle (100)

##### minIdle (10)

##### initialSize (10)

##### maxWait (30s)

当第101个请求过来时候，等待30s; 30s后如果还没有空闲链接，则报错

#### 由客户端维护

## Netty

### 通讯方式

#### BIO: 同步阻塞

#### NIO: 同步非阻塞

#### AIO: 异步非阻塞

## 微服务

### Spring Cloud

### service mesh

#### consumer端 sidecar

##### 服务发现

##### 负载均衡

##### 熔断降级

#### provider端 sidecar

##### 服务注册

##### 限流降级

##### 监控上报

#### Control Plane

##### 服务注册中心

##### 负载均衡配置

##### 请求路由规则

##### 配额控制

## Spring

### Bean

#### 生命周期

#### 作用域

##### singleton

##### prototype

每次getBean都会创建一个Bean，如果是cglib动态代理，则性能不佳


##### request

##### session

##### globalSession

##### 作用域依赖问题

prototype --> request, 动态代理

#### FactoryBean: 定制实例化Bean的逻辑

#### 配置方式

##### XML配置

##### Groovy配置

##### 注解配置

###### @Component

###### @Service

##### Java类配置

###### @Configuration

###### @Import

参考 `@EnableWebMvc`

####### 还可以@Import(ImportSelector.class)

更加灵活，可以增加条件分支，参考`@EnableCaching`

###### @Bean

#### 创建流程

##### ResourceLoader: 装载配置文件 --> Resouce

##### BeanDefinitionReader: 解析配置文件 --> BeanDefinition，并保存到BeanDefinitionRegistry

##### BeanFactoryPostProcessor: 对BeanDefinition进行加工

###### 对占位符<bean>进行解析

###### 找出实现PropertyEditor的Bean, 注册到PropertyEditorResistry

##### InstantiationStrategy: 进行Bean实例化

###### SimpleInstantiationStrategy

###### CglibSubclassingInstantiationStrategy

##### BeanWapper: 实例化时封装

###### Bean包裹器

###### 属性访问器：属性填充

###### 属性编辑器注册表

属性编辑器：将外部设置值转换为JVM内部对应类型


##### BeanPostProcessor

### DI

#### Bean Factory: IoC容器

#### ApplicationContext: 应用上下文，Spring容器

#### 依赖注入

##### 属性注入

##### 构造函数注入

##### 工厂方法注入

##### 注解默认采用byType自动装配策略

#### 条件装配

##### @Profile

##### @Conditional

例： OnPropertyCondition

### AOP

#### 术语

##### JoinPoint 连接点

AOP黑客攻击的候选锚点
- 方法
- 相对位置


##### Pointcut 切点

定位到某个类的某个方法

##### Advice 增强

- AOP黑客准备的木马
- 以及方位信息


##### Target 目标对象

Advice增强逻辑的织入目标类

##### Introduction 引介

为类添加属性和方法，可继承 `DelegatingIntroductionInterceptor`

##### Weaving 织入

将Advice添加到目标类的具体连接点上的过程。

##### Aspect 切面

Aspect = Pointcut + Advice？


#### 原理

##### JDK动态代理

##### CGLib动态代理

###### 不要求实现接口

###### 不能代理final 或 private方法

###### 性能比JDK好，但是创建花费时间较长

#### 用法

##### 编程方式

###### ProxyFactory.addAdvice / addAdvisor

ProxyFactory.setTarget
ProxyFactory.addAdvice
ProxyFactory.getProxy() --> Target

```
public void addAdvice(int pos, Advice advice) {
  this.addAdvisor(pos, new DefaultPointcutAdvisor(advice));
}
```

###### 配置ProxyFactoryBean

<bean class="aop.ProxyFactoryBean"
p:target-ref="target"
p:interceptorNames="advice or adviso">
  
  

###### 自动创建代理

基于BeanPostProcessor实现，在容器实例化Bean时 自动为匹配的Bean生成代理实例。


####### BeanNameAutoProxyCreator

基于Bean配置名规则

####### DefaultAdvisorAutoProxyCreator

基于Advisor匹配机制

####### AnnotationAwareAspectJAutoPRoxyCreator

##### AspectJ

###### <aop:aspectj-autoproxy>

- 自动为匹配`@AspectJ`切面的Bean创建代理，完成切面织入。
- 底层通过 `AnnotationAwareAspectJAutoProxyCreator`实现。

### 外部属性文件

#### PropertyPlaceholderConfigurer

##### 覆盖其convertProperty方法可实现加密属性值

#### <context:property-placeholder location=/>

### 国际化

#### Java支持

##### ResourceBundle.getBundle

##### MessageFormat.format

#### MessageResource

```
@Bean
	public MessageSourceAccessor messageSourceAccessor() {
		return new MessageSourceAccessor(messageSource());
	}

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setBasenames("classpath:vitamin/messages/message");
		return messageSource;
	}
```    

##### MessageSourceAccessor

##### ApplicationContext继承MessageResource

### 容器事件

#### 事件

##### ApplicationContextEvent

容器的启动、刷新、停止、关闭

##### RequestHandledEvent

HTTP请求被处理


#### 监听器

##### ApplicationListener

实现 `onApplicationEvent(E event)`

#### 事件源

#### 监听器注册表

##### ApplicationEventMulticaster

#### 事件广播器

##### ApplicationEventMulticaster

#### 事件发送者

##### 实现ApplicationContextAware

##### ctx.publishEvent()

## Spring Boot

### 模式注解

#### 派生性

#### 层次性

### 自动装配

#### 1.@EnableAutoConfiguration

#### 2. XXAutoConfiguration

##### 条件判断 @Conditional

##### 模式注解 @Configuration

##### @Enable模块：@EnableXX -> *ImportSelector -> *Configuration

#### 3.配置spring.factories (SpringFactoriesLoader)

### 源码

#### SpringApplication

##### 准备阶段

###### 配置 Spring Boot Bean 源		

###### 推断Web应用类型

根据classpath

###### 推断引导类

根据 Main 线程执行堆栈判断实际的引导类

###### 加载ApplicationContextInitializer

spring.factorie

###### 加载ApplicationListener

spring.factories
例如`ConfigFileApplicationListener`

##### 运行阶段

###### 加载监听器 SpringApplicationRunListeners

spring.factories
getSpringFactoriesInstances(SpringApplicationRunListener.class, types, this, args))

`EventPublishingRunListener` 
--> `SimpleApplicationEventMulticaster`

####### EventPublishingRunListener

####### SimpleApplicationEventMulticaster

###### 运行监听器 SpringApplicationRunListeners

listeners.starting();

###### 创建应用上下文 ConfigurableApplicationContext

createApplicationContext()
- NONE: `AnnotationConfigApplicationContext`
- SERVLET: `AnnotationConfigServletWebServerApplicationContext`
- REACTIVE: `AnnotationConfigReactiveWebServerApplicationContext` 

###### 创建Environment

getOrCreateEnvironment()
- SERVLET: `StandardServletEnvironment`
- NONE, REACTIVE: `StandardEnvironment` 
