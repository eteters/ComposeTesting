package com.example.composetesting.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InterviewsViewModel: ViewModel() {
    private var _futureMeetings = MutableLiveData(listOf<Meeting>())
    val futureMeetings: LiveData<List<Meeting>> = _futureMeetings

    private var _pastMeetings = MutableLiveData(listOf<Meeting>())
    val pastMeetings: LiveData<List<Meeting>> = _pastMeetings

    // val allMeetings: List<Meeting>
    // get() = futureMeetings.value + pastMeetings.value

    init {
        // If debug or something
        val exampleMeeting = Meeting("0",
            getDaysAgo(7), getDaysAgo(7),
            "1 on 1 with Airbnb",
            "1 on 1 Quick Screen with Airbnb", "1 on 1 Quick Screen",
            "Airbnb", null )

        val futureMeetings = List(2){index -> exampleMeeting.copy(attendee = if (Math.random() > 0.5) "Mario" else null )}
        _futureMeetings.value = futureMeetings

        val pastMeetings = List(5){index -> exampleMeeting.copy(attendee = if (Math.random() > 0.5) "Jonathon Peabody" else null )}
        _pastMeetings.value = pastMeetings
    }

    fun newList(meetings: List<Meeting>) {
        _futureMeetings.value = meetings
    }
}