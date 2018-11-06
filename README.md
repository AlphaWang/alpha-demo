Java知识脑图：http://naotu.baidu.com/file/b38589b975d51e3851f2c3315a895b72

# Java

## JVM

### 内存模型

### 工具

#### jconsole

查看MBean


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

#### cluster

##### 集群成员：Master + Slave

##### 扩展性：迁移slot (同步)

- Redis 迁移的单位是槽，当一个槽正在迁移时，这个槽就处于中间过渡状态。这个槽在原节点的状态为`migrating`，在目标节点的状态为`importing`，  

- 客户端先尝试访问旧节点，如果对应的数据不在旧节点里面，旧节点会向客户端返回一个`-ASK targetNodeAddr`的重定向指令。

- 客户端收到这个重定向指令后，先去目标节点执行一个不带任何参数的`asking`指令，然后在目标节点再重新执行原先的操作指令。

- 迁移过程是同步的，在目标节点执行`restore指令`到原节点删除key之间，原节点的主线程会处于阻塞状态，直到key被成功删除。

当客户端向一个错误的节点发出了指令，该节点会向客户端发送MOVED，告诉客户端去连正确的节点。 
 GET x
 -MOVED 3999 127.0.0.1:6381

##### 主从复制 (异步)：SYNC snapshot + backlog队列

- slave启动时，向master发起 `SYNC` 命令。

- master收到 SYNC 后，开启 `BGSAVE` 操作，全量持久化。

- BGSAVE 完成后，master将 `snapshot` 发送给slave.

- 发送期间，master收到的来自clien新的写命令，正常响应的同时，会再存入一份到 `backlog 队列`。

- snapshot 发送完成后，master会继续发送backlog 队列信息。

- backlog 发送完成后，后续的写操作同时发给slave，保持实时地异步复制。

##### 读写分离: READONLY 命令

- 默认情况下，某个slot对应的节点一定是一个master节点。客户端通过`MOVED`消息得知的集群拓扑结构也只会将请求路由到各个master中。

- 即便客户端将读请求直接发送到slave上，slave也会回复MOVED到master的响应。

- 为此，Redis Cluster引入了 `READONLY` 命令，客户端向slave发送READONLY命令后，slave对于读操作将不再返回moved，而是直接处理。

##### 主从切换: gossip PFAIL / FAIL

##### Failover: Master选举

如果B已被集群公认为是FAIL状态了，则其slave会发起竞选，期望成为新的master。

- 在竞选前，slave间会协商优先级，优先级高的slave更有可能更早地发起选举。优先级最重要的决定因素是`slave最后一次同步master信息的时间`，越新表示这个slave数据越新，竞选优先级越高。

- slave通过向其他master发送FAILOVER_AUTH_REQUEST消息发起竞选，master回复FAILOVER_AUTH_ACK告知是否同意。

##### 一致性: 保证朝着epoch值更大的信息收敛

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

#### version保证原子性

##### version表示修改次数

##### 乐观锁

#### watcher

##### 推拉结合

##### 流程

###### 客户端注册Watcher

###### 客户端将Watcher对象存到WatchManager

###### 服务端发送通知、客户端线程从WatchManager中取出对象，执行回调逻辑

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

##### request

##### session

##### globalSession

##### 作用域依赖问题

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

### AOP

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
