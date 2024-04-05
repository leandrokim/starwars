package com.leandrokim.people.infrastructure.repository

import com.leandrokim.people.domain.model.Person
import com.leandrokim.people.domain.model.exception.InvalidIndexException
import com.leandrokim.people.domain.repository.PeopleRepository

class CachePeopleRepository(private val remotePeopleRepository: PeopleRepository) :
    PeopleRepository {

    private val cache: MutableList<List<Person>> = mutableListOf()

    override suspend fun getPeoplePage(page: Int): List<Person> {
        if (page < 1) throw InvalidIndexException()
        return if (cache.size >= page) cache[page - 1]
        else {
            val newPage = remotePeopleRepository.getPeoplePage(page)
            cache.add(newPage)
            newPage
        }
    }

    override suspend fun getPerson(name: String): Person {
        cache.forEach { peoplePage: List<Person> ->
            val personWithName = peoplePage.find { person: Person -> person.name == name }
            if (personWithName != null) return personWithName
        }
        return remotePeopleRepository.getPerson(name)
    }
}