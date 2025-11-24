package petlink.android.feature_calendar_domain.usecase

interface DeleteCalendarEventUseCase {
    suspend fun invoke(id: String)
}