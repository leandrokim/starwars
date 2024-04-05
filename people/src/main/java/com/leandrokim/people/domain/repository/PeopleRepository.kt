package com.leandrokim.people.domain.repository

import com.leandrokim.people.domain.model.Person

interface PeopleRepository {
    suspend fun getPeoplePage(page: Int): List<Person>
    suspend fun getPerson(name: String): Person
}