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
                hint = model.hint
                inputType = model.inputType
                isEnabled = model.editable
                setText(model.defaultValue)
                textWatcher = model.textChangedListener?.let {
                    addTextChangedListener(onTextChanged = { char, p0, p1, p2 ->
                        model.textChangedListener(char.toString())
                        if (!model.canBeEmpty && char?.isEmpty() == true){
                            binding.textInputLayout.error = model.error
                        }
                    })
                }
                if (model.endIconMode == TextInputLayout.END_ICON_PASSWORD_TOGGLE) {
                    transformationMethod = PasswordTransformationMethod.getInstance()
                }
            }
            binding.textInputLayout.endIconMode = model.endIconMode
        }
    }
}