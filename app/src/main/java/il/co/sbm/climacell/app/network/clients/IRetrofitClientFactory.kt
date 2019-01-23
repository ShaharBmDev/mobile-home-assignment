package il.co.sbm.climacell.app.network.clients

import okhttp3.OkHttpClient
import retrofit2.Retrofit

interface IRetrofitClientFactory {
    fun createClient(iOkHttpClient: OkHttpClient, iBaseUrl: String): Retrofit
}