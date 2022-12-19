package hotelbooking

import java.util.*

internal fun getDatesBetweenTwoDates(startDate: Date, endDate: Date): ArrayList<Date> {
    val datesInRange = ArrayList<Date>()
    val calendar: Calendar = getCalendarWithoutTime(startDate)
    val endCalendar: Calendar =getCalendarWithoutTime(endDate)
    while (calendar.before(endCalendar)) {
        val result = calendar.time
        datesInRange.add(result)
        calendar.add(Calendar.DATE, 1)
    }
    return datesInRange
}

private fun getCalendarWithoutTime(date: Date): Calendar {
    val calendar: Calendar = Calendar.getInstance()
    calendar.time = date
    calendar[Calendar.HOUR] = 0
    calendar[Calendar.HOUR_OF_DAY] = 0
    calendar[Calendar.MINUTE] = 0
    calendar[Calendar.SECOND] = 0
    calendar[Calendar.MILLISECOND] = 0
    return calendar
}

internal fun modifyString(str: String): String {
    var newStr = StringBuilder()
    for (i in str.indices) {
        if (str[i] == ' ') {
            continue
        }
        newStr.append(str[i])
    }
    newStr = StringBuilder(newStr.toString().uppercase(Locale.getDefault()))
    return newStr.toString()
}