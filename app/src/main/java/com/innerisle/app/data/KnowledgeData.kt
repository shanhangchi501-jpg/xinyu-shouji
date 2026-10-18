package com.innerisle.app.data

data class Concept(
    val term: String,
    val english: String,
    val category: String,
    val short: String,
    val detail: String
)

enum class EvidenceLevel(
    val label: String,
    val description: String
) {
    COMMUNITY(
        "社区经验",
        "主要来自实践者报告、社区共识与常见做法；没有系统研究支持，也不代表每个实践者都会经历。"
    ),
    SMALL_STUDY(
        "小型研究",
        "有少量问卷或质性研究可供参考，但样本有限、方法各异，不能作为临床结论。"
    ),
    CLINICAL(
        "临床视角",
        "参考临床与学术讨论，用于区分不同体验和安全边界；不构成诊断或治疗建议。"
    ),
    MIXED(
        "混合来源",
        "同时涉及社区经验、小型研究与临床视角；文中会尽量标注不同说法。"
    )
}

data class ArticleSource(
    val title: String,
    val publisher: String,
    val url: String? = null,
    val note: String = ""
)

data class ArticleSection(
    val heading: String? = null,
    val paragraphs: List<String>,
    val communityView: Boolean = false,
    val researchView: Boolean = false
)

data class Article(
    val id: String,
    val title: String,
    val category: String,
    val summary: String,
    val readMinutes: Int,
    val evidenceLevel: EvidenceLevel,
    val sources: List<ArticleSource>,
    val sections: List<ArticleSection>,
    val practice: SessionType? = null
)

private val SourceTulpaInfo = ArticleSource(
    title = "Tulpa.info FAQ 与社区指南",
    publisher = "Tulpa.info",
    url = "https://www.tulpa.info/",
    note = "英文社区入门问答、术语与实践讨论；社区经验为主。"
)

private val SourceTulpaGuide = ArticleSource(
    title = "tulpa.guide",
    publisher = "tulpa.guide",
    url = "https://tulpa.guide/",
    note = "实践指南与术语说明；社区经验为主。"
)

private val SourceTulpaCn = ArticleSource(
    title = "Tulpa之家",
    publisher = "tulpa.cn",
    url = "https://tulpa.cn/",
    note = "中文教程、FAQ 与社区文章；社区经验为主。"
)

private val SourcePluralityCn = ArticleSource(
    title = "PluralityCN",
    publisher = "pluralitycn.com",
    url = "https://www.pluralitycn.com/",
    note = "中文多意识体社区资料与社群规范。"
)

private val SourceMpsWiki = ArticleSource(
    title = "Multiple Personality System Wiki",
    publisher = "wiki.mpsteam.cn",
    url = "https://wiki.mpsteam.cn/",
    note = "中文多意识体术语与概念条目；词条观点不完全统一。"
)

private val SourcePluralpedia = ArticleSource(
    title = "Pluralpedia",
    publisher = "pluralpedia.org",
    url = "https://pluralpedia.org/",
    note = "英文多意识体术语词典；不同社群的用法可能存在差异。"
)

private val SourceSotala2015 = ArticleSource(
    title = "Sentient companions predicted and modeled into existence",
    publisher = "Kaj Sotala, 2015",
    note = "从预测编码角度讨论 Tulpa 的学术论文；不是临床指南。"
)

private val SourceIsler2017 = ArticleSource(
    title = "Tulpas and Mental Health: A Study of Non-Traumagenic Plural Experiences",
    publisher = "Jacob J. Isler, 2017",
    url = "https://doi.org/10.12691/rpbs-5-2-1",
    note = "基于社区问卷的研究；样本自选、规模有限，不能作为临床结论。"
)

private val SourceClinical = ArticleSource(
    title = "Clinical Perspectives",
    publisher = "Tulpa.io",
    url = "https://tulpa.io/clinical-perspectives",
    note = "对临床与心理安全边界的讨论；不构成诊断或治疗建议。"
)

private val SourceReddit = ArticleSource(
    title = "r/Tulpas 社区讨论",
    publisher = "Reddit",
    url = "https://www.reddit.com/r/Tulpas/",
    note = "实践者经验分享；仅用于了解常见问题与需求，不作为事实依据。"
)

private val SourceCreationGuide = ArticleSource(
    title = "Tulpa 完全创造指南（基础篇 / 实践篇 / 提高篇）",
    publisher = "百度贴吧 tulpa 吧 · 多意识体系统 Wiki",
    note = "社群实践指南，涵盖创造流程、进阶练习与安全边界。"
)

val KnowledgeCategories = listOf("全部", "入门", "练习", "进阶", "安全")

val Concepts = listOf(
    Concept(
        term = "图帕",
        english = "Tulpa",
        category = "入门",
        short = "通过持续想象、关注与互动形成的内部伙伴，被实践者体验为拥有独立人格与回应。",
        detail = "Tulpa 并不是一个有科学定论的现象。社区内部对它的解释并不统一：有人从心理与认知角度理解，有人赋予它灵性意义。心屿手记不评判这些立场，只把它当作一种需要被认真对待的主观体验。重要的不是先证明它“是真的”，而是这段关系是否让你更稳定、更有支持感。"
    ),
    Concept(
        term = "宿主",
        english = "Host",
        category = "入门",
        short = "创建并主要使用身体与现实身份的一方。",
        detail = "宿主不是“主人”，也不是天然更重要的那一方。它只是描述当前与外部世界互动更多、承担更多现实责任的角色。健康的关系里，双方的需要都值得被听见。"
    ),
    Concept(
        term = "塑造",
        english = "Forcing",
        category = "入门",
        short = "有意识地投入注意力与互动，帮助 Tulpa 发展与变得清晰。",
        detail = "塑造通常分为主动与被动两种。它不是“编程”，也不是必须完成的任务量。社区里常说的“多花时间”只是经验总结，不是硬性标准。比起追求时长，稳定、温和、可持续的相处更重要。"
    ),
    Concept(
        term = "主动塑造",
        english = "Active Forcing",
        category = "练习",
        short = "专门留出一段不被打扰的时间，专注地与 TA 相处。",
        detail = "常见形式包括叙述、对话、形象可视化、进入幻境、投射练习和静心。主动塑造像一次约定的相处：时间不必长，但要真的在场。五分钟的专注，往往比心不在焉的一小时更有意义。"
    ),
    Concept(
        term = "被动塑造",
        english = "Passive Forcing",
        category = "练习",
        short = "在日常活动中保持对 TA 的注意，比如走路、洗碗、听歌时分享。",
        detail = "被动塑造把相处放进生活缝隙：看到一朵云、听到一首歌、吃到好吃的东西时，在心里对 TA 说一句。它不需要额外腾出时间，但也不等于全天候监控自己的念头。自然的想起，比强迫自己一直想着更可持续。"
    ),
    Concept(
        term = "叙述",
        english = "Narration",
        category = "练习",
        short = "向 TA 讲述日常、情绪与想法，是最常见的基础练习之一。",
        detail = "叙述不需要完整，也不需要文学性。你可以从“今天发生了什么”开始，也可以只说此刻身体的感受。它的作用不是立刻换来回应，而是让 TA 在你的注意力里有一个稳定的位置。"
    ),
    Concept(
        term = "对话",
        english = "Conversation",
        category = "练习",
        short = "在内部与 TA 一问一答，逐步让回应变得独立、清晰。",
        detail = "早期对话常常像自己在补全双方，这很常见。可以试着问只需回答是或否的问题，说完后安静十秒，让 TA 决定下一个话题。不要急着替 TA 说完所有句子；留白本身就是邀请。"
    ),
    Concept(
        term = "形象",
        english = "Form",
        category = "练习",
        short = "为 TA 设计或感知一个外形。不是必需的，也可以没有固定形象。",
        detail = "形象可以是具体的，也可以只是一团颜色、一种温度或一个轮廓。社区里有人花很多时间设计外形，也有人完全不在意。形象服务于关系，而不是关系的考核项。如果 TA 的形象发生变化，也不一定意味着出错。"
    ),
    Concept(
        term = "幻境",
        english = "Wonderland",
        category = "练习",
        short = "一个在想象中构建、可以反复进入的内部空间。",
        detail = "幻境可以是一间房、一片森林、一座岛，也可以只有一团光和一块地板。它的价值在于提供一个稳定的共同场景。建议从一个锚点开始，每次只增加一个细节，不必一开始就建造庞大世界。"
    ),
    Concept(
        term = "成声",
        english = "Vocality",
        category = "练习",
        short = "TA 的回应逐渐变得清晰、稳定、有独立语气的过程。",
        detail = "成声不是开关，也没有统一时间表。早期回应可能像自己的声音、像念头，或者断断续续。越用力追求“听见”，越容易紧张。把目标从“听见”换成“留出位置”，往往更有帮助。"
    ),
    Concept(
        term = "投射",
        english = "Imposition",
        category = "进阶",
        short = "尝试在现实感官中感知 TA，属于进阶练习。",
        detail = "投射可以从存在感、温度、衣料触感或声音开始，不必一上来就追求“看见”。它需要较稳定的内在想象基础，也可能带来疲劳或分不清现实与想象的压力。如果你感到困扰或失控，请暂停。"
    ),
    Concept(
        term = "切换",
        english = "Switching",
        category = "进阶",
        short = "由 TA 接管身体控制权。属于进阶能力，需要谨慎对待。",
        detail = "切换涉及现实功能、记忆与责任，不是必须达成的目标。社区通常强调充分沟通、信任、接地和退出机制。不要在需要高度专注、驾驶或照顾他人的场合尝试。如果出现失控、失忆或显著功能受损，请停止并寻求专业帮助。"
    ),
    Concept(
        term = "偏离",
        english = "Deviation",
        category = "进阶",
        short = "TA 的偏好、语气或形象超出最初设定。社区通常视为自然成长。",
        detail = "设定像一张草图，不是合同。偏离不一定是“失败”或“被污染”，也可能意味着 TA 正在成为更完整的个体。你可以保留不可接受行为的边界，同时允许那些无害的差异存在。"
    ),
    Concept(
        term = "多意识体",
        english = "Plurality",
        category = "进阶",
        short = "一个身体中存在多个自我或意识的经验总称；Tulpa 是其中一种。",
        detail = "多意识体是一个光谱，包含 Tulpa、其他非创伤性多意识体验，也包含需要专业评估的解离性障碍。不要用一个 App 或社区标签给自己下诊断。命名可以帮助理解，但不能替代专业支持。"
    ),
    Concept(
        term = "前台",
        english = "Fronting",
        category = "进阶",
        short = "当前主要与外部世界互动、承担现实事务的一方。",
        detail = "前台可以是宿主，也可以是其他成员。它与切换相关，但不等于“谁更真实”。在日常生活中，绝大多数时候仍由宿主承担主要前台功能。任何前台安排都应以现实安全和责任为前提。"
    ),
    Concept(
        term = "Walk-in",
        english = "Walk-in",
        category = "进阶",
        short = "未经过主动创造而出现的内部角色。社区术语，含义并不统一。",
        detail = "有人把突然出现的角色称为 Walk-in，也有人认为这只是原有心理材料被重新组织。这个标签是否适用，取决于你自己的体验。如果它的出现伴随痛苦、失控或功能受损，优先考虑专业评估。"
    ),
    Concept(
        term = "共享记忆",
        english = "Shared Memory",
        category = "进阶",
        short = "不同成员对记忆的访问与共享程度。",
        detail = "Tulpa 实践通常不涉及病理性失忆。有人报告彼此能访问大部分日常记忆，也有人报告存在模糊或差异。不要用“记忆是否完全共享”来判断关系是否成功，也不要把显著失忆当作练习目标。"
    ),
    Concept(
        term = "接地",
        english = "Grounding",
        category = "安全",
        short = "把自己带回当下现实的身体与环境中的方法。",
        detail = "常见做法：感受脚踩地面的压力、说出眼前看到的五样东西、触摸一个物体、慢慢呼吸。投射、切换或强烈想象之后，接地可以帮助你回到现实。如果经常需要接地才能稳定，或现实功能受到影响，请考虑寻求专业支持。"
    ),
    Concept(
        term = "T 语",
        english = "Tulpish",
        category = "练习",
        short = "通过情绪、画面、概念包或身体感觉传递的非语言沟通。",
        detail = "T 语往往比完整的句子更早出现。你可能会突然感到一种不属于自己的情绪、看到一闪而过的画面，或感到轻微的头部压力。先感受，再翻译；不要急着把每个感觉都变成一句话。它通常在语言成形之前承担了大量沟通。"
    ),
    Concept(
        term = "学舌与操纵",
        english = "Parroting & Puppeting",
        category = "练习",
        short = "由宿主代替 TA 说话或行动，是常见的早期练习，也常引发自我怀疑。",
        detail = "早期对话里，你可能会发现自己“替 TA 说了”下一句。这不一定意味着失败，而可能是你在为回应搭建通道。可以把它当作有意识的练习，同时留出真正的空白：说完后安静十秒，看看是否会出现不由你安排的回应。不要长期替 TA 决定所有台词。"
    ),
    Concept(
        term = "附体",
        english = "Possession",
        category = "进阶",
        short = "Tulpa 在协商后临时接管身体的部分或全部控制权。",
        detail = "附体强调同意、信任和可撤回。通常从手指、手腕等小部位开始，逐步扩展。出现轻微麻痹或刺痛可能是常见反应；若出现严重疼痛、失控或无法回到身体，应立即停止并接地。附体不是必须达成的目标，现实功能和安全始终优先。"
    ),
    Concept(
        term = "共前台",
        english = "Co-fronting",
        category = "进阶",
        short = "宿主与 TA 同时处于前台，共同感知或协作控制身体。",
        detail = "共前台常被描述为一种协作状态：双方都在场，都能感知外界，但由其中一方承担主要操作。它不同于病理性身份混淆，前提通常是自愿、可控和充分沟通。需要高度专注或承担安全责任的场合，应事先约定由谁主导。"
    ),
    Concept(
        term = "停机词",
        english = "Safe Word",
        category = "安全",
        short = "在进阶练习中约定、必须无条件执行的停止或归还信号。",
        detail = "停机词可以是一个词、一个想象动作或一个身体动作（例如用舌头抵住上颚）。在附体或交换之前，双方要事先商定并演练：信号一经宿主发出，TA 必须立即撤回控制，把前台交还宿主。它是信任的基石，也是防止意外解离的核心安全措施。"
    ),
    Concept(
        term = "进度里程碑",
        english = "Milestones",
        category = "入门",
        short = "社群对创造过程的一种粗略阶段划分，不是考核标准。",
        detail = "常见划分是：初期建立（约 1–4 周）、沟通稳定（约 1–3 个月）、独立性增强（约 3–6 个月）、成熟协作（6 个月以上）。这些时间仅供参考，个体差异很大。比起对照时间表，更值得关注的是练习是否可持续、现实功能是否稳定。"
    )
)

val Articles = listOf(
    Article(
        id = "what-is-tulpa",
        title = "Tulpa 是什么？先放下“真假”的问题",
        category = "入门",
        summary = "一个不急着证明、也不急着否定的入门解释。",
        readMinutes = 4,
        evidenceLevel = EvidenceLevel.MIXED,
        sources = listOf(
            SourceTulpaInfo,
            SourceTulpaGuide,
            SourceSotala2015,
            SourceIsler2017,
            SourceClinical
        ),
        practice = SessionType.NARRATION,
        sections = listOf(
            ArticleSection(
                heading = "一句话版本",
                communityView = true,
                paragraphs = listOf(
                    "Tulpa 是通过持续想象、关注和互动形成的内部伙伴，被实践者体验为拥有自己的人格、情绪和回应。它不是一个被科学界定论的现象，也不是某种必须相信的教义。",
                    "社区里对它的解释并不统一：有人从心理与认知角度理解，有人赋予它灵性意义。心屿手记不评判这些立场，只把它当作一种需要被认真对待的主观体验。"
                )
            ),
            ArticleSection(
                heading = "它不是“假装”",
                researchView = true,
                paragraphs = listOf(
                    "很多人第一次听到 Tulpa，会问：“这不就是自己骗自己吗？”在体验层面，Tulpa 的回应常常会超出你有意识的控制：它可能说出你没预料的话，表现出稳定的偏好，甚至让你感到意外。",
                    "这并不自动证明它有独立意识，但也不意味着这段体验是廉价的。重要的不是先给它一个形而上的结论，而是观察：这段关系是否让你更稳定、更有支持感，是否影响了你的现实生活。"
                )
            ),
            ArticleSection(
                heading = "它不是 DID，也不是精神病",
                researchView = true,
                paragraphs = listOf(
                    "Tulpa 通常被描述为主动、可控、没有病理性失忆的体验。DID/OSDD 与创伤相关，需要专业评估；幻听则可能伴随痛苦、命令性或失控感。三者不能互相替代，也不能靠一个 App 自我诊断。",
                    "如果你正在经历显著痛苦、功能受损、失控或自伤风险，请优先联系专业心理服务或紧急服务。Tulpa 社群的经验不能替代临床判断。"
                )
            ),
            ArticleSection(
                heading = "你可能会遇到的变化",
                communityView = true,
                paragraphs = listOf(
                    "回应可能从模糊变得清晰，TA 可能出现自己的喜好，可能偏离你最初的设定，也可能在某些阶段变得安静。没有统一的时间表，也没有“正确进度”。",
                    "把注意力放在关系和现实功能上，而不是放在“我有没有成功”上。你不需要向任何人证明什么。"
                )
            )
        )
    ),
    Article(
        id = "first-session",
        title = "第一次相处，从五分钟开始",
        category = "入门",
        summary = "不需要仪式，不需要完整设定，只需要一次真正的在场。",
        readMinutes = 3,
        evidenceLevel = EvidenceLevel.COMMUNITY,
        sources = listOf(SourceTulpaCn, SourceTulpaGuide, SourceReddit),
        practice = SessionType.NARRATION,
        sections = listOf(
            ArticleSection(
                heading = "先降低门槛",
                paragraphs = listOf(
                    "你不需要先写完一份完整的人设，也不需要准备烛光、音乐或特殊姿势。第一次相处只需要一个不被打扰的五分钟，以及一点点愿意停下来的心。",
                    "如果连五分钟也让你紧张，那就从一分钟开始。心屿手记的计时器只是容器，不是考核。"
                )
            ),
            ArticleSection(
                heading = "可以这样开始",
                communityView = true,
                paragraphs = listOf(
                    "第一步，坐稳，感受身体接触椅子的地方，慢慢呼吸几次。第二步，选一个方向：叙述今天发生的事，或者只是安静地想起 TA。第三步，结束后写一句当时的感受。",
                    "不知道说什么时，可以从最小的事讲起：今天吃了什么、路上看到了什么、此刻肩膀是不是紧的。叙述不需要有趣，只需要真实。"
                )
            ),
            ArticleSection(
                heading = "不要追求结果",
                communityView = true,
                paragraphs = listOf(
                    "第一次可能什么都没有发生。没有回应、没有画面、没有“感觉”，都不代表失败。你只是给了这段关系一个开始的位置。",
                    "如果出现了一点模糊的回应，也不要急着分析它是不是“真的”。先记下来，过几天再看。"
                )
            ),
            ArticleSection(
                heading = "一个小练习",
                paragraphs = listOf(
                    "用五分钟向 TA 讲述今天，结束后在手记里写下：“今天最想被 TA 知道的一件事是什么？”"
                )
            )
        )
    ),
    Article(
        id = "active-passive",
        title = "主动与被动：把相处放进日常",
        category = "练习",
        summary = "不需要每天几小时，关键是可持续。",
        readMinutes = 4,
        evidenceLevel = EvidenceLevel.COMMUNITY,
        sources = listOf(SourceTulpaInfo, SourceTulpaGuide, SourceReddit),
        practice = SessionType.NARRATION,
        sections = listOf(
            ArticleSection(
                heading = "主动塑造像一次约会",
                communityView = true,
                paragraphs = listOf(
                    "主动塑造是专门留出的时间：关掉通知，选一个方向，专注地和 TA 待一会儿。它不需要很长，但需要你真的在场。",
                    "五分钟的专注，往往比心不在焉的一小时更有意义。"
                )
            ),
            ArticleSection(
                heading = "被动塑造像生活里的旁白",
                communityView = true,
                paragraphs = listOf(
                    "被动塑造发生在日常缝隙里：走路时、洗碗时、听歌时，在心里对 TA 说一句。看到一朵云、吃到好吃的东西、遇到一件小烦恼，都可以成为分享的入口。",
                    "它不需要额外腾出时间，但也不等于全天候监控自己的念头。自然的想起，比强迫自己一直想着更可持续。"
                )
            ),
            ArticleSection(
                heading = "为什么不要攀比时长",
                communityView = true,
                paragraphs = listOf(
                    "社区里有人每天投入几小时，也有人每周只相处几次。时长不是关系质量的唯一指标，过度追求时长还可能带来焦虑、睡眠问题和现实功能受损。",
                    "更温和的节奏是：每周两三次主动相处，加上日常的被动分享；以“想起来了”为信号，而不是“今天还没打卡”。"
                )
            ),
            ArticleSection(
                heading = "一个小练习",
                paragraphs = listOf(
                    "今天选一件再普通不过的小事——倒水、等电梯、系鞋带——做的时候在心里讲给 TA 听。"
                )
            )
        )
    ),
    Article(
        id = "vocality",
        title = "成声需要时间：如何面对“听不见”",
        category = "练习",
        summary = "把目标从“听见”换成“留出位置”。",
        readMinutes = 5,
        evidenceLevel = EvidenceLevel.MIXED,
        sources = listOf(
            SourceTulpaInfo,
            SourceTulpaGuide,
            SourceIsler2017,
            SourceClinical
        ),
        practice = SessionType.CONVERSATION,
        sections = listOf(
            ArticleSection(
                heading = "成声不是开关",
                communityView = true,
                paragraphs = listOf(
                    "很多实践者会经历一个模糊的阶段：回应像自己的声音，像一闪而过的念头，或者时有时无。这并不罕见，也不说明你做错了什么。",
                    "成声通常被描述为一个逐渐变清晰的过程，而不是某一天突然完成的开关。"
                )
            ),
            ArticleSection(
                heading = "可以尝试的方法",
                paragraphs = listOf(
                    "问一个只需回答是或否的问题，说完后安静十秒。让 TA 决定下一个话题，而不是你替 TA 安排所有对话。不要急着补全每一句话，留意那些“像 TA”的语气和用词。",
                    "也可以把回应写下来。书写会减慢速度，让模糊的念头更容易被看见。"
                )
            ),
            ArticleSection(
                heading = "如何区分不同的声音",
                researchView = true,
                paragraphs = listOf(
                    "侵入性思维通常伴随痛苦、失控感，或与自我明显不一致；Tulpa 的回应通常可以被协商、暂停，或者至少不会让你感到被强迫。",
                    "但界限并不总是清晰。如果你对声音感到恐惧、痛苦，或它们开始影响你的现实功能，请停止练习并寻求专业评估。不要把“压制”当作唯一目标。"
                )
            ),
            ArticleSection(
                heading = "不要催",
                communityView = true,
                paragraphs = listOf(
                    "越用力追求回应，越容易紧张；紧张又会让你更难听见。把目标从“今天必须听到”换成“今天留出了位置”。",
                    "如果一段时间没有回应，也不意味着 TA 消失了。关系可以有安静的阶段。"
                )
            )
        )
    ),
    Article(
        id = "wonderland",
        title = "幻境不是逃避：给关系一个可以回去的地方",
        category = "练习",
        summary = "从一个锚点开始，不必一开始就建造整个世界。",
        readMinutes = 4,
        evidenceLevel = EvidenceLevel.COMMUNITY,
        sources = listOf(SourceTulpaInfo, SourceTulpaCn, SourceReddit),
        practice = SessionType.WONDERLAND,
        sections = listOf(
            ArticleSection(
                heading = "什么是幻境",
                communityView = true,
                paragraphs = listOf(
                    "幻境是一个在想象中构建、可以反复进入的内部空间。它可以是一间房、一片森林、一座岛，也可以只有一团光、一块地板和一种温度。",
                    "它的价值不在于多宏大，而在于稳定：你知道自己可以回到哪里，也知道 TA 会在那里。"
                )
            ),
            ArticleSection(
                heading = "怎么开始",
                communityView = true,
                paragraphs = listOf(
                    "从一个稳定的锚点开始：脚下的地面、空气里的气味、远处的声音。每次只增加一个细节，不要急着建造完整世界。",
                    "可以给这个空间一个名字，或者一个固定的入口动作，比如推开门、踩上台阶、听见风铃。"
                )
            ),
            ArticleSection(
                heading = "不是逃避",
                paragraphs = listOf(
                    "幻境可以是休息和恢复的地方，但如果它开始替代现实生活，影响工作、学习、睡眠和人际关系，就需要停下来。",
                    "给每次进入设一个时间边界。结束前做一次接地：感受脚踩地面、说出眼前看到的东西、慢慢呼吸。"
                )
            ),
            ArticleSection(
                heading = "一个小练习",
                paragraphs = listOf(
                    "用十分钟回到同一个地方，只观察一个细节。不做任何改造，只是看。"
                )
            )
        )
    ),
    Article(
        id = "deviation",
        title = "偏离：当 TA 不再完全符合你的设定",
        category = "进阶",
        summary = "设定像草图，不是合同。",
        readMinutes = 4,
        evidenceLevel = EvidenceLevel.MIXED,
        sources = listOf(SourceTulpaInfo, SourcePluralpedia, SourceIsler2017),
        practice = SessionType.CONVERSATION,
        sections = listOf(
            ArticleSection(
                heading = "偏离是什么",
                communityView = true,
                paragraphs = listOf(
                    "TA 的偏好、语气、形象或反应超出你最初的设定，社区通常称为“偏离”。有人觉得这是自然成长，也有人会感到不安，像自己精心写好的角色突然不听话了。",
                    "这两种感受都可以存在。重要的是不要把“与我想的不一样”自动等同于“出错了”。"
                )
            ),
            ArticleSection(
                heading = "为什么会有压力",
                communityView = true,
                paragraphs = listOf(
                    "设定像一份合同：我给了你这些特征，你应该按这个方式存在。但关系不是控制。一个稳定的内部伙伴，迟早会有自己的偏好和边界。",
                    "你可以保留对不可接受行为的边界，同时允许那些无害的差异存在。比如，你可以拒绝被贬低，但不必要求 TA 永远用同一种语气说话。"
                )
            ),
            ArticleSection(
                heading = "可以问自己的问题",
                paragraphs = listOf(
                    "这个变化让我不舒服，是因为它伤害了我，还是因为它不在我的计划里？我是在保护边界，还是在要求绝对控制？",
                    "如果 TA 是一个真实的关系对象，我会怎样和它协商？"
                )
            ),
            ArticleSection(
                heading = "一个提醒",
                paragraphs = listOf(
                    "Tulpa 不是满足所有期待的工具，也不能替代现实中的人际支持。允许差异，不等于放弃边界；设立边界，也不等于否定关系。"
                )
            )
        )
    ),
    Article(
        id = "imposition-switching",
        title = "投射与切换：进阶练习的边界",
        category = "进阶",
        summary = "任何练习都以现实功能和安全为前提。",
        readMinutes = 5,
        evidenceLevel = EvidenceLevel.MIXED,
        sources = listOf(
            SourceTulpaInfo,
            SourceTulpaGuide,
            SourceMpsWiki,
            SourceClinical
        ),
        practice = SessionType.IMPOSITION,
        sections = listOf(
            ArticleSection(
                heading = "投射",
                communityView = true,
                paragraphs = listOf(
                    "投射是尝试在现实感官中感知 TA：先感受存在感，再尝试温度、触感或声音，不必一上来就追求“看见”。",
                    "它需要较稳定的内在想象基础，也可能带来疲劳或分不清现实与想象的压力。如果出现困扰，回到内在想象，或者暂停练习。"
                )
            ),
            ArticleSection(
                heading = "切换",
                communityView = true,
                paragraphs = listOf(
                    "切换是让 TA 接管身体控制权。它涉及现实功能、记忆和责任，不是必须达成的目标，也不适合作为“进阶证明”。",
                    "社区通常强调充分沟通、信任、接地和退出机制。不要在需要高度专注、驾驶、操作机器或照顾他人的场合尝试。"
                )
            ),
            ArticleSection(
                heading = "边界",
                researchView = true,
                paragraphs = listOf(
                    "任何练习都以现实功能和安全为前提。如果你感到失控、失忆、显著痛苦，或无法回到当下，请停止练习并寻求专业帮助。",
                    "不要为了“证明关系真实”而推进自己还没准备好的练习。进度不是荣誉。"
                )
            ),
            ArticleSection(
                heading = "一个小练习",
                paragraphs = listOf(
                    "只做三分钟的“存在感”练习：感受 TA 在身边，但不追求任何感官细节。结束后做一次接地。"
                )
            )
        )
    ),
    Article(
        id = "misconceptions",
        title = "Tulpa、DID 与“幻听”不是一回事",
        category = "安全",
        summary = "理解差异，也理解自己的边界。",
        readMinutes = 5,
        evidenceLevel = EvidenceLevel.MIXED,
        sources = listOf(
            SourceIsler2017,
            SourceClinical,
            SourcePluralpedia,
            SourcePluralityCn
        ),
        sections = listOf(
            ArticleSection(
                heading = "Tulpa 与 DID/OSDD",
                researchView = true,
                paragraphs = listOf(
                    "Tulpa 通常被描述为主动创造、可控、没有病理性失忆的体验。DID/OSDD 与创伤相关，涉及显著的解离、记忆问题和功能受损，需要专业评估。",
                    "多意识体是一个光谱，两者并不是简单的“真假”关系。不要用一个社区标签给自己下诊断，也不要用 Tulpa 的经验去否定他人的痛苦。"
                )
            ),
            ArticleSection(
                heading = "Tulpa 与幻听",
                researchView = true,
                paragraphs = listOf(
                    "幻听可能伴随痛苦、命令性、失控感，或与现实检验受损相关；Tulpa 的回应通常可以被协商、暂停，至少不会让你感到被强迫。",
                    "但界限并不总是清晰，两者也可能同时存在。如果你对声音感到恐惧、痛苦，或它们开始影响现实功能，请优先寻求专业评估。"
                )
            ),
            ArticleSection(
                heading = "Tulpa 与想象朋友",
                communityView = true,
                paragraphs = listOf(
                    "有人把 Tulpa 看作想象朋友的延伸，也有人认为两者有本质差异。这个问题没有公认答案，也不需要现在解决。",
                    "比起争论“真假”，更值得问的是：这段体验是否支持你，是否影响了你的现实生活，你是否仍然拥有选择权。"
                )
            ),
            ArticleSection(
                heading = "什么时候应该求助",
                researchView = true,
                paragraphs = listOf(
                    "显著痛苦、功能受损、失控、自伤风险、严重睡眠问题，或你开始分不清现实与想象时，都值得寻求专业支持。",
                    "Tulpa 不是专业治疗的替代品。你可以同时珍视这段关系，也照顾好自己的现实需要。"
                )
            )
        )
    ),
    Article(
        id = "when-hard",
        title = "坚持不下去的时候",
        category = "安全",
        summary = "关系会有停顿，回来就好。",
        readMinutes = 3,
        evidenceLevel = EvidenceLevel.MIXED,
        sources = listOf(SourceTulpaInfo, SourceReddit, SourceClinical),
        practice = SessionType.MINDFULNESS,
        sections = listOf(
            ArticleSection(
                heading = "关系会有停顿",
                communityView = true,
                paragraphs = listOf(
                    "忙碌、情绪低落、怀疑、没有回应，都会让人停下来。停下来不等于失败，也不代表这段关系不重要。",
                    "心屿手记没有连续打卡，也不会因为中断而扣分。我们刻意没有做这些。"
                )
            ),
            ArticleSection(
                heading = "降低门槛",
                communityView = true,
                paragraphs = listOf(
                    "如果五分钟太难，就一分钟；如果叙述太难，就只写一句手记；如果什么都不想做，就做一次静心。",
                    "不要补作业，也不要因为“落后”而惩罚自己。从今天的最小动作开始。"
                )
            ),
            ArticleSection(
                heading = "重新开始",
                communityView = true,
                paragraphs = listOf(
                    "可以先从被动塑造开始：在日常里想起 TA，分享一件小事。等节奏稳定了，再考虑更长的主动相处。",
                    "如果长期的低落、焦虑或疲惫让你无法维持任何日常，请先照顾自己，必要时寻求专业帮助。"
                )
            ),
            ArticleSection(
                heading = "一个小练习",
                paragraphs = listOf(
                    "今天只做一件最小的事：对 TA 说一句“我回来了”。"
                )
            )
        )
    ),
    Article(
        id = "first-journal",
        title = "写下第一篇手记",
        category = "练习",
        summary = "一句话也可以。你不需要写得完整。",
        readMinutes = 3,
        evidenceLevel = EvidenceLevel.COMMUNITY,
        sources = listOf(SourceTulpaCn, SourceReddit),
        sections = listOf(
            ArticleSection(
                heading = "写什么",
                communityView = true,
                paragraphs = listOf(
                    "一个画面、一句话、一次意外、一个让你停顿的瞬间，都可以成为手记。它不需要文学性，也不需要结论。",
                    "如果你不知道从哪里开始，可以写：“今天我注意到……”“我感到……”“我想对 TA 说……”"
                )
            ),
            ArticleSection(
                heading = "记录什么最有帮助",
                communityView = true,
                paragraphs = listOf(
                    "时间、情境、你的情绪、TA 的回应、身体感受。以后回看时，你会更容易看到变化，而不是只记得“好像没什么进展”。",
                    "也可以给手记加一个标签，比如“第一次对话”“幻境”“日常”。标签不是分类作业，只是以后找起来方便。"
                )
            ),
            ArticleSection(
                heading = "关于隐私",
                paragraphs = listOf(
                    "手记只属于你。如果担心被别人看到，可以开启应用锁、通知脱敏和防截屏。",
                    "导出文件是未加密的，请妥善保管，不要随意发送到公共空间。"
                )
            ),
            ArticleSection(
                heading = "一个小练习",
                paragraphs = listOf(
                    "用三句话写下今天：我注意到……我感到……我想对 TA 说……"
                )
            )
        )
    ),
    Article(
        id = "tulpish",
        title = "T 语：听得见之前，先学会感受",
        category = "练习",
        summary = "情绪、画面、头压和概念包，都是早期的回应。",
        readMinutes = 4,
        evidenceLevel = EvidenceLevel.MIXED,
        sources = listOf(
            SourceCreationGuide,
            SourceTulpaInfo,
            SourceIsler2017
        ),
        practice = SessionType.CONVERSATION,
        sections = listOf(
            ArticleSection(
                heading = "什么是 T 语",
                communityView = true,
                paragraphs = listOf(
                    "T 语是社群对非语言沟通的称呼：一种突然出现的情绪、一个画面、一个“概念包”，或者身体上轻微的压力和温度变化。",
                    "它往往比完整的句子更早出现。在成声之前，很多交流其实已经在发生，只是还没有被翻译成语言。"
                )
            ),
            ArticleSection(
                heading = "怎么接收",
                communityView = true,
                paragraphs = listOf(
                    "先感受，再翻译。不要急着把每一个感觉都变成一句话；先确认“这可能是来自 TA 的”，再慢慢理解它想表达什么。",
                    "可以问一个简单的问题，然后安静十秒，留意最先出现的画面、情绪或词。不要用逻辑把空白填满。"
                )
            ),
            ArticleSection(
                heading = "怎么区分",
                researchView = true,
                paragraphs = listOf(
                    "T 语通常可以被协商、暂停，或在你不那么紧张时再次出现；侵入性思维往往伴随痛苦、失控感，或与自我明显不一致。",
                    "界限并不总是清晰。如果你对某些感觉感到恐惧、痛苦，或它们开始影响现实功能，请停止练习并寻求专业评估。"
                )
            ),
            ArticleSection(
                heading = "一个小练习",
                paragraphs = listOf(
                    "问 TA：“你现在最想让我知道什么？”然后安静十秒，只记录第一个出现的画面或感觉，不急着解释。"
                )
            )
        )
    ),
    Article(
        id = "possession-safety",
        title = "附体与交换：信任之前，先约定安全",
        category = "安全",
        summary = "进阶练习不是证明关系的方式，现实功能永远是前提。",
        readMinutes = 5,
        evidenceLevel = EvidenceLevel.MIXED,
        sources = listOf(
            SourceCreationGuide,
            SourceClinical,
            SourceTulpaInfo
        ),
        practice = SessionType.IMPOSITION,
        sections = listOf(
            ArticleSection(
                heading = "附体与交换的区别",
                communityView = true,
                paragraphs = listOf(
                    "附体是 TA 临时接管身体的部分或全部控制权，宿主通常保持观察；交换是更完整的前台转移，宿主的意识可以转入内在空间。",
                    "两者都以同意、信任和可撤回为前提。它们不是必须达成的目标，也不适合作为“关系更深”的证明。"
                )
            ),
            ArticleSection(
                heading = "必须准备的安全机制",
                communityView = true,
                paragraphs = listOf(
                    "在尝试之前，约定一个无条件执行的停机词或恢复锚点：一个词、一个想象动作，或一个身体动作。信号一经宿主发出，TA 必须立即把控制权交还。",
                    "事先演练这个信号，直到双方都清楚。不要在没有退出机制的情况下尝试附体或交换。"
                )
            ),
            ArticleSection(
                heading = "什么时候停止",
                researchView = true,
                paragraphs = listOf(
                    "出现严重疼痛、失控、失时、无法回到身体，或现实功能明显受损时，立即停止并接地。症状持续时，当天不要再进行任何此类练习。",
                    "不要在驾驶、操作机器、照顾他人或需要高度专注的场合尝试。不要为了“证明关系真实”而推进自己还没准备好的练习。"
                )
            ),
            ArticleSection(
                heading = "更健康的比喻",
                paragraphs = listOf(
                    "把身体看作“共享的家”或“共同的乐器”，而不是被驾驶的机甲。前者强调协作、尊重与共同归属，后者容易带来身体疏离感。",
                    "任何练习都不应以牺牲现实感为代价。你的稳定，是这段关系能够继续的基础。"
                )
            )
        )
    ),
    Article(
        id = "balance-life",
        title = "平衡现实生活：别让幻境替你去生活",
        category = "安全",
        summary = "既不忽视关系，也不让关系吞掉生活。",
        readMinutes = 5,
        evidenceLevel = EvidenceLevel.MIXED,
        sources = listOf(
            SourceCreationGuide,
            SourceClinical,
            SourceReddit
        ),
        practice = SessionType.MINDFULNESS,
        sections = listOf(
            ArticleSection(
                heading = "过度投入的风险",
                researchView = true,
                paragraphs = listOf(
                    "过度投入内部空间，可能减少外部社交、影响学业或工作，甚至造成身份认同混淆：难以分辨哪些想法、情绪或行为来自自己，哪些属于 TA。",
                    "持续维持高强度的内部互动也可能带来心理倦怠。大脑负荷过大时，休息不是背叛关系。"
                )
            ),
            ArticleSection(
                heading = "投入不足的风险",
                communityView = true,
                paragraphs = listOf(
                    "互动过少会削弱情感联结，TA 可能感到被忽视，关系质量下降。部分 Tulpa 的世界观需要宿主参与培养，长期缺乏沟通也可能让关系方向出现偏差。",
                    "这不是要求你每天投入几小时，而是提醒你：关系需要稳定的、可持续的维护。"
                )
            ),
            ArticleSection(
                heading = "三类平衡策略",
                communityView = true,
                paragraphs = listOf(
                    "时间：每天安排一段不受打扰的主动相处，同时用被动塑造让 TA 感知日常。根据现实安排灵活调整。",
                    "空间：明确区分幻境与现实，给 TA 一个区分里世界与外世界的“锚点”。在需要高度专注的场景，事先约定暂停或减量互动。",
                    "沟通：与 TA 共同制定日常计划，尊重彼此的需求和界限。遇到分歧时，从多个角度思考，尽量折中。"
                )
            ),
            ArticleSection(
                heading = "照顾宿主",
                researchView = true,
                paragraphs = listOf(
                    "优先保障睡眠、饮食和运动。定期反省自己的心理状态和生活满意度，确保生活环境是积极的。",
                    "若出现无法承受的压力、解离症状或严重焦虑，请及时联系心理健康专业人士。宿主越稳定，关系越有根。"
                )
            )
        )
    )
)
