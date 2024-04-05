package com.leandrokim.people.infrastructure.service

import com.leandrokim.people.infrastructure.service.dto.PeopleDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface PeopleService {
    @GET("/api/people/")
    suspend fun getPeopleByPage(
        @Query("page") page: Int
    ) : PeopleDTO

    @GET("/api/people/")
    suspend fun getPeopleByName(
        @Query("search") name: String
    ) : PeopleDTO
}