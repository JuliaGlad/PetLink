package petlink.android.feature_calendar_domain_impl.usecase

import petlink.android.feature_calendar_data.repository.CalendarRepository
import petlink.android.feature_calendar_domain.usecase.DeleteCalendarEventUseCase
import javax.inject.Inject

class DeleteCalendarEventUseCaseImpl @Inject constructor(
    private val repository: CalendarRepository
): DeleteCalendarEventUseCase {
    override suspend fun invoke(id: String){
        repository.deleteEvent(id = id)
    }
}