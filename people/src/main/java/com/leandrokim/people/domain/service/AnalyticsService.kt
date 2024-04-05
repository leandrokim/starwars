package com.leandrokim.people.domain.service

interface AnalyticsService {
    fun trackError(errorCode: Int, message: String)
}