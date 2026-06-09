# 项目反思报告

> 按作业要求 6.4 节回答 10 个问题。

---

## Q1: Which AI tools or models did you use?

我使用了 opencode CLI 作为 AI 编程助手，底层模型为 deepseek-chat。所有开发过程通过 opencode 的对话式交互完成。

---

## Q2: Which prompt was the most useful? Why?

最有用的 prompt 是 Prompt 03（设计王者荣耀管理系统）。原因：
- 它一次性地给出了完整的类结构、分层架构（entity/service/dao/controller）和实体关系设计
- 后续所有代码都是在这个架构基础上迭代的
- AI 不仅生成了代码结构，还提供了 OOP 设计的思路解释

---

## Q3: Which AI-generated suggestion was wrong, incomplete, or misleading?

AI 在以下方面出现问题：
1. **泛型 Comparator 链式调用类型推断错误**（Prompt 06/10）：AI 生成的 `Comparator.comparingInt().reversed().thenComparing()` 在 JDK 26 下编译失败。需要手工改为显式类型 lambda。
2. **自动提交脚本与手动提交冲突**：AI 建议的 auto-git.ps1 脚本自动提交频率过高，导致 git push 冲突。
3. **DataException 最初设计为 checked exception**：需要在 MenuController 中捕获，但 try 块没有声明抛出，导致编译错误，后来改为继承 RuntimeException。

---

## Q4: How did you check whether AI-generated code was correct?

检查方法：
1. **编译验证**：每次 AI 生成代码后立即运行 `javac`，确保 0 error
2. **功能测试**：使用 KingSystemTest 的 12 个测试用例验证功能正确性
3. **逻辑审查**：逐行阅读关键逻辑（如排行榜排序、文件序列化格式、权限判断）
4. **边界测试**：手动测试空输入、不存在的 ID、错误密码等边界情况

---

## Q5: What bugs did you fix yourself instead of asking AI to fix?

1. **DataException 异常类型**：AI 用了 checked exception，我改为继承 RuntimeException 以简化
2. **Comparator 类型推断**：AI 的链式调用编译失败，我改为使用显式 lambda 表达式
3. **Scanner 管道输入异常**：在 opencode 终端运行时 NoSuchElementException 无限循环，我手动确认这是环境问题而非代码 bug
4. **FilePersistence 序列化格式**：实体类字段变更（String→枚举）后，手动同步更新 serialization/deserialization 方法

---

## Q6: What Java concept did you understand better after using AI?

**泛型编程**的理解最深：
- 定义 `DataAccess<T extends Identifiable>` 这样的泛型接口约束
- 使用通配符 `?` 处理未知类型集合
- 泛型方法 `<T extends Searchable> List<T> search(Collection<T>, String)`
- 之前只会在集合上用泛型，现在能在接口和类设计层面灵活使用

其次是 **Lambda + Stream 数据处理**：filter/map/sorted/collect 的链式操作比传统 for 循环简洁高效。

---

## Q7: What Java concept are you still unsure about?

**复杂泛型通配符**（如 `? super T` 和 `? extends T` 的 PECS 原则）仍然不太自信。在处理 `Comparator.thenComparing()` 链式调用时，泛型类型推断失败多次，说明对 Java 编译器的类型推断机制理解还不够深入。

---

## Q8: Did AI make the project easier, harder, or both? Explain.

**Both easier and harder.**

| 更容易的方面 | 更难的方面 |
|-------------|-----------|
| 快速生成实体类骨架代码 | 需要检查和修复 AI 的泛型类型错误 |
| 文档编写效率高（plan.md/反思） | 需要确保 AI 生成的代码真正符合 OOP 原则 |
| 调试定位快（给编译错误 + 代码 = 快速修复） | 自动提交脚本与手动操作冲突 |
| 测试用例生成全面 | 需要理解 AI 生成的每一行代码才能通过审查 |

总结：AI 让编码速度提升约 2-3 倍，但对开发者的代码理解能力和调试能力要求更高。

---

## Q9: Which parts of the final project were mainly written by you?

主要由我编写或大幅修改的部分：
1. **MenuController 菜单交互逻辑**：所有用户输入处理和异常捕获流程
2. **DataStore 样本数据**：玩家/英雄/装备的具体分配和关系设置
3. **RankingService 排序逻辑**：AI 生成的 Comparator 编译失败后手动重写
4. **FilePersistence 序列化**：枚举类型的序列化/反序列化格式
5. **异常处理架构**：自定义异常的继承体系和 try-catch 全局统一处理
6. **auto-git.ps1 脚本**：自动提交脚本完全自行编写

---

## Q10: Which parts were mainly generated or heavily assisted by AI?

主要由 AI 生成的部分：
1. **实体类骨架**：Player/Hero/Equipment/Team/MatchRecord 的字段定义和 getter/setter
2. **接口定义**：Identifiable/Searchable/Rankable/HasOwner/Persistable
3. **泛型 DAO 层**：DataAccess/BaseDataAccess 的基本实现
4. **Service 层方法**：大部分业务逻辑（SearchService/PlayerQueryService/TeamOverviewService 等）
5. **初始目录结构**：Maven 项目的 pom.xml 和包结构
6. **文档编写**：plan.md 的章节框架和格式
7. **测试用例框架**：KingSystemTest 的结构和测试方法
