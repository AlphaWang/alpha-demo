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
