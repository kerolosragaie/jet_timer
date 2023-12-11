package com.kerollosragaie.jettimer.features.main.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kerollosragaie.jettimer.R
import com.kerollosragaie.jettimer.core.utils.getFormattedTime

@Composable
fun TimerItem(
    currentTimer: Long,
    isRunning: Boolean,
    onStart: () -> Unit,
    onReStart: () -> Unit,
) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Button(onClick = onStart) {
                Text(text = stringResource(id = R.string.start))
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = onReStart) {
                Text(text = stringResource(id = R.string.restart))
            }
        }

        Text(text = getFormattedTime(currentTimer))
    }
}

@Preview(showBackground = true)
@Composable
fun PrevTimer() {
    TimerItem(
        currentTimer = 2000L,
        isRunning = false,
        onStart = {},
        onReStart = {},
    )
}