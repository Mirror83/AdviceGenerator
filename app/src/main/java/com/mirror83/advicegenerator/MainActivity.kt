package com.mirror83.advicegenerator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mirror83.advicegenerator.network.Advice
import com.mirror83.advicegenerator.ui.state.AdviceGeneratorUiState
import com.mirror83.advicegenerator.ui.state.AdviceGeneratorViewModel
import com.mirror83.advicegenerator.ui.theme.AdviceGeneratorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AdviceGeneratorTheme {
                AdviceGeneratorApp()
            }
        }
    }
}

@Composable
fun AdviceGeneratorApp(adviceGeneratorViewModel: AdviceGeneratorViewModel = viewModel()) {
    val adviceGeneratorUiState = adviceGeneratorViewModel.uiState.collectAsState().value
    val isLoading = adviceGeneratorUiState is AdviceGeneratorUiState.Loading

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            when (adviceGeneratorUiState) {
                is AdviceGeneratorUiState.Error ->
                    Text(
                        "Unable to get advice. Check your connection and try again.",
                        modifier = Modifier.align(
                            Alignment.CenterHorizontally
                        )
                    )

                is AdviceGeneratorUiState.Loading -> Text("Loading")
                is AdviceGeneratorUiState.Success -> RandomAdviceCard(
                    advice = adviceGeneratorUiState.advice,
                    modifier = Modifier
                        .widthIn(100.dp, 400.dp)
                        .heightIn(100.dp, 200.dp)
                        .padding(16.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            NewRandomAdviceButton(
                getNextAdvice = { adviceGeneratorViewModel.getNextAdvice() },
                isLoading = isLoading
            )
        }
    }
}

@Composable
fun NewRandomAdviceButton(getNextAdvice: () -> Unit, isLoading: Boolean) {
    val infiniteTransition = rememberInfiniteTransition(label = "icon rotation")
    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        label = "rotation angle",
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = EaseInOutCubic)
        )
    )

    IconButton(
        onClick = { getNextAdvice() },
        enabled = !isLoading,
        modifier = if (isLoading) Modifier.rotate(rotationAngle) else Modifier
    ) {
        Icon(
            painter = painterResource(R.drawable.icon_dice),
            contentDescription = "New Advice",
        )
    }
}

@Composable
fun RandomAdviceCard(advice: Advice, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize().padding(8.dp)
        ) {
            Text(text = "Advice #${advice.id}", style = MaterialTheme.typography.titleSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = advice.content, textAlign = TextAlign.Center)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RandomAdviceCardPreview() {
    AdviceGeneratorTheme {
        RandomAdviceCard(Advice(1, "Don't take it personally."))
    }
}