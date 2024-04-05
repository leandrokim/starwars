package com.leandrokim.people.domain.action

import com.leandrokim.people.domain.model.Person
import com.leandrokim.people.domain.model.PersonDataset
import com.leandrokim.people.domain.model.exception.InvalidIndexException
import com.leandrokim.people.domain.repository.PeopleRepository
import com.leandrokim.people.domain.service.AnalyticsService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetPeopleShould {

    private val repository: PeopleRepository = mockk(relaxed = true)
    private val analytics: AnalyticsService = mockk(relaxed = true)
    private lateinit var action: GetPeople

    @Before
    fun init() {
        coEvery { repository.getPeoplePage(0) }.throws(InvalidIndexException())
        coEvery { repository.getPeoplePage(1) }.returns(listOf(PersonDataset.luke))
        coEvery { repository.getPeoplePage(2) }.returns(emptyList())
    }

    @Test
    fun `page 0 doesn't exist`() = runTest {
        givenAction()

        val list = whenAskingForPeopleInPage(0)

        Assert.assertEquals(0, list.size)
    }

    @Test
    fun `page 0 doesn't exist so tracks error`() = runTest {
        givenAction()

        whenAskingForPeopleInPage(0)

        coVerify { analytics.trackError(5000, "InvalidIndexException") }
    }

    @Test
    fun `report unexpected error`() = runTest {
        val peopleRepository: PeopleRepository = mockk(relaxed = true)
        coEvery { peopleRepository.getPeoplePage(1) }.throws(Exception())
        val getPeopleAction = GetPeople(peopleRepository, analytics)

        getPeopleAction(1)

        coVerify { analytics.trackError(5001, "UnexpectedGetPeopleException") }
    }

    @Test
    fun `returns page 1`() = runTest {
        givenAction()

        val list = whenAskingForPeopleInPage(1)

        Assert.assertEquals(1, list.size)
    }

    @Test
    fun `returns empty list if ended`() = runTest {
        givenAction()

        val list = whenAskingForPeopleInPage(2)

        Assert.assertEquals(0, list.size)
    }

    private fun givenAction() {
        action = GetPeople(repository, analytics)
    }

    private suspend fun whenAskingForPeopleInPage(page: Int): List<Person> {
        return action.invoke(page)
    }
}
