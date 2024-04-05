package com.leandrokim.starwars.ui.screen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leandrokim.people.domain.model.Person
import com.leandrokim.starwars.R
import com.leandrokim.starwars.ui.viewModel.DetailsViewModel

@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel,
    personName: String,
    onBackPressed: () -> Boolean
) {
    val context = LocalContext.current
    val state = viewModel.state.collectAsState()

    LaunchedEffect(key1 = state) {
        if (state.value?.error == true)
            Toast.makeText(context, "Please try again in a few minutes", Toast.LENGTH_LONG).show()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "Star background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )

        Column(
            modifier = Modifier
                .matchParentSize()
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            TitleText(personName)

            if (state.value?.person != null)
                PersonDetail(state.value?.person!!)

            Spacer(modifier = Modifier.weight(1.0f))

            BackButton(onBackPressed)
        }
    }
}

@Composable
private fun PersonDetail(person: Person) {
    DetailRow("Height:", person.height)
    DetailRow("Mass:", person.mass)
    DetailRow("Hair Color:", person.hairColor)
    DetailRow("Skin Color:", person.skinColor)
    DetailRow("Eye Color:", person.eyeColor)
    DetailRow("Birth Year:", person.birthYear)
    DetailRow("Gender:", person.gender)
}

@Composable
private fun DetailRow(label: String, description: String) {
    Row {
        Text(
            text = label,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                color = Color.Yellow
            ),
            modifier = Modifier
                .width(150.dp)
                .padding(vertical = 16.dp, horizontal = 32.dp)
        )

        Text(
            text = description,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                color = Color.Yellow
            ),
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
private fun BackButton(onBackPressed: () -> Boolean) {
    OutlinedButton(
        modifier = Modifier
            .padding(bottom = 16.dp),
        border = BorderStroke(1.dp, Color.Yellow),
        onClick = { onBackPressed() }) {
        Text(
            text = "Go Back",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color.Yellow
            ),
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
private fun TitleText(name: String) {
    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = Color.Black,
        ),
        border = BorderStroke(1.dp, Color.Yellow),
    ) {
        Text(
            text = name,
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color.Yellow
            ),
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth()
        )
    }
}