package com.example.android_midterm1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NumberGuessingGame()
        }
    }
}

@Composable
fun NumberGuessingGame() {
    var minRange by remember { mutableStateOf(0) }
    var maxRange by remember { mutableStateOf(100) }
    var sliderValue by remember { mutableStateOf((minRange + maxRange) / 2) }
    var targetValue by remember { mutableStateOf(generateRandomTarget()) }
    var score by remember { mutableStateOf(0) }
    var totalScore by remember { mutableStateOf(0) }
    var message by remember { mutableStateOf("Try to guess")}


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Bully's Eye Game", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(100.dp))
        Text("Move the number as close as you can to: $targetValue", fontSize = 16.sp)

        Spacer(modifier = Modifier.height(40.dp))


        Slider(
            value = sliderValue.toFloat(),
            onValueChange = { newValue -> sliderValue = newValue.toInt() },
            valueRange = minRange.toFloat()..maxRange.toFloat()
        )

        Spacer(modifier = Modifier.height(100.dp))

        Button(
            onClick = {
                score = calculateScore(targetValue, sliderValue)
                totalScore += score
                message = showMessage(score)
                targetValue = generateRandomTarget()
                sliderValue = (minRange + maxRange) / 2
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Hit Me!")
        }

        Spacer(modifier = Modifier.height(100.dp))

        Text("Your Score: $totalScore", fontSize = 16.sp)

        Spacer(modifier = Modifier.height(200.dp))

        Text(message, fontSize = 16.sp)

    }
}

fun generateRandomTarget(): Int {
    return Random.nextInt(0, 101)
}


fun calculateScore(targetValue: Int, playerGuess: Int): Int {
    val difference = kotlin.math.abs(targetValue - playerGuess)
    return when {
        difference <= 3 -> 5
        difference <= 8 -> 1
        else -> 0
    }
}


fun showMessage(score: Int): String {
    return when(score) {
        5 -> "Perfect! You scored 5 points."
        1 -> "Nearly there!"
        else -> "Try again to get closer."
    }
}