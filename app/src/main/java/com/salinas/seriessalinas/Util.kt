package com.salinas.seriessalinas

import android.app.Activity

object Util {

    fun isActivityAlive(activity: Activity): Boolean {
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            !activity.isFinishing && !activity.isDestroyed
        } else {
            !activity.isFinishing
        }
    }
}