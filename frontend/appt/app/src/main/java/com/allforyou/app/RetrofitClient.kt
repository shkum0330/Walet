package com.allforyou.app

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
class RetrofitClient{
   companion object {
       private const val BASE_URL = "http://43.201.195.182/"
       private var retrofit: Retrofit? = null

       fun getClient(): Retrofit {
           if (retrofit == null) {
               retrofit = Retrofit.Builder()
                   .baseUrl(BASE_URL)
                   .addConverterFactory(GsonConverterFactory.create())
                   .build()
           }
           return retrofit!!
       }
   }
}
