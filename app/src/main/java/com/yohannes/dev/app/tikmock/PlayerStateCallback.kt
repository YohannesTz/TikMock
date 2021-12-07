package com.yohannes.dev.app.tikmock

import com.google.android.exoplayer2.Player

interface PlayerStateCallback {
    fun onVideoDurationRetrived(duration: Long, player: Player)
    fun onVideoBuffering(player: Player)
    fun onStartedPlaying(player: Player)
    fun onFinishedPlaying(player: Player)
}