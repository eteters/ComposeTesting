package com.example.composetesting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.setContent
import com.example.composetesting.models.InterviewsViewModel
import com.example.composetesting.ui.ComposeTestingTheme
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.material.MaterialTheme
import com.example.composetesting.models.NavigationViewModel
import com.example.composetesting.models.Screen
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


