package com.kerollosragaie.jettimer.features.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.kerollosragaie.jettimer.features.main.components.TimerItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
) {
    val systemUiController = rememberSystemUiController()
    val currentTime by mainViewModel.currentTime.collectAsState()
    val isTimerRunning by mainViewModel.isTimerRunning.collectAsState()

    LaunchedEffect(systemUiController) {
        systemUiController.isStatusBarVisible = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onSecondary
                ),
                title = {
                    Text(
                        text = "Timer",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                    )
                },
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            TimerItem(
                currentTimer = currentTime,
                isRunning = isTimerRunning,
                onStart = { mainViewModel.startTimer() },
                onReStart = { mainViewModel.restartTimer() },
            )
        }
    }


}


@Preview(showBackground = true)
@Composable
fun PrevMainScreen() {
    MainScreen(MainViewModel())
}