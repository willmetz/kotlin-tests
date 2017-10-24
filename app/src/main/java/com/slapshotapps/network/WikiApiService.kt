package com.slapshotapps.network

import com.slapshotapps.network.response.RelatedPagesModel
import com.slapshotapps.network.response.SearchResultModel
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface WikiApiService {

    companion object {
        fun create(): WikiApiService {

            val interceptor = HttpLoggingInterceptor();

            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            val httpClient = OkHttpClient.Builder().addInterceptor(interceptor).build();

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .baseUrl("https://en.wikipedia.org/w/")
                .build()

            return retrofit.create(WikiApiService::class.java)
        }
    }

    @GET("api.php")
    fun hitCountCheck(@Query("action") action: String, @Query("format") format: String,
        @Query("list") list: String, @Query(
            "srsearch") searchString: String): Observable<SearchResultModel.Result>;

    @GET("page/related/{term}")
    fun relatedPages(@Path("term") term: String) : Observable<List<RelatedPagesModel>>;
}