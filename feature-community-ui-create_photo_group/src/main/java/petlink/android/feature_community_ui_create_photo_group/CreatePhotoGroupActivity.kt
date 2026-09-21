package petlink.android.feature_community_ui_create_photo_group

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class CreatePhotoGroupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse("app://community/create")).apply {
                putExtra(COMMUNITY_TYPE_ARG, PHOTOS)
                flags = Intent.FLAG_ACTIVITY_FORWARD_RESULT
            }
        )
        finish()
    }

    companion object {
        private const val COMMUNITY_TYPE_ARG = "CommunityTypeArg"
        private const val PHOTOS = "photos"
    }
}
