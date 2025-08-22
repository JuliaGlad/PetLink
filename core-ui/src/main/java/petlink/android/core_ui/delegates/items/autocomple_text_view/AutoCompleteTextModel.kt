package petlink.android.core_ui.delegates.items.autocomple_text_view

import android.text.TextWatcher
import kotlin.random.Random

data class AutoCompleteTextModel(
    val id: Int = Random.nextInt(),
    val hint: String,
    val values: List<String>,
    val defaultValue: String = "",
    val textChangedListener: (CharSequence?, Int, Int, Int) -> Unit
)
