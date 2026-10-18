package com.innerisle.app.work

import com.innerisle.app.data.tr

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.innerisle.app.MainActivity
import com.innerisle.app.R
import com.innerisle.app.data.L
import com.innerisle.app.data.Prefs
import java.util.Calendar
import java.util.concurrent.TimeUnit

object NotificationHelper {

    const val CHANNEL_REMINDERS = "reminders"
    const val CHANNEL_SESSIONS = "sessions"

    fun createChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = context.getSystemService(NotificationManager::class.java)
            val reminders = NotificationChannel(
                CHANNEL_REMINDERS,
                context.getString(R.string.notification_channel_reminders),
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = context.getString(R.string.notification_channel_description)
                setShowBadge(false)
            }
            val sessions = NotificationChannel(
                CHANNEL_SESSIONS,
                context.getString(R.string.notification_channel_sessions),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = context.getString(R.string.notification_channel_description)
                setShowBadge(false)
            }
            manager.createNotificationChannel(reminders)
            manager.createNotificationChannel(sessions)
        }
    }

    fun notify(context: Context, id: Int, channel: String, title: String, text: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, channel)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(text)
            .setStyle(NotificationCompat.BigTextStyle().bigText(text))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        runCatching {
            NotificationManagerCompat.from(context).notify(id, notification)
        }
    }
}

class ReminderWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {
        val prefs = Prefs(applicationContext)
        L.language = prefs.language
        if (!prefs.reminderEnabled) return Result.success()
        NotificationHelper.createChannels(applicationContext)
        val title = if (prefs.stealthNotifications) tr("提醒") else tr("心屿手记")
        val text = if (prefs.stealthNotifications) {
            tr("该休息一下了。")
        } else {
            tr("给 TA 留一点时间。")
        }
        NotificationHelper.notify(
            applicationContext,
            1001,
            NotificationHelper.CHANNEL_REMINDERS,
            title,
            text
        )
        return Result.success()
    }
}

class SessionTimerWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {
        val prefs = Prefs(applicationContext)
        L.language = prefs.language
        NotificationHelper.createChannels(applicationContext)
        val title = if (prefs.stealthNotifications) tr("提醒") else tr("心屿手记")
        val text = if (prefs.stealthNotifications) {
            tr("时间到了。")
        } else {
            tr("这次的相处时间到了，记得回来看看。")
        }
        NotificationHelper.notify(
            applicationContext,
            1002,
            NotificationHelper.CHANNEL_SESSIONS,
            title,
            text
        )
        return Result.success()
    }
}

object ReminderScheduler {

    private const val DAILY_WORK = "daily_reminder"
    private const val SESSION_WORK = "session_timer"

    fun scheduleDaily(context: Context, hour: Int, minute: Int) {
        val now = Calendar.getInstance()
        val next = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour.coerceIn(0, 23))
            set(Calendar.MINUTE, minute.coerceIn(0, 59))
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            if (before(now)) add(Calendar.DAY_OF_YEAR, 1)
        }
        val delay = (next.timeInMillis - now.timeInMillis).coerceAtLeast(0L)

        val request = PeriodicWorkRequestBuilder<ReminderWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .addTag(DAILY_WORK)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            DAILY_WORK,
            ExistingPeriodicWorkPolicy.UPDATE,
            request
        )
    }

    fun cancelDaily(context: Context) {
        WorkManager.getInstance(context).cancelUniqueWork(DAILY_WORK)
    }

    fun scheduleSessionEnd(context: Context, minutes: Int) {
        if (minutes <= 0) return
        val request = OneTimeWorkRequestBuilder<SessionTimerWorker>()
            .setInitialDelay(minutes.toLong(), TimeUnit.MINUTES)
            .addTag(SESSION_WORK)
            .build()
        WorkManager.getInstance(context).enqueueUniqueWork(
            SESSION_WORK,
            ExistingWorkPolicy.REPLACE,
            request
        )
    }

    fun cancelSessionEnd(context: Context) {
        WorkManager.getInstance(context).cancelUniqueWork(SESSION_WORK)
    }

    fun cancelAll(context: Context) {
        WorkManager.getInstance(context).cancelUniqueWork(DAILY_WORK)
        WorkManager.getInstance(context).cancelUniqueWork(SESSION_WORK)
    }
}
