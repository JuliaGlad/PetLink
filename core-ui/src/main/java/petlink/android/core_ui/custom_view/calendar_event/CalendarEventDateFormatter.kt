package petlink.android.core_ui.custom_view.calendar_event

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun formatCalendarEventDate(date: String): String {
    val parsedDate = DATE_PATTERNS.firstNotNullOfOrNull { pattern ->
        runCatching { SimpleDateFormat(pattern, Locale.US).parse(date) }.getOrNull()
    } ?: return date
    val calendar = Calendar.getInstance().apply { time = parsedDate }
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()).orEmpty()
    val year = calendar.get(Calendar.YEAR)
    val weekDay = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
        ?.replaceFirstChar { char -> if (char.isLowerCase()) char.titlecase(Locale.getDefault()) else char.toString() }
        .orEmpty()
    return "$day $month $year, $weekDay"
}

fun formatCalendarDateInput(raw: String): String {
    val trimmed = raw.trim()
    if (trimmed.isEmpty()) return trimmed
    val parts = trimmed.split(*DATE_SEPARATORS).filter { it.isNotBlank() }
    if (parts.size != DATE_PARTS_COUNT) return trimmed
    val (year, month, day) = resolveDateParts(parts) ?: return trimmed
    if (!isValidDate(year, month, day)) return trimmed
    return String.format(Locale.US, DATE_OUTPUT_FORMAT, year, month, day)
}

fun formatCalendarTimeInput(raw: String): String {
    val trimmed = raw.trim()
    if (trimmed.isEmpty()) return trimmed
    val parts = trimmed.split(*TIME_SEPARATORS).filter { it.isNotBlank() }
    val (hour, minute) = when (parts.size) {
        TIME_PARTS_COUNT -> parts[0].toIntOrNull() to parts[1].toIntOrNull()
        1 -> parseCompactTime(parts[0])
        else -> return trimmed
    }
    if (hour == null || minute == null || !isValidTime(hour, minute)) return trimmed
    return String.format(Locale.US, TIME_OUTPUT_FORMAT, hour, minute)
}

private fun resolveDateParts(parts: List<String>): Triple<Int, Int, Int>? {
    val first = parts[0].toIntOrNull() ?: return null
    val second = parts[1].toIntOrNull() ?: return null
    val third = parts[2].toIntOrNull() ?: return null
    return if (parts[0].length == YEAR_LENGTH || first > MAX_DAY) {
        Triple(normalizeYear(first), second, third)
    } else {
        Triple(normalizeYear(third), second, first)
    }
}

private fun normalizeYear(year: Int): Int =
    if (year < SHORT_YEAR_THRESHOLD) year + CURRENT_CENTURY else year

private fun parseCompactTime(value: String): Pair<Int?, Int?> {
    val digits = value.filter { it.isDigit() }
    return when (digits.length) {
        COMPACT_TIME_SHORT -> digits.substring(0, 1).toIntOrNull() to digits.substring(1).toIntOrNull()
        COMPACT_TIME_LONG -> digits.substring(0, 2).toIntOrNull() to digits.substring(2).toIntOrNull()
        else -> null to null
    }
}

private fun isValidDate(year: Int, month: Int, day: Int): Boolean {
    if (year !in MIN_YEAR..MAX_YEAR || month !in 1..MAX_MONTH || day !in 1..MAX_DAY) return false
    return runCatching {
        Calendar.getInstance().apply {
            isLenient = false
            set(year, month - 1, day)
            time
        }
        true
    }.getOrDefault(false)
}

private fun isValidTime(hour: Int, minute: Int): Boolean =
    hour in 0..MAX_HOUR && minute in 0..MAX_MINUTE

private val DATE_PATTERNS = listOf("yyyy-MM-dd", "yyyy-MM-dd HH:mm")
private val DATE_SEPARATORS = charArrayOf('.', ',', '/', '-', ' ')
private val TIME_SEPARATORS = charArrayOf(':', '.', ',', '-', ' ')
private const val DATE_OUTPUT_FORMAT = "%d-%02d-%02d"
private const val TIME_OUTPUT_FORMAT = "%02d:%02d"
private const val DATE_PARTS_COUNT = 3
private const val TIME_PARTS_COUNT = 2
private const val YEAR_LENGTH = 4
private const val MAX_DAY = 31
private const val MAX_MONTH = 12
private const val MIN_YEAR = 1900
private const val MAX_YEAR = 2100
private const val SHORT_YEAR_THRESHOLD = 100
private const val CURRENT_CENTURY = 2000
private const val COMPACT_TIME_SHORT = 3
private const val COMPACT_TIME_LONG = 4
private const val MAX_HOUR = 23
private const val MAX_MINUTE = 59
