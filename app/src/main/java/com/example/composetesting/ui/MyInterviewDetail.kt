package com.example.composetesting.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.example.composetesting.R
import com.example.composetesting.models.InterviewsViewModel
import com.example.composetesting.models.Meeting
import com.example.composetesting.models.formatDateAndTimeRange
import com.example.composetesting.models.formatTimeRange
import com.example.composetesting.models.getDaysAgo

@Composable
fun InterviewDetailActivity(meetingId: String, viewModel: InterviewsViewModel, onBack: () -> Unit) {
    val meeting = viewModel.futureMeetings.value?.firstOrNull{it.id == meetingId} ?: return
    InterviewDetail(meeting = meeting, onBack = onBack)
}

@Composable
fun InterviewDetail(meeting: Meeting, onBack: () -> Unit) {
    val Ximage = imageResource(id = R.drawable.avatar)
    val imageModifier = Modifier
        .preferredHeight(16.dp)
        .preferredWidth(16.dp)
        .padding(10.dp)
        .clickable(onClick = onBack)
    Surface(color = MaterialTheme.colors.background) {
        Column(Modifier.padding(24.dp)) {
            Image(
                asset = Ximage,
                modifier = imageModifier
            )
            Text(
                text = "Registered",
                modifier = Modifier.background(Color.Black), color = Color.White
            )
            Text(
                text = meeting.meetingType + "." + formatTimeRange(
                    meeting.startDate,
                    meeting.endDate
                ),
                color = Color.Gray
            )
            Text(
                text = meeting.meetingTitle,
                style = MaterialTheme.typography.h4
            )
            InterviewCancelView(
                meeting.isCompleted,
                formatDateAndTimeRange(meeting.startDate, meeting.endDate)
            )
        }
    }
}

@Composable
fun InterviewCancelView(isComplete: Boolean, rangeString: String) {
    Surface(color = Color.LightGray, shape = RoundedCornerShape(5.dp), elevation = 1.dp) {
        Row {
            val greenBarModifier = Modifier.preferredWidth(8.dp)
            Surface(modifier = greenBarModifier, color = Color(0xFF026773), elevation = 2.dp) {}
            Column {
                Text(text = if (isComplete) "Session Completed" else "Session Upcoming")
                Text(text = "Registered From $rangeString")
            }
        }
    }
}


@Preview
@Composable
fun DetailPreview() {
    val meeting = Meeting("0",
        getDaysAgo(7), getDaysAgo(5),
        "1 on 1 with Airbnb",
        "1 on 1 Quick Screen with Airbnb",
        "1 on 1 Quick Screen",
        "Airbnb", null )

    InterviewDetail(meeting = meeting) {}
}

