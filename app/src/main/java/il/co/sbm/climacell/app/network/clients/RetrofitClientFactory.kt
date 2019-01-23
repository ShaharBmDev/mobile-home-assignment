package il.co.sbm.climacell.app.network.clients

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory

object RetrofitClientFactory : IRetrofitClientFactory {

    override fun createClient(iOkHttpClient: OkHttpClient, iBaseUrl: String): Retrofit {
        return Retrofit
            .Builder()
            .client(iOkHttpClient)
            .baseUrl(iBaseUrl)
            .addConverterFactory(JacksonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}