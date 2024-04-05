package com.leandrokim.people.domain.action

import com.leandrokim.people.domain.model.Person
import com.leandrokim.people.domain.model.exception.PersonNotFoundException
import com.leandrokim.people.domain.repository.PeopleRepository
import com.leandrokim.people.domain.service.AnalyticsService

class GetPerson(
    private val repository: PeopleRepository,
    private val analyticsService: AnalyticsService
) {
    suspend operator fun invoke(name: String): Person {
        return try {
            repository.getPerson(name)
        }  catch (exception: Exception) {
            analyticsService.trackError(PersonNotFoundException.errorCode, "PersonNotFoundException")
            throw exception
        }
    }
}