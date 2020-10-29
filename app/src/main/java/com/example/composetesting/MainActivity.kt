package com.example.composetesting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.example.composetesting.models.InterviewsViewModel
import com.example.composetesting.models.Meeting
import com.example.composetesting.models.formatDateAndTimeRange
import com.example.composetesting.models.getDaysAgo
import com.example.composetesting.ui.ComposeTestingTheme
import com.example.composetesting.ui.gray
import com.example.composetesting.ui.textLightGray
import com.example.composetesting.ui.typography
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.getValue
import com.example.composetesting.models.NavigationViewModel
import com.example.composetesting.models.Screen
import com.example.composetesting.ui.InterviewDetail
import com.example.composetesting.ui.InterviewDetailActivity
import com.example.composetesting.ui.MyInterviewsActivity

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<InterviewsViewModel>()
    val navigationViewModel by viewModels<NavigationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTestingTheme {
                AppContent(viewModel, navigationViewModel)
            }
        }
    }

    override fun onBackPressed() {
        if (!navigationViewModel.onBack()) {
            super.onBackPressed()
            // viewModel.newList( listOf(Meeting("0",
            //     getDaysAgo(7), getDaysAgo(5),
            //     "1 on 1 with Airbnb",
            //     "1 on 1 Quick Screen with Airbnb",
            //     "1 on 1 Quick Screen",
            //     "Airbnb", null )))
        }
    }
}

@Composable
fun AppContent(viewModel: InterviewsViewModel, navigationViewModel: NavigationViewModel) {
    Crossfade(navigationViewModel.currentScreen) { screen ->
        Surface(color = MaterialTheme.colors.background) {
            when (screen) {
                is Screen.My_Interviews -> MyInterviewsActivity(
                    viewModel = viewModel,
                    navigateTo = navigationViewModel::navigateTo
                )
                is Screen.Interview_Detail -> InterviewDetailActivity(
                    meetingId = screen.meetingId,
                    viewModel = viewModel,
                    onBack = navigationViewModel::onBack
                )
            }
        }
    }
}


