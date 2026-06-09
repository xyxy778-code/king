# Prompt 记录

> 本项目所有与 AI 的交互 prompt 记录。

## Prompt 1 - 初始化 Git 项目

```
将桌面上的git文件夹初始化为git项目，并且推送到github上去
```

**AI 回答总结**: 执行 `git init`，配置 user/email，添加远程仓库，创建 README 并推送。

---

## Prompt 2 - Java 项目结构

```
我今后的程序用java来写
```

**AI 回答总结**: 创建 Maven 项目结构，生成 pom.xml，创建标准目录 `src/main/java/com/king/`。

---

## Prompt 3 - 实体类设计

```
设计一个java系统 用于管理 类似王者荣耀的玩家 英雄 装备 队伍 比赛记录 同时还须支持搜索 排行榜 数据管理 登录认证的功能
```

**AI 回答总结**: 设计了 Player/Hero/Equipment/Team/MatchRecord 五个实体类，配套 DAO/Service/Controller 分层。

---

## Prompt 4 - OOP 重构

```
需要存在继承 接口 集合等oop设计
```

**AI 回答总结**: 引入 BaseEntity 抽象类、Identifiable/Searchable/Rankable/HasOwner 接口、泛型 DataAccess<T>、集合框架。

---

## Prompt 5 - Person 基类和 Admin 类

```
3.1 必须包含的类: Person(用户基类) Player Admin Hero Equipment Team MatchRecord
```

**AI 回答总结**: 创建 Person 抽象类继承 BaseEntity，Player 和 Admin 继承 Person，更新 AuthService 支持双角色。

---

## Prompt 6 - 枚举/异常/文件IO

```
必须体现：继承/关联/组合/接口/封装/多态/集合/异常处理/文件IO/枚举
```

**AI 回答总结**: 新增 HeroType/EquipmentCategory 枚举，InputException/DataException 异常，FilePersistence 文件IO，Persistable 接口。

---

## Prompt 7 - 完整样本数据

```
至少包含：3个队伍 10个玩家(每个≥3英雄) 15个英雄(每个≥2装备) 20个装备 10条比赛记录
```

**AI 回答总结**: 更新 DataStore 填充完整样本数据，验证通过。

---

## Prompt 8 - 玩家查询

```
5.1 玩家查询：输入ID或名字，显示基本信息/队伍/等级胜率/英雄及装备
```

**AI 回答总结**: 创建 PlayerQueryService，Hero 增加 equipIds，实现玩家详情完整查询。

---

## Prompt 9 - 队伍/英雄/装备/比赛功能

```
5.2 队伍概览 5.3 英雄详情 5.4 装备统计 5.5 比赛历史
```

**AI 回答总结**: 创建 4 个 Service 类：TeamOverviewService/HeroDetailService/EquipmentStatsService/MatchHistoryService。

---

## Prompt 10 - 排行榜/数据管理/登录

```
5.6 排行榜 5.7 数据管理 5.8 登录系统
```

**AI 回答总结**: 重写 RankingService(4种排行+同分规则)，数据管理区分 Admin/Player 权限，角色登录完善。

---

## Prompt 11 - 开发日志

```
必须记录：时间/AI工具/角色/prompt内容/AI回答总结/是否采纳/Git commit
```

**AI 回答总结**: 创建 DevLog 实体、DevLogService、DevLogMenu，主菜单添加开发日志入口。

---

## Prompt 12 - plan.md

```
8. plan.md要求：项目目标/功能分析/Java知识位置/类设计/UML/数据设计/AI计划/Prompt策略/时间计划/测试计划/风险分析/最终反思
```

**AI 回答总结**: 编写完整 plan.md，含 12 个章节。

---

## Prompt 13 - 目录结构规范

```
代码要求：可运行/main方法/清晰结构/不要全写一个类/命名规范/错误处理/注释合理。推荐结构：src/model/service/util/docs/ai/
```

**AI 回答总结**: entity→model 重命名，新增 docs/ai/ 目录，更新 package 引用，编译通过。

---

## Prompt 14 - 测试用例

```
至少10个测试用例：包括输入/预期输出/实际输出/是否通过
```

**AI 回答总结**: 创建 KingSystemTest，12 个测试用例全部通过。

---

## Prompt 15 - AI 使用规范

```
允许AI但禁止：不理解代码/隐藏AI使用/伪造Git记录/抄别人代码/捏造测试结果。允许用于：设计/调试/解释/测试
```

**AI 回答总结**: plan.md 新增第12章 AI使用规范，创建 ai/USAGE_RECORD.md 合规记录。

---

## Prompt 16 - 最终验收

```
须具备：程序可运行/≥7个类/≥10玩家/≥15英雄/≥20装备/≥3队伍/≥10比赛/登录系统/所有查询功能/plan.md/prompts.md/agent-log.md/reflection.md/Git≥12提交
```

**AI 回答总结**: 创建 prompts.md/agent-log.md/reflection.md，验证所有硬性要求，确认达标后提交。
