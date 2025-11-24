package petlink.android.feature_calendar_domain.usecase

interface UpdateCalendarEventUseCase  {
    suspend fun invoke(
        eventId: String,
        title: String,
        date: String,
        theme: String,
        time: String,
        dateForTimestamp: String,
        isNotificationOn: Boolean
    )
}