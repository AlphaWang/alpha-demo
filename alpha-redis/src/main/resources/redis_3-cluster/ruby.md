# ruby脚本初始化redis cluster

## 准备

### 安装Ruby

```bash
wget https://cache.ruby-lang.org/pub/ruby/2.3/ruby-2.3.1.tar.gz
tar -xvf ruby-2.3.1.tar.gz

./configure -prefix=/usr/local/ruby

make
make install

cd /usr/local/ruby
cp bin/ruby /usr/local/bin
cp bin/gem /usr/local/bin
```



### 安装rubygem redis

```bash
wget https://rubygems.org/downloads/redis-3.3.0.gem
gem install -l redis-3.3.0.gem
gem list -- check redis gem
```

验证：

ruby -v



## redis-trib执行

安装redis-trib.rb

```
cp ${REDIS_HOME}/src/redis-trib.rb /usr/local/bin
```

启动server

```bash
redis-server 8000.conf
redis-server 8001.conf...
```

一键开启redis-trib.rb create

```bash
./redis-trib.rb create 
--replicas 1 #一个从节点
127.0.0.1:8000 #master
127.0.0.1:8001 #master
127.0.0.1:8002 #replica
127.0.0.1:8003 #replica
```



## 原生命令 vs. trib工具

原生命令：理解redis cluster架构、生成环境不是用；

trib工具：高效、准确。