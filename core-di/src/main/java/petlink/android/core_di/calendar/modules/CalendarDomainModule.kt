package petlink.android.core_di.calendar.modules

import dagger.Binds
import dagger.Module
import dagger.Reusable
import petlink.android.core_di.calendar.component.CalendarScope
import petlink.android.feature_calendar_domain.usecase.AddCalendarEventUseCase
import petlink.android.feature_calendar_domain.usecase.AddEventToHistoryUseCase
import petlink.android.feature_calendar_domain.usecase.DeleteCalendarEventUseCase
import petlink.android.feature_calendar_domain.usecase.GetCalendarEventsUseCase
import petlink.android.feature_calendar_domain.usecase.GetEventsByDateUseCase
import petlink.android.feature_calendar_domain.usecase.GetEventsFromMonthUseCase
import petlink.android.feature_calendar_domain.usecase.GetHistoryEventsUseCase
import petlink.android.feature_calendar_domain.usecase.UpdateCalendarEventUseCase
import petlink.android.feature_calendar_domain_impl.usecase.AddCalendarEventUseCaseImpl
import petlink.android.feature_calendar_domain_impl.usecase.AddEventToHistoryUseCaseImpl
import petlink.android.feature_calendar_domain_impl.usecase.DeleteCalendarEventUseCaseImpl
import petlink.android.feature_calendar_domain_impl.usecase.GetCalendarEventUseCaseImpl
import petlink.android.feature_calendar_domain_impl.usecase.GetEventsByDateUseCaseImpl
import petlink.android.feature_calendar_domain_impl.usecase.GetEventsFromMonthUseCaseImpl
import petlink.android.feature_calendar_domain_impl.usecase.GetHistoryEventUseCaseImpl
import petlink.android.feature_calendar_domain_impl.usecase.UpdateCalendarEventUseCaseImpl

@Module
interface CalendarDomainModule {

    @CalendarScope
    @Binds
    fun bindAddCalendarEventUseCase(addCalendarEventUseCaseImpl: AddCalendarEventUseCaseImpl): AddCalendarEventUseCase

    @CalendarScope
    @Binds
    fun bindAddEventToHistory(addEventToHistoryUseCaseImpl: AddEventToHistoryUseCaseImpl): AddEventToHistoryUseCase

    @CalendarScope
    @Binds
    fun bindDeleteCalendarEventUseCase(deleteCalendarEventUseCaseImpl: DeleteCalendarEventUseCaseImpl): DeleteCalendarEventUseCase

    @CalendarScope
    @Binds
    fun bindGetCalendarEventUseCase(getCalendarEventUseCaseImpl: GetCalendarEventUseCaseImpl): GetCalendarEventsUseCase

    @CalendarScope
    @Binds
    fun bindGetEventsByDateUseCase(getEventsByDateUseCaseImpl: GetEventsByDateUseCaseImpl): GetEventsByDateUseCase

    @CalendarScope
    @Binds
    fun bindGetEventsFromMonthUseCase(getEventsFromMonthUseCaseImpl: GetEventsFromMonthUseCaseImpl): GetEventsFromMonthUseCase

    @CalendarScope
    @Binds
    fun bindGetHistoryEventsUseCase(getHistoryEventUseCaseImpl: GetHistoryEventUseCaseImpl): GetHistoryEventsUseCase

    @CalendarScope
    @Binds
    fun bindUpdateCalendarEventUseCase(updateCalendarEventUseCaseImpl: UpdateCalendarEventUseCaseImpl): UpdateCalendarEventUseCase

}