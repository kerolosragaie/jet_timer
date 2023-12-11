package com.kerollosragaie.jettimer.features.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.kerollosragaie.jettimer.features.main.components.TimerItem

@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
) {
    val currentTime by mainViewModel.currentTime.collectAsState()
    val isTimerRunning by mainViewModel.isTimerRunning.collectAsState()

    TimerItem(
        currentTimer = currentTime,
        isRunning = isTimerRunning,
        onStart = { mainViewModel.startTimer() },
        onReStart = { mainViewModel.restartTimer() },
    )
}


@Preview(showBackground = true)
@Composable
fun PrevMainScreen() {
    MainScreen(MainViewModel())
}