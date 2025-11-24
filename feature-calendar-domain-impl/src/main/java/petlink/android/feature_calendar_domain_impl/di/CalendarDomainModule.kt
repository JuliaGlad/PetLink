package petlink.android.feature_calendar_domain_impl.di

import dagger.Binds
import dagger.Module
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

    @Binds
    fun bindAddCalendarEventUseCase(addCalendarEventUseCaseImpl: AddCalendarEventUseCaseImpl): AddCalendarEventUseCase

    @Binds
    fun bindAddEventToHistory(addEventToHistoryUseCaseImpl: AddEventToHistoryUseCaseImpl): AddEventToHistoryUseCase

    @Binds
    fun bindDeleteCalendarEventUseCase(deleteCalendarEventUseCaseImpl: DeleteCalendarEventUseCaseImpl): DeleteCalendarEventUseCase

    @Binds
    fun bindGetCalendarEventUseCase(getCalendarEventUseCaseImpl: GetCalendarEventUseCaseImpl): GetCalendarEventsUseCase

    @Binds
    fun bindGetEventsByDateUseCase(getEventsByDateUseCaseImpl: GetEventsByDateUseCaseImpl): GetEventsByDateUseCase

    @Binds
    fun bindGetEventsFromMonthUseCase(getEventsFromMonthUseCaseImpl: GetEventsFromMonthUseCaseImpl): GetEventsFromMonthUseCase

    @Binds
    fun bindGetHistoryEventsUseCase(getHistoryEventUseCaseImpl: GetHistoryEventUseCaseImpl): GetHistoryEventsUseCase

    @Binds
    fun bindUpdateCalendarEventUseCase(updateCalendarEventUseCaseImpl: UpdateCalendarEventUseCaseImpl): UpdateCalendarEventUseCase

}