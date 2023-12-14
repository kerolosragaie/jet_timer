package com.kerollosragaie.jettimer.features.main

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {
    companion object {
        const val totalTime = 30 * 1000L
        const val interval = 1000L
    }

    private val _currentTime: MutableStateFlow<Long> = MutableStateFlow(totalTime)
    val currentTime: StateFlow<Long> = _currentTime

    private val _isTimerRunning: MutableStateFlow<Boolean> =
        MutableStateFlow(false)
    val isTimerRunning: StateFlow<Boolean> = _isTimerRunning

    private val timer: CountDownTimer =
        object : CountDownTimer(totalTime, interval) {
            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = millisUntilFinished
            }

            override fun onFinish() {
                _currentTime.value = totalTime
                _isTimerRunning.value = false
            }
        }

    fun startTimer() {
        if (_isTimerRunning.value) {
            resetTimer()
        }
        timer.start()
        _isTimerRunning.value = true
    }

    fun restartTimer() {
        if (_isTimerRunning.value) {
            resetTimer()
        }
    }

    private fun resetTimer() {
        timer.cancel()
        _currentTime.value = totalTime
        _isTimerRunning.value = false
    }

    override fun onCleared() {
        super.onCleared()
        resetTimer()
    }
}