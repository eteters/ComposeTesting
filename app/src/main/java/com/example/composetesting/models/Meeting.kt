package com.example.composetesting.models
import java.util.Date

data class Meeting(
    val id: String,
    val startDate: Date,
    val endDate: Date,
    val meetingShortTitle: String,
    val meetingTitle:String,
    val meetingType: String,
    val company: String,
    val attendee: String? = null,

    val isCompleted: Boolean = Date() > endDate
)

