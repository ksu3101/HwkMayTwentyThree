package com.swkang.hwkmaytwentythree.base

import androidx.multidex.MultiDexApplication
import com.swkang.hwkmaytwentythree.base.di.appModule
import com.swkang.hwkmaytwentythree.base.di.helpersModule
import com.swkang.hwkmaytwentythree.base.di.reducersModule
import com.swkang.hwkmaytwentythree.base.di.viewModelsModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class HwkApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@HwkApplication)
            modules(appModule)
            //repositoriesModule
            modules(helpersModule)
            modules(reducersModule)
            modules(viewModelsModules)
        }
    }

}