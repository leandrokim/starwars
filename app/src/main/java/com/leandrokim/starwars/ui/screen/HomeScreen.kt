package com.leandrokim.starwars.ui.screen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
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
import com.leandrokim.starwars.ui.viewModel.HomeViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel, showPersonDetail: (name: String) -> Unit) {
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
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            TitleText()

            InfiniteScrollList(
                items = state.value?.people ?: emptyList(),
                showPersonDetail = showPersonDetail,
                onLoadMore = {
                    viewModel.loadNextPage()
                }
            )
        }
    }
}

@Composable
private fun TitleText() {
    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = Color.Black,
        ),
        border = BorderStroke(1.dp, Color.Yellow),
    ) {
        Text(
            text = "Star Wars Characters",
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

@Composable
private fun InfiniteScrollList(
    items: List<Person>,
    onLoadMore: () -> Unit,
    showPersonDetail: (name: String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        content = {
            itemsIndexed(items, key = { index, _ -> index }) { index, person ->
                if (index == items.size - 1) {
                    onLoadMore()
                }
                PersonCard(person, showPersonDetail)
            }
        },
        state = rememberLazyListState()
    )
}

@Composable
private fun PersonCard(person: Person, showPersonDetail: (name: String) -> Unit) {
    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = Color.Black,
        ),
        border = BorderStroke(1.dp, Color.Yellow),
        modifier = Modifier
            .wrapContentSize(align = Alignment.CenterStart)
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                showPersonDetail(person.name)
            }
    ) {
        Text(
            text = person.name,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                color = Color.Yellow
            ),
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 32.dp)
        )
    }
}