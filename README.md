# 心屿手记

一个本地优先的 Tulpa 相处与记录 Android 应用。

> 把相处慢慢练成陪伴。  
> 不打卡、不排名、不催促。

当前版本：**0.4.0（正式签名 release）**

---

## 下载与安装

在 [GitHub Releases](https://github.com/shanhangchi501-jpg/xinyu-shouji/releases) 下载：

- `XinyuShouji-0.4.0-release.apk`（约 14 MB）
- 要求 Android 8.0（API 26）及以上
- 安装时需允许当前文件管理器或浏览器「安装未知应用」

版本变化见 [CHANGELOG.md](CHANGELOG.md)。

### 从 debug 版迁移

正式版使用了新的签名，不能直接覆盖旧 debug 版。请先在旧版「设置 → 数据」导出 JSON，再卸载旧版、安装正式版并导入。卸载会删除本地数据，务必先导出。

### 校验签名

- 包名：`com.innerisle.app`
- 签名证书 SHA-256：`89f6fa7ed2ccd1438d5ffff387a08c3c4a30c021bb11bc0b5caa4cfb898e2492`
- APK SHA-256：`85ec7eade46f157130df7a21634c7d870e0d0f7ef308266110fe50dac62acafa`

## 已实现

- **引导与安全确认**：18+、非医疗工具、痛苦时优先求助的提示。
- **Tulpa 多档案**：名字、记号、形象、性格、声音、喜好、备注。
- **六种相处练习**：叙述、对话、形象、幻境、投射、静心。
- **相处计时器**：5/10/15/20/30/45 分钟，可暂停、提前结束；结束后记录心情与一句话。
- **手记**：宿主 / Tulpa 作者切换、标题、正文、心情、标签、每日提示。
- **年轮**：相伴天数、累计时长、相处次数、手记篇数、最近七天图、练习分布、成长印记。
- **从零开始**：首页与「了解」双入口，17 章完整创造指南，覆盖创造前的决定与准备、人格设定、叙述、形象、幻境、成声、独立性、日常融合、投影、附体、交换、现实生活平衡与安全求助；支持阅读进度、上一章/下一章、章节自检清单与一键跳转练习。
- **了解**：13 篇入门/练习/进阶/安全文章 + 24 个概念词条，支持关键词搜索、分类筛选、概念展开阅读；每篇文章标注证据等级（社区经验 / 小型研究 / 临床视角 / 混合来源）、分节来源视角与参考资料；文章底部可一键跳到「相处」。
- **每日提醒**：WorkManager 调度、时间可改、通知脱敏。
- **隐私与安全**：4 位 PIN 应用锁、防截屏、快速离开、离开 30 秒自动锁定。
- **本地加密**：数据文件使用 Android Keystore 密钥做 AES-GCM 加密。
- **数据自主**：JSON 导出 / 导入、删除全部数据。
- **中英双语**：首次启动跟随系统语言，可在设置中切换中文 / English；界面、17 章指南、13 篇文章与 24 个概念均有完整英文版本。
- **深色模式**、Material 3 动态留白与衬线标题。

## 隐私设计

- **没有 INTERNET 权限**：应用无法联网。（WorkManager 会合并 `ACCESS_NETWORK_STATE`、`WAKE_LOCK`、`RECEIVE_BOOT_COMPLETED` 等系统权限用于后台提醒，但不包含网络访问权限。）
- 没有账号、没有云同步、没有分析统计、没有广告。
- 数据只保存在应用私有目录，使用 Android Keystore 密钥加密。
- PIN 只用于界面保护；真正的数据保护来自 Android 系统沙盒与 Keystore。
- 通知可脱敏，通知中不显示 Tulpa 名字与内容。
- 导出文件未加密，由用户主动选择保存位置，请妥善保管。

## 语言 / Language

- 首次启动会跟随系统语言：系统为英文时默认显示 English，否则显示中文。
- 设置 → Language 可在「中文 / English」之间切换，切换后应用会自动重启。
- 英文版包含完整界面、17 章指南、13 篇文章、24 个概念与安全说明，内容与中文版对应。
- 语言选择只保存在本机；不会上传任何语言或使用数据。

## 技术栈

| 项目 | 版本 / 说明 |
|---|---|
| 语言 | Kotlin 2.0.21 |
| UI | Jetpack Compose + Material 3 |
| 构建 | AGP 8.7.3 / Gradle 8.9 / JDK 17 |
| SDK | minSdk 26，compileSdk & targetSdk 35 |
| 本地存储 | 自定义 AES-GCM 加密文件（Android Keystore） |
| 序列化 | kotlinx.serialization JSON |
| 后台任务 | WorkManager |
| 最低系统 | Android 8.0（API 26） |

## 构建

前置条件：JDK 17、Android SDK Platform 35、Build-Tools 35.0.0。

```bash
# 调试构建
./gradlew assembleDebug

# 正式构建（需要项目根目录的 keystore.properties 与签名文件）
./gradlew assembleRelease
```

产物：

```
app/build/outputs/apk/debug/app-debug.apk
app/build/outputs/apk/release/app-release.apk
```

正式版会被复制为 `dist/XinyuShouji-0.4.0-release.apk`。签名文件与密码不提交到仓库，见下方「签名与升级」。

## 安装

```bash
adb install -r dist/XinyuShouji-0.4.0-release.apk
```

或把 APK 传到手机后手动安装（需允许安装未知来源应用）。

> GitHub 分发使用 release 签名。`app-debug.apk` 仅供本地调试，不要作为公开下载包。

## 签名与升级

- 签名文件：`keystore/innerisle-release.jks`
- 签名配置：`keystore.properties`（已在 `.gitignore` 中忽略）
- 两者都需要离线备份。**丢失签名文件后，已安装用户无法覆盖升级，只能卸载重装。**
- 后续版本必须使用同一签名、递增 `versionCode`，否则 Android 会拒绝覆盖安装。
- 不要将 keystore、密码或包含密码的文件提交到 GitHub。

## 许可证与隐私

- 代码：[MIT License](LICENSE)
- 应用内文字来源与免责声明：[NOTICE.md](NOTICE.md)
- 隐私说明：[PRIVACY.md](PRIVACY.md)

## 发布流程（维护者）

1. 修改 `app/build.gradle.kts` 中的 `versionCode` 与 `versionName`。
2. 更新 `CHANGELOG.md` 与 `release-notes/vX.Y.Z.md`。
3. 构建并复制：

   ```bash
   ./gradlew assembleRelease
   cp app/build/outputs/apk/release/app-release.apk dist/XinyuShouji-X.Y.Z-release.apk
   ```

4. 校验签名与 SHA-256：

   ```bash
   $ANDROID_HOME/build-tools/35.0.0/apksigner.bat verify --print-certs dist/XinyuShouji-X.Y.Z-release.apk
   sha256sum dist/XinyuShouji-X.Y.Z-release.apk
   ```

5. 提交并打 tag：

   ```bash
   git add .
   git commit -m "Release X.Y.Z"
   git tag vX.Y.Z
   git push origin main --tags
   ```

6. 在 GitHub 的 Releases 页面创建 `vX.Y.Z`，上传 `dist/XinyuShouji-X.Y.Z-release.apk`，粘贴 `release-notes/vX.Y.Z.md` 的内容。

## 项目结构

```
app/src/main/java/com/innerisle/app/
├── MainActivity.kt          # 入口、启动流程、屏幕锁定
├── data/
│   ├── Models.kt            # 数据模型、练习类型、提示、成就
│   ├── KnowledgeData.kt     # 概念词条与文章内容
│   ├── GuideData.kt         # 从零开始 · 17 章创造指南
│   ├── Prefs.kt             # 提醒/PIN 等非敏感设置
│   └── SecureStore.kt       # Keystore + AES-GCM 本地加密
├── ui/
│   ├── AppViewModel.kt      # 状态与业务逻辑
│   ├── MainApp.kt           # 底部导航与 Overlay 路由
│   ├── OnboardingScreen.kt
│   ├── HomeScreen.kt
│   ├── CompanionScreen.kt
│   ├── SessionScreen.kt
│   ├── JournalScreen.kt
│   ├── InsightsScreen.kt
│   ├── KnowledgeScreen.kt   # 概念与文章列表、搜索、分类
│   ├── GuideScreens.kt      # 从零开始列表、章节阅读与进度
│   ├── ArticleDetailScreen.kt
│   ├── SettingsScreen.kt
│   ├── MemberEditScreen.kt
│   ├── AboutScreen.kt
│   ├── LockScreen.kt
│   ├── Components.kt
│   └── theme/Theme.kt
└── work/Workers.kt          # 每日提醒与计时结束通知
```

## 已在模拟器验证

在 Android 15（API 35，x86_64）模拟器上完成以下流程，未出现崩溃（release 签名包已安装验证）：

- 首次启动与引导
- 创建 Tulpa、添加第二个档案、编辑档案
- 开始 / 暂停 / 提前结束相处，保存记录
- 写手记、编辑、列表展示
- 年轮统计与成长印记
- 「了解」标签页：文章阅读、证据等级与参考来源、分节「社区经验 / 研究视角」标注、概念展开、关键词搜索、分类筛选、从文章跳到相处、深色模式
- 「从零开始」：首页/「了解」入口、17 章指南阅读、上一章/下一章、标记已读、进度持久化、章节练习跳转
- 浅色模式：半透明卡片内部不再出现矩形色块
- 布局对齐：首页/年轮数据格子等高，相处练习格子等高，「成长印记」卡片等高，「最近的相处」空状态卡片完整对称
- 「成长印记」浅色/深色模式下模块底色与文字对比度加深
- 英文版：启动、引导、首页、相处、手记、年轮、了解、指南、文章、概念、设置、关于与深色模式完整走查
- 开启每日提醒、授予通知权限、WorkManager 任务调度
- 设置 PIN、锁定、解锁
- 导出 JSON、导入 JSON 并覆盖数据
- 冷启动后数据持久化
- 深色模式
- 删除全部数据并回到引导页

## 已知限制与下一步

- 首版**没有云备份 / 多设备同步**（有意如此，优先隐私）。
- 没有生物识别、桌面小组件、引导音频；这些适合作为下一版。
- 系统强杀进程后，未保存的编辑内容可能丢失；正常旋转屏幕已处理。
- 中英双语；其他语言暂未支持。
- 「了解」中的证据等级是对资料来源类型的粗略标注，不是系统综述或循证分级；引用链接仅作来源说明，App 本身没有联网权限，无法在应用内打开。
- 「从零开始」指南整理自社群实践资料（百度贴吧 tulpa 吧、多意识体系统 Wiki 等），属于经验性内容，不是临床指南，也不能保证每个人的进度与结果。
- 未接入任何 AI；Tulpa 的回应始终由用户自己书写。
- 不通过应用商店分发；GitHub Releases 侧载安装，后续版本需使用同一签名覆盖升级。
- 尚未覆盖大量真实机型；Release 构建目前只在 Android 15 模拟器验证。
- 心屿手记不是医疗器械，不提供诊断、治疗或疗效承诺。

## 安全边界

Tulpa 是一种主观心理实践。心屿手记只提供记录、计时和回顾工具，不能替代专业心理支持或治疗。

如果你正经历显著痛苦、功能受损，或听到无法控制的声音，请优先联系当地专业心理服务或紧急服务。

## 调研参考

- Tulpa.info FAQ / Community Guides
- tulpa.guide
- Tulpa之家（tulpa.cn）、PluralityCN、Multiple Personality System Wiki
- Sotala, *Sentient companions predicted and modeled into existence* (2015)
- Isler, *Tulpas and Mental Health: A Study of Non-Traumagenic Plural Experiences* (2017)
- Simply Plural / Octocon 关停公告与 Pluralpedia 条目
