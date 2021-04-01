package mehmetbalbay.spaceApp.di

import mehmetbalbay.spaceApp.data.network.EndPoint
import mehmetbalbay.spaceApp.data.network.RequestInterceptor
import mehmetbalbay.spaceApp.data.network.client.SpaceStationClient
import mehmetbalbay.spaceApp.data.network.service.SpaceStationService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(RequestInterceptor(context = get()))
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .build()
    }

    single {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .baseUrl(EndPoint.BASE_URL)
            .build()
    }

    single { get<Retrofit>().create(SpaceStationService::class.java) }

    single { SpaceStationClient(service = get()) }
}