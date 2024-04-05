package com.leandrokim.people.di

import com.leandrokim.people.domain.action.GetPeople
import com.leandrokim.people.domain.action.GetPerson

internal object ActionFactory {
    fun createGetPeople(): GetPeople {
        return GetPeople(
            InfrastructureFactory.createPeopleRepository(),
            InfrastructureFactory.createAnalyticsService()
        )
    }

    fun createGetPerson(): GetPerson {
        return GetPerson(
            InfrastructureFactory.createPeopleRepository(),
            InfrastructureFactory.createAnalyticsService()
        )
    }
}