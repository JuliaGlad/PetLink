package petlink.android.core_di.calendar.component

import com.github.terrakok.cicerone.Router
import dagger.Component
import petlink.android.core_di.app.AppComponent
import petlink.android.core_di.calendar.modules.CalendarDataModule
import petlink.android.core_di.calendar.modules.CalendarDatabaseModule
import petlink.android.core_di.calendar.modules.CalendarDomainModule
import petlink.android.feature_calendar_domain.usecase.AddCalendarEventUseCase
import petlink.android.feature_calendar_domain.usecase.AddEventToHistoryUseCase
import petlink.android.feature_calendar_domain.usecase.DeleteCalendarEventUseCase
import petlink.android.feature_calendar_domain.usecase.GetCalendarEventsUseCase
import petlink.android.feature_calendar_domain.usecase.GetEventsByDateUseCase
import petlink.android.feature_calendar_domain.usecase.GetEventsFromMonthUseCase
import petlink.android.feature_calendar_domain.usecase.GetHistoryEventsUseCase
import petlink.android.feature_calendar_domain.usecase.UpdateCalendarEventUseCase
import javax.inject.Scope

@CalendarScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        CalendarDatabaseModule::class,
        CalendarDataModule::class,
        CalendarDomainModule::class
    ]
)
interface CalendarComponent {

    @Component.Factory
    interface Factory{
        fun create(appComponent: AppComponent): CalendarComponent
    }
    fun router(): Router

    fun aAddCalendarEventUseCase(): AddCalendarEventUseCase

    fun addEventToHistory(): AddEventToHistoryUseCase

    fun deleteCalendarEventUseCase(): DeleteCalendarEventUseCase

    fun getCalendarEventUseCase(): GetCalendarEventsUseCase

    fun getEventsByDateUseCase(): GetEventsByDateUseCase

    fun getEventsFromMonthUseCase(): GetEventsFromMonthUseCase

    fun getHistoryEventsUseCase(): GetHistoryEventsUseCase

    fun updateCalendarEventUseCase(): UpdateCalendarEventUseCase
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class CalendarScope