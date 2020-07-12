
# 设计实现一个支持自定义规则的灰度发布组件


https://time.geekbang.org/column/article/247776

## 需求分析

- 使用方式
例子：需要发布一个全新的接口调用方式，替换老的RPC

``` 
// as-is
boolean callRestfulApi = true;

if (!callRestfulApi) {
  // 老的调用RPC接口的代码逻辑
} else {
  // 新的调用Resful接口的代码逻辑
} 

// to-be
DarkLaunch darkLaunch = new DarkLaunch(); 
DarkFeature darkFeature = darkLaunch.getDarkFeature("call_newapi_getUserById");
if (darkFeature.enabled() && darkFeature.dark(893)) {

}
   
```  
- 功能性需求
  - 灰度规则配置化
    ```
        features:
        - key: call_newapi_getUserById
          enabled: true // enabled为true时，rule才生效
          rule: {893,342,1020-1120,%30} // 按照用户ID来做灰度
        - key: call_newapi_registerUser
          enabled: true
          rule: {1391198723, %10}  //按照手机号来做灰度
        - key: newalgo_loan
          enabled: true
          rule: {0-1000} //按照贷款(loan)的金额来做灰度     
    ``` 
  - 提供编程接口 用于判断是否灰度
    ``` 
    public interface DarkFeature {
      boolean enabled();
      boolean dark(String darkTarget); //darkTarget是灰度对象，比如前面提到的用户ID、手机号码、金额等
    }
    ```   
 

## 设计

- 非功能性需求
  - 易用性：
  可接受代码侵入性；
  支持规则热更新；--> 定时拉取
  
  - 扩展性、灵活性：
  支持各种配置格式、数据源(本地，zk，配置中心)；
  支持多种规则语法（具体指、区间值、比例值）；
  复杂规则：--> Drools, or 编程
  
  - 性能：
  无需访问外部存储、调用外部api，灰度规则组织成快速查找的数据结构即可。
  
  - 容错性：
  内部消化，or 终止业务 
  
## 实现

```
com.xzg.darklaunch
  --DarkLaunch      (框架的最顶层入口类：读取灰度规则，定期更新规则)
  --IDarkFeature    (抽象接口)
  --DarkFeature     (实现IDarkFeature接口，解析配置文件的灰度规则，提供判断接口)
  --DarkRule        (灰度规则：api key --> DarkFeature)
  --DarkRuleConfig  (将灰度规则映射到内存中)
```
// 第一步的代码目录结构
com.xzg.darklaunch
  --DarkLaunch(框架的最顶层入口类)
  --DarkFeature(每个feature的灰度规则)
  --DarkRule(灰度规则)
  --DarkRuleConfig(用来映射配置到内存中)

// 第二步的代码目录结构
