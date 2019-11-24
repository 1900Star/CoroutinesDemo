package com.yibao.croutinesdemo

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GankService {
    @GET("users/{user}/repos")
    fun getListRepos(@Path("user") user: String): Call<List<Repo>>

}