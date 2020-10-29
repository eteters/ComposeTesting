package com.example.composetesting.models
//
import android.os.Bundle
import androidx.annotation.MainThread
import androidx.annotation.StringRes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.os.bundleOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.composetesting.R

/**
 * Screen names (used for serialization)
 */
private enum class ScreenName {
    HOME, DETAIL;

    val routeName: String
        get() = when(this) {
            HOME -> "home"
            DETAIL -> "detail/{meetingId}"
        }

    val title: String
        get() = when(this) {
            HOME -> "Interviews"
            DETAIL -> "Meeting Details"
        }
}

/**
 * Class defining the screens we have in the app: home, article details and interests
 */
// title should be @StringRes val resourceId: Int
sealed class Screen(val route: String, val title: String ) {
    object MyInterviews : Screen(ScreenName.HOME.routeName, ScreenName.HOME.title)
    object InterviewDetail : Screen(ScreenName.DETAIL.routeName, ScreenName.DETAIL.title) // TODO (val meetingId: String)
}

// sealed class Screen(val route: String, @StringRes val resourceId: Int) {
//     object Profile : Screen("profile", R.string.profile)
//     object FriendsList : Screen("friendslist", R.string.friends_list)
// }
//
