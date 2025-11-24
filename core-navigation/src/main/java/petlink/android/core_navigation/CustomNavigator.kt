package petlink.android.core_navigation

import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.Forward
import com.github.terrakok.cicerone.Replace
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.AppNavigator

class CustomNavigator(
    activity: AppCompatActivity,
    containerId: Int
) : AppNavigator(activity, containerId) {

    override fun applyCommand(command: Command) {
        when (command) {
            is Forward -> {
                if (command.screen is ActivityScreen) {
                    openActivity(command.screen as ActivityScreen)
                } else {
                    super.applyCommand(command)
                }
            }

            is Replace -> {
                if (command.screen is ActivityScreen) {
                    openActivity(command.screen as ActivityScreen)
                } else {
                    super.applyCommand(command)
                }
            }

            else -> super.applyCommand(command)
        }
    }


    private fun openActivity(screen: ActivityScreen) {
        val intent = screen.createIntent(activity).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        activity.startActivity(intent)
    }
}