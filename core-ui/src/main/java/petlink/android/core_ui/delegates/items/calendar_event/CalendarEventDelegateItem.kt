package petlink.android.core_ui.delegates.items.calendar_event

import petlink.android.core_ui.delegates.main.DelegateItem

class CalendarEventDelegateItem(
    private val model: CalendarEventModel
): DelegateItem {
    override fun content(): Any = model

    override fun id(): Int = model.hashCode()

    override fun compareToOther(other: DelegateItem): Boolean =
        other.content() == content()
}