# AI-Assisted Honor of Kings Information Management System

## 1. Project Overview

A Java console-based information management system for Honor of Kings. Manages players, heroes, equipment, teams, and match records with search, ranking, data management, and authentication features. Developed with AI assistance (opencode/deepseek-chat).

## 2. How to Run

```bash
cd C:\Users\lenovo\Desktop\git
javac -d target src\main\java\com\king\*.java src\main\java\com\king\model\*.java src\main\java\com\king\dao\*.java src\main\java\com\king\service\*.java src\main\java\com\king\util\*.java src\main\java\com\king\controller\*.java
java -cp target com.king.App
```

Run tests:
```bash
javac -d target -cp target src\test\java\com\king\KingSystemTest.java
java -cp target com.king.KingSystemTest
```

## 3. Default Login Accounts

| Role | Username | Password |
|------|----------|----------|
| Admin | root | root123 |
| Admin | op | op123 |
| Player | admin | 123456 |
| Player | player1 | 123456 |

## 4. Implemented Features

- **Authentication**: Admin/Player dual-role login and registration
- **Player Lookup**: Search by ID or name, show team/heroes/equipment
- **Team Overview**: Members, average level, matches, win rate, top player
- **Hero Details**: Type, stats, equipment, owners, recommended equipment
- **Equipment Statistics**: Sort by usage/win rate/custom score with formula
- **Match History**: Recent N matches, opponent/date/result/hero picks
- **Leaderboard**: Win rate/level/match count/custom score with tie rules
- **Data Management**: Admin CRUD, Player read-only + edit profile
- **File Persistence**: Save/load data to data/*.txt
- **Dev Log**: Record AI-assisted development process

## 5. Java Concepts Used

| Concept | Usage |
|---------|-------|
| Inheritance | Player/Admin → Person → BaseEntity |
| Interface | Searchable, Rankable, HasOwner, Identifiable, Persistable |
| Polymorphism | Person reference for Player and Admin |
| Generics | DataAccess<T>, BaseDataAccess<T> |
| Collections | ArrayList, LinkedHashMap |
| Enum | HeroType, EquipmentCategory |
| Exception | InputException, DataException, global try-catch |
| File I/O | FilePersistence (Files.readAllLines / Files.write) |
| Lambda/Stream | RankingService, SearchService |
| Optional | AuthService.login return value |

## 6. AI Usage Summary

- **Tool**: opencode CLI / deepseek-chat
- **Total prompts**: 16
- **Agent roles**: Architect, Implementation, Testing/Reviewer, Documentation
- **Evidence files**: ai/prompts.md, ai/agent-log.md, ai/reflection.md

## 7. Testing Summary

12 automated test cases, all passed (100%).

## 8. Known Limitations

- Data is stored in memory; file persistence required on restart
- Console interface only (no GUI)
- Limited input validation
