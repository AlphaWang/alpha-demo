# pre-condition

```
# start zk
zkServer.sh start

# test zk
telnet localhost 2181
srvr
```

# create
```
# start broker
﻿kafka-server-start.sh -daemon /usr/local/kafka/config/server.properties

# create topic
﻿kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test

# check topic
﻿kafka-topics.sh --zookeeper localhost:2181 --describe --topic test

# send topic 
﻿kafka-console-producer.sh --broker-list localhost:9092 --topic test

# consume
﻿kafka-console-consumer.sh --zookeeper localhost:2181 --topic test --from-beginning

```