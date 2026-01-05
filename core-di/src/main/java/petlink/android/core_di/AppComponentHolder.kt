package petlink.android.core_di

object AppComponentHolder {

    lateinit var appComponent: AppComponent
        private set

    fun init(component: AppComponent){
        if (::appComponent.isInitialized) return
        appComponent = component
    }

}