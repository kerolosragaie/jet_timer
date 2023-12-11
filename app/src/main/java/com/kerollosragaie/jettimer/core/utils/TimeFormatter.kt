package com.kerollosragaie.jettimer.core.utils

import java.util.concurrent.TimeUnit

fun getFormattedTime(millis:Long):String{
    val second = TimeUnit.MILLISECONDS.toSeconds(millis)
    return if(second.toString().length==1)
        "0$second"
    else second.toString()
}