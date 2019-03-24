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

验证：

ruby -v



### 安装rubygem redis

```bash
wget https://rubygems.org/downloads/redis-3.3.0.gem
gem install -l redis-3.3.0.gem
gem list --check redis gem
```



### 安装redis-trib.rb

```
cp ${REDIS_HOME}/src/redis-trib.rb /usr/local/bin
```

