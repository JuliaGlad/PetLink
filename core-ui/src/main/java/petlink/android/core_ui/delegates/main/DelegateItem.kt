package petlink.android.core_ui.delegates.main

interface DelegateItem {
    fun content(): Any

    fun id(): Int

    fun compareToOther(other: DelegateItem): Boolean
}
