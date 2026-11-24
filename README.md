![Title](.github/title.png)

<div align="center">

![Minecraft 1.20.1](https://img.shields.io/badge/Minecraft-1.20.1-62b47a?style=flat-square)
![Java 17](https://img.shields.io/badge/Java-17-ee9258?logo=coffeescript&logoColor=ffffff&labelColor=606060&style=flat-square)
![Environment: Client & Server](https://img.shields.io/badge/environment-Client%20%26%20Server-1976d2?style=flat-square)
![License: GPL-3.0](https://img.shields.io/badge/license-GPL--3.0-blue?style=flat-square)

</div>

# Combat Roll（1.20.1 中文 Fork）

这是 [ZsoltMolnarrr/CombatRoll](https://github.com/ZsoltMolnarrr/CombatRoll) 的独立维护分支，面向 **Minecraft 1.20.1（Fabric / Forge）**，在原版战斗翻滚的基础上加入空中翻滚属性、翻滚时取消当前动作、`shiledattack` 自动攻击兼容和 Extreme Evasion 兼容。

> **分支说明：** 本仓库只保留 `1.20.1` 一个分支，不维护、不合并其他 Minecraft 版本。需要 1.20.1 以外的版本，请前往[上游仓库](https://github.com/ZsoltMolnarrr/CombatRoll)。

## 本 Fork 与上游的区别

| 项目 | 上游 Combat Roll | 本 Fork |
| --- | --- | --- |
| 维护范围 | 同时维护多个 Minecraft 版本分支 | **只维护 `1.20.1`**，其他版本分支已删除 |
| 文档 | 英文 README | **完整中文 README**，并明确列出本 Fork 的差异 |
| 空中翻滚 | 服务端布尔开关 `allow_rolling_while_airborn` | 新增玩家属性 **`combatroll:air_roll`**（`0`～`1`）；配置项改为 **`allow_rolling_while_airborn_default`**，用于决定该属性的默认值 |
| 使用物品 / 格挡时翻滚 | 默认禁止翻滚 | 新增服务端配置 **`cancel_current_action_on_roll`**，默认 `true`：自动停止当前动作并继续翻滚；设为 `false` 可恢复上游行为 |
| `shiledattack` 自动攻击 | 按住攻击键时会被原版翻滚前置检查拦截 | 安装 `shiledattack` 后，在“按住攻击键自动攻击且未使用物品”的情况下允许翻滚 |
| Extreme Evasion | 无专门联动 | 安装 Extreme Evasion 后，在翻滚开始时通过反射通知其开启完美闪避窗口；未安装时完全跳过 |
| 中文属性名 | 无 `combatroll:air_roll` 的中文翻译 | 已补充“空中翻滚”本地化 |

### 兼容性改动的具体行为

- **`shiledattack` 兼容**仅在模组 ID `shiledattack` 已加载时生效。它只放宽“按住攻击键自动攻击”场景下的武器冷却与攻击键检查，不会绕过饥饿、骑乘、游泳或爬行等基础限制；使用物品或格挡时是否翻滚仍由 `cancel_current_action_on_roll` 决定。
- **Extreme Evasion 兼容**不使用编译期依赖，也不要求双方版本一一对应；通过运行时反射调用 `cc.sighs.extremeevasion.ExtremeEvasionEvents.startExternalRollWindow(Player)`。目标方法不存在或调用失败时只跳过联动，不影响翻滚本身。
- 本 Fork 的模组 ID 仍是 `combatroll`。**不要在同一整合包中同时安装上游版本和本 Fork**，请二选一。
- 完整实现可查看提交 [`67c8d8e`](https://github.com/3128194922/CombatRoll/commit/67c8d8e45b92788061a70820a10e72beacbe1f88)。

## 主要功能

- 按 `R`（默认键位）进行战术翻滚，可通过客户端设置修改键位。
- HUD 显示剩余翻滚次数与冷却进度。
- 翻滚会增加消耗度（exhaustion），具体数值由服务端配置。
- 支持 Fabric 与 Forge；客户端和服务端都需要安装。
- 保留上游的翻滚距离、冷却恢复、额外翻滚次数和附魔系统。
- 与 [Better Combat](https://github.com/ZsoltMolnarrr/BetterCombat) 保持兼容。

### 玩家属性

| 属性 | 作用 | 默认值 |
| --- | --- | --- |
| `combatroll:distance` | 翻滚距离 | 3 格 |
| `combatroll:recharge` | 翻滚冷却恢复速度 | 4 秒冷却 |
| `combatroll:count` | 额外翻滚次数 | 1 次 |
| `combatroll:air_roll` | 是否允许空中翻滚；`0` 为禁止，`1` 为允许 | 由 `allow_rolling_while_airborn_default` 决定，默认 `0` |

`combatroll:air_roll` 示例（Minecraft 1.20.1 NBT 语法）：

```mcfunction
/give @s golden_boots{AttributeModifiers:[{AttributeName:"combatroll:air_roll",Name:"air_roll_test",Amount:1.0,Operation:0,Slot:"feet",UUID:[I; 66453, 79497593, -201178, -16957605]}]} 1
```

该属性被限制在 `0`～`1`。值为 `1` 时允许空中翻滚；低于 `1` 时，玩家离地后不能翻滚。

## 安装

1. 安装 **Minecraft 1.20.1** 和对应加载器：
   - Fabric Loader `0.14.21` 或兼容版本
   - Forge `47.0.4` 或兼容版本
2. 安装 **Java 17**。
3. 将对应加载器的 Combat Roll JAR 放入 `mods` 目录。
4. 客户端和服务端都需要安装；仅客户端安装无法在多人服务器使用。

`shiledattack` 与 Extreme Evasion 都是**可选**联动模组，不安装时不影响本模组的基础功能。

## 配置

### 客户端

- **Fabric：** 通过 [Mod Menu](https://github.com/TerraformersMC/ModMenu) 打开 Combat Roll 设置。
- **Forge：** 主菜单 → Mods → Combat Roll → Config。

### 服务端

首次启动后，服务器配置会生成在 `config` 目录。与上游不同的配置项如下：

| 配置项 | 默认值 | 说明 |
| --- | --- | --- |
| `allow_rolling_while_airborn_default` | `false` | 决定 `combatroll:air_roll` 的默认值。设为 `true` 时默认允许所有玩家空中翻滚，但仍可被其他属性来源覆盖。 |
| `cancel_current_action_on_roll` | `true` | 翻滚时自动停止使用物品、格挡等当前动作并继续翻滚。设为 `false` 时，正在使用物品或格挡将阻止翻滚，行为与上游一致。 |

> **从上游升级时注意：** 上游的 `allow_rolling_while_airborn` 已被移除，请改用 `allow_rolling_while_airborn_default`，并通过 `combatroll:air_roll` 属性控制单个玩家。旧配置项不会继续生效。

## 从源码构建

需要 **JDK 17**。在仓库根目录执行：

```bash
./gradlew build
```

Windows：

```powershell
.\gradlew.bat build
```

构建产物位于各加载器子项目的 `build/libs` 目录：

- Fabric：`fabric/build/libs`
- Forge：`forge/build/libs`

## 分支与贡献

- 唯一维护分支：`1.20.1`
- 不接受面向其他 Minecraft 版本的分支或 PR
- 提交 PR 时请以 `1.20.1` 为目标分支，并说明是否改变了现有行为或配置格式
- 本 Fork 不是上游官方发行版；相关问题请先确认是否由本 Fork 的改动引起

## 致谢与许可

- 原项目：[ZsoltMolnarrr/CombatRoll](https://github.com/ZsoltMolnarrr/CombatRoll)
- 本 Fork 在原项目基础上修改，遵循 **GNU General Public License v3.0**，详见 [LICENCE](LICENCE)
- 感谢上游作者及所有翻译、兼容性贡献者
