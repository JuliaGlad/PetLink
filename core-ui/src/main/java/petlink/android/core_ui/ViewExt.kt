package petlink.android.core_ui

import android.view.View

fun View.playPressAnimation() {
    animate()
        .scaleX(0.97f)
        .scaleY(0.97f)
        .setDuration(80)
        .withEndAction {
            animate()
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(80)
                .start()
        }
        .start()
}
