## redis主从配置
﻿
一般的redis目录下的redis.conf保存的是默认配置，尽量不要对其进行修改，这里我们复制三份redis.conf文件，分别命名为6379.conf，6380.conf和6381.conf，如下是端口为6379的主机的主要配置：

```
bind 127.0.0.1
port 6379
logfile "6379.log"
dbfilename "dump-6379.rdb"
```


如下是端口为6380和6381的从机的配置：`slaveof`
```
bind 127.0.0.1
port 6380
logfile "6380.log"
dbfilename "dump-6380.rdb"
slaveof 127.0.0.1 6379
```

```
bind 127.0.0.1
port 6381
logfile "6381.log"
dbfilename "dump-6381.rdb"
slaveof 127.0.0.1 6379
```
可以看到，端口为6380和6381的实例被配置为端口为6379的实例的从机。配置完成后使用redis-server分别执行如下命令启动三个实例：

```
./src/redis-server 6379.conf
./src/redis-server 6380.conf
./src/redis-server 6381.conf
```


## 测试主从
启动之后分别开启开启三个命令行工具分别执行以下命令连接redis实例：

```
./src/redis-cli -p 6379
./src/redis-cli -p 6380
./src/redis-cli -p 6381
```
分别在三个命令行工具中执行一个get命令，获取键名为msg的数据，如下所示：

```
127.0.0.1:6379> get msg
(nil)
127.0.0.1:6380> get msg
(nil)
127.0.0.1:6381> get msg
(nil)
```

可以看到，在三个redis实例中都不存在键为msg的数据，现在我们在主机6379上设置一个键为msg的数据，如下所示：

```
127.0.0.1:6379> set msg "hello"
OK
```
可以看到设置成功了，此时我们在6380和6381的实例上执行get msg的命令，如下所示：

```
127.0.0.1:6380> get msg
"hello"
127.0.0.1:6381> get msg
"hello"
```

可以看到，虽然我们只是在6379的实例上设置了msg这条数据，但是在6380和6381的实例上也存有了相应的数据，说明我们成功配置了redis的主从模式。

另外，如果不在配置文件中指定主从节点的关系，也可以在启动相关redis实例之后使用slaveof命令来指定当前节点称为某个节点的从节点，如：

```
127.0.0.1:6380> slaveof 127.0.0.1 6379
```



## sentinel配置
每个sentinel节点在本质上还是一个redis实例，只不过和redis数据节点不同的是，其主要作用是监控redis数据节点。

在redis安装目录下有个默认的sentinel配置文件sentinel.conf，和配置主从节点类似，这里复制三个配置文件：sentinel-26379.conf，sentinel-26380.conf和sentinel-26381.conf。分别按照如下示例编辑这三个配置文件：

```
port 26379  
daemonize yes  
logfile "26379.log"  
dir /opt/soft/redis/data  
sentinel monitor mymaster 127.0.0.1 6379 2
sentinel down-after-milliseconds mymaster 30000  
sentinel parallel-syncs mymaster 1  
sentinel failover-timeout mymaster 180000
sentinel myid mm55d2d712b1f3f312b637f9b546f00cdcedc787
```

对于端口为26380和26381的sentinel，其配置和上述类似，只需要把相应的端口号修改为对应的端口号即可。这里注意两点：  
①每个sentinel的myid参数也要进行修改，因为sentinel之间是通过该属性来唯一区分其他sentinel节点的；     
②参数中sentinel monitor mymaster 127.0.0.1 6379 2这里的端口号6379是不用更改的，因为sentinel是通过检测主节点的状态来得知当前主节点的从节点有哪些的，因而设置为主节点的端口号即可。

配置完成后我们首先启动三个主从节点，然后分别使用三个配置文件使用如下命令启用sentinel：

```
./src/redis-sentinel sentinel-26379.conf
./src/redis-sentinel sentinel-26380.conf
./src/redis-sentinel sentinel-26381.conf
```


## 测试
由于sentinel节点也是一个redis实例，因而我们可以通过如下命令使用redis-cli连接sentinel节点：

```
./src/redis-cli -p 26379
```


连上sentinel节点之后我们可以通过如下命令查看sentinel状态：`info sentinel`

```
127.0.0.1:26379> info sentinel
```
结果如下：

```
# Sentinel
sentinel_masters:1
sentinel_tilt:0
sentinel_running_scripts:0
sentinel_scripts_queue_length:0
sentinel_simulate_failure_flags:0
master0:name=mymaster,status=ok,address=127.0.0.1:6379,slaves=2,sentinels=3
```

可以看到，sentinel检测到主从节点总共有三个，其中一个主节点，两个从节点，并且sentinel节点总共也有三个。


启动完成之后，我们可以通过主动下线主节点来模拟sentinel的故障转移过程。
首先我们连接上端口为6379的主节点，使用如下命令查看主从节点状态：`info replication`

```
127.0.0.1:6379> info replication
```

结果如下：

```
# Replication
role:master
connected_slaves:2
slave0:ip=127.0.0.1,port=6380,state=online,offset=45616,lag=1
slave1:ip=127.0.0.1,port=6381,state=online,offset=45616,lag=1
master_repl_offset:45616
repl_backlog_active:1
repl_backlog_size:1048576
repl_backlog_first_byte_offset:2
repl_backlog_histlen:45615
```

可以看到，当前主节点有两个从节点，端口分别为6380和6381。然后我们对主节点执行如下命令：

```
127.0.0.1:6379> shutdown save
```

然后我们连接上端口号为6380的从节点，并执行如下命令：

```
127.0.0.1:6380> info replication 
```
结果如下：

```
# Replication
role:master
connected_slaves:1
slave0:ip=127.0.0.1,port=6381,state=online,offset=12344,lag=0
master_repl_offset:12477
repl_backlog_active:1
repl_backlog_size:1048576
repl_backlog_first_byte_offset:2
repl_backlog_histlen:12476
```

可以看到，当端口为6379的实例下线之后，端口为6380的实例被重新竞选为新的主节点，并且端口为6381的实例被设置为6380的实例的从节点。

如果我们此时重新启用端口为6379的节点，然后再查看主从状态，结果如下：

```
# Replication
role:master
connected_slaves:2
slave0:ip=127.0.0.1,port=6381,state=online,offset=59918,lag=0
slave1:ip=127.0.0.1,port=6379,state=online,offset=59918,lag=1
master_repl_offset:60051
repl_backlog_active:1
repl_backlog_size:1048576
repl_backlog_first_byte_offset:2
repl_backlog_histlen:60050
```

可以看到，端口为6379的redis实例重新连接后，sentinel节点检测到其重新连接，那么对其发送命令，使其成为新的主节点的从节点。

