package ru.malygin.anytoany.data.dateTime

import kotlinx.datetime.*
import kotlinx.datetime.format.char

fun getTodayDate():String{
    val format = LocalDate.Format {
        dayOfMonth()
        char('.')
        monthNumber()
        char('.')
        year()
    }

    val now = Clock.System.now()
    val zone = TimeZone.currentSystemDefault()

    return now.toLocalDateTime(zone).date.format(format)
}