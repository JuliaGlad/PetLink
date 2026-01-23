package petlink.android.feature_calendar_ui_add_event.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import petlink.android.core_di.app.AppComponentHolder
import petlink.android.core_di.calendar.component.DaggerCalendarComponent
import petlink.android.core_di.profile.component.DaggerProfileComponent
import petlink.android.feature_calendar_domain.usecase.AddEventToHistoryUseCase
import petlink.android.feature_calendar_ui_add_event.R
import petlink.android.feature_calendar_ui_add_event.worker.di.DaggerCalendarEventComponent
import javax.inject.Inject
import kotlin.random.Random

class CalendarEventWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    @Inject
    lateinit var addEventToHistoryUseCase: AddEventToHistoryUseCase

    init {
        val profileComponent = DaggerProfileComponent.factory().create(AppComponentHolder.appComponent)
        val calendarComponent = DaggerCalendarComponent.factory().create(AppComponentHolder.appComponent)
        DaggerCalendarEventComponent.factory()
            .create(profileComponent, calendarComponent)
            .inject(this)
    }

    override suspend fun doWork(): Result {
        createNotification()
        addCalendarEventToHistory()
        return Result.success()
    }

    private suspend fun addCalendarEventToHistory() {
        val eventId = inputData.getString(EVENT_ID_WORK_ARG)
        eventId?.let { id ->
            addEventToHistoryUseCase.invoke(id)
        }
    }

    private fun createNotification() {
        val title = inputData.getString(EVENT_TITLE_WORK_ARG)
        val date = inputData.getString(EVENT_DATE_WORK_ARG)
        val time = inputData.getString(EVENT_TIME_WORK_ARG)

        val remoteView: RemoteViews = initRemoteView(title, "$date $time")

        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channelId = CHANNEL_ID
        val channel = NotificationChannel(
            channelId,
            CHANNEL_ID,
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setCustomContentView(remoteView)
            .setSmallIcon(petlink.android.core_ui.R.drawable.ic_notification)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(Random.nextInt(), notification)
    }

    private fun initRemoteView(
        title: String?,
        date: String?
    ): RemoteViews = RemoteViews(applicationContext.packageName, R.layout.layout_calendar_event_notification).apply {
        setTextViewText(R.id.event_title, title)
        setTextViewText(R.id.event_time, date)
    }

    companion object{
        const val CHANNEL_ID = "event_channel_id"
        const val EVENT_ID_WORK_ARG = "EventIdWorkArg"
        const val EVENT_TITLE_WORK_ARG = "EventTitleWorkArg"
        const val EVENT_DATE_WORK_ARG = "EventDateWorkArg"
        const val EVENT_TIME_WORK_ARG = "EventTimeWorkArg"
    }

}