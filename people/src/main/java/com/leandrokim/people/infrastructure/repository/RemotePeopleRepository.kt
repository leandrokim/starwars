package com.leandrokim.people.infrastructure.repository

import com.leandrokim.people.domain.model.Person
import com.leandrokim.people.domain.model.exception.PersonNotFoundException
import com.leandrokim.people.domain.repository.PeopleRepository
import com.leandrokim.people.infrastructure.service.PeopleService
import com.leandrokim.people.infrastructure.service.dto.PersonDTO

class RemotePeopleRepository(private val peopleService: PeopleService) : PeopleRepository {
    override suspend fun getPeoplePage(page: Int): List<Person> {
        val peopleDTO = peopleService.getPeopleByPage(page)

        return parseDTO(peopleDTO.results)
    }

    override suspend fun getPerson(name: String): Person {
        val peopleDTO = peopleService.getPeopleByName(name)
        if (peopleDTO.count < 1) throw PersonNotFoundException()

        val peopleList = parseDTO(peopleDTO.results)
        return peopleList[0]
    }

    private fun parseDTO(peopleDTO: List<PersonDTO>): List<Person> {
        return peopleDTO.map { dto -> dto.toPerson() }
    }
}