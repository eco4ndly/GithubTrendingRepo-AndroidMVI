package com.eco4ndly.githubtrendingrepo.di.modules

import com.eco4ndly.githubtrendingrepo.BuildConfig
import com.eco4ndly.githubtrendingrepo.data.api.WebService
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * A Sayan Porya code on 12/05/20
 */

val BaseUrl = named("BaseUrl")

val networkModule = module {
  single { provideMoshi() }
  single(BaseUrl) { provideBaseUrl()}
  single { provideHttpLoggingInterceptor() }
  single { provideOkHttpClient(get()) }
  single { provideRetrofit(get(BaseUrl), get(), get()) }
  single { provideWebApi(get()) }
}

private fun provideOkHttpClient(logger: HttpLoggingInterceptor): OkHttpClient {
  return OkHttpClient.Builder()
    .addInterceptor(logger)
    .connectTimeout(15, TimeUnit.SECONDS)
    .readTimeout(20, TimeUnit.SECONDS)
    .writeTimeout(15, TimeUnit.SECONDS)
    .build()
}

private fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
  val interceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
      Timber.d(message)
    }
  })
  if (BuildConfig.DEBUG) {
    interceptor.level = HttpLoggingInterceptor.Level.BODY
  } else {
    interceptor.level = HttpLoggingInterceptor.Level.NONE
  }

  return interceptor
}

private fun provideRetrofit(baseUrl: String, okHttpClient: OkHttpClient, moshiFactory: Moshi): Retrofit {
  return Retrofit.Builder()
    .baseUrl(baseUrl)
    .addConverterFactory(MoshiConverterFactory.create(moshiFactory))
    .client(okHttpClient)
    .build()
}

private fun provideWebApi(retrofit: Retrofit): WebService {
  return retrofit.create(WebService::class.java)
}

private fun provideMoshi(): Moshi {
  return Moshi.Builder().build()
}

private fun provideBaseUrl() = "https://ghapi.huchen.dev/"