package com.leandrokim.starwars.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.leandrokim.starwars.R
import com.leandrokim.starwars.ui.theme.StarWarsTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(showHome: () -> Unit) {
    val duration = 1000L

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "Star background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Star Wars logo"
            )
        }
    }

    NavigateToHome(showHome, duration)
}

@Composable
fun NavigateToHome(showHome: () -> Unit, duration: Long) {
    LaunchedEffect(true) {
        delay(duration)
        showHome()
    }
}

@Preview(showBackground = true)
@Composable
fun SplashPreview() {
    StarWarsTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SplashScreen {}
        }
    }
}
