# 测试用例文档

> 共 12 个测试用例，全部通过。

---

## Test 01: 管理员登录成功

**Function Tested**: 认证 - 登录
**Input**: 用户名 `root`, 密码 `root123`
**Expected Output**: 登录成功，角色为管理员
**Actual Output**: 登录成功，角色为管理员
**Result**: Pass
**Bug Found**: 无

---

## Test 02: 错误密码登录失败

**Function Tested**: 认证 - 错误密码
**Input**: 用户名 `root`, 密码 `wrong`
**Expected Output**: 返回空 Optional，登录失败
**Actual Output**: 返回空 Optional，登录失败
**Result**: Pass
**Bug Found**: 无

---

## Test 03: 注册新玩家

**Function Tested**: 认证 - 注册
**Input**: ID `P999`, 用户名 `testuser`, 密码 `123`, 昵称 `测试玩家`
**Expected Output**: 注册成功，玩家数 +1
**Actual Output**: 注册成功，玩家数 +1
**Result**: Pass
**Bug Found**: 无

---

## Test 04: 玩家查询 - 按ID

**Function Tested**: 玩家查询
**Input**: 搜索关键词 `P01`
**Expected Output**: 找到玩家 `王者管理员`
**Actual Output**: 找到玩家 `王者管理员`
**Result**: Pass
**Bug Found**: 无

---

## Test 05: 英雄搜索 - 按名称

**Function Tested**: 英雄详情
**Input**: 搜索关键词 `赵云`
**Expected Output**: 找到英雄 `H01 赵云`
**Actual Output**: 找到英雄 `H01 赵云`
**Result**: Pass
**Bug Found**: 无

---

## Test 06: 队伍概览统计

**Function Tested**: 队伍概览
**Input**: 查询队伍 `T01`
**Expected Output**: 队伍有 ≥5 名成员，平均等级 > 20，有比赛记录
**Actual Output**: 成员 5 人，平均等级 26.4，有比赛记录
**Result**: Pass
**Bug Found**: 无

---

## Test 07: 英雄装备详情

**Function Tested**: 英雄详情
**Input**: 查询英雄 `H01 赵云`
**Expected Output**: 赵云有 3 件装备，类型为战士
**Actual Output**: 类型战士，3 件装备，有推荐
**Result**: Pass
**Bug Found**: 无

---

## Test 08: 积分排行顺序

**Function Tested**: 排行榜
**Input**: 查看玩家积分排行
**Expected Output**: 第一名积分 >= 第二名积分
**Actual Output**: 第一名 9999 分 >= 第二名 9130 分
**Result**: Pass
**Bug Found**: 无

---

## Test 09: 等级排行同分处理

**Function Tested**: 排行榜 - 同分规则
**Input**: 查看玩家等级排行
**Expected Output**: 等级相同时按积分降序排列
**Actual Output**: 同等级按积分降序排列正确
**Result**: Pass
**Bug Found**: 无

---

## Test 10: 比赛历史最近 N 场

**Function Tested**: 比赛历史
**Input**: 查询最近 3 场比赛
**Expected Output**: 返回 3 场比赛，按时间降序排列
**Actual Output**: 返回 3 场，按时间降序排列
**Result**: Pass
**Bug Found**: 无

---

## Test 11: 装备统计综合评分

**Function Tested**: 装备统计
**Input**: 按综合评分排序装备，公式 = 胜率×0.4 + 携带英雄数×2.0 + 使用次数×0.1
**Expected Output**: 返回 20 件装备，最高评分 > 0
**Actual Output**: 共 20 件装备，最高评分 23.3
**Result**: Pass
**Bug Found**: 无

---

## Test 12: 数据完整性校验

**Function Tested**: 数据完整性
**Input**: 查看所有数据集合
**Expected Output**: 玩家 ≥ 10，英雄 ≥ 15，装备 ≥ 20，队伍 ≥ 3，比赛 ≥ 10
**Actual Output**: 玩家 10 / 英雄 15 / 装备 20 / 队伍 3 / 比赛 10 - 全部达标
**Result**: Pass
**Bug Found**: 无
