package petlink.android.feature_calendar_domain_impl.usecase

import petlink.android.feature_calendar_data.repository.CalendarRepository
import petlink.android.feature_calendar_domain.usecase.AddEventToHistoryUseCase
import javax.inject.Inject

class AddEventToHistoryUseCaseImpl @Inject constructor(
    private val calendarRepository: CalendarRepository
): AddEventToHistoryUseCase {
    override suspend fun invoke(eventId: String){
        calendarRepository.addEventToHistory(eventId)
    }
}