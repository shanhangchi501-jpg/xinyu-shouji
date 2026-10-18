package com.innerisle.app

import com.innerisle.app.data.tr

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.innerisle.app.ui.AppViewModel
import com.innerisle.app.ui.AppMark
import com.innerisle.app.ui.LockScreen
import com.innerisle.app.ui.MainApp
import com.innerisle.app.ui.OnboardingScreen
import com.innerisle.app.ui.WarmBackground
import com.innerisle.app.ui.findActivity
import com.innerisle.app.data.L
import com.innerisle.app.data.Language
import com.innerisle.app.data.Prefs
import com.innerisle.app.ui.theme.InnerIsleTheme
import com.innerisle.app.work.NotificationHelper
import java.util.Locale

class MainActivity : ComponentActivity() {

    private val viewModel: AppViewModel by viewModels()

    override fun attachBaseContext(newBase: Context) {
        val language = Prefs(newBase).language
        val locale = if (language == Language.EN) Locale.ENGLISH else Locale.SIMPLIFIED_CHINESE
        val config = Configuration(newBase.resources.configuration)
        config.setLocale(locale)
        super.attachBaseContext(newBase.createConfigurationContext(config))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        L.language = Prefs(this).language
        NotificationHelper.createChannels(this)
        setContent {
            InnerIsleTheme {
                InnerIsleRoot(viewModel)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.onAppForegrounded()
    }

    override fun onStop() {
        super.onStop()
        if (!isChangingConfigurations) {
            viewModel.onAppBackgrounded()
        }
    }
}

@Composable
private fun InnerIsleRoot(viewModel: AppViewModel) {
    val loaded by viewModel.loaded.collectAsStateWithLifecycle()
    val data by viewModel.data.collectAsStateWithLifecycle()
    val settings by viewModel.settings.collectAsStateWithLifecycle()
    val locked by viewModel.locked.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val activity = context.findActivity()

    LaunchedEffect(settings.preventScreenshots) {
        if (settings.preventScreenshots) {
            activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
        } else {
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
        }
    }

    WarmBackground {
        when {
            !loaded -> SplashScreen()
            !settings.onboardingComplete -> OnboardingScreen(
                onComplete = { name, emoji, color ->
                    viewModel.completeOnboarding(name, emoji, color)
                }
            )
            locked -> LockScreen(
                onUnlock = { pin -> viewModel.unlock(pin) },
                onQuickExit = { quickExit(context) }
            )
            else -> MainApp(
                viewModel = viewModel,
                data = data,
                settings = settings,
                onQuickExit = { quickExit(context) }
            )
        }
    }
}

@Composable
private fun SplashScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AppMark(88.dp)
        Spacer(Modifier.height(20.dp))
        Text(
            text = tr("心屿手记"),
            style = MaterialTheme.typography.displaySmall
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = tr("只属于你们的内部空间"),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

fun quickExit(context: Context) {
    val activity = context.findActivity()
    activity?.finishAndRemoveTask()
    runCatching {
        context.startActivity(
            Intent(Intent.ACTION_MAIN)
                .addCategory(Intent.CATEGORY_HOME)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    }
}
