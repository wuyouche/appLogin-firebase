package com.example.new1.android.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.new1.android.model.Bubble
import kotlin.random.Random

class BubbleViewModel : ViewModel() {
    var bubbles by mutableStateOf<List<Bubble>>(emptyList())
        private set

    init {
        generateBubbles()
    }

    private fun generateBubbles() {
        bubbles = List(20) {
            Bubble(
                x = Random.nextFloat() * 1000,
                y = Random.nextFloat() * 2000,
                radius = Random.nextFloat() * 20 + 10,
                speed = Random.nextFloat() * 2 + 1,
                amplitude = Random.nextFloat() * 20 + 10,
                phase = Random.nextFloat() * 2 * Math.PI.toFloat()
            )
        }
    }
}
