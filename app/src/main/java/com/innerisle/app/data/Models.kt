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
    val createdAt: Long = System.currentTimeMillis(),
    val startedAt: Long = 0L
) {
    /** The date the relationship started. Older data falls back to createdAt. */
    fun relationshipStart(): Long = if (startedAt > 0L) startedAt else createdAt

    fun daysTogether(): Int {
        val start = relationshipStart().toLocalDate()
        val days = java.time.temporal.ChronoUnit.DAYS.between(start, LocalDate.now()).toInt() + 1
        return days.coerceAtLeast(1)
    }
}

@Serializable
enum class SessionType(
    private val labelZh: String,
    private val subtitleZh: String,
    private val guidanceZh: List<String>,
    private val labelEn: String,
    private val subtitleEn: String,
    private val guidanceEn: List<String>
) {
    NARRATION(
        "叙述", "把今天讲给 TA 听",
        listOf(
            "从今天最小的一件事讲起，不用追求完整。",
            "说说刚才让你停了一下神的那个瞬间。",
            "如果 TA 就在旁边，TA 会先问什么？",
            "把情绪说出来，不必先整理好。",
            "讲一件你还没告诉任何人的小事。"
        ),
        "Narration", "Tell TA about your day",
        listOf(
            "Start with one small thing from today. It doesn't have to be complete.",
            "Talk about the moment that made you pause.",
            "If TA were beside you, what would they ask first?",
            "Say the feeling out loud. You don't have to sort it first.",
            "Share one small thing you haven't told anyone."
        )
    ),
    CONVERSATION(
        "对话", "留出安静的一问一答",
        listOf(
            "问一个只有 TA 会回答的问题。",
            "说完之后，先安静十秒，等一等。",
            "让 TA 来决定下一个话题。",
            "留意 TA 的语气，而不是急着补全句子。",
            "如果听不清，就先问一个只需回答是或否的问题。"
        ),
        "Conversation", "Leave room for a quiet exchange",
        listOf(
            "Ask a question only TA would answer.",
            "After you speak, stay quiet for ten seconds.",
            "Let TA choose the next topic.",
            "Notice TA's tone instead of filling every sentence.",
            "If it's unclear, ask a yes-or-no question."
        )
    ),
    VISUALIZATION(
        "形象", "在心头描摹 TA 的样子",
        listOf(
            "先感受 TA 的轮廓，不必立刻看清细节。",
            "从眼睛或手的形状开始，慢慢补全。",
            "想象 TA 的一个小动作，比如偏头或笑。",
            "让画面自己变化，不急着修正。",
            "把注意力放在“TA 在那里”的感觉上。"
        ),
        "Visualization", "Picture TA in your mind",
        listOf(
            "Feel TA's outline first. Details can wait.",
            "Start with the eyes or hands, then fill in slowly.",
            "Imagine one small gesture, like a head tilt or a smile.",
            "Let the image change without correcting it.",
            "Focus on the sense that TA is there."
        )
    ),
    WONDERLAND(
        "幻境", "回到你们共同的空间",
        listOf(
            "先站稳，感受脚下的地面。",
            "看看这个空间里最熟悉的一样东西。",
            "邀请 TA 带你去看一个角落。",
            "加入一种声音或气味，让场景更具体。",
            "不用建造新东西，只是待一会儿。"
        ),
        "Wonderland", "Return to your shared space",
        listOf(
            "Stand steady and feel the ground under you.",
            "Look at the most familiar thing in the space.",
            "Invite TA to show you a corner.",
            "Add one sound or scent to make it more concrete.",
            "You don't need to build anything. Just stay a while."
        )
    ),
    IMPOSITION(
        "投射", "试着感受 TA 就在身边",
        listOf(
            "先感受 TA 的存在感，而不是外形。",
            "想象 TA 坐在你身旁的位置。",
            "留意温度、呼吸或衣料的触感。",
            "只做一个感官细节，不要贪多。",
            "如果感到疲惫，就回到内在的想象。"
        ),
        "Imposition", "Try to sense TA beside you",
        listOf(
            "Sense TA's presence first, not their appearance.",
            "Imagine TA sitting next to you.",
            "Notice warmth, breath, or the feel of clothing.",
            "Choose one sensory detail. Don't rush.",
            "If you feel tired, return to inner imagination."
        )
    ),
    MINDFULNESS(
        "静心", "先安顿自己，再靠近 TA",
        listOf(
            "慢慢呼吸，把肩膀放下来。",
            "注意身体接触椅子的地方。",
            "让今天的声音进来，再放它们走。",
            "不追求清空头脑，只练习回来。",
            "等呼吸平稳后，再轻轻想起 TA。"
        ),
        "Mindfulness", "Settle yourself before coming closer",
        listOf(
            "Breathe slowly and lower your shoulders.",
            "Notice where your body meets the chair.",
            "Let today's sounds come in, then let them go.",
            "Don't try to empty your mind. Just practice returning.",
            "When your breath settles, gently think of TA."
        )
    );

    val label: String get() = L.t(labelZh, labelEn)
    val subtitle: String get() = L.t(subtitleZh, subtitleEn)
    val guidance: List<String> get() = if (L.isEn) guidanceEn else guidanceZh
}

data class MoodOption(
    val emoji: String,
    private val labelZh: String,
    private val labelEn: String
) {
    val label: String get() = L.t(labelZh, labelEn)
}

val MoodOptions = listOf(
    MoodOption("🌿", "平静", "Calm"),
    MoodOption("☀️", "温暖", "Warm"),
    MoodOption("✨", "开心", "Happy"),
    MoodOption("🌫️", "困惑", "Confused"),
    MoodOption("🌧️", "低落", "Low"),
    MoodOption("🌙", "疲惫", "Tired"),
    MoodOption("🫧", "说不清", "Unclear")
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
        val first = tulpas.minOfOrNull { it.relationshipStart() } ?: return 0
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

val DailyPrompts: List<String>
    get() = if (L.isEn) DailyPromptsEn else DailyPromptsZh

private val DailyPromptsZh = listOf(
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

private val DailyPromptsEn = listOf(
    "What small thing from today do you want to tell TA first?",
    "If TA were sitting across from you, how would they describe your state today?",
    "What was the last interaction that made you genuinely happy?",
    "What small habit of TA's have you noticed recently?",
    "What would you like TA to do with you today?",
    "Is there something you've been wanting to say to TA?",
    "How would TA describe you to someone else?",
    "If you traveled together, where would your first stop be?",
    "What emotion do you have today, and how do you hope TA responds?",
    "Has TA surprised you with anything recently?",
    "Do you have a signal only the two of you understand?",
    "If you gave today's relationship a title, what would it be?",
    "How do you want tomorrow's time together to differ from today?",
    "Has TA's voice become a little clearer recently?",
    "Was there a moment you almost forgot TA was \"imagined\"?",
    "If TA could leave you a note, what would it say?",
    "Do you need to be heard or accompanied today?",
    "Would TA like the song you're listening to right now?",
    "What has recently appeared in your shared space?",
    "What could you say that would make you feel lighter?",
    "What is the most \"TA-like\" thing TA has ever done?",
    "If you could remember only one detail from today, what would it be?",
    "Have you been a little gentler with yourself because of TA lately?",
    "What new way of spending time together would you like to try tomorrow?"
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
            L.t("初次相遇", "First Meeting"),
            L.t("创建第一个 TA 的档案", "Create your first TA profile"),
            data.tulpas.isNotEmpty()
        ),
        Achievement(
            "first_session",
            L.t("第一次相处", "First Session"),
            L.t("完成一次相处练习", "Complete one session"),
            data.sessions.isNotEmpty()
        ),
        Achievement(
            "one_hour",
            L.t("相伴一小时", "One Hour Together"),
            L.t("累计相处满 60 分钟", "Reach 60 minutes together"),
            totalMinutes >= 60
        ),
        Achievement(
            "ten_sessions",
            L.t("十次在场", "Ten Sessions"),
            L.t("完成 10 次相处", "Complete 10 sessions"),
            data.sessions.size >= 10
        ),
        Achievement(
            "seven_days",
            L.t("七日有约", "Seven Days"),
            L.t("在 7 个不同的日子留下记录", "Leave a record on 7 different days"),
            activeDays >= 7
        ),
        Achievement(
            "thirty_days",
            L.t("三十日同行", "Thirty Days"),
            L.t("在 30 个不同的日子留下记录", "Leave a record on 30 different days"),
            activeDays >= 30
        ),
        Achievement(
            "first_journal",
            L.t("第一篇手记", "First Journal"),
            L.t("写下第一篇属于你们的手记", "Write your first journal entry"),
            data.journals.isNotEmpty()
        ),
        Achievement(
            "ten_journals",
            L.t("十页心事", "Ten Pages"),
            L.t("累计写下 10 篇手记", "Write 10 journal entries"),
            data.journals.size >= 10
        ),
        Achievement(
            "voice",
            L.t("第一次对话", "First Conversation"),
            L.t("尝试一次对话练习", "Try a conversation session"),
            SessionType.CONVERSATION in types
        ),
        Achievement(
            "wonderland",
            L.t("回到幻境", "Return to the Wonderland"),
            L.t("尝试一次幻境练习", "Try a wonderland session"),
            SessionType.WONDERLAND in types
        ),
        Achievement(
            "imposition",
            L.t("靠近一点", "Come Closer"),
            L.t("尝试一次投射练习", "Try an imposition session"),
            SessionType.IMPOSITION in types
        ),
        Achievement(
            "mindful",
            L.t("先安顿自己", "Settle Yourself First"),
            L.t("完成一次静心练习", "Complete a mindfulness session"),
            SessionType.MINDFULNESS in types
        )
    )
}
