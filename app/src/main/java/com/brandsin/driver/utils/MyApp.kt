package com.brandsin.driver.utils

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import timber.log.Timber


class MyApp : Application()
{
    companion object{
        private lateinit var mInstance: MyApp
        lateinit var context : Context
        fun getInstance(): MyApp {
            return mInstance
        }
    }

    private fun initTimber() {
        Timber.plant(object : Timber.DebugTree() {
            override fun createStackElementTag(element: StackTraceElement): String? {
                return super.createStackElementTag(element) + " line: " + element.lineNumber
            }
        })
    }

    fun getAppContext(): Context? {
        return context
    }

    override fun onCreate() {
        super.onCreate()
        mInstance = this

        AppCenter.start(
            this,
            "5aa53c48-72f4-41a9-998f-45ef3f9c055c",
            Analytics::class.java,
            Crashes::class.java
        )
        AppCenter.configure(this, "5aa53c48-72f4-41a9-998f-45ef3f9c055c")
        if (AppCenter.isConfigured()) {
            AppCenter.start(Analytics::class.java)
            AppCenter.start(Crashes::class.java)
        }
        if (!AppCenter.isConfigured()) {
            AppCenter.start(this, "\"5aa53c48-72f4-41a9-998f-45ef3f9c055c\"", Analytics::class.java, Crashes::class.java);
        }

//        DataBindingUtil.setDefaultComponent(AppDataBindingComponent())

        context = applicationContext

       /* startKoin {
            androidContext(this@MyApp)
            modules(
                listOf(
                    setAppointmentModule,
                    salonDetailsModule
                )
            )
        }*/

        initTimber()
    }

    protected override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}