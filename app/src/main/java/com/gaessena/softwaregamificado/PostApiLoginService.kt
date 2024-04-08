package com.gaessena.softwaregamificado

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface PostApiLoginService {
    @POST("login.php")
    suspend fun getUserLogin(
        @Body requestBody: RequestBody
    ): PostModel
}