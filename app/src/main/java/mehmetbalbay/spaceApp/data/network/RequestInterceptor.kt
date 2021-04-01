package mehmetbalbay.spaceApp.data.network

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor(val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url
        val url = originalUrl.newBuilder()
            // Global Request Query Parameters Add
            .build()

        val requestBuilder = originalRequest.newBuilder().url(url)
        val newRequest = requestBuilder
            // Global Request Header Parameters Add
            .build()

        return chain.proceed(newRequest)
    }
}