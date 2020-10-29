package com.example.composetesting

import android.os.Bundle
import android.provider.ContactsContract
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.setContent
import com.example.composetesting.models.InterviewsViewModel
import com.example.composetesting.ui.ComposeTestingTheme
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.input.key.Key.Companion.Home
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.example.composetesting.models.Screen
import com.example.composetesting.ui.InterviewDetailActivity
import com.example.composetesting.ui.MyInterviewsActivity

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<InterviewsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeTestingTheme {
                AppContent(viewModel)
            }
        }
    }
    //   UHHHH
    override fun onBackPressed() {
        // navController.navigateUp() // TODO do they did this for me?
        // if (!navigationViewModel.onBack()) {
        //     super.onBackPressed()
        // }
    }
}

@Composable
fun AppContent(viewModel: InterviewsViewModel) {
    val navController = rememberNavController()
    Surface(color = MaterialTheme.colors.background) {
        NavHost(navController, startDestination = Screen.MyInterviews.route) {
            composable(Screen.MyInterviews.route) {
                MyInterviewsActivity(
                    viewModel = viewModel,
                    navController = navController
                )
            }
            composable(
                Screen.InterviewDetail.route,
                arguments = listOf(navArgument("meetingId") { type = NavType.StringType }),
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getString("meetingId")
                    ?: return@composable // TODO return Okay?
                InterviewDetailActivity(meetingId = id, viewModel = viewModel, navController)
            }
        }
    }
}


