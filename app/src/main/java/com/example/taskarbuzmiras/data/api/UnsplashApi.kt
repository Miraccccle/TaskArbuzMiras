package com.example.taskarbuzmiras.data.api

import com.example.taskarbuzmiras.domain.models.UnsplashPhoto
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashApi {
    @GET("photos")
    suspend fun getPhotos(
        @Query("client_id") clientId: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
   ) :List<UnsplashPhoto>
}