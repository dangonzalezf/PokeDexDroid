package com.example.pokedexdroid.ui.theme.screen

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object SoundPlayerManager {

    @Composable
    fun ReproduceSoundFromUrl(url: String, isPlaying: Boolean, context: Context) {
        val mediaPlayer = remember { MediaPlayer() }

        val audioAttributes = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .build()

        LaunchedEffect(key1 = url) {
            withContext(Dispatchers.IO) {
                mediaPlayer.apply {
                    setAudioAttributes(audioAttributes)
                    setDataSource(context, Uri.parse(url))
                    prepare()
                }

                if (isPlaying) {
                    mediaPlayer.start()
                } else {
                    mediaPlayer.stop()
                    mediaPlayer.release()
                }
            }
        }
    }
}
