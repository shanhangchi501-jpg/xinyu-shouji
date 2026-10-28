package com.innerisle.app.ui

import com.innerisle.app.data.tr

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Insights
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.SelfImprovement
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.innerisle.app.data.AppData
import com.innerisle.app.data.SessionType
import com.innerisle.app.ui.theme.IsleMotion
import com.innerisle.app.ui.theme.LocalReduceMotion

sealed interface Overlay {
    data object Settings : Overlay
    data class About(val returnToSettings: Boolean = false) : Overlay
    data class MemberEdit(
        val id: String?,
        val returnToSettings: Boolean = false
    ) : Overlay
    data class JournalEdit(val id: String?) : Overlay
    data class Article(val id: String) : Overlay
    data class Guide(val chapterId: String?) : Overlay
    data class Session(
        val type: SessionType,
        val tulpaId: String,
        val minutes: Int
    ) : Overlay
}

private val OverlaySaver = listSaver<Overlay?, String>(
    save = { overlay ->
        when (overlay) {
            null -> emptyList()
            Overlay.Settings -> listOf("settings")
            is Overlay.About -> listOf(
                "about",
                overlay.returnToSettings.toString()
            )
            is Overlay.MemberEdit -> listOf(
                "member",
                overlay.id.orEmpty(),
                overlay.returnToSettings.toString()
            )
            is Overlay.JournalEdit -> listOf(
                "journal",
                overlay.id.orEmpty()
            )
            is Overlay.Article -> listOf("article", overlay.id)
            is Overlay.Guide -> if (overlay.chapterId == null) {
                listOf("guide")
            } else {
                listOf("guide", overlay.chapterId)
            }
            is Overlay.Session -> listOf(
                "session",
                overlay.type.name,
                overlay.tulpaId,
                overlay.minutes.toString()
            )
        }
    },
    restore = { saved ->
        when (saved.firstOrNull()) {
            null -> null
            "settings" -> Overlay.Settings
            "about" -> Overlay.About(
                returnToSettings = saved.getOrNull(1)?.toBoolean() ?: false
            )
            "member" -> Overlay.MemberEdit(
                id = saved.getOrNull(1)?.ifBlank { null },
                returnToSettings = saved.getOrNull(2)?.toBoolean() ?: false
            )
            "journal" -> Overlay.JournalEdit(
                id = saved.getOrNull(1)?.ifBlank { null }
            )
            "article" -> saved.getOrNull(1)
                ?.ifBlank { null }
                ?.let { Overlay.Article(it) }
            "guide" -> Overlay.Guide(saved.getOrNull(1)?.ifBlank { null })
            "session" -> {
                val type = saved.getOrNull(1)
                    ?.let { runCatching { SessionType.valueOf(it) }.getOrNull() }
                val tulpaId = saved.getOrNull(2)?.ifBlank { null }
                if (type != null && tulpaId != null) {
                    Overlay.Session(
                        type = type,
                        tulpaId = tulpaId,
                        minutes = saved.getOrNull(3)?.toIntOrNull() ?: 15
                    )
                } else {
                    null
                }
            }
            else -> null
        }
    }
)

private val Overlay?.depth: Int
    get() = when (this) {
        null -> 0
        Overlay.Settings -> 1
        is Overlay.About -> 2
        is Overlay.MemberEdit -> 2
        is Overlay.JournalEdit -> 2
        is Overlay.Article -> 2
        is Overlay.Guide -> if (chapterId == null) 2 else 3
        is Overlay.Session -> 2
    }

private fun Overlay?.routeKey(): String = when (this) {
    null -> "root"
    Overlay.Settings -> "settings"
    is Overlay.About -> "about"
    is Overlay.MemberEdit -> "member:${id.orEmpty()}:$returnToSettings"
    is Overlay.JournalEdit -> "journal:${id.orEmpty()}"
    is Overlay.Article -> "article:$id"
    is Overlay.Guide -> "guide:${chapterId.orEmpty()}"
    is Overlay.Session -> "session:${type.name}:$tulpaId:$minutes"
}

private fun AnimatedContentTransitionScope<Overlay?>.overlayTransform(
    reduceMotion: Boolean
): ContentTransform {
    if (reduceMotion) return EnterTransition.None togetherWith ExitTransition.None

    val forward = targetState.depth >= initialState.depth
    val enter = if (targetState is Overlay.Session) {
        fadeIn(
            tween(260, delayMillis = 40, easing = IsleMotion.emphasizedDecelerate)
        ) + scaleIn(
            initialScale = 0.98f,
            animationSpec = tween(260, delayMillis = 40, easing = IsleMotion.emphasizedDecelerate)
        )
    } else {
        fadeIn(
            tween(200, delayMillis = 80, easing = IsleMotion.emphasizedDecelerate)
        ) + slideInHorizontally(IsleMotion.spatialDefault()) {
            if (forward) it / 8 else -it / 8
        }
    }
    val exit = if (initialState is Overlay.Session) {
        fadeOut(tween(120, easing = IsleMotion.emphasizedAccelerate))
    } else {
        fadeOut(tween(90, easing = IsleMotion.emphasizedAccelerate)) +
            slideOutHorizontally(IsleMotion.spatialDefault()) {
                if (forward) -it / 12 else it / 12
            }
    }
    return enter togetherWith exit
}

@Composable
fun MainApp(
    viewModel: AppViewModel,
    data: AppData,
    settings: UiSettings,
    onQuickExit: () -> Unit
) {
    var tab by rememberSaveable { mutableIntStateOf(0) }
    var overlay by rememberSaveable(stateSaver = OverlaySaver) {
        mutableStateOf<Overlay?>(null)
    }
    var insightsAnimated by rememberSaveable { mutableStateOf(false) }
    val reduceMotion = LocalReduceMotion.current

    fun closeCurrentOverlay() {
        overlay = when (val current = overlay) {
            is Overlay.About ->
                if (current.returnToSettings) Overlay.Settings else null
            is Overlay.MemberEdit ->
                if (current.returnToSettings) Overlay.Settings else null
            is Overlay.Guide ->
                if (current.chapterId != null) Overlay.Guide(null) else null
            else -> null
        }
    }

    BackHandler(enabled = overlay != null) { closeCurrentOverlay() }

    AnimatedContent(
        targetState = overlay,
        transitionSpec = { overlayTransform(reduceMotion) },
        contentKey = { it.routeKey() },
        label = "overlay"
    ) { current ->
        when (current) {
        null -> {
            Scaffold(
                containerColor = Color.Transparent,
                bottomBar = {
                    NavigationBar(
                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.96f),
                        tonalElevation = 0.dp
                    ) {
                        NavigationBarItem(
                            selected = tab == 0,
                            onClick = { tab = 0 },
                            icon = { Icon(Icons.Outlined.Home, contentDescription = null) },
                            label = { Text(tr("首页")) }
                        )
                        NavigationBarItem(
                            selected = tab == 1,
                            onClick = { tab = 1 },
                            icon = {
                                Icon(Icons.Outlined.SelfImprovement, contentDescription = null)
                            },
                            label = { Text(tr("相处")) }
                        )
                        NavigationBarItem(
                            selected = tab == 2,
                            onClick = { tab = 2 },
                            icon = {
                                Icon(Icons.AutoMirrored.Outlined.MenuBook, contentDescription = null)
                            },
                            label = { Text(tr("手记")) }
                        )
                        NavigationBarItem(
                            selected = tab == 3,
                            onClick = { tab = 3 },
                            icon = { Icon(Icons.Outlined.Insights, contentDescription = null) },
                            label = { Text(tr("年轮")) }
                        )
                        NavigationBarItem(
                            selected = tab == 4,
                            onClick = { tab = 4 },
                            icon = { Icon(Icons.Outlined.Lightbulb, contentDescription = null) },
                            label = { Text(tr("了解")) }
                        )
                    }
                }
            ) { padding ->
                Box(modifier = Modifier.padding(padding)) {
                    AnimatedContent(
                        targetState = tab,
                        modifier = Modifier.fillMaxSize(),
                        transitionSpec = {
                            if (reduceMotion) {
                                EnterTransition.None togetherWith ExitTransition.None
                            } else {
                                val forward = targetState > initialState
                                (fadeIn(
                                    tween(200, delayMillis = 80, easing = IsleMotion.emphasizedDecelerate)
                                ) + slideInHorizontally(IsleMotion.spatialDefault()) {
                                    if (forward) it / 10 else -it / 10
                                })
                                    .togetherWith(
                                        fadeOut(tween(80, easing = IsleMotion.emphasizedAccelerate)) +
                                            slideOutHorizontally(IsleMotion.spatialDefault()) {
                                                if (forward) -it / 14 else it / 14
                                            }
                                    )
                            }
                        },
                        contentKey = { it },
                        label = "tab"
                    ) { currentTab ->
                        when (currentTab) {
                        0 -> HomeScreen(
                            data = data,
                            settings = settings,
                            onOpenSettings = { overlay = Overlay.Settings },
                            onOpenCompanion = { tab = 1 },
                            onQuickSession = { type, minutes ->
                                val tulpa = data.tulpas.firstOrNull()
                                if (tulpa != null) {
                                    overlay = Overlay.Session(type, tulpa.id, minutes)
                                }
                            },
                            onWriteJournal = { overlay = Overlay.JournalEdit(null) },
                            onOpenJournal = { overlay = Overlay.JournalEdit(it) },
                            onAddMember = { overlay = Overlay.MemberEdit(null) },
                            onLock = { viewModel.lock() },
                            onOpenGuide = { overlay = Overlay.Guide(null) },
                            guideReadCount = settings.guideReadIds.size
                        )
                        1 -> CompanionScreen(
                            data = data,
                            onStart = { type, tulpaId, minutes ->
                                overlay = Overlay.Session(type, tulpaId, minutes)
                            },
                            onAddMember = { overlay = Overlay.MemberEdit(null) }
                        )
                        2 -> JournalScreen(
                            data = data,
                            onAdd = { overlay = Overlay.JournalEdit(null) },
                            onOpen = { overlay = Overlay.JournalEdit(it) }
                        )
                        3 -> InsightsScreen(
                            data = data,
                            animateBars = !insightsAnimated,
                            onBarsAnimationPlayed = { insightsAnimated = true }
                        )
                        else -> KnowledgeScreen(
                            onOpenArticle = { overlay = Overlay.Article(it) },
                            onOpenGuide = { overlay = Overlay.Guide(null) },
                            readGuideCount = settings.guideReadIds.size
                        )
                        }
                    }
                }
            }
        }

        Overlay.Settings -> SettingsScreen(
            viewModel = viewModel,
            data = data,
            settings = settings,
            onBack = { overlay = null },
            onEditMember = { overlay = Overlay.MemberEdit(it, returnToSettings = true) },
            onAddMember = { overlay = Overlay.MemberEdit(null, returnToSettings = true) },
            onAbout = { overlay = Overlay.About(returnToSettings = true) },
            onQuickExit = onQuickExit
        )

        is Overlay.About -> AboutScreen(
            onBack = {
                overlay = if (current.returnToSettings) Overlay.Settings else null
            }
        )

        is Overlay.MemberEdit -> MemberEditScreen(
            data = data,
            memberId = current.id,
            onBack = {
                overlay = if (current.returnToSettings) Overlay.Settings else null
            },
            onSave = { member ->
                if (current.id == null) {
                    viewModel.addMember(
                        name = member.name,
                        emoji = member.emoji,
                        color = member.color,
                        form = member.form,
                        personality = member.personality,
                        voice = member.voice,
                        likes = member.likes,
                        notes = member.notes,
                        startedAt = member.startedAt
                    )
                } else {
                    viewModel.updateMember(member)
                }
                overlay = if (current.returnToSettings) Overlay.Settings else null
            },
            onDelete = { id ->
                viewModel.deleteMember(id)
                overlay = if (current.returnToSettings) Overlay.Settings else null
            }
        )

        is Overlay.JournalEdit -> JournalEditScreen(
            data = data,
            entryId = current.id,
            onBack = { overlay = null },
            onSave = { entry ->
                viewModel.addOrUpdateJournal(entry)
                overlay = null
            },
            onDelete = { id ->
                viewModel.deleteJournal(id)
                overlay = null
            }
        )

        is Overlay.Session -> SessionScreen(
            data = data,
            tulpaId = current.tulpaId,
            type = current.type,
            plannedMinutes = current.minutes,
            onBack = { overlay = null },
            onSave = { startedAt, minutes, mood, note ->
                viewModel.addSession(
                    tulpaId = current.tulpaId,
                    type = current.type,
                    startedAt = startedAt,
                    minutes = minutes,
                    mood = mood,
                    note = note
                )
                overlay = null
            }
        )

        is Overlay.Article -> ArticleDetailScreen(
            articleId = current.id,
            onBack = { overlay = null },
            onPractice = {
                overlay = null
                tab = 1
            }
        )

        is Overlay.Guide -> if (current.chapterId == null) {
            GuideListScreen(
                readIds = settings.guideReadIds,
                onOpenChapter = { overlay = Overlay.Guide(it) },
                onBack = { overlay = null }
            )
        } else {
            GuideChapterScreen(
                chapterId = current.chapterId,
                readIds = settings.guideReadIds,
                onToggleRead = viewModel::toggleGuideRead,
                onOpenChapter = { overlay = Overlay.Guide(it) },
                onPractice = {
                    overlay = null
                    tab = 1
                },
                onBack = { overlay = Overlay.Guide(null) }
            )
        }
        }
    }
}
