
- 打家劫舍
- 顺时针打印矩阵
- 数组的最长连续子序列
- LeetCode 76. Minimum Window Substring. Hard 
- LeetCode 25. 正向K位翻转链表 
- 564: 寻找最近的回文数



## Sort
- 堆排序
- 快速排序

## Array

- [ ] : 旋转数组最小值  
https://leetcode.com/problems/find-minimum-in-rotated-sorted-array

- [ ] : 旋转数组最小值 (有重复值)   
https://leetcode.com/problems/find-minimum-in-rotated-sorted-array-ii

- [ ] : 旋转数组的某个值(无重复值)  
https://leetcode.com/problems/search-in-rotated-sorted-array/discuss/14425/Concise-O(log-N)-Binary-search-solution

- [ ] : 旋转数组的某个值(有重复值)
https://leetcode.com/problems/find-minimum-in-rotated-sorted-array-ii/discuss/48808/My-pretty-simple-code-to-solve-it/48840

- [ ] : 两个有序数组找中位数

- [ ] 560: 和为K的子数组
https://leetcode-cn.com/problems/subarray-sum-equals-k/



## LinkedList

- [x] 206: reverse-linked-list   
https://leetcode.com/problems/reverse-linked-list/ 
  > 1. 两个指针：pre/cur；  
  > 指针操作：cur.next = pre; pre = cur; cur后移 

- [ ] 24: swap-nodes-in-pairs   
https://leetcode.com/problems/swap-nodes-in-pairs 
  > 1. 遍历：pre.next && pre.next.next；  
  > 指针操作：？

- [ ] 25: reverse-nodes-in-k-group  
https://leetcode.com/problems/reverse-nodes-in-k-group/

- [x] 141: linked-list-cycle  
https://leetcode.com/problems/linked-list-cycle
  > 1. 暴力：限时循环，判断是否能走到null
  > 2. 遍历，Set 存储走过的节点
  > 3. 快慢指针，判断是否相交

- [x] 142: linked-list-cycle-ii  
https://leetcode.com/problems/linked-list-cycle-ii
  > 1. 遍历，Set 存储走过的节点；如果当前节点在Set中已存在，则是环的入口
  > 1. 快慢指针，找到相交点；再用另外两个指针，分别指向头结点、相交点，同时往后遍历找到相交点即是入口。

- [ ] 147: 插入排序链表  
https://leetcode-cn.com/problems/insertion-sort-list/

- [ ] 148: 排序链表 （归并）  
https://leetcode-cn.com/problems/sort-list/

- [x] 21: 合并两个有序的链表
https://leetcode.com/problems/merge-two-sorted-lists/ 

- [ ] : 合并K个有序链表  
https://leetcode.com/problems/merge-k-sorted-lists/


## Queue / Stack

- [ ] 20: valid-parentheses  
https://leetcode.com/problems/valid-parentheses/
  > 1. 栈。左括号入栈，右括号取栈顶，判断是否匹配。
  >   tips: 用 Map<右括号，左括号> 方便验证是否匹配。
  > 2. s.replace("()", "")，循环删除匹配的括号
 
  - 给定一个只包括 '('，')'的字符串，判断字符串是否有效。
  - 优化：提升空间复杂度，不用栈 --> 记录变量 ( 则 +1, ) 则 -1
  - 给定一个只包含 '(' 和 ')' 的字符串，找出最长的包含有效括号的子串的长度。
   暴力解法：依次遍历
  - 优化：https://www.cnblogs.com/kubidemanong/p/11949244.html 

- [ ] 225: implement-stack-using-queues  
https://leetcode.com/problems/implement-stack-using-queues/

- [ ] 232: implement-queue-using-stacks   
https://leetcode.com/problems/implement-queue-using-stacks/
  > 1. 两个Stack
  > push(): 直接放入输入栈
  > pop(): 输出栈不为空，则pop输出栈；输出栈为空，则先将输入栈 导入到 输出栈，再pop输出栈

- [ ] 239: sliding-window-maximum   
https://leetcode.com/problems/sliding-window-maximum/
  > 1. 暴力解法：循环找最大值，存入数组。
  > 2. 维护大顶堆。Q: 需要删除非堆顶元素  
  > 3. 维护一个双端队列。队列元素为下标，最左为当前最大值的下标。



## Tree

- [ ] : 二叉树转中序链表

- [ ] : 二叉树右视图  
https://leetcode.com/problems/binary-tree-right-side-view/ 

- [ ] : 二叉树中序遍历（stack）
https://leetcode.com/problems/binary-tree-inorder-traversal/

- [ ] 98: 验证二叉搜索树
https://leetcode.com/problems/validate-binary-search-tree 
  > 1. 中序遍历，校验递增。优化：遍历过程中与前继节点值。
  > 2. 递归 validate(node, min, max)，校验子树各节点值区间 (min, max)

- [ ] 235: 二叉搜索树的最近公共祖先
https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/
  > 1. 可用 236 的思路。
  > 2. 如果p/q都大于 cur，则找右子树；如果 p/q 都小于cur，则找左子树；否则，cur即是祖先。

- [ ] 236: 二叉树的最近公共祖先  
https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/
  > 1. 向上找 Path，找到两条 Path 的交汇点。
  > 2. 递归 findPorQ(), 如果左右子树返回值都 != null，则当前节点是祖先。否则继续查找返回值 != null 的子树。 


## Heap  

- [ ] 703: kth-largest-element-in-a-stream  
https://leetcode.com/problems/kth-largest-element-in-a-stream/discuss/149050/Java-Priority-Queue
  > 1. 维护最大K个元素，新元素进入并排序。 
  > 2. 用小顶堆，保证size = K。如果新元素 > 顶，则调整：移除顶，新元素进入堆

- [ ] : 一个无序数组求第K大数  
https://leetcode.com/problems/kth-largest-element-in-an-array

- [ ] : 数据流的中位数



## Hash

- [ ] 242: Valid Anagram
https://leetcode.com/problems/valid-anagram/ 
  > 1. 先排序，再比较 
  > 2. Map 计数，比较Map是否相同
  > 3. int[26] 计数
   
- [ ] 1: Two Sum   
https://leetcode.com/problems/two-sum/
  > 1. 两重遍历 
  > 2. 先遍历一遍，记录 Map<期望匹配值，index> 

- [ ] 15: Three Sum  
https://leetcode.com/problems/3sum/
  > 1. 三重遍历
  > 2. 先把数组放入Map；两重遍历，查询Map中是否有 -(a+b)    
  > 3. 先排序；遍历，同时在后继元素中使用双指针

- [ ] 18: Four Sum   
https://leetcode.com/problems/4sum/

- [ ] : Group Anagram
https://leetcode.com/problems/group-anagrams/


## 递归、分治

- 递归
```
public void recursion(level, param1, ...) {
  // terminator
  if (level > MAX) return;  

  // process logic in current level 
  process();

  // drill down
  recusion(level + 1, p1, ...);

  // reverse the current level status if needed
  reverseState(level);
}
```  

- [ ] 50: powx-n  
https://leetcode.com/problems/powx-n/
  > 1. 调用库函数
  > 2. 暴力：一层循环，逐个相乘
  > 3. 分治：如果是偶数个 = y * y; 如果是奇数个 = y * y * x. (y = x的n/2方)

- [ ] 169: 众数
https://leetcode.com/problems/majority-element
  > 1. 暴力: 遍历元素，再遍历求次数
  > 2. Map: 遍历元素，放入map计数
  > 3. 排序: 排序后遍历。--> 优化：排序后直接取最中间元素
  > 4. 分治: 分别求左右众数，当 left == right --> left; 当left count > right count --> left

- [ ] https://leetcode.com/problems/maximum-subarray
- [ ] https://leetcode.com/problems/valid-anagram
- [ ] https://leetcode.com/problems/find-all-anagrams-in-a-string 
- [ ] https://leetcode.com/problems/anagrams

## 搜索：BFS / DFS

- DFS: 递归
```
visited = set()
dfs(node, visited) {
  visited.add(node);
  // process node
  ...
  for nextNode in node.children() 
    if (!visited.contains(nextNode)) 
       dfs(nextNode, visited)
}
```   

- BFS：队列存储
```
bfs(node, start, end) {
  queue = []
  queue.append([start])
  visited.add(start)

  while (queue) {
    node = queue.pop()
    visited.add(node) 

    process(node);  
 
    nodes = node.children()
    queue.push(nodes)
  }
}

```

- [ ] 102: 二叉树层次遍历
https://leetcode.com/problems/binary-tree-level-order-traversal/
  > 1. BFS: 队列如何保存层信息？ --> batch process (queue size)，不存层信息
  > 2. DFS: 逐个放入结果二维数组。BFS 按行放，DFS 按列放。

- [ ] 104: 二叉树最大深度
https://leetcode.com/problems/maximum-depth-of-binary-tree/
  > 1. 递归：max(left, right) + 1
  > 2. BFS: 按层遍历，扫到最后一层 
  > 2. DFS: 遍历节点，记录深度；如果是叶子节点，则更新 min/max


- [ ] 111: 二叉树最小深度
https://leetcode.com/problems/minimum-depth-of-binary-tree/
  > 1. 递归：min(left, right) + 1 ，注意：没有left/right的情况
  > 2. BFS: 按层遍历，如果碰到叶子节点，则找到最小值
  > 2. DFS: 遍历节点，记录深度；如果是叶子节点，则更新 min/max

- [ ] 22: generate-parentheses
https://leetcode.com/problems/generate-parentheses/
  > 1. 数学归纳法：n=1, 2, ...
  > 2. 递归：构造2*n长度的数组，元素分别填入左右括号，validate
  > 3. 改进2，剪枝：
       a) 局部不合法，不再递归； 
       b) 保存leftUsed / rightUsed

### 二分查找
- [ ] 69: 平方根 
https://leetcode.com/problems/sqrtx/
  > 1. 二分法，因为单调递增
  > 2. 牛顿迭代法* 

- [ ] : 
https://leetcode.com/problems/valid-perfect-square/


### 剪枝

- [ ] 51: N 皇后 
https://leetcode.com/problems/n-queens/
  > 1. 暴力
  > 2. 剪枝：标记 行col[col]，撇pie[row+col]，捺na[row-col]

- [ ] 52:  
https://leetcode.com/problems/n-queens-ii/

- [ ] 36: 数独
https://leetcode.com/problems/valid-sudoku/
  > 1. Naive DFS
  > 2. 加速：预处理 找出每个位置的可选数；按可选数个数排序；从选项少的开始
  > 3. 高级数据结构：DancingLink

- [ ] 37: 数独 
https://leetcode.com/problems/sudoku-solver/

## 贪心、动态规划
- [ ] : 
https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii
  > 1. DFS: ？
  > 2. 贪心：只要后一天价格比前一天高，则前一天买 后一天卖
  > 3. DP: ？

- [ ] : 
https://leetcode.com/problems/lemonade-change/

- [ ] : 
https://leetcode.com/problems/assign-cookies/

- [ ] : 
https://leetcode.com/problems/walking-robot-simulation/





## Java 数据结构操作
PriorityQueue:
- offer()
- poll()
- peak()

Deque
- getFirst() / removeFirst()
- getLast() / removeLast()
- addLast() / addFirst()