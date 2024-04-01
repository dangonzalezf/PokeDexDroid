package com.example.pokedexdroid.ui.theme.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class LoadingScreen {
    @Composable
    fun LoadingIndicator(modifier: Modifier) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Red), contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = modifier
                    .padding(22.dp)
                    .size(222.dp), color = Color.White, strokeWidth = 16.dp
            )
        }
    }
}
