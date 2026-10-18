package com.innerisle.app.ui

import com.innerisle.app.data.L

import com.innerisle.app.data.tr

import android.Manifest
import android.app.TimePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.DeleteForever
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material.icons.outlined.Upload
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.innerisle.app.BuildConfig
import com.innerisle.app.data.AppData
import com.innerisle.app.data.Language
import com.innerisle.app.data.MemberRole
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun SettingsScreen(
    viewModel: AppViewModel,
    data: AppData,
    settings: UiSettings,
    onBack: () -> Unit,
    onEditMember: (String) -> Unit,
    onAddMember: () -> Unit,
    onAbout: () -> Unit,
    onQuickExit: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var showPinSetup by rememberSaveable { mutableStateOf(false) }
    var showDeleteAll by rememberSaveable { mutableStateOf(false) }
    var pendingImport by remember { mutableStateOf<Uri?>(null) }
    var showImportConfirm by remember { mutableStateOf(false) }

    val notificationLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (!granted) {
            Toast.makeText(context, tr("未授予通知权限，提醒不会显示。"), Toast.LENGTH_LONG).show()
        }
    }

    fun ensureNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            notificationLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    val exportLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.CreateDocument("application/json")
    ) { uri ->
        if (uri != null) {
            scope.launch {
                val result = viewModel.exportTo(uri)
                Toast.makeText(
                    context,
                    if (result.isSuccess) tr("已导出。请妥善保管这个未加密的备份文件。") else tr("导出失败。"),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    val importLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            pendingImport = uri
            showImportConfirm = true
        }
    }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = { AppTopBar(title = tr("设置"), onBack = onBack) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding),
            contentPadding = PaddingValues(
                start = 20.dp,
                top = 8.dp,
                end = 20.dp,
                bottom = 30.dp
            ),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            item {
                SettingsSection(title = L.t("语言", "Language")) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 18.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        FilterChip(
                            selected = settings.language == Language.ZH,
                            onClick = {
                                if (settings.language != Language.ZH) {
                                    viewModel.setLanguage(Language.ZH)
                                    val intent = context.packageManager
                                        .getLaunchIntentForPackage(context.packageName)
                                    if (intent != null) {
                                        intent.addFlags(
                                            Intent.FLAG_ACTIVITY_CLEAR_TOP or
                                                Intent.FLAG_ACTIVITY_NEW_TASK or
                                                Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        )
                                        context.startActivity(intent)
                                    }
                                    context.findActivity()?.finish()
                                }
                            },
                            label = { Text("中文") },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        )
                        FilterChip(
                            selected = settings.language == Language.EN,
                            onClick = {
                                if (settings.language != Language.EN) {
                                    viewModel.setLanguage(Language.EN)
                                    val intent = context.packageManager
                                        .getLaunchIntentForPackage(context.packageName)
                                    if (intent != null) {
                                        intent.addFlags(
                                            Intent.FLAG_ACTIVITY_CLEAR_TOP or
                                                Intent.FLAG_ACTIVITY_NEW_TASK or
                                                Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        )
                                        context.startActivity(intent)
                                    }
                                    context.findActivity()?.finish()
                                }
                            },
                            label = { Text("English") },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        )
                    }
                }
            }

            item {
                SettingsSection(title = tr("档案")) {
                    data.members.forEachIndexed { index, member ->
                        SettingsRow(
                            icon = Icons.Outlined.Person,
                            title = "${member.emoji} ${member.name}",
                            subtitle = if (member.role == MemberRole.HOST) tr("宿主") else "Tulpa",
                            onClick = { onEditMember(member.id) },
                            trailing = {
                                Icon(
                                    Icons.Outlined.ChevronRight,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        )
                        if (index != data.members.lastIndex) {
                            SettingsDivider()
                        }
                    }
                    SettingsDivider()
                    SettingsRow(
                        icon = Icons.Outlined.Add,
                        title = tr("添加一位 TA"),
                        subtitle = tr("可以创建多个 Tulpa 档案"),
                        onClick = onAddMember
                    )
                }
            }

            item {
                SettingsSection(title = tr("提醒")) {
                    SettingsRow(
                        icon = Icons.Outlined.Notifications,
                        title = tr("每日提醒"),
                        subtitle = if (settings.reminderEnabled) {
                            L.t("${formatReminderTime(settings.reminderHour, settings.reminderMinute)} · 每天", "${formatReminderTime(settings.reminderHour, settings.reminderMinute)} · Daily")
                        } else {
                            tr("轻轻提醒，不连续催促")
                        },
                        onClick = {
                            val enabled = !settings.reminderEnabled
                            if (enabled) ensureNotificationPermission()
                            viewModel.setReminderEnabled(enabled)
                        },
                        trailing = {
                            Switch(
                                checked = settings.reminderEnabled,
                                onCheckedChange = { enabled ->
                                    if (enabled) ensureNotificationPermission()
                                    viewModel.setReminderEnabled(enabled)
                                }
                            )
                        }
                    )
                    if (settings.reminderEnabled) {
                        SettingsDivider()
                        SettingsRow(
                            icon = Icons.Outlined.Timer,
                            title = tr("提醒时间"),
                            subtitle = formatReminderTime(
                                settings.reminderHour,
                                settings.reminderMinute
                            ),
                            onClick = {
                                TimePickerDialog(
                                    context,
                                    { _, hour, minute ->
                                        viewModel.setReminderTime(hour, minute)
                                    },
                                    settings.reminderHour,
                                    settings.reminderMinute,
                                    true
                                ).show()
                            },
                            trailing = {
                                Icon(
                                    Icons.Outlined.ChevronRight,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        )
                    }
                    SettingsDivider()
                    SettingsRow(
                        icon = Icons.Outlined.Shield,
                        title = tr("通知脱敏"),
                        subtitle = tr("通知中不显示 TA 的名字与内容"),
                        trailing = {
                            Switch(
                                checked = settings.stealthNotifications,
                                onCheckedChange = viewModel::setStealthNotifications
                            )
                        }
                    )
                }
            }

            item {
                SettingsSection(title = tr("隐私")) {
                    SettingsRow(
                        icon = Icons.Outlined.Lock,
                        title = tr("应用锁"),
                        subtitle = if (settings.appLockEnabled) {
                            tr("离开 30 秒后再次进入需要 PIN")
                        } else {
                            tr("用 4 位 PIN 保护界面")
                        },
                        trailing = {
                            Switch(
                                checked = settings.appLockEnabled,
                                onCheckedChange = { enabled ->
                                    if (!enabled) {
                                        viewModel.disableAppLock()
                                    } else if (settings.hasPin) {
                                        viewModel.setAppLockEnabled(true)
                                    } else {
                                        showPinSetup = true
                                    }
                                }
                            )
                        }
                    )
                    if (settings.hasPin) {
                        SettingsDivider()
                        SettingsRow(
                            icon = Icons.Outlined.Lock,
                            title = tr("修改 PIN"),
                            onClick = { showPinSetup = true },
                            trailing = {
                                Icon(
                                    Icons.Outlined.ChevronRight,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        )
                    }
                    SettingsDivider()
                    SettingsRow(
                        icon = Icons.Outlined.VisibilityOff,
                        title = tr("防止截屏"),
                        subtitle = tr("在系统允许的应用中阻止截图与录屏"),
                        trailing = {
                            Switch(
                                checked = settings.preventScreenshots,
                                onCheckedChange = viewModel::setPreventScreenshots
                            )
                        }
                    )
                    if (settings.appLockEnabled) {
                        SettingsDivider()
                        SettingsRow(
                            icon = Icons.Outlined.Lock,
                            title = tr("立即锁定"),
                            onClick = { viewModel.lock() }
                        )
                    }
                    SettingsDivider()
                    SettingsRow(
                        icon = Icons.AutoMirrored.Outlined.ExitToApp,
                        title = tr("快速离开"),
                        subtitle = tr("关闭心屿手记并回到桌面"),
                        onClick = onQuickExit
                    )
                }
            }

            item {
                SettingsSection(title = tr("数据")) {
                    SettingsRow(
                        icon = Icons.Outlined.Upload,
                        title = tr("导出备份"),
                        subtitle = tr("JSON 文件未加密，请妥善保管"),
                        onClick = {
                            val name = "xinyu-shouji-backup-${
                                LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE)
                            }.json"
                            exportLauncher.launch(name)
                        }
                    )
                    SettingsDivider()
                    SettingsRow(
                        icon = Icons.Outlined.Download,
                        title = tr("导入备份"),
                        subtitle = tr("导入会覆盖当前的全部内容"),
                        onClick = {
                            importLauncher.launch(arrayOf("application/json", "text/plain"))
                        }
                    )
                    SettingsDivider()
                    SettingsRow(
                        icon = Icons.Outlined.DeleteForever,
                        title = tr("删除全部数据"),
                        subtitle = tr("加密数据、设置与 PIN 都会被清除"),
                        destructive = true,
                        onClick = { showDeleteAll = true }
                    )
                }
            }

            item {
                SettingsSection(title = tr("关于")) {
                    SettingsRow(
                        icon = Icons.Outlined.Info,
                        title = tr("关于心屿手记"),
                        subtitle = tr("隐私承诺、安全边界与版本信息"),
                        onClick = onAbout,
                        trailing = {
                            Icon(
                                Icons.Outlined.ChevronRight,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    )
                }
            }

            item {
                Text(
                    text = L.t("心屿手记 ${BuildConfig.VERSION_NAME} · 本地优先 · 无账号 · 无云同步", "Xinyu Shouji ${BuildConfig.VERSION_NAME} · Local-first · No account · No cloud sync"),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

    if (showPinSetup) {
        PinSetupDialog(
            hasExistingPin = settings.hasPin,
            onDismiss = { showPinSetup = false },
            onConfirm = { pin ->
                if (settings.hasPin) {
                    viewModel.changePin(pin)
                    viewModel.setAppLockEnabled(true)
                } else {
                    viewModel.enableAppLock(pin)
                }
                showPinSetup = false
            }
        )
    }

    if (showImportConfirm && pendingImport != null) {
        AlertDialog(
            onDismissRequest = {
                showImportConfirm = false
                pendingImport = null
            },
            title = { Text(tr("导入备份？")) },
            text = { Text(tr("当前的所有档案、相处记录和手记都会被导入文件覆盖。建议先导出备份。")) },
            confirmButton = {
                TextButton(
                    onClick = {
                        val uri = pendingImport
                        showImportConfirm = false
                        pendingImport = null
                        if (uri != null) {
                            scope.launch {
                                val result = viewModel.importFrom(uri)
                                Toast.makeText(
                                    context,
                                    if (result.isSuccess) tr("导入完成。") else tr("导入失败，请检查文件格式。"),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                ) {
                    Text(tr("导入并覆盖"))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showImportConfirm = false
                        pendingImport = null
                    }
                ) {
                    Text(tr("取消"))
                }
            }
        )
    }

    if (showDeleteAll) {
        AlertDialog(
            onDismissRequest = { showDeleteAll = false },
            title = { Text(tr("删除全部数据？")) },
            text = { Text(tr("这会永久删除所有档案、相处记录、手记和设置，且无法恢复。")) },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteAll = false
                        viewModel.deleteAllData()
                    }
                ) {
                    Text(tr("永久删除"), color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteAll = false }) {
                    Text(tr("取消"))
                }
            }
        )
    }
}

@Composable
private fun SettingsSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
        )
        SoftCard {
            content()
        }
    }
}

@Composable
private fun SettingsRow(
    icon: ImageVector,
    title: String,
    subtitle: String? = null,
    destructive: Boolean = false,
    onClick: (() -> Unit)? = null,
    trailing: @Composable () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (onClick != null) {
                    Modifier.clickable { onClick() }
                } else {
                    Modifier
                }
            )
            .padding(vertical = 11.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (destructive) {
                MaterialTheme.colorScheme.error
            } else {
                MaterialTheme.colorScheme.primary
            },
            modifier = Modifier.size(20.dp)
        )
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = if (destructive) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.onSurface
                }
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        trailing()
    }
}

@Composable
private fun SettingsDivider() {
    HorizontalDivider(
        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.12f),
        modifier = Modifier.padding(vertical = 2.dp)
    )
}

@Composable
private fun PinSetupDialog(
    hasExistingPin: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var pin by rememberSaveable { mutableStateOf("") }
    var confirm by rememberSaveable { mutableStateOf("") }
    val valid = pin.length == 4 && pin == confirm
    val mismatch = pin.length == 4 && confirm.length == 4 && pin != confirm

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (hasExistingPin) tr("修改 PIN") else tr("设置应用锁")) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = tr("设置一个 4 位 PIN。它用于打开心屿手记时的界面保护；数据本身由 Android Keystore 加密。"),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                OutlinedTextField(
                    value = pin,
                    onValueChange = { value ->
                        if (value.length <= 4 && value.all { it.isDigit() }) pin = value
                    },
                    label = { Text("PIN") },
                    singleLine = true,
                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                        keyboardType = KeyboardType.NumberPassword
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = confirm,
                    onValueChange = { value ->
                        if (value.length <= 4 && value.all { it.isDigit() }) confirm = value
                    },
                    label = { Text(tr("再次输入")) },
                    singleLine = true,
                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                        keyboardType = KeyboardType.NumberPassword
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
                if (mismatch) {
                    Text(
                        text = tr("两次输入不一致。"),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(pin) },
                enabled = valid
            ) {
                Text(tr("保存"))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(tr("取消"))
            }
        }
    )
}
