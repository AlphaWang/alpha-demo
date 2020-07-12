
# 设计一个支持各种算法的限流框架

https://time.geekbang.org/column/article/242314
https://github.com/wangzheng0822/ratelimiter4j

## 需求分析
限制每个调用方对接口请求的频率，当超过预先设定的访问频率后，我们就触发限流熔断。  
比如，限制调用方 app-1 对公共服务平台总的接口请求频率不超过 1000 次 / 秒，超过之后的接口请求都会被决绝。  

除此之外，为了更加精细化地限流，除了限制每个调用方对公共服务平台总的接口请求频率之外，我们还希望能对单独某个接口的访问频率进行限制，
比如，限制 app-1 对接口 /user/query 的访问频率为每秒钟不超过 100 次。

- 使用方式

调用方示例代码如下。可集成到 RPC 框架中，或 Spring AOP 切面中。
``` 
String appId = "app-1"; // 调用方APP-ID
String url = "http://www.eudemon.com/v1/user/12345";// 请求url  

RateLimiter ratelimiter = new RateLimiter();
boolean passed = ratelimiter.limit(appId, url);
if (passed) {
  // 放行接口请求，继续后续的处理。
} else {
  // 接口请求被限流。
}

```    


- 规则配置化
```
configs:
- appId: app-1
  limits:
  - api: /v1/user
    limit: 100
  - api: /v1/order
    limit: 50
- appId: app-2
  limits:
  - api: /v1/user
    limit: 50
  - api: /v1/order
    limit: 50
```

- 非功能需求
    - 易用性：规则配置、接口使用简单；
    - 扩展性、灵活性：灵活扩展各种限流算法，支持各种配置格式、数据源(本地，zk，配置中心)；
    - 性能；
    - 容错性；


## 设计

- 限流算法
  - 固定时间窗口
  - 滑动时间窗口
  - 令牌桶
  - 漏桶

- 限流模式
  - 单机
  - 分布式

## 实现

```

// 重构前：
com.xzg.ratelimiter
  --RateLimiter   读取Rules，提供limit()入口  

com.xzg.ratelimiter.rule
  --ApiLimit      数据结构：单个API的限流规则
  --RuleConfig    数据结构：规则配置集合
  --RateLimitRule 抽象数据结构：  
  --TrieRateLimitRule 实现类，Trie树提高规则查询速度  

com.xzg.ratelimiter.rule.parser
  --RuleConfigParser     (抽象接口：规则配置文件的加载)
  --YamlRuleConfigParser (Yaml格式配置文件解析类)
  --JsonRuleConfigParser (Json格式配置文件解析类)   

com.xzg.ratelimiter.rule.datasource
  --RuleConfigSource      (抽象接口)
  --FileRuleConfigSource  (基于本地文件的配置类)    

com.xzg.ratelimiter.alg
  --RateLimitAlg        接口：限流算法
  --FixedTimeWinRateLimitAlg 实现类：固定窗口限流

```


