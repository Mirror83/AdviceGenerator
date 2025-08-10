package com.mirror83.advicegenerator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
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

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            RandomAdviceCard(
                advice = adviceGeneratorUiState.advice,
                modifier = Modifier
                    .padding(16.dp)
                    .widthIn(100.dp, 400.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            NewRandomAdviceButton(getNextAdvice = { adviceGeneratorViewModel.getNextAdvice() })
        }
    }
}

@Composable
fun NewRandomAdviceButton(getNextAdvice: () -> Unit) {
    Button(onClick = { getNextAdvice() }) {
        Text("New Advice")
    }
}

@Composable
fun RandomAdviceCard(advice: String, modifier: Modifier = Modifier) {
    Card {
        Text(
            text = advice,
            modifier = modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RandomAdviceCardPreview() {
    AdviceGeneratorTheme {
        RandomAdviceCard("Don't take it personally.")
    }
}