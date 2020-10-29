package com.example.composetesting.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.ui.tooling.preview.Preview
import com.example.composetesting.R
import com.example.composetesting.models.InterviewsViewModel
import com.example.composetesting.models.Meeting
import com.example.composetesting.models.Screen
import com.example.composetesting.models.formatDateAndTimeRange
import com.example.composetesting.models.getDaysAgo

@Composable
fun MyInterviewsActivity(viewModel: InterviewsViewModel, navController: NavController) {

    Column {
        TopAppBar(
            title = { Text("Interviews") },
            elevation = 3.dp
        )
        MyInterviewsActivityList(viewModel = viewModel, navController = navController)
    }
}

sealed class MyInterviewItem
data class MyHeader(val title: String): MyInterviewItem()
data class MyCard(val meeting: Meeting): MyInterviewItem()

@Composable
fun MyInterviewsActivityList(viewModel: InterviewsViewModel, navController: NavController) {
    val items: List<Meeting> by viewModel.futureMeetings.observeAsState(listOf())
    val pastItems: List<Meeting> by viewModel.pastMeetings.observeAsState(listOf())

    val presentCards = items.map { MyCard(it) }
    val headerItem = MyHeader("Past Sessions")
    val pastCards = pastItems.map { MyCard(it) }
    val listItems: List<MyInterviewItem> = presentCards + headerItem + pastCards
    MyInterviewsList(
        listItems = listItems,
        navController = navController
    )
}

@Composable
fun MyInterviewsList(listItems: List<MyInterviewItem>, navController: NavController) {
    Spacer(modifier = Modifier.preferredHeight(16.dp))
    LazyColumnFor(listItems) { item ->
        when (item) {
            is MyCard -> {
                MyInterviewsCell(item.meeting, navController = navController)
                // Takes care of the space between items
                Spacer(modifier = Modifier.preferredHeight(16.dp))
            }
            is MyHeader -> {
                Text(
                    text = item.title,
                    style = typography.h5,
                    modifier = Modifier.padding(24.dp, 40.dp)
                )
            }
        }
    }
}

@Composable
fun MyInterviewsCell(meeting: Meeting, navController: NavController) {
    val image = imageResource(id = R.drawable.avatar)
    val elementSpacing = Modifier.padding(0.dp, 4.dp)
    val imageModifier = Modifier
        .preferredHeight(40.dp)
        .preferredWidth(40.dp)
        .fillMaxWidth()
        .clip(shape = RoundedCornerShape(4.dp))
    val cellModifier = Modifier
        .padding(24.dp, 0.dp, 24.dp, 0.dp)
        .fillMaxWidth()
        .clickable(onClick = { navController.navigate("detail/${meeting.id}".toUri()) })
    val columnPadding = Modifier.padding(24.dp, 12.dp, 24.dp, 24.dp)

    Surface(
        modifier = cellModifier,
        color = gray,
        elevation = 2.dp
    ) {

        Column(columnPadding) {
            Text(
                text = formatDateAndTimeRange(meeting.startDate, meeting.endDate),
                modifier = elementSpacing,
                style = TextStyle(color = textLightGray)
            )
            Text(
                text = meeting.meetingShortTitle,
                modifier = elementSpacing,
                style = typography.h6
            )
            Row {
                // Would use Coil, maybe, for image loading. Also see yt video about using own async thing
                Image(
                    image,
                    modifier = imageModifier + elementSpacing + Modifier.clickable(onClick = {}),
                    contentScale = ContentScale.Crop
                )
                Column(Modifier
                    .align(Alignment.CenterVertically)
                    .padding(8.dp, 0.dp)
                ) {
                    Text(
                        text = meeting.company,
                        style = TextStyle(fontWeight = FontWeight.Bold)
                    )
                    meeting.attendee?.let {
                        Text(text = it)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    val navController = rememberNavController()
    val meeting = Meeting("0",
        getDaysAgo(7), getDaysAgo(5),
        "1 on 1 with Airbnb",
        "1 on 1 Quick Screen with Airbnb",
        "1 on 1 Quick Screen","Airbnb", null )

    MyInterviewsCell(meeting, navController) // ???
}