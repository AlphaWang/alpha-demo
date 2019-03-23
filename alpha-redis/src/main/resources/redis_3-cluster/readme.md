http://redis.cn/topics/cluster-tutorial.html

# redis cluster

## 原理
redis集群是在redis 3.0版本推出的一个功能，其有效的解决了redis在分布式方面的需求。
当遇到单机内存，并发和流量瓶颈等问题时，可采用Cluster方案达到负载均衡的目的。  

并且从另一方面讲，redis中sentinel有效的解决了故障转移的问题，也解决了主节点下线客户端无法识别新的可用节点的问题，
但是如果是从节点下线了，sentinel是不会对其进行故障转移的，并且连接从节点的客户端也无法获取到新的可用从节点，
而这些问题在Cluster中都得到了有效的解决。


redis集群中数据是和槽（`slot`）挂钩的，其总共定义了`16384`个槽，所有的数据根据一致哈希算法会被映射到这16384个槽中的某个槽中；
另一方面，这16384个槽是按照设置被分配到不同的redis节点上的，比如启动了三个redis实例：`cluster-A`，`cluster-B`和`cluster-C`，
这里将0-5460号槽分配给cluster-A，将5461-10922号槽分配给cluster-B，将10923-16383号槽分配给cluster-C（总共有16384个槽，但是其标号类似数组下标，是从0到16383）。
也就是说数据的存储只和槽有关，并且槽的数量是一定的，由于一致hash算法是一定的，因而将这16384个槽分配给无论多少个redis实例，对于确认的数据其都将被分配到确定的槽位上。
redis集群通过这种方式来达到redis的`高效`和`高可用`目的。



这里需要进行说明的一点是，一致哈希算法根据数据的key值计算映射位置时和所使用的节点数量有非常大的关系。
一致哈希分区的实现思路是为系统中每个节点分配一个token，范围一般在0~2^32，这些token构成一个哈希环，
数据读写执行节点查找操作时，先根据key计算hash值，然后顺时针找到第一个大于等于该hash值的token节点，需要操作的数据就保存在该节点上。


通过分析可以发现，一致哈希分区存在如下问题：

- 加减节点会造成哈希环中部分数据无法命中，需要手动处理或忽略这部分数据；
- 当使用少量节点时，节点变化将大范围影响环中数据映射，因此这种方式不适合少量节点的分布式方案；
- 普通的一致性哈希分区在增减节点时需要增加一倍或减去一半节点才能保证数据和负载的平衡。

正是由于一致哈希分区的这些问题，redis使用了虚拟槽来处理分区时节点变化的问题，也即将所有的数据映射到16384个虚拟槽位上，
当redis节点变化时数据映射的槽位将不会变化，并且这也是redis进行节点扩张的基础。



## 配置
对于redis集群的配置，首先将redis安装目录下的`redis.conf`文件复制六份，分别取名为：cluster-6379.conf、cluster-6380.conf、cluster-6381.conf、cluster-6382.conf、cluster-6383.conf、cluster-6384.conf。
对于一个高可用的集群方案，集群每个节点都将为其分配一个从节点，以防止数据节点因为故障下线，这里使用六份配置文件定义六个redis实例，其中三个作为主节点，剩余三个分别作为其从节点。

对于这六份配置文件，以其中一份为例，以下是其需要修改的参数：
```
port 6379
cluster-enabled yes
cluster-node-timeout 15000
cluster-config-file "nodes-6379.conf"
pidfile /var/run/redis_6379.pid
logfile "cluster-6379.log"
dbfilename dump-cluster-6379.rdb
appendfilename "appendonly-cluster-6379.aof"
```

对于其余的配置文件，只需要将其中对应项的端口号和带有端口号的文件名修改为当前要指定的端口号和端口号的文件名即可。
配置文件配置好之后使用如下命令启动集群中的每个实例：

```
./src/redis-server cluster-6379.conf
./src/redis-server cluster-6380.conf
./src/redis-server cluster-6381.conf
./src/redis-server cluster-6382.conf
./src/redis-server cluster-6383.conf
./src/redis-server cluster-6384.conf
```


### cluster meet

仔细阅读上述配置文件可发现，**当前配置和启动过程中并没有指定这六个实例的主从关系，也没有对16384个槽位进行分配**。

因而我们还需要进行进一步的配置，槽位的分配和主从关系的设定有两种方式进行，一种是使用redis-cli连接到集群节点上后使用 `cluster meet` 命令连接其他的节点，如我们首先执行如下命令连接到6379端口的节点：

```
./src/redis-cli -p 6379
```

连接上后使用 `cluster meet` 命令分别连接其余节点：

```
127.0.0.1:6379>cluster meet 127.0.0.1 6380
127.0.0.1:6379>cluster meet 127.0.0.1 6381
127.0.0.1:6379>cluster meet 127.0.0.1 6382
127.0.0.1:6379>cluster meet 127.0.0.1 6383
127.0.0.1:6379>cluster meet 127.0.0.1 6384 
```


连接好后可以使用 `cluster nodes` 命令查看当前集群状态：
```
127.0.0.1:6379> cluster nodes
4fa7eac4080f0b667ffeab9b87841da49b84a6e4 127.0.0.1:6384 master - 0 1468073975551 5 connected
cfb28ef1deee4e0fa78da86abe5d24566744411e 127.0.0.1:6379 myself,master - 0 0 0 connected
be9485a6a729fc98c5151374bc30277e89a461d8 127.0.0.1:6383 master - 0 1468073978579 4 connected
40622f9e7adc8ebd77fca0de9edfe691cb8a74fb 127.0.0.1:6382 master - 0 1468073980598 3 connected
8e41673d59c9568aa9d29fb174ce733345b3e8f1 127.0.0.1:6380 master - 0 1468073974541 1 connected
40b8d09d44294d2e23c7c768efc8fcd153446746 127.0.0.1:6381 master - 0 1468073979589 2 connected
```

### cluster addslots

可以看到配置的六个节点都已经加入到了集群中，但是其现在还不能使用，因为还没有将16384个槽分配到集群节点中。
虚拟槽的分配 `cluster addslots {start...end}`，

可以使用redis-cli分别连接到6379，6380和6381端口的节点中，然后分别执行如下命令：

```
127.0.0.1:6379>cluster addslots {0...5461}
127.0.0.1:6380>cluster addslots {5462...10922}
127.0.0.1:6381>cluster addslots {10923...16383}
```


> 批量addslots不支持！！！    

#### 解决方法一
> first=`echo {0..5461}` # you can insert your own start and end numbers  
> redis-cli -a kimi -p 6379 cluster addslots $first

#### 解决方法二
在每个节点上执行脚本
```
#!/bin/bash
n=0
for ((i=n;i<=5461;i++))
do
   /usr/local/bin/redis-cli -h 192.168.100.134 -p 17021 -a dxy CLUSTER ADDSLOTS $i
done
```

```
start=$1
end=$2
port=$3

for slot in `seq ${start} ${end}`
do
    echo "slot: ${slot}"
    redis-cli -p ${port} cluster addslots ${slot}
done
```


添加完槽位后可使用 `cluster info` 命令查看当前集群状态：

```
127.0.0.1:6379> cluster info
cluster_state:ok
cluster_slots_assigned:16384
cluster_slots_ok:16384
cluster_slots_pfail:0
cluster_slots_fail:0
cluster_known_nodes:6
cluster_size:3
cluster_current_epoch:5
cluster_my_epoch:0
cluster_stats_messages_sent:4874
cluster_stats_messages_received:4726 
```


### 从节点配置

这里我们将16384个虚拟槽位分配给了三个节点，而剩余的三个节点我们通过 `cluster replicate` 将其配置为这三个节点的从节点，从而达到高可用的目的：

```
127.0.0.1:6382>cluster replicate cfb28ef1deee4e0fa78da86abe5d24566744411e
OK
127.0.0.1:6383>cluster replicate 8e41673d59c9568aa9d29fb174ce733345b3e8f1
OK
127.0.0.1:6384>cluster replicate 40b8d09d44294d2e23c7c768efc8fcd153446746
OK 
```

如此，所有的集群节点都配置完毕，并且处于可用状态。这里可以使用 `cluster nodes` 命令查看当前节点的状态：
```
127.0.0.1:6379> cluster nodes
4fa7eac4080f0b667ffeab9b87841da49b84a6e4 127.0.0.1:6384 slave 40b8d09d44294d2e23c7c768efc8fcd153446746 0 1468076865939 5 connected
cfb28ef1deee4e0fa78da86abe5d24566744411e 127.0.0.1:6379 myself,master - 0 0 0 connected 0-5461
be9485a6a729fc98c5151374bc30277e89a461d8 127.0.0.1:6383 slave 8e41673d59c9568aa9d29fb174ce733345b3e8f1 0 1468076868966 4 connected
40622f9e7adc8ebd77fca0de9edfe691cb8a74fb 127.0.0.1:6382 slave cfb28ef1deee4e0fa78da86abe5d24566744411e 0 1468076869976 3 connected
8e41673d59c9568aa9d29fb174ce733345b3e8f1 127.0.0.1:6380 master - 0 1468076870987 1 connected 5462-10922
40b8d09d44294d2e23c7c768efc8fcd153446746 127.0.0.1:6381 master - 0 1468076867957 2 connected 10923-16383
```

## 测试
我们使用redis-cli使用如下命令连接集群：
```
./src/redis-cli -c -p 6380
```
注意连接集群模式的redis实例时需要加上参数`-c`，表示连接的是集群模式的实例。连接上后执行get命令：

```
127.0.0.1:6380> get hello
-> Redirected to slot [866] located at 127.0.0.1:6379
(nil)
```

可以看到，在6380端口的实例上执行get命令时，其首先会为当前的键通过一致哈希算法计算其所在的槽位，
并且判断该槽位不在当前redis实例中，因而重定向到目标实例上执行该命令，最后发现没有该键对应的值，因而返回了一个（nil）。