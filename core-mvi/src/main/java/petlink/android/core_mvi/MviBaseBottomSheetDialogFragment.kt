package petlink.android.core_mvi

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

abstract class MviBaseBottomSheetDialogFragment<
        PartialState : MviPartialState,
        Intent : MviIntent,
        State : MviState,
        Effect : MviEffect>(@LayoutRes layoutId: Int) :
    BottomSheetDialogFragment(layoutId) {

    protected abstract val store: MviStore<PartialState, Intent, State, Effect>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            store.uiState.collect(::render)
        }
        lifecycleScope.launch {
            store.effect.collect(::resolveEffect)
        }
    }

    protected abstract fun render(state: State)

    protected abstract fun resolveEffect(effect: Effect)
}