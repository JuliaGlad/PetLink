package petlink.android.petlink.ui.profile.edit

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract
import com.github.dhaval2404.imagepicker.ImagePicker

data class PickImageResult(
    val avatarId: Int,
    val uri: Uri?
)

class ImagePickerActivityResultContractWithId :
    ActivityResultContract<Int, PickImageResult>() {

    override fun createIntent(
        context: Context,
        input: Int
    ): Intent {
        var generatedIntent: Intent? = null

        ImagePicker.with(context as Activity)
            .crop()
            .compress(512)
            .maxResultSize(512, 512)
            .createIntent { intent ->
                intent.putExtra(AVATAR_ID, intent)
                generatedIntent = intent
            }

        return generatedIntent ?: Intent()
    }

    override fun parseResult(
        resultCode: Int,
        intent: Intent?
    ): PickImageResult {
        val avatarId = intent?.getIntExtra(AVATAR_ID, -1) ?: EMPTY
        val uri = if (resultCode == Activity.RESULT_OK) intent?.data else null
        return PickImageResult(avatarId, uri)
    }

    companion object {
        const val AVATAR_ID = "AvatarIdExtra"
        const val EMPTY = -1
    }

}