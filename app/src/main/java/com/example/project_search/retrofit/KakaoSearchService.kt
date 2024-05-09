package com.example.project_search.retrofit

import com.example.project_search.data.ImageResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoSearchService {
    @GET("image")
    suspend fun searchImage(
        @Header("Authorization") apiKey: String = "KakaoAK 8664d1d2036b2777bbdef8e3e01b19c4",
        @Query("query") query: String
    ): ImageResponse

}
