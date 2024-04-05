package com.leandrokim.people.di

import com.leandrokim.people.domain.repository.PeopleRepository
import com.leandrokim.people.domain.service.AnalyticsService
import com.leandrokim.people.infrastructure.repository.CachePeopleRepository
import com.leandrokim.people.infrastructure.repository.RemotePeopleRepository
import com.leandrokim.people.infrastructure.service.ApiService
import com.leandrokim.people.infrastructure.service.LoggerAnalyticsService

internal object InfrastructureFactory {
    fun createPeopleRepository(): PeopleRepository {
        return peopleCacheInstance
    }

    fun createAnalyticsService(): AnalyticsService {
        return LoggerAnalyticsService()
    }

    private val peopleCacheInstance : PeopleRepository = createCachePeopleRepository()

    private fun createCachePeopleRepository(): PeopleRepository {
        return CachePeopleRepository(RemotePeopleRepository(ApiService.get()))
    }
}