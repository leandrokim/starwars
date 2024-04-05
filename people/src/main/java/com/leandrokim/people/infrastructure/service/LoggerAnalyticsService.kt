package com.leandrokim.people.infrastructure.service

import com.leandrokim.people.domain.service.AnalyticsService

class LoggerAnalyticsService : AnalyticsService {
    override fun trackError(errorCode: Int, message: String) {
        print("ERROR: code $errorCode - error type: $message")
    }
}