package com.leandrokim.people.domain.action

import com.leandrokim.people.domain.model.Person
import com.leandrokim.people.domain.model.PersonDataset
import com.leandrokim.people.domain.model.exception.PersonNotFoundException
import com.leandrokim.people.domain.repository.PeopleRepository
import com.leandrokim.people.domain.service.AnalyticsService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class GetPersonShould {

    private val repository: PeopleRepository = mockk(relaxed = true)
    private val analytics: AnalyticsService = mockk(relaxed = true)

    private lateinit var action: GetPerson
    private var error: Exception? = null

    @Before
    fun init() {
        coEvery { repository.getPerson("invalid") }.throws(PersonNotFoundException())
        coEvery { repository.getPerson(LUKE) }.returns(PersonDataset.luke)
    }

    @Test
    fun `get person finds and return person by name`() = runTest {
        givenAction()

        val person = whenSearchingForPerson(LUKE)

        Assert.assertEquals(PersonDataset.luke, person)
    }

    @Test
    fun `person not found exception with invalid name`() = runTest {
        givenAction()

        whenSearchingForPerson("invalid")

        coVerify { analytics.trackError(5002, "PersonNotFoundException") }
        assert(error is PersonNotFoundException)
    }

    private fun givenAction() {
        action = GetPerson(repository, analytics)
    }

    private suspend fun whenSearchingForPerson(name: String): Person? {
        return try {
            action.invoke(name)
        } catch (e: Exception) {
            error = e
            null
        }
    }

    private companion object {
        const val LUKE = "Luke Skywalker"
    }
}