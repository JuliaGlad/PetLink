package petlink.android.core_ui.delegates.items.text_input_layout

import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import petlink.android.core_ui.databinding.DelegateTextInputLayoutBinding
import petlink.android.core_ui.delegates.main.AdapterDelegate
import petlink.android.core_ui.delegates.main.DelegateItem

class TextInputLayoutDelegate : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            DelegateTextInputLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: DelegateItem,
        position: Int
    ) {
        (holder as ViewHolder).bind(item.content() as TextInputLayoutModel)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is TextInputLayoutDelegateItem

    class ViewHolder(private val binding: DelegateTextInputLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var textWatcher: TextWatcher? = null
        fun bind(model: TextInputLayoutModel) {
            with(binding.editText) {
                textWatcher?.let { removeTextChangedListener(it) }
                binding.textInputLayout.hint = model.hint
                inputType = model.inputType
                isEnabled = model.editable
                setText(model.defaultValue)
                hint = model.hint
                binding.textInputLayout.error = null
                textWatcher = model.textChangedListener?.let { listener ->
                    addTextChangedListener(onTextChanged = { char, _, _, _ ->
                        listener(char.toString())
                    })
                }
                setOnFocusChangeListener { _, hasFocus ->
                    binding.textInputLayout.error = if (hasFocus) {
                        null
                    } else {
                        validateError(text.toString(), model)
                    }
                }
                if (model.endIconMode == TextInputLayout.END_ICON_PASSWORD_TOGGLE) {
                    transformationMethod = PasswordTransformationMethod.getInstance()
                }
            }
            binding.textInputLayout.endIconMode = model.endIconMode
        }

        private fun validateError(text: String, model: TextInputLayoutModel): String? =
            if (text.isEmpty() && !model.canBeEmpty) model.error else null
    }
}