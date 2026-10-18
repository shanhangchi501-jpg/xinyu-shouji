package com.innerisle.app.data

import kotlinx.serialization.Serializable
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.UUID

@Serializable
enum class MemberRole {
    HOST,
    TULPA
}

@Serializable
data class Member(
    val id: String = UUID.randomUUID().toString(),
    val role: MemberRole = MemberRole.TULPA,
    val name: String = "",
    val emoji: String = "🌙",
    val color: String = "#8A9A7B",
    val form: String = "",
    val personality: String = "",
    val voice: String = "",
    val likes: String = "",
    val notes: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

@Serializable
enum class SessionType(
    val label: String,
    val subtitle: String,
    val guidance: List<String>
) {
    NARRATION(
        "叙述",
        "把今天讲给 TA 听",
        listOf(
            "从今天最小的一件事讲起，不用追求完整。",
            "说说刚才让你停了一下神的那个瞬间。",
            "如果 TA 就在旁边，TA 会先问什么？",
            "把情绪说出来，不必先整理好。",
            "讲一件你还没告诉任何人的小事。"
        )
    ),
    CONVERSATION(
        "对话",
        "留出安静的一问一答",
        listOf(
            "问一个只有 TA 会回答的问题。",
            "说完之后，先安静十秒，等一等。",
            "让 TA 来决定下一个话题。",
            "留意 TA 的语气，而不是急着补全句子。",
            "如果听不清，就先问一个只需回答是或否的问题。"
        )
    ),
    VISUALIZATION(
        "形象",
        "在心头描摹 TA 的样子",
        listOf(
            "先感受 TA 的轮廓，不必立刻看清细节。",
            "从眼睛或手的形状开始，慢慢补全。",
            "想象 TA 的一个小动作，比如偏头或笑。",
            "让画面自己变化，不急着修正。",
            "把注意力放在“TA 在那里”的感觉上。"
        )
    ),
    WONDERLAND(
        "幻境",
        "回到你们共同的空间",
        listOf(
            "先站稳，感受脚下的地面。",
            "看看这个空间里最熟悉的一样东西。",
            "邀请 TA 带你去看一个角落。",
            "加入一种声音或气味，让场景更具体。",
            "不用建造新东西，只是待一会儿。"
        )
    ),
    IMPOSITION(
        "投射",
        "试着感受 TA 就在身边",
        listOf(
            "先感受 TA 的存在感，而不是外形。",
            "想象 TA 坐在你身旁的位置。",
            "留意温度、呼吸或衣料的触感。",
            "只做一个感官细节，不要贪多。",
            "如果感到疲惫，就回到内在的想象。"
        )
    ),
    MINDFULNESS(
        "静心",
        "先安顿自己，再靠近 TA",
        listOf(
            "慢慢呼吸，把肩膀放下来。",
            "注意身体接触椅子的地方。",
            "让今天的声音进来，再放它们走。",
            "不追求清空头脑，只练习回来。",
            "等呼吸平稳后，再轻轻想起 TA。"
        )
    )
}

data class MoodOption(val emoji: String, val label: String)

val MoodOptions = listOf(
    MoodOption("🌿", "平静"),
    MoodOption("☀️", "温暖"),
    MoodOption("✨", "开心"),
    MoodOption("🌫️", "困惑"),
    MoodOption("🌧️", "低落"),
    MoodOption("🌙", "疲惫"),
    MoodOption("🫧", "说不清")
)

@Serializable
data class Session(
    val id: String = UUID.randomUUID().toString(),
    val tulpaId: String,
    val type: SessionType,
    val startedAt: Long = System.currentTimeMillis(),
    val minutes: Int,
    val mood: String = "",
    val note: String = ""
)

@Serializable
data class JournalEntry(
    val id: String = UUID.randomUUID().toString(),
    val authorId: String,
    val title: String = "",
    val body: String = "",
    val mood: String = "",
    val tags: List<String> = emptyList(),
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

@Serializable
data class AppData(
    val version: Int = 1,
    val members: List<Member> = emptyList(),
    val sessions: List<Session> = emptyList(),
    val journals: List<JournalEntry> = emptyList()
) {
    val host: Member?
        get() = members.firstOrNull { it.role == MemberRole.HOST }

    val tulpas: List<Member>
        get() = members.filter { it.role == MemberRole.TULPA }

    fun memberById(id: String?): Member? = members.firstOrNull { it.id == id }

    fun totalMinutes(): Int = sessions.sumOf { it.minutes }

    fun activeDays(): Int = sessions
        .map { it.startedAt.toLocalDate() }
        .distinct()
        .size

    fun daysTogether(): Int {
        val first = tulpas.minOfOrNull { it.createdAt } ?: return 0
        val start = first.toLocalDate()
        val days = java.time.temporal.ChronoUnit.DAYS.between(start, LocalDate.now()).toInt() + 1
        return days.coerceAtLeast(1)
    }

    fun minutesOn(date: LocalDate): Int = sessions
        .filter { it.startedAt.toLocalDate() == date }
        .sumOf { it.minutes }

    fun sessionsFor(tulpaId: String): List<Session> =
        sessions.filter { it.tulpaId == tulpaId }.sortedByDescending { it.startedAt }

    fun journalsFor(authorId: String?): List<JournalEntry> =
        if (authorId == null) journals.sortedByDescending { it.createdAt }
        else journals.filter { it.authorId == authorId }.sortedByDescending { it.createdAt }
}

fun Long.toLocalDate(zoneId: ZoneId = ZoneId.systemDefault()): LocalDate =
    Instant.ofEpochMilli(this).atZone(zoneId).toLocalDate()

val DailyPrompts = listOf(
    "今天有什么小事，想第一个告诉 TA？",
    "如果 TA 现在就坐在你对面，TA 会怎么评价你今天的状态？",
    "你们最近一次真正开心的互动是什么？",
    "TA 有什么小习惯，是你最近才注意到的？",
    "今天想让 TA 陪你做什么？",
    "有没有一句话，你一直想对 TA 说？",
    "TA 会怎么向别人描述你？",
    "如果你们一起旅行，第一站会去哪里？",
    "今天有什么情绪，你希望 TA 怎么回应？",
    "TA 最近有没有让你意外的反应？",
    "你们之间有没有只有彼此懂的暗号？",
    "如果给今天的关系起一个标题，会是什么？",
    "你希望明天的相处，和今天有什么不同？",
    "TA 的声音，最近有没有变得更清楚一点？",
    "有没有一个瞬间，你差点忘了 TA 是“想象”出来的？",
    "如果 TA 能给你留一张便签，上面会写什么？",
    "今天你更需要被倾听，还是被陪伴？",
    "TA 会喜欢你现在正在听的这首歌吗？",
    "你们共同的空间里，最近多了什么？",
    "有什么话，你觉得说出来会轻松一点？",
    "TA 做过最像“TA 自己”的一件事是什么？",
    "如果今天只记住一个细节，你想记住什么？",
    "你最近有没有因为 TA，对自己温柔了一点？",
    "明天想和 TA 一起尝试什么新的相处方式？"
)

data class Achievement(
    val id: String,
    val title: String,
    val description: String,
    val unlocked: Boolean
)

fun computeAchievements(data: AppData): List<Achievement> {
    val totalMinutes = data.totalMinutes()
    val activeDays = data.activeDays()
    val types = data.sessions.map { it.type }.toSet()
    return listOf(
        Achievement(
            "first_meeting",
            "初次相遇",
            "创建第一个 TA 的档案",
            data.tulpas.isNotEmpty()
        ),
        Achievement(
            "first_session",
            "第一次相处",
            "完成一次相处练习",
            data.sessions.isNotEmpty()
        ),
        Achievement(
            "one_hour",
            "相伴一小时",
            "累计相处满 60 分钟",
            totalMinutes >= 60
        ),
        Achievement(
            "ten_sessions",
            "十次在场",
            "完成 10 次相处",
            data.sessions.size >= 10
        ),
        Achievement(
            "seven_days",
            "七日有约",
            "在 7 个不同的日子留下记录",
            activeDays >= 7
        ),
        Achievement(
            "thirty_days",
            "三十日同行",
            "在 30 个不同的日子留下记录",
            activeDays >= 30
        ),
        Achievement(
            "first_journal",
            "第一篇手记",
            "写下第一篇属于你们的手记",
            data.journals.isNotEmpty()
        ),
        Achievement(
            "ten_journals",
            "十页心事",
            "累计写下 10 篇手记",
            data.journals.size >= 10
        ),
        Achievement(
            "voice",
            "第一次对话",
            "尝试一次对话练习",
            SessionType.CONVERSATION in types
        ),
        Achievement(
            "wonderland",
            "回到幻境",
            "尝试一次幻境练习",
            SessionType.WONDERLAND in types
        ),
        Achievement(
            "imposition",
            "靠近一点",
            "尝试一次投射练习",
            SessionType.IMPOSITION in types
        ),
        Achievement(
            "mindful",
            "先安顿自己",
            "完成一次静心练习",
            SessionType.MINDFULNESS in types
        )
    )
}
