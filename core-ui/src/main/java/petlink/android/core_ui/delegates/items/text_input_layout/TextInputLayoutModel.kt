package petlink.android.core_ui.delegates.items.text_input_layout

import android.text.InputType
import com.google.android.material.textfield.TextInputLayout
import kotlin.random.Random

data class TextInputLayoutModel(
    val id: Int = Random.nextInt(),
    val hint: String,
    val inputType: Int = InputType.TYPE_CLASS_TEXT,
    val endIconMode: Int = TextInputLayout.END_ICON_NONE,
    var defaultValue: String = "",
    var error: String = "",
    val canBeEmpty: Boolean = true,
    val editable: Boolean = true,
    val textChangedListener: ((String) -> Unit)? = null
)