
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
- [x] 283: 移动零 `****` `E`  
https://leetcode.com/problems/move-zeroes/
  > 1: loop, count zero  
  > 2: new int[], loop  
  > 3: index，挪动非0元素    

- [ ] 11: 盛水最多的容器 `****` 
https://leetcode.com/problems/container-with-most-water/
  > 1: 嵌套循环，枚举 left / right   
  > 2: 左右边界，向中间收敛

- [ ] 15: 三数之和 `*****`
https://leetcode.com/problems/3sum/

- [x] 26: 删除排序数组中的重复项 `****` `E`
https://leetcode.com/problems/remove-duplicates-from-sorted-array/
  > 1: 开辟新数组，遍历原数组 并拷贝非重复元素到新数组
  > 2: 双指针遍历。碰到不相等的元素，则拷贝到index+1指针处

- [x] 189: 旋转数组 `****` `E`
https://leetcode.com/problems/rotate-array/
  > 1: 计算需要移动的步数，for steps, 从后往前遍历数组 移动元素
  > 2: 使用额外数据，暂存头部（或尾部）待移动的元素，再依次移动
  > 3: 使用额外数据，直接计算目标下标 `(k+i) % length`     
  > 4: 三次翻转数组 ！ 
  > 5: 环状替换 (?)

- [x] 88: 合并有序数组 `*****` `E`
https://leetcode.com/problems/merge-sorted-array/
  > 1: 双指针从前往后遍历，如果nums1元素大，则往后挪动元素
  > 2: 双指针从后往前遍历：从 m+n-1 --> 0
  > 3: 双指针从后往前遍历：从 m-1 / n-1 --> 0 ，优化循环判断逻辑
 
- [x] 66: 加一
https://leetcode.com/problems/plus-one/
  > 从后往前遍历，逐个+1，加完如果>10，则返回
  > 注意全9的情况，直接新建一个数组，首位为1，其余全0. （无需拷贝原始数组）

- [x] 42: 接雨水 `*****` `H`
https://leetcode.com/problems/trapping-rain-water/
  > 1: 暴力：对每个元素，遍历2遍分别计算 leftMax/rightMax
  > 2: DP： 双指针两次遍历，计算每个元素的 leftMax/rightMax，存入数组
  > 3: DP优化：双指针夹逼
  > 4: 栈：(?)
 
- [x] 153: 旋转数组最小值  `*****` `M`  
https://leetcode.com/problems/find-minimum-in-rotated-sorted-array
  > 1: 遍历，如果nums[i] < num[i-1]，则找到。O(N)
  > 2: 二分查找：if nums[mid] < nums[right], 往左侧找 
  >             if nums[mid] > nums[right], 往右侧找

- [ ] 154: 旋转数组最小值 (有重复值) `**` `H`  
https://leetcode.com/problems/find-minimum-in-rotated-sorted-array-ii

- [x] 33: 旋转数组的某个值(无重复值) `*****` `M`  
https://leetcode.com/problems/search-in-rotated-sorted-array/discuss/14425/Concise-O(log-N)-Binary-search-solution
  > 1: 暴力解法，遍历一遍 O(N)
  > 2: 暴力解法优化：找到最小值，还原成有序数组 O(N) or O(logN)
  > 3: 二分法：左右侧总有一个是有序的

- [ ] 154: 旋转数组的某个值(有重复值) `**` `H`
https://leetcode.com/problems/find-minimum-in-rotated-sorted-array-ii/discuss/48808/My-pretty-simple-code-to-solve-it/48840

- [x] 88: 合并有序数组 `*****` `E`
https://leetcode.com/problems/merge-sorted-array/
  > 双指针从后往前遍历 
  > while (index1 >= 0 && index2 >= 0) 

- [ ] 4: 两个有序数组找中位数 `**` `H`
https://leetcode.com/problems/median-of-two-sorted-arrays/
  > 1: 暴力，合并数组，再排序
  > 2: 双指针合并有序数组，see T88
  > 3: 二分查找 (!)

- [ ] 560: 和为K的子数组 `**` `M`
https://leetcode.com/problems/subarray-sum-equals-k/


- [ ] 面试40: 最小的K个数 
https://leetcode-cn.com/problems/zui-xiao-de-kge-shu-lcof/
  > 1: 堆
  > 2: 排序
  > 3: 计数排序
  > 4: 快排思想 TODO

## LinkedList

- [x] 206: reverse-linked-list `****`  
https://leetcode.com/problems/reverse-linked-list/ 
  > 1: 两个指针：pre/cur；  
  > 指针操作：cur.next = pre; pre = cur; cur后移 

- [x] 24: swap-nodes-in-pairs `*****` `M` 
https://leetcode.com/problems/swap-nodes-in-pairs 
  > 1: 递归: prev.next = swap(curr.next); curr.next = prev.
  > 2: 遍历: pre.next && pre.next.next；  
  > 指针操作：？

- [x] 25: reverse-nodes-in-k-group `*****` `H`
https://leetcode.com/problems/reverse-nodes-in-k-group/
  > 1: 递归：先遍历K个元素，同时swap；再将tail.next指向下一个递归
 
- [x] 141: linked-list-cycle `****` `E`  
https://leetcode.com/problems/linked-list-cycle
  > 1: 暴力：限时循环，判断是否能走到null
  > 2: 遍历，Set 存储走过的节点
  > 3: 快慢指针，判断是否相交

- [x] 142: linked-list-cycle-ii `****` `M`   
https://leetcode.com/problems/linked-list-cycle-ii
  > 1: 遍历，Set 存储走过的节点；如果当前节点在Set中已存在，则是环的入口
  > 1: 快慢指针，找到相交点；再用另外两个指针，分别指向头结点、相交点，同时往后遍历找到相交点即是入口。

- [x] 147: 插入排序链表  
https://leetcode.com/problems/insertion-sort-list/
  > 1: 遍历节点，对每个节点 找到待插入的位置。  
  >   curr: 待处理的节点
  >   prev: 待处理的前一个节点  
  >   dummy: 虚拟指针 指向头结点

- [x] 148: 排序链表 （归并）  
https://leetcode.com/problems/sort-list/
  > 1: 归并：先找到中点，在分别排序左侧和右侧，最后merge (同 21)

- [x] 21: 合并两个有序的链表 `*****` ``
https://leetcode.com/problems/merge-two-sorted-lists/ 
  > 1: 循环两个链表；最后处理剩余的
  > 2: 递归

- [x] 23: 合并K个有序链表 `**` `H` 
https://leetcode.com/problems/merge-k-sorted-lists/
  > 1: 依次进行 k-1 次二链表合并。
  > 2: 分治 --> 高效
  > 3: 优先队列，保存K个待处理元素
 
## Queue / Stack

- [ ] 20: valid-parentheses `****` `E` 
https://leetcode.com/problems/valid-parentheses/
  > 1: 暴力：
  >     遍历，替换匹配的括号对；
  >     或者replace("()", "")，循环删除匹配的括号；
  > 2: 栈：最近相关性，类比洋葱
  >     左括号入栈，右括号取栈顶，判断是否匹配。
  >     tips: 用 Map<右括号，左括号> 方便验证是否匹配。

扩展： 
  - 给定一个只包括 '('，')'的字符串，判断字符串是否有效。
  - 优化：提升空间复杂度，不用栈 --> 记录变量 ( 则 +1, ) 则 -1
  - 给定一个只包含 '(' 和 ')' 的字符串，找出最长的包含有效括号的子串的长度。
      暴力解法：依次遍历
  - 优化：https://www.cnblogs.com/kubidemanong/p/11949244.html 
  
- [ ] 155: Min Stack `****` `E`
https://leetcode.com/problems/min-stack/
  > 1: 维护两个栈

- [ ] 225: implement-stack-using-queues  
https://leetcode.com/problems/implement-stack-using-queues/

- [ ] 232: implement-queue-using-stacks   
https://leetcode.com/problems/implement-queue-using-stacks/
  > 1: 两个Stack
  > push(): 直接放入输入栈
  > pop(): 输出栈不为空，则pop输出栈；输出栈为空，则先将输入栈 导入到 输出栈，再pop输出栈

- [ ] 84: 柱状图中的最大矩形 `*****` `H`
https://leetcode.com/problems/largest-rectangle-in-histogram/
  > 1: 暴力：两重循环，每次找最小高度
  > 2: 暴力2：遍历，找到左右边界，area = height[i] * (right - left)
  > 3: 栈：优化思路 左边界无需遍历寻找；遍历入栈，栈内元素从小到大排列，

- [ ] 239: sliding-window-maximum `*****` `H`  
  https://leetcode.com/problems/sliding-window-maximum/

  > 1: 暴力解法：循环找最大值，存入数组。
  > 2: 维护大顶堆。Q: 需要删除非堆顶元素  
  > 3: 维护一个双端队列。队列元素为下标，最左为当前最大值的下标。

- [x] 641: 设计循环双端队列 `*****` `M`
https://leetcode.com/problems/design-circular-deque/
  > 1: 链表
  >
  > 2: 数组，头尾指针 (浪费一个元素)
  >    插入头：head = (head - 1 + capacity) % capacity;
  >    插入尾：tail = (tail + 1) % capacity;
  >    判空/满：head == tail / (tail + 1) % capacity == head
  >
  > 3: 数组，头指针+size （不浪费元素）
  >    插入头：head = (head - 1 + capacity) % capacity;
  >    插入尾：(head + size) % capacity
  >    判空/满：size == 0 / size == capacity




## Tree

- [ ] : 二叉树转中序链表

- [x] 199: 二叉树右视图 `*****` `M` 
https://leetcode.com/problems/binary-tree-right-side-view/ 
  > 1: BFS，记录最后一个item
  > 2: DFS，中 - 右 - 左，每层最先访问的都是最右边节点  

- [x] 94: 二叉树中序遍历 `*****` `M`  
https://leetcode.com/problems/binary-tree-inorder-traversal/
  > 1: 递归  
  > 2: 迭代，使用栈  
  > 3: 优化栈，保存状态：更通用  

- [x] 144: 二叉树前序遍历 `****` `M`  
https://leetcode.com/problems/binary-tree-preorder-traversal/  
  > 1: 递归  
  > 2: 迭代，使用栈   
  > 3: 优化栈，保存状态：更通用   
  
- [x] 145: 二叉树后序遍历 `****` `H`
https://leetcode.com/problems/binary-tree-postorder-traversal/
  > 1: 节点栈 + 状态栈
  
  
- [x] 589: N叉树前序遍历 `*****` `E`  
https://leetcode.com/problems/n-ary-tree-preorder-traversal/  
  > 1: 递归  
  > 2: 栈：倒序遍历子节点，入栈   

- [ ] 590: N叉树后序遍历
https://leetcode.com/problems/n-ary-tree-postorder-traversal/

- [x] 429: N叉树层序遍历 `*****` `M`  
https://leetcode.com/problems/n-ary-tree-level-order-traversal/  
  > 1: BFS 基于队列       
  > 2: DFS    
  > 3: BFS 基于List保存上一层数据 *****   

- [ ] 515: 在每个树行中找最大值 `*****` `M`
https://leetcode.com/problems/find-largest-value-in-each-tree-row/


- [ ] 98: 验证二叉搜索树 `*****` `M`
https://leetcode.com/problems/validate-binary-search-tree 
  > 1: 中序遍历，校验递增。优化：遍历过程中与前继节点值。
  > 2: 递归 validate(node, min, max)，校验子树各节点值区间 (min, max)

- [ ] 235: 二叉搜索树的最近公共祖先
https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/
  > 1: 可用 236 的思路。
  > 2: 如果p/q都大于 cur，则找右子树；如果 p/q 都小于cur，则找左子树；否则，cur即是祖先。

- [x] 236: 二叉树的最近公共祖先 `****` `M`  
https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/
  > 1: 向上找 Path，找到两条 Path 的交汇点。
  > 2: 递归 findPorQ(), 如果左右子树返回值都 != null，则当前节点是祖先。否则继续查找返回值 != null 的子树。 

- [ ] 226: 翻转二叉树 `***` `E`
https://leetcode.com/problems/invert-binary-tree/

- [x] 102: 二叉树层次遍历 `*****` `M`
https://leetcode.com/problems/binary-tree-level-order-traversal/
  > 1: BFS: 队列如何保存层信息？ --> batch process (queue size)，不存层信息
  > 2: DFS: 逐个放入结果二维数组。BFS 按行放，DFS 按列放。

- [ ] 104: 二叉树最大深度 `****` `E`   
https://leetcode.com/problems/maximum-depth-of-binary-tree/
  > 1: 递归、分治：max(left, right) + 1
  > 2: BFS: 按层遍历，扫到最后一层 
  > 2: DFS: 遍历节点，记录深度；如果是叶子节点，则更新 min/max

- [ ] 111: 二叉树最小深度  `****` `E`  
https://leetcode.com/problems/minimum-depth-of-binary-tree/
  > 1: 递归：min(left, right) + 1 ，注意：没有left/right的情况
  > 2: BFS: 按层遍历，如果碰到叶子节点，则找到最小值
  > 2: DFS: 遍历节点，记录深度；如果是叶子节点，则更新 min/max

- [ ] 297: 二叉树的序列化与反序列化 `*` `H`  
https://leetcode.com/problems/serialize-and-deserialize-binary-tree/

- [x] 105: 从前序与中序遍历序列构造二叉树 `*****` `M` //TODO
https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/
  > 1: 递归 
  >    preStart: 根节点，遍历preorder
  >    inStart / inEnd: 中序遍历数组的有效位


## Heap  

- [ ] 703: kth-largest-element-in-a-stream  
https://leetcode.com/problems/kth-largest-element-in-a-stream/discuss/149050/Java-Priority-Queue
  > 1: 维护最大K个元素，新元素进入并排序。 
  > 2: 用小顶堆，保证size = K。如果新元素 > 顶，则调整：移除顶，新元素进入堆

- [ ] : 一个无序数组求第K大数  
https://leetcode.com/problems/kth-largest-element-in-an-array

- [x] 263: 丑数 `***` `E`   
https://leetcode.com/problems/ugly-number/  
  > 1: 递归，依次尝试除以 [2,3,5]    
  > 2: 迭代，如能整除 [2,3,5]，则除   

- [x] 264: 丑数2 `*****` `M`    
https://leetcode.com/problems/ugly-number-ii/   
  > 1：暴力，循环判断isUgly      
  > 2: 堆，将计算得出的丑数放入Heap，再从小到大取出依次乘以 [2,3,5]  
  > 3: DP 三指针，表示待与 [2,3,5] 相乘的数       

- [x] 347: 前K个高频元素 `*****` `M`   
https://leetcode.com/problems/top-k-frequent-elements/   
  > Map: num - count    
  > 1: 对Map进行排序，取前K       
  > 2: 将Map元素导入堆中    
  > 3: 将Map元素进行桶排序 （只适用于非负数元素）   
  > 4: 快排思想 （?）  
  > 5: 不用Map，而将 num+count作为对象排序。--> 为什么会更快？   

- [ ] : 数据流的中位数



## Hash

- [x] 242: 有效的字母异位词 `*****` `E`   
https://leetcode.com/problems/valid-anagram/   
  > 1: 先排序，再比较   
  > 2: Map 计数，比较Map是否相同  
  > 3: int[26] 计数   
 
- [x] 49: 字母异位词分组 `*****` `M`  
https://leetcode.com/problems/group-anagrams/  
  > Map: key - 异位词hash, value - 字符串列表  
  > 哈希算法：1. 排序后的字符串；2. int[26] ；  
  
- [x] 1: Two Sum `***` `E`  
https://leetcode.com/problems/two-sum/  
  > 1: 暴力解法：两重遍历   
  > 2: 哈希：遍历一遍，记录 Map<期望匹配值，index>；或记录 Map<元素值, index>   

- [ ] 15: Three Sum `*****`  
https://leetcode.com/problems/3sum/
  > 1: 暴力：三重遍历
  > 2: Map：先把数组放入Map；两重遍历，查询Map中是否有 -(a+b)    
  > 3: 双指针：先排序；遍历，同时在后继元素中使用双指针

- [ ] 18: Four Sum   
https://leetcode.com/problems/4sum/


## 递归、分治、回溯

- [x] 50: powx-n   
https://leetcode.com/problems/powx-n/   
  > 1: 调用库函数   
  > 2: 暴力：一层循环，逐个相乘    
  > 3: 分治：如果是偶数个 = y * y; 如果是奇数个 = y * y * x. (y = x的n/2方)    
``` 
  r = power(a, n / 2);
```
  > 4: 迭代：
```   
  int res = 1, tmp = a;
  while(n) {
    if (n & 1 ) res *= tmp;  
    n >>= 1;
    tmp *= tmp;
  }    
  return res;
```


- [x] 22: 括号生成 `*****` `M` 
https://leetcode.com/problems/generate-parentheses/   
  > 1: 数学归纳法：n=1, 2, ...   
  > 2: 递归：构造2*n长度的数组，元素分别填入左右括号，validate     
  > 3: 改进2，剪枝：        
       a) 局部不合法，不再递归；  
       b) 保存leftUsed / rightUsed   
                     
- [x] 77: 组合 `*****` `M`
https://leetcode.com/problems/combinations/
  > 1: 递归回溯: 
  >    for i in level~n, list.add(i) 
  >    dfs(i + 1)

- [x] 46: 全排列 `*****` `M` //TODO
https://leetcode.com/problems/permutations/
  > 1: 递归回溯
  >    for i in 0~len, path.add(nums[i])
  >    dfs(depth + 1)
  >    状态变量
  >    - depth: 递归到第几层 
  >    - path: 已经选择了哪些数 (Stack)
  >    - used: boolean[]，已经选择的数
  
- [x] 47: 全排列2 `*****` `M` 
https://leetcode.com/problems/permutations-ii/

- [x] 169: 众数
https://leetcode.com/problems/majority-element
  > 1: 暴力: 遍历元素，再遍历求次数
  > 2: Map: 遍历元素，放入map计数
  > 3: 排序: 排序后遍历。--> 优化：排序后直接取最中间元素
  > 4: 分治: 分别求左右众数，当 left == right --> left; 当left count > right count --> left

- [x] 17: 电话号码字母组合 `*****` `M`
https://leetcode.com/problems/letter-combinations-of-a-phone-number/
  > 1: 回溯：level = 当前处理第几个数字

- [ ] https://leetcode.com/problems/maximum-subarray
- [ ] https://leetcode.com/problems/valid-anagram
- [ ] https://leetcode.com/problems/find-all-anagrams-in-a-string 
- [ ] https://leetcode.com/problems/anagrams


### 二分查找 / BFS / DFS
- [x] 69: 平方根 `*****` `E` 
https://leetcode.com/problems/sqrtx/
  > 1: 二分法，因为单调递增
  > 2: 牛顿迭代法* 

- [ ] 367: 有效的完全平方数 `*****` `E` 
https://leetcode.com/problems/valid-perfect-square/

- [ ] 208: 实现字典树 
https://leetcode.com/problems/implement-trie-prefix-tree/

- [ ] 79: 单词查找 
https://leetcode.com/problems/word-search/

- [ ] 212: 单词查找2
https://leetcode.com/problems/word-search-ii/ 
  > 1: DFS；缺点：对每个候选词都要重新计算
  > 2: Trie: 把候选词构造成Trie树，对矩阵进行 DFS

- [x] 127: 单词接龙 `*****` `M` (!!!)
https://leetcode.com/problems/word-ladder/
  > 1: DFS //TODO
  > 2: BFS. 先构造patternMap，再从beginWord开始按层次遍历
  > 3: BFS 优化。分别从 beginWord/endWord 往中间夹逼

- [x] 126: 单词接龙2 `*` `H`
https://leetcode.com/problems/word-ladder-ii/
  > 126: 找出"所有"从 beginWord 到 endWord 的最短转换序列
  > 127: 找到从 beginWord 到 endWord 的最短转换序列的长度
  > 1: BFS, queue存储当前处理的路径（单词列表） --》 TODO 执行超时

- [ ] 433: 最小基因变化 `*****` `M`
https://leetcode.com/problems/minimum-genetic-mutation/


- [x] 200: 岛屿数量 `*****` `M`
https://leetcode.com/problems/number-of-islands/
  > 遍历矩阵，碰到1 --> res++，并且 DFS 遍历周边节点，置为0

- [x] 529: 扫雷游戏 `*****` `M`
https://leetcode.com/problems/minesweeper/
  > DFS
  >  - 遇到 M，标记，结束
  >  - 遇到 X，结束
  >  - 否则是 E，
  >     - 如果周边有雷，更新当前位置
  >     - 如果周边无雷，DFS 向四周扩散

- [x] 74: 搜索二维矩阵 `*****` `M`
https://leetcode.com/problems/search-a-2d-matrix/
  > 1: 两步走：先二分找到 子数组；再查子数组
  > 2: 矩阵其实是 M * N 的有序数组

### 剪枝

- [x] 51: N 皇后 `**` `H`   
https://leetcode.com/problems/n-queens/  
  > 1: 暴力    
  > 2: 剪枝：标记 行col[col]，撇pie[row+col]，捺na[row-col]     
  > 3: 位运算：判重记录二进制 col, pie, na (???)    

- [ ] 52: N 皇后2 `**` `H` 
https://leetcode.com/problems/n-queens-ii/

- [ ] 36: 数独
https://leetcode.com/problems/valid-sudoku/
  > 1: Naive DFS
  > 2: 加速：预处理 找出每个位置的可选数；按可选数个数排序；从选项少的开始
  > 3: 高级数据结构：DancingLink

- [ ] 37: 数独 
https://leetcode.com/problems/sudoku-solver/

### 贪心
- [x] 455: 分发饼干 `*****` `E`
https://leetcode.com/problems/assign-cookies/
  > 贪心，先排序，从最小的胃口还是满足 

- [x] 860: 柠檬水找零 `*****` `E` 
https://leetcode.com/problems/lemonade-change/
  > 遍历，记录可用的零钱数目；收到整钱后，对零钱数目扣减

- [x] 874: 模拟行走机器人 `*****` `E`
https://leetcode.com/problems/walking-robot-simulation/
  > 逐个解析 commands，
  >   如果是-1/-2，则更新方向
  >   其他情况，for 0-command, 更新res = max(res, x*x + y*y)
  >            注意如果碰到obstacle，则不动

- [x] 55: 跳跃游戏 `*****` `M`
https://leetcode.com/problems/jump-game/
  > 1: 从前往后遍历，将能走到的索引置为 true
  > 2: 贪心：从前往后遍历，维护最大可达位置
  > 3: 贪心：从后往前遍历，if (nums[i] + i >= endReachable) endReachable = i;

- [x] 45: 跳跃游戏2 `*****` `H`
https://leetcode.com/problems/jump-game-ii/
  > 1: 动态规划 --TODO 击败11%，太慢
  >      状态: dp[n] = 索引 n 到最后位置的 minSteps
  >      状态转移方程：dp[n] = min{dp[n + 1...nums[n]]}
  > 2: BFS, --TODO 超时
## 位运算
- [ ] 191: Number of 1 bits `***` `E`
https://leetcode.com/problems/number-of-1-bits/
  > 1: 枚举所有位数：不断右移 %2， 余数==1则count++ 
  >    枚举所有位数：& mask(1), mask每次左移一位
  > 2: x = x & (x-1) 清零最低位的1

- [ ] 231: Power of Two `***` `E`  
https://leetcode.com/problems/power-of-two/    
  > 1: 不断 mod 2, 测试是否能被2整除   
  > 2: 数学求 log2     
  > 3: 位运算：特点 最前面是1，后面全0：x & (x-1) == 0       

- [ ] 338: Counting Bits `***` `M`
https://leetcode.com/problems/counting-bits/ 
  > 1: 遍历0~n, ?  
  > 2: count[i] = count[i&(i-1)] + 1    
  >    i&(i-1) : 清零最低位的1

- [ ] :
https://leetcode.com/problems/n-queens-ii/



## 动态规划    

DP：
1. 递归 + 记忆化 ==> 递推
2. 状态的定义： opt[n]
3. 状态转移方程：opt[n] = bestOf(opt[n-1], opt[n-2], ...)
4. 最优子结构

- [x] 70: 爬楼梯 `****` `E`   
https://leetcode.com/problems/climbing-stairs/  
  > 找最近重复子问题，数学归纳法。    
  > 如何走到第n级：从n-1走过来 + 从n-2级走过来。   
  > 1: 斐波那契数列，递归 f(n) = f(n-1) + f(n-2)。   
  > 2: 优化：缓存，保存a[i]；或直接只保存最后三个数。  
  > 3: 迭代：3~n      
  > 4: DP:  
  >    状态： dp[n] 登到第n级台阶的方法数     
  >    状态转移方程： dp[n] = dp[n-1] + dp[n-2]      

- [x] 78: 子集 `*****` `M`  
https://leetcode.com/problems/subsets/  
  > 1: 对 nums 依次删除元素，依靠Set去重  
  > 2: 从空数组开始，依次考虑原数组每个元素，往已有的结果中添加  
  > 3: DFS，类似创建括号对  
  > 3: 回溯   
  > 4: 位运算？    

- [x] 120: 三角形最小路径和 `*****` `M`
https://leetcode.com/problems/triangle/
  > 1: 递归
  > 2: 贪心，可能不是最优
  > 3: DP，动态递归； 两层循环： for i m-1 --> 0, for j   
  >    状态定义：dp[i, j]，从底走到(i, j) 路径和的最小值 
  >    状态转移方程：`dp[i, j] = min(dp[i+1, j], dp[i+1, j+1]) + triangle[i, j]`
  >    起始状态：`dp[m-1, j] = triangle[m-1, j]`
  >    结果值：dp[0, 0]
  > 
  >  优化：状态存储无需二维，只需一位数组存储当前层的min

- [] 152: 乘积最大子数组 `****` `M`
https://leetcode.com/problems/maximum-product-subarray/
  > 1: 暴力，递归
  > 2: DP，
  >    状态定义：max[i]，存储从0走到i的max product
  >             min[i]，存储从0走到i的min product(负的max)，以便处理后续的负值
  >    状态转移方程：if a[i] >= 0, `max[i] = max[i-1] * a[i]`, `min[i] = min[i-1] * a[i]`
  >                else          `max[i] = min[i-1] * a[i]`, `min[i] = max[i-1] * a[i]` 
  >  空间优化：只存最近两次的max / min

- [ ] : 股票买卖  
121: https://leetcode.com/problems/best-time-to-buy-and-sell-stock/ `E`  
- [x] 122: https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/ `*****` `E`  
123: https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/ `H`  
188: https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iv/ `H`  
309: https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/ `M`  
714: https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/ `M`  
  > 1: 暴力
  > 2: 贪心（122）：只要后一天价格比前一天高，则前一天买 后一天卖
  > 3: 通用DP (188，不考虑K): 
  >    状态：`mp[i][j]` 到第i天的最大利润, j=0/1 表示当前是否已持有股票
  >    方程：当前不持有: `m[i, 0] = MAX{ mp[i-1, 0], mp[i-1, 1] + a[i] }` // MAX{ 前一天不持有, 前一天持有当天卖出 }
  >         当前持有:   `m[i, 1] = MAX{ mp[i-1, 1], mp[i-1, 0] - a[i] }` // MAX{ 前一天持有，前一天不持有当天买入}
  > 
  > 4: 通用DP (188，考虑K):
  >    状态：`mp[i][k][j]` 到第i天的最大利润, j=0/1 表示当前是否已持有股票，k 表示之前总共交易的次数
  >    方程：当前已持有: `mp[i, k, 0] = MAX{ mp[i-1, k, 0], mp[i-1, k-1, 1] + a[i] }` // MAX{ 前一天不持有, 前一天持有当天卖出 }
  >         当前不持有：`mp[i, k, 1] = MAX{ mp[i-1, k, 1], mp[i-1, k-1, 0] - a[i] }` // MAX{ 前一天持有，前一天不持有当天买入}
  >    循环：for i 0-->n-1, k 0-->K； 结果 MAX{ mp[n-1, {0..k}, 0] }

- [ ] 322: 零钱兑换 `*****` `M`
https://leetcode.com/problems/coin-change/
  > 1: 暴力，递归 ？  
  > 2: 贪心，不最优，例如 [1,6,7] --> 30  
  > 3: DP，类比爬楼梯问题  
  >    状态：dp[i] 上到第i阶的最小步数  
  >    方程：dp[i] = min(dp[i-coins[j]]) + 1

- [ ] 72: 编辑距离 `***` `H`
https://leetcode.com/problems/edit-distance/
  > 1: 暴力，bfs + queue  
  > 2: DP   
  >    状态：`dp[i,j]` word1的前一个字符，替换为word2前j个字符，需要的最少步数   
  >    方程：if w1[i] == w2[j], `dp[i,j] = dp[i-1,j-1]`;   
            else,              `dp[i,j] = 1 + min(dp[i-1,j], dp[i,j-1], dp[i-1,j-1])`; //分别对应增/删/替换

- [ ] 300: 最长上升子序列 `*****` `M`
https://leetcode.com/problems/longest-increasing-subsequence/
  > 1: 暴力，2N次方  
  > 2: DP，N平方 
  >    状态：`dp[i]` 从头到i的最长子序列长度  
  >    方程：for i 0-->n-1, j 0-->i-1, if a[j]<a[i] `dp[i] = max(dp[j]) + 1`
  > 
  > 3: 二分法，优化第二层循环，N(logN)
  >    维护数组LIS; 遍历数组，if a[i] > LIS[max], 插入LIS尾部; 否则，替换LIS中最早>a[i]的元素   
  > 

- [ ] 887: 鸡蛋掉落 `*` `H`
https://leetcode.com/problems/super-egg-drop/


- [ ] : 斐波那切数列
  > 1: 递归 f(n) = f(n-1) + f(n - 2)
  > 2: 动态规划：递归 + 记忆化, 缓存f(i)   
  > 3: 动态规划2：从最小数开始算起，for 2~n


## 并查集

- [ ] 547: Friend Circles `***` `M`
https://leetcode.com/problems/friend-circles/


## 代码模板


- 递归

```
public void recursion(level, param1, ...) {
  // terminator
  if (level > MAX) return;  

  // process logic in current level : 只做当前层的任务！不要尝试下探
  process(level, params);

  // drill down
  recusion(level + 1, p1, ...);

  // reverse the current level status if needed
  reverseState(level);
}
```

- 回溯，分治，DP: 找重复性（最近重复性、最优重复性）
  > https://labuladong.gitbook.io/algo/di-ling-zhang-bi-du-xi-lie/hui-su-suan-fa-xiang-jie-xiu-ding-ban
  > vs. DP: 回溯相当于DP的暴力求解，因为没有重叠子问题，就像DP那样无法大幅剪枝 
``` 
backtrack(path, depth, choices) { 
    if (满足条件) 
        res.add(path)
        return;
    for choice : choices
        path += choice //做选择
        backtrack(path, depth + 1, choices); 
        path -= choice // 撤销选择
}

```

- DFS: 递归
```
visited = set()
dfs(node, visited) { 
  //terminatior
  if (visited.contains(node) {
    return;
  }    

  visited.add(node);
  // process current node
  ...       
      
  // drill down
  for nextNode in node.children() 
    if (!visited.contains(nextNode)) 
       dfs(nextNode, visited)
}
```

- BFS：队列存储
```
bfs(node, start, end) {
  queue = []
  queue.append(start)
  visited.add(start) // 避免走回头路

  while (queue) { 
    if 达到目标
       return;
    node = queue.pop()
    visited.add(node) 

    process(node);  
 
    nodes = node.children()
    queue.push(nodes)
  }
}

```  

- 二分搜索
  1. 目标函数单调性
  2. 存在上下界
  3. 能够通过索引访问 （反例：单链表）
```
left = 0, right = arr.length - 1;
while (left <= right) {
  mid = left + (right - left) / 2;
  if (arr[mid] == target) 
     // found
  else if (arr[mid] < target) 
     left = mid + 1;
  else if (arr[mid] > target)
     right = mid - 1;
}
```  

二分搜索左边界 
[left, right)
```
left = 0; 
right = arr.length;
while (left < right) {
  mid = left + (right - left) / 2;
    if (arr[mid] == target) 
       right = mid;
    else if (arr[mid] < target) 
       left = mid + 1;
    else if (arr[mid] > target)
       right = mid;
}     
if (left == arr.length) return -1;
return arr[left] == target ? left : -1;
```    

二分搜索左边界 
[left, right]
```
left = 0; 
right = arr.length - 1;
while (left <= right) {
  mid = left + (right - left) / 2;
  if (arr[mid] < target) {
     left = mid + 1;
  } else if(arr[mid] > target) {
     right = mid - 1;
  } else if(arr[mid] == target) {
     right = mid - 1;
  }
}  
if (left >= arr.length || nums[left] != target ) return -1;
return left;
```

- DP
```
// 状态定义
dp = new int[m+1][n+1]

// 初始状态
dp[0][0] = x
dp[0][1] = y

// DP状态推导
for (int i = 0; i <= n; i++)
  for (int j = 0; j <= m; j++) 
    d[i][j] = min {dp[i-1][j], dp[i][j-1], ...}
```     

- 滑动窗口
```
/* 滑动窗口算法框架 */
void slidingWindow(string s, string t) {
    unordered_map<char, int> need, window;
    for (char c : t) need[c]++;

    int left = 0, right = 0;
    int valid = 0; 
    while (right < s.size()) {
        // c 是将移入窗口的字符
        char c = s[right];
        // 右移窗口
        right++;
        // 进行窗口内数据的一系列更新
        ...

        /*** debug 输出的位置 ***/
        printf("window: [%d, %d)\n", left, right);
        /********************/

        // 判断左侧窗口是否要收缩
        while (window needs shrink) {
            // d 是将移出窗口的字符
            char d = s[left];
            // 左移窗口
            left++;
            // 进行窗口内数据的一系列更新
            ...
        }
    }
}

```

## Java 数据结构操作
Queue:
- offer() / add()  
- poll() / remove()
- peek() / element() null / exception

Deque
- getFirst() / getLast()
- removeFirst() / removeLast()  : exception
- addLast() / addFirst()
- pollFirst() / pollLast()   : null
- offerFirst() / offerLast() 