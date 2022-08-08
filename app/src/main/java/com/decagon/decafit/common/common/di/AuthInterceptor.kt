package com.decagon.decafit.common.common.di

import android.content.Context
import com.decagon.decafit.common.common.data.preferences.Preference.getHeader
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val header = getHeader("Header_KEY")
        return if (header == null){
            val request = chain.request().newBuilder()
                .build()
            chain.proceed(request)
        }else{
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $header" )
                .build()
            chain.proceed(request)
        }
    }
}