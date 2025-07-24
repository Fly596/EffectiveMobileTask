package com.example.effectivemobiletask.data

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

data class Course (
    val id: Int,
    val title: String,
    val text: String,
    val price: String,
    val rate: String,
    val startDate: LocalDate,
    val hasLike: Boolean,
    val publishDate: LocalDate,
){
    val formattedDate: String
        //get() = startDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy"))
        get() = startDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
}