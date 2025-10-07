package petlink.android.petlink.ui.calendar.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import petlink.android.petlink.R
import kotlin.random.Random

class CalendarEventWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {
    override fun doWork(): Result {
        val title = inputData.getString(EVENT_TITLE_WORK_ARG)
        val date = inputData.getString(EVENT_DATE_WORK_ARG)

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
            .setContentTitle(title)
            .setContentText(date)
            .setSmallIcon(R.drawable.ic_notification)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(Random.nextInt(), notification)

        return Result.success()
    }

    companion object{

        const val CHANNEL_ID = "event_channel_id"
        const val EVENT_TITLE_WORK_ARG = "EventTitleWorkArg"
        const val EVENT_DATE_WORK_ARG = "EventDateWorkArg"
    }

}