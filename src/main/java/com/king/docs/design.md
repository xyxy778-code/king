# 设计文档

## 1. 架构概览

项目采用经典的三层架构 + 工具层：

```
Presentation (Controller) → Business (Service) → Data Access (DAO) → Data (Model)
                                    ↕
                              Utility (异常/工具类)
```

## 2. 包结构

```
com.king
├── App.java              # 程序入口
├── model/                # 数据模型层
│   ├── BaseEntity.java   # 抽象基类
│   ├── Person.java       # 用户抽象类
│   ├── Player.java       # 玩家
│   ├── Admin.java        # 管理员
│   ├── Hero.java         # 英雄
│   ├── Equipment.java    # 装备
│   ├── Team.java         # 队伍
│   ├── MatchRecord.java  # 比赛记录
│   ├── DevLog.java       # 开发日志
│   ├── HeroType.java     # 英雄类型枚举
│   ├── EquipmentCategory.java  # 装备类别枚举
│   └── 接口 (Identifiable, Searchable, Rankable, HasOwner)
├── dao/                  # 数据访问层
│   ├── DataAccess.java   # 泛型数据访问接口
│   ├── BaseDataAccess.java  # 泛型实现（LinkedHashMap）
│   ├── DataStore.java    # 单例数据存储 + 样本数据
│   ├── Persistable.java  # 持久化接口
│   └── FilePersistence.java  # 文件读写实现
├── service/              # 业务逻辑层（9个服务类）
├── controller/           # 控制层（菜单交互）
├── util/                 # 异常类
├── docs/                 # 文档
└── ai/                   # AI 使用记录
```

## 3. 类图

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
        │           │ - ownerId  │  └────────────────┘
   ┌────┴────┐      │ + matches  │
┌──┴───┐ ┌──┴───┐  │ + getRank  │
│Player│ │Admin │  └────────────┘
│- lvl │ │- role│
│-score│ └──────┘
│-hero │
│Ids   │
└──────┘

┌─────────────────┐   ┌──────────────────────┐   ┌─────────────────┐
│     Team        │   │   MatchRecord        │   │    DevLog       │
│ - playerIds     │   │ - teamAId/teamBId    │   │ - time          │
│ + getMembers()  │   │ - winnerTeamId       │   │ - aiTool        │
│ + getTotalScore │   │ - matchTime          │   │ - promptContent │
│ + avgLevel      │   │ - heroIds            │   │ - aiAnswerSum   │
└─────────────────┘   └──────────────────────┘   │ - adopted       │
                                                  │ - gitCommit     │
                                                  └─────────────────┘
```

## 4. 关系说明

| 关系类型 | 说明 | 实现方式 |
|----------|------|----------|
| 继承 | Player/Admin → Person → BaseEntity | extends 关键字 |
| 接口实现 | 实体类实现 Searchable/Rankable 等 | implements 关键字 |
| 关联 | Player 拥有 Hero | Player.heroIds 列表引用 Hero.id |
| 关联 | Hero 可装备 Equipment | Hero.equipIds 列表引用 Equipment.id |
| 组合 | Team 包含 Player | Team.playerIds + getMembers() 返回 Player 对象 |
| 多态 | Person 引用 Player 和 Admin | AuthService 中 Person currentUser |

## 5. 数据流

```
用户输入 → MenuController(解析选择)
         → Service(业务逻辑)
         → DAO/DataStore(数据读写)
         → 返回结果 → 控制台输出
         
文件操作:
FilePersistence.saveAll()
  → 遍历所有 BaseDataAccess
  → 序列化为 | 分隔的文本行
  → Files.write() 写入 data/*.txt

FilePersistence.loadAll()
  → Files.readAllLines() 读取 data/*.txt
  → 反序列化为实体对象
  → BaseDataAccess.add() 加入内存
```
