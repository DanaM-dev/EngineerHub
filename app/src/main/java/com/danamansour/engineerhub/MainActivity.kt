package com.danamansour.engineerhub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.danamansour.engineerhub.ui.theme.EngineerHubTheme
import com.danamansour.engineerhub.ui.theme.ThemeSetting

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var currentTheme by remember { mutableStateOf(ThemeSetting.SYSTEM) }


            val useDarkTheme = when (currentTheme) {
                ThemeSetting.SYSTEM -> isSystemInDarkTheme()
                ThemeSetting.LIGHT -> false
                ThemeSetting.DARK -> true
            }


            EngineerHubTheme(darkTheme = useDarkTheme) {
                EngineerHubApp(currentTheme = currentTheme,
                    onThemeChange = { newTheme -> currentTheme = newTheme })
            }
        }
    }
}