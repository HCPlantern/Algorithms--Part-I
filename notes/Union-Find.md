# Union-Find

# Steps to developing a usable algorithm

1. 对问题建立数学模型
2. 寻找解决问题的算法
3. 算法可能效率不够高
4. 找出造成这些问题的源头
5. 提出新的算法
6. 循环直到满意为止

# Dynamic connectivity

1. Union command: 合并两个对象
2. Find/connect query: 查询两个对象是否连通

## Modeling the objects

现实生活中的应用：

* 图片中的像素
* 网络中的计算机
* 社交网络
* ……

编程时, 可以将对象用整数命名以忽略无关的对象细节（也就是符号表的方法. Chapter 3 将会讲到）

## Modeling the connections
连接是一个等价关系: 

* 自反
* 对称
* 传递

连通分量: 互相连接的对象的最大集合

![image.png](https://s2.loli.net/2022/01/18/reFEG3moQjPgzua.png)

## Implementing the operations
* 查找

* 合并

## Union-find data type (API)
目标：

* 对象和操作数的数目可能会非常大
* 查找和合并操作可能不是孤立的


# Quick Find

## Quick-find [eager approach]

### Data Structure

* 长度为 $N$ 的整数数组 $id[]$
* $p$ 和 $q$ 拥有相同 $id$ 则它们是连通的

### Find

检查 $p$ 和 $q$ 是否拥有相同 $id$

### Union

* 将所有 $id$ 等于 $id[p]$ 的结点改为 $id[q]$

* 需要遍历整个数组

![image.png](https://s2.loli.net/2022/01/18/L6Awp1a5uZi7fKO.png)

## Java implementation
![image.png](https://s2.loli.net/2022/01/18/YNVeHGJAMlmRrP7.png)


## Quick-find is too slow
* ==合并==操作时间复杂度 $O(n)$
* 需要改进算法

![image.png](https://s2.loli.net/2022/01/18/h8k2VgCnFrIizJO.png)

# Quick-union [lazy approach]

### Union

合并两项时将 $p$ 的根指向 $q$ 的根

## Quick-union: Java implementation
![image.png](https://s2.loli.net/2022/01/18/HKUpniVON6Daz8B.png)

## Quick-union is also too slow

* ==查找==操作复杂度过高 ( 树可能太高了 )

![image.png](https://s2.loli.net/2022/01/18/j6TVD3RMPzghKpF.png)

## Improvement 1: weighting
1. 记录每一棵树的大小 ( 对象的数目 )
2. 小树根连大树根

![image.png](https://s2.loli.net/2022/01/18/fKC1LptkBzYIjoH.png)

### Analysis

* Find: 取决于 $ p$ 和 $q$ 的深度
* Union: 常数时间 ( 已经找到根结点的情况下 )
* 任何结点 $x$ 的深度最多只有 $lgN$

![image.png](https://s2.loli.net/2022/01/18/6puYyfVsg5JaQA1.png)

## Improvement 2: path compression
思想: 查找时将路径上的每一个结点都指向它的父节点, 直到根结点.

![image.png](https://s2.loli.net/2022/01/18/dALl4x9yGTcjhJR.png)


### Weighted quick-union with path compression: amortized analysis
* [Hopcroft-Ulman, Tarjan] ==从空开始, 任何在 $ N$ 个结点上的 $ M$ 次并查操作访问数组的 次数 ≤c(N+MlogN)==

* 理论上证明并查集操作**不存在线性时间复杂度**的算法 [Fredman-Saks]
* 实际操作中 WQUPC 已经非常接近线性复杂度


# Union-find applications
## 渗透问题 Percolation

* $N * N$ 个结点
* 每个点开放的概率为 $p$
* 当且仅当顶端和底端以开放点相连, 这个系统才被称为**渗透的**

## Percolation phase transition
存在一个相变的点 $p^*$

* $p > p^*$: 几乎一定渗透
* $p < p^*$: 几乎一定不渗透

![image.png](https://s2.loli.net/2022/01/18/bQ6yYujLptJ5WDG.png)


## Monte Carlo simulation
* 蒙特卡洛模拟

* 随机选择点开放直到渗透, 开放的点的比例可以被证明就是 $p^*$ 的估计

![image.png](https://s2.loli.net/2022/01/18/mb8UC45QVqjucYi.png)


* 实际使用并查集模拟过程中还会加上两个虚拟点以方便验证系统是否渗透 ( 即两个虚拟点是否连通 )

![image.png](https://s2.loli.net/2022/01/18/sjKz96MhtZCl2Fc.png)


* 使用计算机程序估计出 $p^*$ 约为 0.592746
