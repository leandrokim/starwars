package com.leandrokim.people.domain.action

import com.leandrokim.people.domain.model.Person
import com.leandrokim.people.domain.model.exception.InvalidIndexException
import com.leandrokim.people.domain.repository.PeopleRepository
import com.leandrokim.people.domain.service.AnalyticsService

class GetPeople(
    private val repository: PeopleRepository,
    private val analyticsService: AnalyticsService
) {

    suspend operator fun invoke(page: Int = 1): List<Person> {
        return try {
            repository.getPeoplePage(page)
        } catch (indexException: InvalidIndexException) {
            analyticsService.trackError(InvalidIndexException.errorCode, "InvalidIndexException")
            emptyList()
        } catch (exception: Exception) {
            analyticsService.trackError(5001, "UnexpectedGetPeopleException")
            emptyList()
        }
    }
}