package com.leandrokim.people.infrastructure.repository

import com.leandrokim.people.domain.model.Person
import com.leandrokim.people.domain.model.PersonDataset
import com.leandrokim.people.domain.model.exception.InvalidIndexException
import com.leandrokim.people.domain.repository.PeopleRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CachePeopleRepositoryShould {

    private val remoteRepository: PeopleRepository = mockk(relaxed = true)
    private lateinit var repository: CachePeopleRepository
    private var error: Exception? = null

    @Before
    fun init() {
        coEvery { remoteRepository.getPeoplePage(0) }.throws(InvalidIndexException())
        coEvery { remoteRepository.getPeoplePage(1) }.returns(listOf(PersonDataset.luke))
        coEvery { remoteRepository.getPeoplePage(2) }.returns(emptyList())
    }

    @Test
    fun `page 0 doesn't exist`() = runTest {
        givenRepository()

        whenAskingForPeopleInPage(0)

        Assert.assertEquals(true, error is InvalidIndexException)
    }

    @Test
    fun `doesn't ask page 1 twice`() = runTest {
        givenRepository()

        whenAskingForPeopleInPage(1)
        whenAskingForPeopleInPage(1)

        coVerify(exactly = 1) { remoteRepository.getPeoplePage(1) }
    }

    private fun givenRepository() {
        repository = CachePeopleRepository(remoteRepository)
    }

    private suspend fun whenAskingForPeopleInPage(page: Int): List<Person> {
        return try {
            repository.getPeoplePage(page)
        } catch (e: Exception) {
            error = e
            emptyList()
        }
    }
}