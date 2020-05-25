package com.swkang.hwkmaytwentythree.base.di

import com.swkang.hwkmaytwentythree.base.helper.LocalFileLoadHelperImpl
import com.swkang.hwkmaytwentythree.base.helper.MessageHelperImpl
import com.swkang.model.base.helper.LocalFileLoadHelper
import com.swkang.model.base.helper.MessageHelper
import com.swkang.model.base.redux.*
import com.swkang.model.domain.common.MessageReducer
import com.swkang.model.domain.hwk.CoupleListReducer
import com.swkang.model.domain.hwk.HwkCoupleActionProcessor
import com.swkang.model.domain.hwk.InitializedState
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {
    single {
        AppState(mapOf())
    }
    single {
        AppStore(get(), AppReducer(get()))
    }
    single<Array<Middleware<AppState>>> {
        arrayOf(
            ActionProcessorMiddleWare(
                CombinedActionProcessors(
                    listOf(
                        HwkCoupleActionProcessor(get())
                    )
                )
            ),
            LoggerMiddleware()
        )
    }
}

/* NOT USE
val repositoriesModule = module {
    single {
        OkHttpClient.Builder()
            .connectTimeout(DEFAULT_TIMEOUT_SEC, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_TIMEOUT_SEC, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }
}
*/

val helpersModule = module {
    single<MessageHelper> { MessageHelperImpl(androidApplication()) }
    single<LocalFileLoadHelper> { LocalFileLoadHelperImpl(androidApplication()) }
}

val reducersModule = module {
    single<List<Reducer<*>>> {
        listOf(
            MessageReducer(),

            // domain reducers
            CoupleListReducer(InitializedState)
        )
    }
}

val viewModelsModules = module {
    /* fixme
    viewModel<BaseViewModel<SomeDomainState>> {
        // create instance of ViewModel
    }
    */
}
