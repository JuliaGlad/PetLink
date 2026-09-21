package petlink.android.core_ui.photo_preview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import petlink.android.core_ui.R
import petlink.android.core_ui.databinding.DialogPhotoPreviewBinding

class PhotoPreviewDialog : DialogFragment() {

    private var _binding: DialogPhotoPreviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.PhotoPreviewDialog_PetLink)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogPhotoPreviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val uri = requireArguments().getString(URI_ARG).orEmpty()
        if (uri.isNotBlank()) {
            Glide.with(this)
                .load(uri.toUri())
                .fitCenter()
                .listener(object : RequestListener<android.graphics.drawable.Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<android.graphics.drawable.Drawable>,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.loader.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: android.graphics.drawable.Drawable,
                        model: Any,
                        target: Target<android.graphics.drawable.Drawable>?,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.loader.visibility = View.GONE
                        return false
                    }
                })
                .into(binding.photo)
        } else {
            binding.loader.visibility = View.GONE
        }
        binding.iconBack.setOnClickListener { dismiss() }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        const val TAG = "PhotoPreviewDialog"
        private const val URI_ARG = "PhotoPreviewUri"

        fun newInstance(uri: String) = PhotoPreviewDialog().apply {
            arguments = bundleOf(URI_ARG to uri)
        }
    }
}

fun Fragment.showPhotoPreview(uri: String) {
    if (uri.isBlank()) return
    PhotoPreviewDialog.newInstance(uri).show(childFragmentManager, PhotoPreviewDialog.TAG)
}
