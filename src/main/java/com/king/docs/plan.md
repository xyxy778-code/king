# 王者荣耀管理系统 - 开发计划书

## 1. 项目目标

开发一个基于 Java 控制台的王者荣耀主题管理系统，支持玩家、英雄、装备、队伍、比赛记录等核心数据的管理与查询。通过该项目综合运用 Java OOP 核心知识，并借助 AI 工具辅助开发，最终交付一个结构清晰、功能完整的控制台应用程序。

目标用户：Java 初学者 / 课程设计提交者
技术栈：Java SE 26（纯控制台，无框架）

---

## 2. 功能分析

### 2.1 核心功能模块

| 模块 | 功能说明 |
|------|----------|
| 登录认证 | 支持 Admin / Player 双角色登录，注册新玩家 |
| 玩家查询 | 输入 ID 或昵称，显示基本信息、所属队伍、英雄及装备详情 |
| 队伍概览 | 显示队伍成员、平均等级、比赛数量、胜率、最强玩家 |
| 英雄详情 | 显示类型、基础属性、可用装备、使用玩家、推荐装备 |
| 装备统计 | 按使用次数 / 胜率影响 / 综合评分排序，展示排序公式 |
| 比赛历史 | 显示最近 N 场比赛的对手、时间、胜负、英雄选择 |
| 排行榜 | 按胜率 / 等级 / 比赛数量 / 自定义评分排序，含同分处理规则 |
| 数据管理 | Admin 可增删改所有数据，Player 仅可浏览和修改个人信息 |
| 文件持久化 | 支持将数据保存到文本文件 / 从文件加载 |
| 搜索 | 全局搜索玩家 / 英雄 / 装备 / 队伍 / 比赛记录 |
| 开发日志 | 记录每次 AI 辅助开发的 prompt、回答、commit 等信息 |

### 2.2 数据规模

- 3 支队伍
- 10 个玩家（每人 ≥ 3 英雄）
- 15 个英雄（每人 ≥ 2 装备）
- 20 件装备
- 10 条比赛记录

---

## 3. Java 知识使用位置

| Java 知识点 | 使用位置（文件:行号） |
|-------------|---------------------|
| **类与对象** | 所有 entity 类 (Player.java, Hero.java 等) |
| **继承** | Player → Person → BaseEntity (Player.java:6, Person.java:3) |
| **接口** | Searchable, Rankable, HasOwner, Identifiable, Persistable |
| **多态** | AuthService 中 Person currentUser 指向 Player 或 Admin (AuthService.java:11) |
| **封装** | 所有字段 private，通过 getter/setter 访问 |
| **抽象类** | BaseEntity (提供公共字段和抽象方法 toDisplayString) |
| **集合框架** | ArrayList (Entity 内列表), LinkedHashMap (BaseDataAccess 存储), HashMap |
| **泛型** | DataAccess<T>, BaseDataAccess<T>, 泛型方法 search() / rank() |
| **枚举** | HeroType (6 种英雄类型), EquipmentCategory (5 种装备类型) |
| **异常处理** | InputException, DataException, MenuController 全局 try-catch |
| **文件 IO** | FilePersistence 读写 data/*.txt (Files.readAllLines / Files.write) |
| **Lambda / Stream** | RankingService / SearchService 中的 stream().filter().sorted() |
| **Optional** | AuthService.login 返回 Optional<Person> |
| **LocalDateTime** | MatchRecord 比赛时间和 DevLog 日志时间 |
| **Comparator** | RankingService 自定义比较器进行多级排序 |
| **toString 重写** | 所有 Entity 类重写 toString/toDisplayString |

---

## 4. 类设计

### 4.1 包结构

```
com.king
├── App.java                        # 程序入口
├── entity/                         # 实体层
│   ├── BaseEntity.java             # 抽象基类，实现 Identifiable
│   ├── Person.java                 # 用户抽象类，实现 Searchable
│   │   ├── Player.java             # 玩家，实现 Rankable, HasOwner
│   │   └── Admin.java              # 管理员
│   ├── Hero.java                   # 英雄，实现 Searchable, Rankable
│   ├── Equipment.java              # 装备，实现 Searchable, Rankable
│   ├── Team.java                   # 队伍，实现 Searchable, HasOwner
│   ├── MatchRecord.java            # 比赛记录，实现 Searchable
│   ├── DevLog.java                 # 开发日志，实现 Identifiable
│   ├── HeroType.java               # 英雄类型枚举
│   ├── EquipmentCategory.java      # 装备类别枚举
│   └── 接口 (Identifiable, Searchable, Rankable, HasOwner)
├── dao/                            # 数据访问层
│   ├── DataAccess.java             # 泛型数据访问接口
│   ├── BaseDataAccess.java         # 泛型数据访问实现（LinkedHashMap）
│   ├── DataStore.java              # 单例数据存储 + 样本数据
│   ├── Persistable.java            # 持久化接口
│   └── FilePersistence.java        # 文件读写实现
├── service/                        # 业务逻辑层
│   ├── AuthService.java            # 登录认证
│   ├── DataManageService.java      # 数据 CRUD
│   ├── SearchService.java          # 搜索
│   ├── RankingService.java         # 排行榜
│   ├── PlayerQueryService.java     # 玩家查询
│   ├── TeamOverviewService.java    # 队伍概览
│   ├── HeroDetailService.java      # 英雄详情
│   ├── EquipmentStatsService.java  # 装备统计
│   ├── MatchHistoryService.java    # 比赛历史
│   └── DevLogService.java          # 开发日志
├── controller/                     # 控制层
│   ├── MenuController.java         # 主菜单控制器
│   └── DevLogMenu.java             # 开发日志菜单
└── util/                           # 工具类
    ├── InputException.java         # 输入异常
    └── DataException.java          # 数据异常
```

---

## 5. UML 图

```
┌─────────────────────┐       ┌──────────────────────┐
│    <<interface>>    │       │    <<interface>>     │
│    Identifiable     │       │     Searchable       │
│  + getId(): String  │       │  + matches(k): bool  │
└─────────┬───────────┘       └──────────┬────────────┘
          │                              │
┌─────────┴──────────────────────────────┴────────────┐
│                BaseEntity (abstract)                │
│  # id: String                                        │
│  # name: String                                      │
│  + toDisplayString(): String (abstract)              │
└─────────────────────────┬──────────────────────────┘
                          │
         ┌────────────────┼─────────────────┐
         │                │                  │
┌────────┴────────┐ ┌─────┴──────┐  ┌───────┴────────┐
│  Person         │ │   Hero     │  │  Equipment     │
│ (abstract)      │ │            │  │                │
│ - username      │ │ - title    │  │ - category     │
│ - password      │ │ - heroType │  │ - price        │
│ - nickname      │ │ - price    │  │ - effect       │
│ + matches()     │ │ - winRate  │  │ + matches()    │
└───────┬─────────┘ │ - equipIds │  │ + getRankValue │
        │           │ + matches  │  └────────────────┘
   ┌────┴────┐      │ + getRank  │
┌──┴───┐ ┌──┴───┐  └────────────┘
│Player│ │Admin │
│- lvl │ │- role│
│-score│ └──────┘
│-hero │
│Ids   │
└──────┘

┌─────────────────┐   ┌──────────────────────┐
│     Team        │   │   MatchRecord        │
│ - playerIds     │   │ - teamAId/teamBId    │
│ + getMembers()  │   │ - winnerTeamId       │
│ + getTotalScore │   │ - matchTime          │
│ + avgLevel      │   │ - heroIds            │
└─────────────────┘   └──────────────────────┘

┌─────────────────┐
│    DevLog       │
│ - time          │
│ - aiTool        │
│ - role          │
│ - promptContent │
│ - aiAnswerSum   │
│ - adopted       │
│ - gitCommit     │
└─────────────────┘

关系说明:
  Player ──→ Hero      : 关联（玩家拥有英雄，通过 heroIds 关联）
  Hero ──→ Equipment   : 关联（英雄配备装备，通过 equipIds 关联）
  Team ──→ Player      : 组合（队伍包含玩家，队伍销毁则关系解除）
  MatchRecord ──→ Team : 关联（比赛涉及两支队伍）
```

---

## 6. 数据设计

### 6.1 数据格式（文本文件持久化）

| 文件 | 格式 (| 分隔) |
|------|--------------|
| admins.txt | id\|username\|password\|nickname\|role |
| players.txt | id\|username\|password\|nickname\|level\|score\|heroId1,heroId2,... |
| heroes.txt | id\|name\|title\|HeroType枚举名\|price\|winRate\|ownerPlayerId |
| equipments.txt | id\|name\|EquipCategory枚举名\|price\|effect |
| teams.txt | id\|name\|playerId1,playerId2,... |
| matches.txt | id\|teamAId\|teamBId\|winnerTeamId\|matchTime(ISO)\|heroId1,heroId2,... |

### 6.2 存储结构（内存）

所有数据使用 `LinkedHashMap<String, T>` 存储，Key 为 ID，Value 为实体对象，保证插入顺序。

### 6.3 样本数据规模

- 管理员: 2
- 玩家: 10, 每人 3-4 个英雄
- 英雄: 15, 每人 3 件装备
- 装备: 20
- 队伍: 3
- 比赛: 10

---

## 7. AI 使用计划

### 7.1 使用工具

- **AI 模型**: deepseek-chat (via opencode CLI)
- **交互方式**: 自然语言对话式编程

### 7.2 使用场景

| 阶段 | AI 角色 | 使用方式 |
|------|---------|----------|
| 需求分析 | 辅助讨论 | 用自然语言讨论功能列表，AI 帮助梳理 |
| 架构设计 | 提供建议 | AI 推荐包结构和类设计 |
| 代码生成 | 编码助手 | 按 prompt 生成 Java 类文件 |
| 调试排错 | 调试助手 | 提供编译错误信息，AI 定位修复 |
| 代码重构 | 重构助手 | 要求 AI 按 OOP 原则重构代码 |
| 文档生成 | 文档助手 | 生成 plan.md，整理知识使用位置 |

### 7.3 开发日志记录

每次 AI 对话都将记录在 DevLog 中，包括：
- 时间、AI 工具名、角色（user/assistant）
- prompt 内容
- AI 回答总结
- 是否采纳
- 对应的 git commit

---

## 8. Prompt 策略

### 8.1 通用 Prompt 模板

```
角色: 你是一位 Java 导师 / 高级工程师
任务: [具体任务描述]
要求:
  - 使用 Java 标准库，不使用第三方框架
  - 遵循 OOP 原则（封装、继承、多态、接口）
  - [其他具体约束]
输出格式: [完整代码 / 修改说明 / 设计方案]
```

### 8.2 分阶段 Prompt 示例

| 阶段 | Prompt 要点 |
|------|-------------|
| 初始项目 | "帮我初始化一个 Java Maven 项目，包名 com.king" |
| 实体设计 | "为王者荣耀管理系统设计实体类，需要继承、接口、枚举" |
| 功能实现 | "实现玩家查询功能，输入ID显示基本信息、队伍、英雄及装备" |
| 数据填充 | "生成完整样本数据：3队伍/10玩家/15英雄/20装备/10比赛" |
| 异常处理 | "为系统添加异常处理机制，包括自定义异常类和 try-catch" |
| 文件 IO | "实现文件持久化，将数据保存到文本文件" |
| 重构优化 | "按 OOP 原则重构，确保继承/接口/多态/集合/泛型全部体现" |

### 8.3 质量控制策略

- 每次 prompt 后编译验证，发现错误立即反馈给 AI 修复
- 每完成一个功能模块提交一次 git commit
- 定期检查代码中 OOP 特性的完整覆盖

---

## 9. 时间计划

| 阶段 | 内容 | 预计时间 |
|------|------|---------|
| 第1天 | 需求分析、架构设计、创建项目结构 | 1小时 |
| 第1天 | 创建实体类、接口、枚举 | 1小时 |
| 第2天 | 实现 DAO 层（DataStore + 泛型 DataAccess） | 1小时 |
| 第2天 | 实现 Service 层（认证、搜索、排行榜、数据管理） | 2小时 |
| 第3天 | 实现 Controller 层（菜单交互） | 1.5小时 |
| 第3天 | 添加异常处理和文件 IO 持久化 | 1小时 |
| 第4天 | 5.1-5.5 功能：玩家查询/队伍/英雄/装备/比赛 | 2小时 |
| 第4天 | 5.6-5.8 功能：排行榜/数据管理权限/登录系统 | 1.5小时 |
| 第5天 | 填充完整样本数据（10玩家/15英雄/20装备等） | 0.5小时 |
| 第5天 | OOP 重构：确保继承/接口/多态/泛型/集合全覆盖 | 1小时 |
| 第5天 | 添加开发日志模块 | 0.5小时 |
| 第5天 | 编写 plan.md、最终测试、提交 | 1小时 |

---

## 10. 测试计划

### 10.1 功能测试

| 测试项 | 预期结果 |
|--------|----------|
| 管理员登录 (root/root123) | 登录成功，显示管理员角色 |
| 玩家登录 (admin/123456) | 登录成功，显示玩家角色 |
| 错误密码登录 | 提示"用户名或密码错误" |
| 注册新玩家 | 注册成功后可登录 |
| 玩家查询 (ID: P01) | 显示基本信息 + 队伍 + 4个英雄 + 每个英雄3件装备 |
| 队伍概览 (T01) | 显示4名成员 + 平均等级 + 比赛数 + 胜率 + 最强玩家 |
| 英雄详情 (H01 赵云) | 显示战士类型 + 属性 + 3件装备 + 使用玩家 + 推荐装备 |
| 装备统计 | 按3种排序方式正确排序并显示公式 |
| 比赛历史 (N=5) | 显示最近5场比赛，含对手/时间/胜负/英雄 |
| 排行榜 (玩家等级) | 按等级降序，同分按积分降序 |
| 数据浏览 (普通玩家) | 只能查看，不能增删改 |
| 数据管理 (管理员) | 可使用增删改功能 |
| 修改个人信息 | 昵称和密码可正常更新 |
| 保存/加载文件 | 数据写入 data/*.txt 并可重新加载 |
| 开发日志 | 可新增/查看列表/查看详情/删除 |

### 10.2 编译测试

每次修改后执行：
```
javac -d target src/main/java/com/king/**/*.java
```
确保 0 errors。

### 10.3 边界测试

- 空输入、过长输入
- 查找不存在的 ID
- 删除不存在的数据
- 加载空文件

---

## 11. 风险分析

| 风险 | 概率 | 影响 | 应对措施 |
|------|------|------|----------|
| 时间不足 | 中 | 高 | 优先实现核心功能（数据管理 + 查询），UI 简化 |
| AI 生成代码有 Bug | 高 | 中 | 每次生成后编译验证，逐行 review 关键逻辑 |
| 需求变更 | 中 | 中 | 使用模块化设计，便于扩展 |
| 数据量不足影响演示 | 低 | 高 | 提前准备完整样本数据（10人/15英雄/20装备等） |
| 文件读写编码问题 | 中 | 低 | 统一使用 UTF-8 编码 |
| 控制台中文乱码 | 中 | 低 | 设置系统编码，使用 Scanner 标准输入 |
| 自动提交脚本冲突 | 中 | 低 | 手动提交前先 git pull --rebase |

---

## 12. 最终反思（TODO）

> 项目完成后在此处填写反思内容。
> 建议包含：
> - 项目完成度评估
> - AI 辅助开发的体验总结（效果好的地方 / 需要改进的地方）
> - 遇到的困难和解决方案
> - 学到的新知识
> - 后续改进方向
>
> 示例:
> 项目总共实现了 X 个类，覆盖了继承/接口/多态/泛型/集合/枚举/异常/文件IO等Java核心知识。
> AI辅助效率较高，但在处理复杂泛型和编译错误时需多次迭代。
> ...
