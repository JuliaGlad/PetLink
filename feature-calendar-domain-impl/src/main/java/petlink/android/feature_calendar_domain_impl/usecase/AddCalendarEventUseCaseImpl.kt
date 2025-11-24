package petlink.android.feature_calendar_domain_impl.usecase

import petlink.android.feature_calendar_data.repository.CalendarRepository
import petlink.android.feature_calendar_domain.usecase.AddCalendarEventUseCase
import javax.inject.Inject

class AddCalendarEventUseCaseImpl @Inject constructor(
    private val repository: CalendarRepository
): AddCalendarEventUseCase {
    override suspend fun invoke(
        title: String,
        date: String,
        theme: String,
        time: String,
        dateForTimestamp: String,
        isNotificationOn: Boolean
    ): String = repository.addEvent(
        title = title,
        date = date,
        theme = theme,
        time = time,
        dateForTimestamp = dateForTimestamp,
        isNotificationOn = isNotificationOn
    )
}