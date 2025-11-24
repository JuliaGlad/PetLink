package petlink.android.feature_calendar_domain.usecase

interface AddEventToHistoryUseCase {
    suspend fun invoke(eventId: String)
}