package petlink.android.core_ui.image_picker

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.github.dhaval2404.imagepicker.ImagePicker
import petlink.android.core_ui.R

class ImagePickerHelper(
    private val fragment: Fragment? = null,
    private val activity: Activity? = null
) {

    init {
        require(fragment != null || activity != null) {
            "ImagePickerHelper needs Fragment or Activity"
        }
    }

    private var pending: Pending? = null

    private val permissionLauncher = registerPermissionsLauncher()
    private val pickLauncher = registerPickLauncher()

    private val hostContext: Context
        get() = fragment?.requireContext() ?: activity!!

    fun hasPermissions(): Boolean = ImagePermissions.areGranted(hostContext)

    fun ensurePermissions(
        onDenied: (() -> Unit)? = null,
        onGranted: () -> Unit
    ) {
        if (hasPermissions()) {
            onGranted()
            return
        }
        pending = Pending.PermissionOnly(onGranted, onDenied)
        permissionLauncher.launch(ImagePermissions.missing(hostContext).toTypedArray())
    }

    fun pick(
        cropWidth: Float? = null,
        cropHeight: Float? = null,
        compressKb: Int = 512,
        maxWidth: Int = 512,
        maxHeight: Int = 512,
        onResult: (Uri) -> Unit
    ) {
        pending = Pending.Pick(
            cropWidth = cropWidth,
            cropHeight = cropHeight,
            compressKb = compressKb,
            maxWidth = maxWidth,
            maxHeight = maxHeight,
            onResult = onResult
        )
        launchPicker(pending as Pending.Pick)
    }

    private fun proceedGranted() {
        val action = pending as? Pending.PermissionOnly ?: return
        pending = null
        action.onGranted()
    }

    private fun handleDenied() {
        val onDenied = (pending as? Pending.PermissionOnly)?.onDenied
        pending = null
        if (onDenied != null) {
            onDenied()
        } else {
            Toast.makeText(
                hostContext,
                hostContext.getString(R.string.image_permission_denied),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun launchPicker(action: Pending.Pick) {
        val builder = fragment?.let { ImagePicker.with(it) } ?: ImagePicker.with(activity!!)
        val cropWidth = action.cropWidth
        val cropHeight = action.cropHeight
        if (cropWidth != null && cropHeight != null) {
            builder.crop(cropWidth, cropHeight)
        }
        builder
            .compress(action.compressKb)
            .maxResultSize(action.maxWidth, action.maxHeight)
            .createIntent { intent: Intent -> pickLauncher.launch(intent) }
    }

    private fun registerPermissionsLauncher() =
        if (fragment != null) {
            fragment.registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions(),
                ::onPermissionResult
            )
        } else {
            (activity as ComponentActivity).registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions(),
                ::onPermissionResult
            )
        }

    private fun registerPickLauncher() =
        if (fragment != null) {
            fragment.registerForActivityResult(
                ActivityResultContracts.StartActivityForResult(),
                ::onPickResult
            )
        } else {
            (activity as ComponentActivity).registerForActivityResult(
                ActivityResultContracts.StartActivityForResult(),
                ::onPickResult
            )
        }

    private fun onPermissionResult(grants: Map<String, Boolean>) {
        val granted = hasPermissions() || (grants.isNotEmpty() && grants.values.all { it })
        if (granted) proceedGranted() else handleDenied()
    }

    private fun onPickResult(result: ActivityResult) {
        val action = pending as? Pending.Pick
        pending = null
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri -> action?.onResult?.invoke(uri) }
        }
    }

    private sealed class Pending {
        class Pick(
            val cropWidth: Float?,
            val cropHeight: Float?,
            val compressKb: Int,
            val maxWidth: Int,
            val maxHeight: Int,
            val onResult: (Uri) -> Unit
        ) : Pending()

        class PermissionOnly(
            val onGranted: () -> Unit,
            val onDenied: (() -> Unit)?
        ) : Pending()
    }
}
