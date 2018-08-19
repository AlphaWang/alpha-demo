线程不安全的类：

- StringBuilder -> StringBuffer
- SimpleDateFormat -> JodaTime
- ArrayList, HashSet, HashMap
- 先检查再执行的做法都可能线程不安全：`if (condition(a)) {handle(a);}`