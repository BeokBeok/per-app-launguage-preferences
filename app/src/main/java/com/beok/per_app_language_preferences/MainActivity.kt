package com.beok.per_app_language_preferences

import android.app.LocaleManager
import android.os.Bundle
import android.os.LocaleList
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.beok.per_app_language_preferences.ui.theme.PerapplanguagepreferencesTheme
import java.util.Locale

@RequiresApi(33)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PerAppLanguagePreferencesTheme()
        }
    }

    @Composable
    private fun PerAppLanguagePreferencesTheme() {
        PerapplanguagepreferencesTheme {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {
                PerAppLanguageDropBoxMenu()
            }
        }
    }

    @Composable
    private fun PerAppLanguageDropBoxMenu() {
        var isDropDownMenuExpanded by remember { mutableStateOf(false) }

        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.wrapContentSize()) {
                Button(
                    onClick = { isDropDownMenuExpanded = true },
                    modifier = Modifier.wrapContentSize()
                ) {
                    Text(text = currentLanguage())
                }

                DropdownMenu(
                    expanded = isDropDownMenuExpanded,
                    onDismissRequest = { isDropDownMenuExpanded = false },
                ) {
                    Language.values().forEach {
                        DropdownMenuItem(
                            onClick = {
                                updateAppLocale(Locale.forLanguageTag(it.value))
                                isDropDownMenuExpanded = false
                            }
                        ) {
                            Text(text = it.value)
                        }
                    }
                }
            }
            Text(text = stringResource(id = R.string.hello_android))
        }
    }

    private fun updateAppLocale(vararg locales: Locale) {
        val localeManager = getSystemService(LocaleManager::class.java)
        localeManager.applicationLocales = LocaleList(*locales)
    }

    private fun currentLanguage(): String {
        val localeManager = getSystemService(LocaleManager::class.java)
        val appLocales = localeManager.applicationLocales
        return if (appLocales.isEmpty) "" else appLocales[0].displayName
    }
}
