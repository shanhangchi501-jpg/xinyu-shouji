package com.innerisle.app.ui

import com.innerisle.app.data.L

import com.innerisle.app.data.tr

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.innerisle.app.data.Article
import com.innerisle.app.data.Concepts
import com.innerisle.app.data.Articles
import com.innerisle.app.data.Concept
import com.innerisle.app.data.GuideChapters
import com.innerisle.app.data.KnowledgeCategories
import com.innerisle.app.ui.theme.IsleMotion
import com.innerisle.app.ui.theme.LocalReduceMotion

@Composable
fun KnowledgeScreen(
    onOpenArticle: (String) -> Unit,
    onOpenGuide: () -> Unit,
    readGuideCount: Int
) {
    val reduceMotion = LocalReduceMotion.current
    var query by rememberSaveable { mutableStateOf("") }
    var category by rememberSaveable { mutableStateOf(tr("全部")) }
    var expandedConcept by rememberSaveable { mutableStateOf<String?>(null) }

    val normalizedQuery = query.trim()
    val articles = Articles.filter { article ->
        (category == tr("全部") || article.category == category) &&
            (
                normalizedQuery.isBlank() ||
                    article.title.contains(normalizedQuery, ignoreCase = true) ||
                    article.summary.contains(normalizedQuery, ignoreCase = true) ||
                    article.sections.any { section ->
                        section.paragraphs.any {
                            it.contains(normalizedQuery, ignoreCase = true)
                        }
                    }
                )
    }
    val concepts = Concepts.filter { concept ->
        (category == tr("全部") || concept.category == category) &&
            (
                normalizedQuery.isBlank() ||
                    concept.term.contains(normalizedQuery, ignoreCase = true) ||
                    concept.english.contains(normalizedQuery, ignoreCase = true) ||
                    concept.short.contains(normalizedQuery, ignoreCase = true) ||
                    concept.detail.contains(normalizedQuery, ignoreCase = true)
                )
    }

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(start = 20.dp, top = 16.dp, end = 20.dp, bottom = 28.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            SectionTitle(
                title = tr("概念与文章"),
                subtitle = tr("慢慢了解，再慢慢靠近。")
            )
        }

        item {
            val total = GuideChapters.size
            val progress = if (total == 0) 0f else readGuideCount.toFloat() / total
            SoftCard(
                onClick = onOpenGuide,
                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.55f)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.MenuBook,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = tr("从零开始"),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = L.t("Tulpa 完全创造指南 · $total 章", "The Complete Tulpa Creation Guide · $total chapters"),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                        )
                    }
                    Icon(
                        Icons.Outlined.ChevronRight,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                Spacer(Modifier.height(12.dp))
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(7.dp)
                        .clip(CircleShape),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.6f)
                )
                Spacer(Modifier.height(7.dp))
                Text(
                    text = L.t("已读 $readGuideCount / $total 章 · 从准备到进阶，按自己的节奏", "Read $readGuideCount / $total chapters · From preparation to advanced practice, at your own pace"),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                )
            }
        }

        item {
            SoftCard(
                containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
            ) {
                Text(
                    text = tr("先说明"),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = tr("这里的内容整理自公开社区经验与资料，不是医学结论，也不构成诊断或治疗建议。如果实践让你持续痛苦或影响现实功能，请优先寻求专业帮助。"),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.85f)
                )
            }
        }

        item {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(tr("搜索概念或文章")) },
                leadingIcon = {
                    Icon(Icons.Outlined.Search, contentDescription = null)
                },
                trailingIcon = {
                    if (query.isNotBlank()) {
                        IconButton(onClick = { query = "" }) {
                            Icon(Icons.Outlined.Close, contentDescription = tr("清除"))
                        }
                    }
                },
                singleLine = true
            )
        }

        item {
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                KnowledgeCategories.forEach { item ->
                    FilterChip(
                        selected = category == item,
                        onClick = { category = item },
                        label = { Text(item) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )
                }
            }
        }

        if (articles.isNotEmpty()) {
            item {
                SectionTitle(
                    title = tr("文章"),
                    subtitle = L.t("${articles.size} 篇 · 点开慢慢读", "${articles.size} articles · Tap to read at your leisure")
                )
            }
            items(articles, key = { it.id }) { article ->
                Box(if (reduceMotion) Modifier else Modifier.animateItem()) {
                    ArticleCard(
                        article = article,
                        onClick = { onOpenArticle(article.id) }
                    )
                }
            }
        }

        if (concepts.isNotEmpty()) {
            item {
                SectionTitle(
                    title = tr("概念词典"),
                    subtitle = tr("点开卡片，看更完整的解释")
                )
            }
            items(concepts, key = { it.term }) { concept ->
                Box(if (reduceMotion) Modifier else Modifier.animateItem()) {
                    ConceptCard(
                        concept = concept,
                        expanded = expandedConcept == concept.term,
                        onClick = {
                            expandedConcept = if (expandedConcept == concept.term) {
                                null
                            } else {
                                concept.term
                            }
                        }
                    )
                }
            }
        }

        if (articles.isEmpty() && concepts.isEmpty()) {
            item {
                EmptyState(
                    emoji = "🔍",
                    title = tr("没有找到"),
                    body = tr("换一个关键词，或者把分类切回“全部”。")
                )
            }
        }

        item {
            Text(
                text = tr("内容会继续补充。你可以先读一篇，再做一次五分钟的相处。"),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp)
            )
        }
    }
}

@Composable
private fun ArticleCard(
    article: Article,
    onClick: () -> Unit
) {
    SoftCard(onClick = onClick) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Pill(article.category)
            Spacer(Modifier.width(8.dp))
            Text(
                text = L.t("${article.readMinutes} 分钟 · ${article.evidenceLevel.label}", "${article.readMinutes} min · ${article.evidenceLevel.label}"),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.weight(1f))
            Icon(
                Icons.Outlined.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(Modifier.height(10.dp))
        Text(
            text = article.title,
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(Modifier.height(5.dp))
        Text(
            text = article.summary,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
        if (article.practice != null) {
            Spacer(Modifier.height(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = article.practice.icon(),
                    contentDescription = null,
                    tint = article.practice.tint(),
                    modifier = Modifier.width(18.dp)
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    text = L.t("可配合练习：${article.practice.label}", "Paired practice: ${article.practice.label}"),
                    style = MaterialTheme.typography.labelLarge,
                    color = article.practice.tint()
                )
            }
        }
    }
}

@Composable
private fun ConceptCard(
    concept: Concept,
    expanded: Boolean,
    onClick: () -> Unit
) {
    val reduceMotion = LocalReduceMotion.current
    SoftCard(onClick = onClick) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = concept.term,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = concept.english,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.weight(1f))
            Pill(concept.category)
        }
        Spacer(Modifier.height(8.dp))
        Text(
            text = concept.short,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        AnimatedVisibility(
            visible = expanded,
            enter = expandVertically(
                animationSpec = if (reduceMotion) snap() else IsleMotion.spatialDefault(),
                expandFrom = Alignment.Top
            ) + fadeIn(
                animationSpec = if (reduceMotion) snap() else tween(180)
            ),
            exit = shrinkVertically(
                animationSpec = if (reduceMotion) snap() else IsleMotion.spatialDefault(),
                shrinkTowards = Alignment.Top
            ) + fadeOut(
                animationSpec = if (reduceMotion) snap() else tween(140)
            )
        ) {
            Column {
                Spacer(Modifier.height(12.dp))
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    text = concept.detail,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
