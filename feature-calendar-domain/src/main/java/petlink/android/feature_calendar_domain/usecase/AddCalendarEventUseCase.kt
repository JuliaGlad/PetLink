package petlink.android.feature_calendar_domain.usecase

interface AddCalendarEventUseCase {
    suspend fun invoke(
        title: String,
        date: String,
        theme: String,
        time: String,
        dateForTimestamp: String,
        isNotificationOn: Boolean
    ): String
}